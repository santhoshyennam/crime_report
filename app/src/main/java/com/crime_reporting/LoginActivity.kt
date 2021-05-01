package com.crime_reporting

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        forgot.setOnClickListener {
            startActivity(Intent(this@LoginActivity,ForgotActivity::class.java))

        }

        create.setOnClickListener {
            startActivity(Intent(this@LoginActivity,Register::class.java))
        }

        login.setOnClickListener {
           // startActivity(Intent(this@LoginActivity,MainActivity::class.java))
            var cm=getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            var e=email.text.toString()
            var p = password.text.toString()
            if(e.isEmpty())
                email.setError("Email!")
            else if(p.isEmpty())
                password.setError("Password!")
            else if(p.length<8)
                password.setError("less than 8 digits")
            else if(cm.activeNetworkInfo==null)
            {
                Toast.makeText(this@LoginActivity,"Please connect to internet", Toast.LENGTH_LONG).show()

            }
            else
            {
                var pd = ProgressDialog(this@LoginActivity)
                pd.setTitle("Authenticating..")
                pd.show()
                var auth = FirebaseAuth.getInstance()

                auth.signInWithEmailAndPassword(e,p).addOnCompleteListener{
                    if(it.isSuccessful)
                    {
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                        pd.dismiss()
                    }
                    else
                    {
                        Toast.makeText(this@LoginActivity,"invalid email or password", Toast.LENGTH_LONG).show()
                        pd.dismiss()
                    }
                }
            }
        }

    }

    override fun onStart() {
        super.onStart()
       /* var cm=getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if(cm.activeNetworkInfo==null)
        {
            startActivity(Intent(this,NetConnection::class.java))
        }*/
        var user  = FirebaseAuth.getInstance().currentUser
        if(user!=null)
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))


    }
}