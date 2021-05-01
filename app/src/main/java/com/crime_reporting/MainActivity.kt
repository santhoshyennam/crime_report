package com.crime_reporting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        file.setOnClickListener {
            startActivity(Intent(this@MainActivity,CrimeFile::class.java))

        }
        profile.setOnClickListener {
            startActivity(Intent(this@MainActivity,MyProfile::class.java))

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
        if(user==null)
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))


    }
}