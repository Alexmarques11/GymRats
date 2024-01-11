package ipca.study.gymrats.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import ipca.study.gymrats.R
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var greetingTextView: TextView

    private val motivationalMessages = listOf(
        "Acredite em si mesmo e todo o resto virá naturalmente.",
        "O único lugar onde o sucesso vem antes do trabalho é no dicionário.",
        "Nunca é tarde demais para ser o que você poderia ter sido.",
        "Treine muito, mas muito mesmo, e quando estiver bem cansado, treine mais um pouquinho, porque esse pouquinho vai te fazer melhor.",
        "Até hoje, para cada não que recebo, vou atrás de um sim!",
        "Eu não sou o melhor não, mas sou capaz de fazer coisas que muitas pessoas não acreditam.",
        "Não tenha medo de falhar. Esse é o caminho para o sucesso.",
        "Confie em mim, a recompensa não é tão grande sem a luta.",
        "Aquele que não é corajoso o suficiente para correr riscos não conseguirá nada na vida.",
        "Não são as montanhas à frente que te deixam esgotado, mas sim as pedras no sapato.",
        "Habilidade só se desenvolve com horas e horas de trabalho.",
        "Quem disse ‘Não interessa ganhar ou perder’ provavelmente perdeu.",
        "Vencer é algo que se constrói a cada treino e a cada vez que sonho.",
        "Se você vai olhar para trás, que seja para ver o que você trabalhou para chegar onde está.",
        "Os bons reclamam, os melhores se adaptam.",
        "Você não pode colocar um limite em nada. Quanto mais você sonha, mais longe você chega."
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        greetingTextView = view.findViewById(R.id.greetingTextView)

        setGreeting()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val motivationalMessageTextView: TextView = view.findViewById(R.id.motivationalMessageTextView)
        motivationalMessageTextView.text = getRandomMotivationalMessage()
    }

    private fun setGreeting() {
        val calendar = Calendar.getInstance()
        val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)

        val greeting = when (hourOfDay) {
            in 0..11 -> "Bom Dia! É de manhã que começa o dia!"
            in 12..17 -> "Boa Tarde! Ótima hora para treinar"
            else -> "Boa Noite! Já está escuro mas há sempre tempo para um treino"
        }

        greetingTextView.text = greeting
    }

    private fun getRandomMotivationalMessage(): String {
        val randomIndex = (0 until motivationalMessages.size).random()
        return motivationalMessages[randomIndex]
    }
}

