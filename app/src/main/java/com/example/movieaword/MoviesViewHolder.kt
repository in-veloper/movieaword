package com.example.movieaword

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MoviesViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    var view : View = v
    val mName = view.findViewById<TextView>(R.id.mName)
    val mPath = view.findViewById<TextView>(R.id.mPath)


    fun bind(item: Movies) {
        mName.text = item.movieName
        mPath.text = item.posterPath
    }
}