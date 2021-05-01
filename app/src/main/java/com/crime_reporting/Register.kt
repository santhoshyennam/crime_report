package com.crime_reporting

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.crime_reporting.classes.UserProfile
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*
import java.text.SimpleDateFormat
import java.util.*

@Suppress("DEPRECATION")
class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        already.setOnClickListener {
            startActivity(Intent(this@Register,LoginActivity::class.java))

        }

        sign.setOnClickListener {
            //val email = Pair<View, String>(name, "otp")

            // val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this@RegisterActivity, email)
            var e = email.text.toString()
            var p = password.text.toString()
            var n = name.text.toString()
            var m= mobile.text.toString()

            var cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (n.isEmpty())
                name.setError("name!")
            else if (n.length > 30)
                name.setError("name should be less than 30 characters!")
            else if (e.isEmpty())
                email.setError("Email!")
            else if (p.isEmpty())
                password.setError("Password!")
            else if(m.isEmpty())
                mobile.setError("Mobile!")
            else if (p.length < 8)
                password.setError("less than 8 digits")
            else if(m.length!=10)
                Toast.makeText(this@Register, "please enter valid 10 digit number", Toast.LENGTH_LONG).show()
            else if (!p.equals(cpass.text.toString())) {
                Toast.makeText(this@Register, "password not matched", Toast.LENGTH_LONG).show()
            } else if (cm.activeNetworkInfo == null) {
                Toast.makeText(this@Register, "Please connect to internet", Toast.LENGTH_LONG)
                    .show()

            } else {
                var auth = FirebaseAuth.getInstance()

                var pd = ProgressDialog(this@Register)
                pd.setTitle("Creating Account..")
                pd.show()
                auth.createUserWithEmailAndPassword(e, p).addOnCompleteListener {
                    if (it.isSuccessful) {
                        pd.dismiss()
                       /* val formatter =
                            SimpleDateFormat("dd.MM.yyyy, HH:mm")
                        formatter.setLenient(false)

                        val curDate = Date()
                        val curTime: String = formatter.format(curDate)*/

                        var uid = FirebaseAuth.getInstance().uid
                        var dbase = FirebaseDatabase.getInstance().getReference("people")
                            .child(uid.toString()).child("myprofile")
                        var user = UserProfile(n,e,m)
                        dbase.setValue(user).addOnCompleteListener(
                            object : OnCompleteListener<Void>
                            {
                                override fun onComplete(p0: Task<Void>) {
                                    if(p0.isSuccessful)
                                    {
                                        Toast.makeText(this@Register, "Success", Toast.LENGTH_LONG).show()
                                        startActivity(Intent(this@Register, MainActivity::class.java))
                                        finish()
                                    }
                                    else
                                    {
                                        Toast.makeText(this@Register, "There was  problem,please try again.", Toast.LENGTH_LONG).show()

                                    }
                                }

                            }
                        )
                        //dbase.child("notificationtime").setValue(curTime)



                    } else {
                        pd.dismiss()
                        Toast.makeText(this@Register, "Email Used by other user", Toast.LENGTH_LONG)
                            .show()
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
            startActivity(Intent(this@Register, MainActivity::class.java))


    }
}