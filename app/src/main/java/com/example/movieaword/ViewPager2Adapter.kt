package com.example.movieaword

import android.media.Image
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import java.nio.file.Files.size

class ViewPager2Adapter(imageList: ArrayList<Int>) :
    RecyclerView.Adapter<ViewPager2Adapter.PagerViewHolder>() {

    var image = imageList

    inner class PagerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder
        (LayoutInflater.from(parent.context).inflate(R.layout.image_view, parent, false)) {
        val imageById = itemView.findViewById<ImageView>(R.id.vp_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PagerViewHolder((parent))

    override fun getItemCount(): Int = image.size

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.imageById.setImageResource(image[position])
    }
}