package com.crime_reporting.adapters


import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.crime_reporting.*
import com.crime_reporting.classes.CrimeInfo
import kotlinx.android.synthetic.main.crime_view.view.*


class CrimeAdapter(var activity: MainActivity, var lis:MutableList<CrimeInfo>): RecyclerView.Adapter<CrimeAdapter.Myholder>() {

    override fun onBindViewHolder(p0: Myholder, p1: Int) {
        p0.subject!!.text = lis[p1].subject
        p0.status!!.text = lis[p1].status
        p0.date!!.text = lis[p1].date
    }


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CrimeAdapter.Myholder {
        var inf = LayoutInflater.from(activity)
        var v = inf.inflate(R.layout.crime_view, p0, false)
        var myholder = CrimeAdapter.Myholder(v)

        v.setOnClickListener {
           ///myholder.tv!!.setBackgroundColor(Color.parseColor("#7ebe43"))
           // myholder.tv!!.setTextColor(Color.parseColor("#FFFFFF"))
            activity.startActivity(Intent(activity,MyCase::class.java).putExtra("key",lis[myholder.adapterPosition].key))

        }
        return myholder
    }

    override fun getItemCount(): Int {
        return lis.size
    }

    class Myholder(v: View) : RecyclerView.ViewHolder(v) {

        var status: TextView? = null
        var date: TextView? = null
        var subject: TextView? = null


        init {
            status = v.status
            date = v.date
            subject = v.subject
        }


    }

}
