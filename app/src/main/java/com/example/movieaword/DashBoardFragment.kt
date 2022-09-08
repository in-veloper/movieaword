package com.example.movieaword

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.DimenRes
import androidx.core.view.marginLeft
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2

class DashBoardFragment : Fragment() {

    lateinit var pagerUp : ViewPager2
    lateinit var pagerDown : ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        var view : View = inflater.inflate(R.layout.fragment_dashboard, container, false)
        // 상단 Hottest Movie A Word의 ViewPager 설정
        pagerUp = view.findViewById(R.id.image_viewpager)
        pagerUp.adapter = ViewPager2Adapter(getImage())
        pagerUp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        // 하단 Newest Movie A Word의 ViewPager 설정
        pagerDown = view.findViewById(R.id.image_viewpager_2)
        pagerDown.adapter = ViewPager2Adapter(getImage())
        pagerDown.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.pageMargin)    // dimen 파일 안에 크기를 정의해둠
        val pagerWidth = resources.getDimensionPixelOffset(R.dimen.pageWidth)   // dimen 파일이 없으면 생성
        val screenWidth = resources.displayMetrics.widthPixels  // 스마트폰의 너비 길이
        val offsetPx = screenWidth - pageMarginPx - pagerWidth
        val pagerPadding = ((screenWidth - pagerWidth) * 1).toInt()

        pagerUp.offscreenPageLimit = 1
//        pagerUp.clipToPadding = false
//        pagerUp.clipChildren = false
        pagerUp.setPadding(pagerPadding, 0, pagerPadding, 0)
        pagerUp.setPageTransformer { page, position ->
            page.translationX = position * offsetPx
        }


        pagerDown.offscreenPageLimit = 1
//        pagerDown.clipToPadding = false
//        pagerDown.clipChildren = false
        pagerDown.setPadding(pagerPadding, 0, pagerPadding, 0)
        pagerDown.setPageTransformer { page, position ->
            page.translationX = position * offsetPx
        }

        return view
    }

    private fun getImage() : ArrayList<Int> {
        return arrayListOf<Int>(
            R.drawable.tmp1,
            R.drawable.tmp2,
            R.drawable.tmp3,
            R.drawable.tmp4
        )
    }
}

