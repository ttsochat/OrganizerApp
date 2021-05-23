package com.example.organizerapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.organizerapp.databinding.ActivityRegisterBinding
import com.example.organizerapp.db.entities.User
import com.example.organizerapp.ui.user.UserViewModel
import com.google.firebase.auth.*


class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityRegisterBinding
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    private lateinit var mUserViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = FirebaseAuth.getInstance()
        binding.register.setOnClickListener{
            signUpUser()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(Intent(this,LoginActivity::class.java))
            true
        } else super.onKeyDown(keyCode, event)
    }

    fun showToast(toast: String?) {
        runOnUiThread {
            Toast.makeText(this@RegisterActivity, toast, Toast.LENGTH_SHORT).show()
        }
    }

    private fun insertUserToDatabase(userFB: FirebaseUser){
        val userId = userFB.uid.toString()
        val username = binding.username.text.toString()
        val email = binding.email.text.toString()

        val user = User(userId,username,email)
        mUserViewModel.addUser(user)
    }

    private fun signUpUser() {
        if(binding.username.text.toString().isEmpty()){
            showToast("Please enter username")
            binding.username.requestFocus()
            return
        }
        if(!binding.email.text.toString().trim().matches(emailPattern.toRegex())){
            showToast("Invalid email")
            binding.email.requestFocus()
            return
        }
        if(binding.password.text.toString().isEmpty()){
            showToast("Please enter password")
            binding.password.requestFocus()
            return
        }
        if(!binding.confirm.text.toString().equals(binding.password.text.toString())){
            showToast("Password doesn't match")
            binding.confirm.requestFocus()
            return
        }
        auth.createUserWithEmailAndPassword(
            binding.email.text.toString(),
            binding.password.text.toString()
        ).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val user : FirebaseUser = auth.currentUser
                user!!.sendEmailVerification()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            insertUserToDatabase(user)
                            startActivity(Intent(this, LoginActivity::class.java))
                            finish()
                        }
                    }
            } else {
                try {
                    throw task.exception!!
                } catch (e: Exception) {
                    showToast(e.message!!)
                    Log.e("TAG", e.message!!)
                }
            }
        }
    }
}