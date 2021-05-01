package com.crime_reporting

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_mycase.*


class MyCase : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mycase)
        var uid = FirebaseAuth.getInstance().uid
        var mykey = intent.getStringExtra("key")
        var db = FirebaseDatabase.getInstance().getReference("people").child(uid.toString()).child("mycases").child(mykey.toString())
        //Toast.makeText(this,mykey.toString(),Toast.LENGTH_LONG).show()
        db.addListenerForSingleValueEvent(
            object : ValueEventListener
            {
                override fun onDataChange(p0: DataSnapshot) {
                    var children = p0.children
                    var map = HashMap<String,String>()
                    children.forEach {
                        map.put(it.key.toString(),it.value.toString())
                    }
                    status.text = map.getValue("status")
                    date.text = map.getValue("date")
                    subject.text = map.getValue("subject")
                    address.text = map.getValue("address")
                    description.text = map.getValue("description")
                    location.text = "Location - "+map.getValue("location")

                }

                override fun onCancelled(p0: DatabaseError) {
                }

            }
        )
        comment.setOnClickListener {

        }
    }
}