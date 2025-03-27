package com.example.travelplanner

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.travelplanner.data.AppDatabase
import com.example.travelplanner.databinding.ActivityLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val db by lazy { AppDatabase.getDatabase(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            CoroutineScope(Dispatchers.IO).launch {
                val user = db.appDao().getUser(email, password)
                runOnUiThread {
                    if (user != null) {
                        // Pass the user's ID to the profile screen
                        val intent = Intent(this@LoginActivity, UserProfileActivity::class.java)
                        intent.putExtra("userId", user.uid)
                        startActivity(intent)
                        finish()
                    } else {
                        binding.tvError.text = "Login failed: Invalid credentials"
                    }
                }
            }
        }
    }
}
