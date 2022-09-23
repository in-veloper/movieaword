package com.example.movieaword

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
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
import com.kakao.sdk.common.util.Utility

class DashBoardFragment : Fragment() {

    lateinit var pagerUp : ViewPager
    lateinit var pagerDown : ViewPager

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
        pagerUp.adapter = ViewPagerAdapter(requireContext(), getImage()) // ViewPager2에서 ViewPager로 변경했습니다. + context를 넘겨줘요

        // 하단 Newest Movie A Word의 ViewPager 설정
        pagerDown = view.findViewById(R.id.image_viewpager_2)
        pagerDown.adapter = ViewPagerAdapter(requireContext(), getImage()) // ViewPager2에서 ViewPager로 변경했습니다. + context를 넘겨줘요

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

