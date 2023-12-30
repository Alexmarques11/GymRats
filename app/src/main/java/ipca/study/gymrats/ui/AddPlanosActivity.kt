package ipca.study.gymrats.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import ipca.study.gymrats.R

class AddPlanosActivity : AppCompatActivity() {

    private var position: Int = -1
    private var timestamp: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_planos)

        val trainingType = findViewById<EditText>(R.id.editTextTrainingType)
        val date = findViewById<EditText>(R.id.editTextDate)

        intent.extras?.let {
            position = it.getInt(DATA_POSITION, -1)
            timestamp = it.getLong(DATA_DATE)
            val training = it.getString(DATA_TRAINING)
            trainingType.setText(training)
        }

        findViewById<Button>(R.id.buttonDone)?.setOnClickListener {
            val intent = Intent()
            intent.putExtra(DATA_TRAINING, trainingType.text.toString())
            intent.putExtra(DATA_DATE, timestamp)  // Usando timestamp em vez de uma string
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    companion object {
        const val DATA_TRAINING = "data_training"
        const val DATA_DATE = "data_date"
        const val DATA_POSITION = "data_position"
    }
}