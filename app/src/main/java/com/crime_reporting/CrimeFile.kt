package com.crime_reporting

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.crime_reporting.classes.CrimeDetails
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_crime_file.*
import java.util.*

class CrimeFile : AppCompatActivity() {
    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crime_file)
        var uid = FirebaseAuth.getInstance().uid
        var cm=getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        submit.setOnClickListener{
            var loc = location.text.toString()
            var sub = subject.text.toString()
            var desc = description.text.toString()
            var addr = address.text.toString()
            if(loc.isEmpty() || sub.isEmpty() || desc.isEmpty() || addr.isEmpty())
                Toast.makeText(this,"Please enter all details",Toast.LENGTH_LONG).show()
            else if(cm.activeNetworkInfo==null)
            {
                Toast.makeText(this@CrimeFile,"Please connect to internet", Toast.LENGTH_LONG).show()

            }
            else
            {
                var date = Date()
                var db = FirebaseDatabase.getInstance().getReference("people").child(uid.toString()).child("mycases")
                var details = CrimeDetails("Not accepted",loc, sub, desc,addr, date.toString())
                db.child(db.ref.push().key.toString()).setValue(details).addOnCompleteListener(
                    object :  OnCompleteListener<Void> {
                        override fun onComplete(p0: Task<Void>) {
                            if(p0.isSuccessful)
                            {
                                Toast.makeText(this@CrimeFile,"Uploaded your details details",Toast.LENGTH_LONG).show()
                                startActivity(Intent(this@CrimeFile,MainActivity::class.java))
                            }
                            else
                            {
                                Toast.makeText(this@CrimeFile,"There was some proble. Try again!",Toast.LENGTH_LONG).show()

                            }
                        }

                    }
                )


            }
        }
    }
}