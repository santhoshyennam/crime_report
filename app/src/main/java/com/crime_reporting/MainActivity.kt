package com.crime_reporting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.crime_reporting.adapters.CrimeAdapter
import com.crime_reporting.classes.CrimeInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var lm= LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL,false)
        rview.layoutManager=lm
        file.setOnClickListener {
            startActivity(Intent(this@MainActivity,CrimeFile::class.java))

        }
        profile.setOnClickListener {
            startActivity(Intent(this@MainActivity,MyProfile::class.java))

        }
        var uid = FirebaseAuth.getInstance().uid
        var db= FirebaseDatabase.getInstance().getReference("people").child(uid.toString()).child("mycases")

        db.addListenerForSingleValueEvent(
            object : ValueEventListener
            {
                override fun onDataChange(p0: DataSnapshot) {
                    var children = p0.children
                    var mycases = mutableListOf<CrimeInfo>()
                    if(!p0.exists())
                    {
                        rview.visibility = View.GONE
                        tview.visibility = View.GONE
                        tt.visibility = View.VISIBLE
                    }
                    else
                    {
                        tview.visibility = View.VISIBLE
                        children.forEach{
                            var chi = it.children
                            var map = HashMap<String,String>()
                            map.put("key",it.key.toString())
                            chi.forEach{
                                map.put(it.key.toString(),it.value.toString())
                            }
                            var crimedetails = CrimeInfo(
                                map.getValue("status"),map.getValue("location"), map.getValue("subject"), map.getValue("description")
                            , map.getValue("address"),map.getValue("date"),map.getValue("key"))
                            mycases.add(crimedetails)
                        }
                        //adapter
                        rview.adapter = CrimeAdapter(this@MainActivity,mycases)
                    }
                }

                override fun onCancelled(p0: DatabaseError) {

                }

            }
        )


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