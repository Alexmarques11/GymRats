package ipca.study.gymrats.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import ipca.study.gymrats.R

class AddPlanosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_planos)

        val trainingType = findViewById<EditText>(R.id.editTextTrainingType)
        val date = findViewById<EditText>(R.id.editTextDate)

        findViewById<Button>(R.id.buttonDone)?.setOnClickListener {
            val intent = Intent()
            intent.putExtra("trainingType", trainingType.text.toString())
            intent.putExtra("date", date.text.toString())
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}
