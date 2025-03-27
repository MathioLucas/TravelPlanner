package com.example.travelplanner

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.travelplanner.data.AppDatabase
import com.example.travelplanner.data.User
import com.example.travelplanner.databinding.ActivityRegisterBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val db by lazy { AppDatabase.getDatabase(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            val newUser = User(name = name, email = email, password = password)
            CoroutineScope(Dispatchers.IO).launch {
                val id = db.appDao().insertUser(newUser)
                runOnUiThread {
                    if (id > 0) {
                        val intent = Intent(this@RegisterActivity, UserProfileActivity::class.java)
                        intent.putExtra("userId", id.toInt())
                        startActivity(intent)
                        finish()
                    } else {
                        binding.tvError.text = "Registration failed"
                    }
                }
            }
        }
    }
}
