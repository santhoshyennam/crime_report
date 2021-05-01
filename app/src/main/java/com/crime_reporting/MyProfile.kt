package com.crime_reporting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.auth.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_my_profile.*

class MyProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)

        var uid  = FirebaseAuth.getInstance().uid
        var dbase = FirebaseDatabase.getInstance().getReference("people").child(uid.toString()).child("myprofile")

        dbase.addListenerForSingleValueEvent(
            object : ValueEventListener
            {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    var childrens = p0.children
                    childrens.forEach {
                        when(it.key)
                        {
                            "fullname"->name.text = it.value.toString()
                            "mobile"->mobile.text = it.value.toString()
                            "email"->email.text = it.value.toString()
                        }
                    }
                }

            }
        )



        logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this@MyProfile,LoginActivity::class.java))


        }
    }

    override fun onStart() {
        super.onStart()
        var user  = FirebaseAuth.getInstance().currentUser
        if(user==null)
            startActivity(Intent(this@MyProfile, LoginActivity::class.java))


    }
}