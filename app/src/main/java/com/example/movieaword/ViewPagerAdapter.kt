package com.example.movieaword

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter

// PagerAdapter를 상속받는 Adapter 생성
class ViewPagerAdapter(private val context: Context, imageList: ArrayList<Int>) : PagerAdapter() {
    var image = imageList

    override fun getCount(): Int {
        return image.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return (view == `object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var view = LayoutInflater.from(context).inflate(R.layout.image_view, null, false)

        val imageView = view.findViewById<ImageView>(R.id.vp_image)
        imageView.setImageResource(image[position])

        container.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    // getPageWidth값을 조절해 Post의 크기 조절 가능
    // 0f ~ 1f 사이의 값을 사용
    override fun getPageWidth(position: Int): Float {
        return 0.3f
    }
}