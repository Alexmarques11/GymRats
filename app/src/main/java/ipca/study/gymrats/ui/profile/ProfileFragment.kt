package ipca.study.gymrats.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import ipca.study.gymrats.R

class ProfileFragment : Fragment() {

    private lateinit var editButton: ImageButton
    private lateinit var nameEditText: EditText
    private lateinit var ageEditText: EditText
    private lateinit var heightEditText: EditText
    private lateinit var weightEditText: EditText
    private lateinit var genderEditText: EditText
    private lateinit var emailEditText: EditText

    private var isEditMode = false
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_profile, container, false)

        editButton = rootView.findViewById(R.id.imageButtonEdit)
        nameEditText = rootView.findViewById(R.id.editTextName)
        ageEditText = rootView.findViewById(R.id.editTextPutAge)
        heightEditText = rootView.findViewById(R.id.editTextHeight)
        weightEditText = rootView.findViewById(R.id.editTextWeight)
        genderEditText = rootView.findViewById(R.id.editTextGender)
        emailEditText = rootView.findViewById(R.id.editTextEmailProfile)

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        // Carrega os dados do Firebase Firestore quando a tela é criada
        loadUserData(currentUser?.email)

        editButton.setOnClickListener {
            if (isEditMode) {
                saveData(currentUser?.email)
            } else {
                toggleEditMode()
            }
        }

        return rootView
    }

    private fun toggleEditMode() {
        isEditMode = !isEditMode
        updateView()
    }

    private fun updateView() {
        if (isEditMode) {
            enableEditing()
            editButton.setImageResource(R.drawable.baseline_save_24)
        } else {
            disableEditing()
            editButton.setImageResource(R.drawable.baseline_brush_24)
        }
    }

    private fun enableEditing() {
        nameEditText.isEnabled = true
        ageEditText.isEnabled = true
        heightEditText.isEnabled = true
        weightEditText.isEnabled = true
        genderEditText.isEnabled = true
        emailEditText.isEnabled = false // O email não deve ser editável
    }

    private fun disableEditing() {
        nameEditText.isEnabled = false
        ageEditText.isEnabled = false
        heightEditText.isEnabled = false
        weightEditText.isEnabled = false
        genderEditText.isEnabled = false
        emailEditText.isEnabled = false
    }

    private fun saveData(email: String?) {
        // Verifica se o email no perfil é o mesmo do registro
        if (emailEditText.text.toString() != email) {
            Toast.makeText(requireContext(), "Email no perfil deve ser o mesmo do registro", Toast.LENGTH_SHORT).show()
            return
        }

        val name = nameEditText.text.toString()
        val age = ageEditText.text.toString()
        val height = heightEditText.text.toString()
        val weight = weightEditText.text.toString()
        val gender = genderEditText.text.toString()

        val db = FirebaseFirestore.getInstance()
        val usersCollection = db.collection("users")

        val userData = hashMapOf(
            "name" to name,
            "age" to age,
            "height" to height,
            "weight" to weight,
            "gender" to gender,
            "email" to email
        )

        usersCollection.document(email ?: "").set(userData)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Dados salvos com sucesso", Toast.LENGTH_SHORT).show()
                isEditMode = false
                updateView()
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Falha ao salvar dados: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun loadUserData(email: String?) {
        val db = FirebaseFirestore.getInstance()
        val usersCollection = db.collection("users")

        usersCollection.document(email ?: "").get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val userData = document.data
                    nameEditText.setText(userData?.get("name").toString())
                    ageEditText.setText(userData?.get("age").toString())
                    heightEditText.setText(userData?.get("height").toString())
                    weightEditText.setText(userData?.get("weight").toString())
                    genderEditText.setText(userData?.get("gender").toString())
                    emailEditText.setText(userData?.get("email").toString())
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Falha ao carregar dados: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
