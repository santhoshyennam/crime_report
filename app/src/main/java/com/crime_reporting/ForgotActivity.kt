package com.crime_reporting

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgot.*

@Suppress("DEPRECATION")
class ForgotActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot)

        send.setOnClickListener {
            //var e = email.text.toString()
            var cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if(email.text.toString().isEmpty())
            {
                email.setError("Email!")
            }
            else if (cm.activeNetworkInfo == null) {
                Toast.makeText(this@ForgotActivity, "please connect to internet", Toast.LENGTH_LONG)
                    .show()

            } else {
                /*if(checkLong(email.text.toString())) {
                    val min = 100000
                    val max = 999999
                    val random = Random.nextInt((max - min) + 1) + min
                    //Toast.makeText(this,random.toString(),Toast.LENGTH_LONG).show()
                    var policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
                    StrictMode.setThreadPolicy(policy)
                    if (!e.startsWith("+91")) {
                        e = "+91" + e
                        sendSms(e,random)
                    }
                    else {
                        sendSms(e,random)

                    }
                }*/


                var pd = ProgressDialog(this@ForgotActivity)
                pd.setTitle("Please wait..")
                pd.show()
                var e = email.text.toString()
                var mAuth = FirebaseAuth.getInstance()
                mAuth.sendPasswordResetEmail(e)
                    .addOnCompleteListener(object : OnCompleteListener<Void> {
                        override fun onComplete(p0: Task<Void>) {
                            pd.dismiss()
                            if (p0.isSuccessful) {
                                Toast.makeText(
                                    this@ForgotActivity,
                                    "check your email",
                                    Toast.LENGTH_LONG
                                ).show()
                                finish()
                            } else
                                Toast.makeText(
                                    this@ForgotActivity,
                                    "Failed to Recover.Please contact our service Provider",
                                    Toast.LENGTH_LONG
                                ).show()


                        }

                    }
                    )

            }
        }
    }
}