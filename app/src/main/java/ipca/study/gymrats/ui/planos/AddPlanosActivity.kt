package ipca.study.gymrats.ui.planos

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import ipca.study.gymrats.DatePickerFragment
import ipca.study.gymrats.R
import ipca.study.gymrats.repository.AppDatabase
import ipca.study.gymrats.repository.Planos
import ipca.study.gymrats.toShortDateTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date
import java.util.UUID

class AddPlanosActivity : AppCompatActivity() {

    private var position: Int = -1
    private var timestamp: Long = 0
    private var planId : String? = null

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
            findViewById<EditText>(R.id.editTextDate).setText( Date(timestamp).toShortDateTime())

           planId = it.getString(DATA_PLAN_ID)

        }

        findViewById<Button>(R.id.buttonDone)?.setOnClickListener {
            if (planId == null) {
                val newPlano = Planos(
                    UUID.randomUUID().toString(),
                    trainingType.text.toString(),
                    if (timestamp != 0L) Date(timestamp) else Date(),
                    false
                )

                lifecycleScope.launch(Dispatchers.IO) {
                    val planosDao = AppDatabase.getDatabase(this@AddPlanosActivity)?.getPlanosDao()
                    planosDao?.insert(newPlano)
                    withContext(Dispatchers.Main) {
                        finish()
                    }
                }
            }else{
                val newPlano = Planos(
                    planId!!,
                    trainingType.text.toString(),
                    if (timestamp != 0L) Date(timestamp) else Date(),
                    false
                )

                lifecycleScope.launch(Dispatchers.IO) {
                    val planosDao = AppDatabase.getDatabase(this@AddPlanosActivity)?.getPlanosDao()
                    planosDao?.insert(newPlano)
                    withContext(Dispatchers.Main) {
                        finish()
                    }
                }
            }
        }
        findViewById<ImageButton>(R.id.imageButtonPicKDate).setOnClickListener {
            val newFragment = DatePickerFragment()
            newFragment.show(supportFragmentManager, "datePicker")
            newFragment.onDateSet = {
                this.timestamp = it.time
                findViewById<EditText>(R.id.editTextDate).setText( it.toShortDateTime())
            }
        }

        findViewById<ImageButton>(R.id.imageButtonDelete).setOnClickListener {
            val newPlano = Planos(
                planId!!,
                trainingType.text.toString(),
                if (timestamp != 0L) Date(timestamp) else Date(),
                false
            )

            lifecycleScope.launch(Dispatchers.IO) {
                val planosDao = AppDatabase.getDatabase(this@AddPlanosActivity)?.getPlanosDao()
                planosDao?.delete(newPlano)
                withContext(Dispatchers.Main) {
                    finish()
                }
            }
        }
    }

    companion object {
        const val DATA_TRAINING = "data_training"
        const val DATA_DATE = "data_date"
        const val DATA_POSITION = "data_position"
        const val DATA_PLAN_ID = "data_plan_id"
    }
}