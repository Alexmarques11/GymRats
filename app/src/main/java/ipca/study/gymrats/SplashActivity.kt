package ipca.study.gymrats

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        auth = Firebase.auth

        lifecycleScope.launch(Dispatchers.IO) {
            delay(1000)
            lifecycleScope.launch(Dispatchers.Main) {
                val currentUser = auth.currentUser
                if (currentUser != null) {
                    val intent = Intent(this@SplashActivity,  MainActivity::class.java)
                    startActivity(intent)
                }else{
                    val intent = Intent(this@SplashActivity,  LoginActivity::class.java)
                    startActivity(intent)
                }
                finish()
            }
        }

    }
}