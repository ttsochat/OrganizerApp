package com.example.organizerapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.organizerapp.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import android.util.Log


class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var binding: ActivityRegisterBinding
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = FirebaseAuth.getInstance()

        binding.register.setOnClickListener{
            signUpUser()
        }
    }

    private fun signUpUser() {
        if(binding.username.text.toString().isEmpty()){
            binding.username.error = "Please enter username"
            binding.username.requestFocus()
            return
        }

        if(!binding.email.text.toString().trim().matches(emailPattern.toRegex())){
            binding.email.error = "Invalid email"
            binding.email.requestFocus()
            return
        }

        if(binding.password.text.toString().isEmpty()){
            binding.password.error = "Please enter password"
            binding.password.requestFocus()
            return
        }

        if(!binding.confirm.text.toString().equals(binding.password.text.toString())){
            binding.confirm.error = "Password doesn't match"
            binding.confirm.requestFocus()
            return
        }
        auth.createUserWithEmailAndPassword(binding.email.text.toString(), binding.password.text.toString())
                .addOnCompleteListener(this) { task ->
                    Log.d("TAG", "Message1")
                    if (task.isSuccessful) {
                        Log.d("TAG", "Message2")
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    } else {
                        Log.d("TAG", "Message3")
                        val errorCode = (task.exception as FirebaseAuthException?)!!.errorCode
                        Log.d("TAG", errorCode)
//                        Toast.makeText(baseContext, "Sign up failed.",
//                                Toast.LENGTH_SHORT).show()
                    }
                }
    }


}