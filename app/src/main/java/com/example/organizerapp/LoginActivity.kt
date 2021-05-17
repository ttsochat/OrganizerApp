package com.example.organizerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.organizerapp.databinding.ActivityMainBinding
import com.example.organizerapp.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = FirebaseAuth.getInstance()

        binding.register.setOnClickListener{
            startActivity(Intent(this,RegisterActivity::class.java))
            finish()
        }

        binding.login.setOnClickListener{
            doLogin()
        }
    }

    fun showToast(toast: String?) {
        runOnUiThread {
            Toast.makeText(this@LoginActivity, toast, Toast.LENGTH_SHORT).show()
        }
    }

    private fun doLogin() {
        if(binding.email.text.toString().isEmpty()){
            showToast("Please enter Email")
            binding.email.requestFocus()
            return
        }
        if(binding.password.text.toString().isEmpty()){
            showToast("Please enter password")
            binding.password.requestFocus()
            return
        }

        auth.signInWithEmailAndPassword(binding.email.text.toString(), binding.password.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    updateUI(null)
                }
            }
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            updateUI(currentUser);
        }
    }

    private fun updateUI(currentUser: FirebaseUser?){
        if(currentUser != null){
            if(currentUser.isEmailVerified){
                startActivity(Intent(this,NavigationActivity::class.java))
                finish()
            }else{
                showToast("Please verify email address.")
            }
        }else{
            showToast("Sign in failed.")
        }
    }
}