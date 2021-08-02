package com.wayneyong.snapchatclone

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class MainActivity : AppCompatActivity() {

    private val TAG: String = "MainActivity"

    var emailEditText: EditText? = null
    var paswordEditText: EditText? = null
    val mAuth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT > 9) {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }

        setContentView(R.layout.activity_main)

        emailEditText = findViewById(R.id.emailEditText)
        paswordEditText = findViewById(R.id.passwordEditText)

        if (mAuth.currentUser != null) {
            logIn()
        }

    }

    fun goClicked(view: View) {
        //Check if we can login the user else sign up the user
        mAuth.signInWithEmailAndPassword(
            emailEditText?.text.toString().trim(),
            paswordEditText?.text.toString().trim()
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    // Sign in success, update UI with the signed-in user's information
                    logIn()
                } else {
//                    Log.w(TAG, "signInWithEmail:failed", task.getException());
//                    Toast.makeText(
//                        this,
//                        "User Authentication Failed: " + task.getException(),
//                        Toast.LENGTH_SHORT
//                    ).show();

                    //Sign up the user, and add them to database(firebase realtime)
                    mAuth.createUserWithEmailAndPassword(emailEditText?.text.toString().trim(),paswordEditText?.text.toString().trim())

                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                //Add to database
                                FirebaseDatabase.getInstance("https://snapchat-clone-1f8f2-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference()
                                    .child("users") //create users folder
                                    .child(task.result!!.user?.uid!!) //take from task to create user with their uid
                                    .child("email").setValue(emailEditText?.text.toString()) //set user email from user input
//                                FirebaseDatabase.getInstance("URL").getReference().child("users").child(task2.result!!.user?.uid!!).child("email").setValue(emailEditText?.text.toString())

                                logIn()
                            } else {
                                Toast.makeText(this, "Login Failed. Try Again.", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                }
            }
    }

    fun logIn() {
        //Move to next activity
        val intent = Intent(this, SnapsActivity::class.java)
        startActivity(intent)
        Toast.makeText(this, "SnapsActivity.", Toast.LENGTH_SHORT)
            .show()

    }
}