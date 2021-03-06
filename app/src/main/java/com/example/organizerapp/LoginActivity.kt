package com.example.organizerapp

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.room.Room
import com.example.organizerapp.databinding.ActivityMainBinding
import com.example.organizerapp.databinding.ActivityRegisterBinding
import com.example.organizerapp.db.AppDatabase
import com.example.organizerapp.db.entities.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlin.concurrent.thread

/**
 * Login activity is activity that is used for signing in. The user is authenticated
 * with Firebase.
 */
class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    /**
     * Overwritten OnCreate method
     */
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
        binding.forgot.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Forgot password")
            val view = layoutInflater.inflate(R.layout.dialog_forgot_password, null)
            val fp_email = view.findViewById<EditText>(R.id.fp_email)
            builder.setView(view)
            builder.setPositiveButton("RESET", DialogInterface.OnClickListener{_, _->
                forgotPassword(fp_email)
            })
            builder.setNegativeButton("CLOSE", DialogInterface.OnClickListener{_, _->
            })
            builder.show()
        }
    }

    /**
     * Sends email to reset the user's password.
     */
    private fun forgotPassword(fp_email: EditText?) {
        if(!fp_email?.text.toString().trim().matches(emailPattern.toRegex())){
            return
        }
        if(fp_email?.text.toString().isEmpty()){
            return
        }
        auth.sendPasswordResetEmail(fp_email?.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    showToast("Email sent.")
                }
            }
    }

    /**
     * Shows a Toast message on the bottom of the screen.
     */
    fun showToast(toast: String?) {
        runOnUiThread {
            Toast.makeText(this@LoginActivity, toast, Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Signs in the user to the app.
     */
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
                    updateUI(user, true)
                } else {
                    updateUI(null, true)
                }
            }
    }

    /**
     * When the app launches if a user is already signed in it automatically redirects him
     * to Navigation Activity else it shows him the Login Activity.
     */
    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            updateUI(currentUser, false);
        }
    }

    /**
     * Updates the UI depending on whether a user is signed in or not.
     */
    private fun updateUI(currentUser: FirebaseUser?, login: Boolean){
        if(currentUser != null){
            if(currentUser.isEmailVerified){
                startActivity(Intent(this,NavigationActivity::class.java))
                finish()
            }else if(login){
                showToast("Please verify email address.")
            }
        }else{
            showToast("Sign in failed.")
        }
    }
}