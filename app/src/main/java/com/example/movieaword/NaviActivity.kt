package com.example.movieaword

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.movieaword.databinding.ActivityNaviBinding

private const val TAG_DASHBOARD = "dashboard_fragment"
private const val TAG_REVIEW = "review_fragment"
private const val TAG_MOVIE = "movie_fragment"
private const val TAG_MY_PAGE = "my_page_fragment"

class NaviActivity : AppCompatActivity() {
    // 뷰 바인딩 사용
    private lateinit var binding: ActivityNaviBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNaviBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var actionBar : ActionBar?

        actionBar = supportActionBar
        actionBar?.hide()

        setFragment(TAG_DASHBOARD, DashBoardFragment())

        binding.mainNavi.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.DashBoardFragment -> setFragment(TAG_DASHBOARD, DashBoardFragment())
                R.id.ReviewFragment -> setFragment(TAG_REVIEW, ReviewFragment())
                R.id.MovieFragment -> setFragment(TAG_MOVIE, MovieFragment())
                R.id.MyPageFragment -> setFragment(TAG_MY_PAGE, MyPageFragment())
            }
            true
        }
    }
    fun setFragment(tag: String, fragment: Fragment){
        val manager: FragmentManager = supportFragmentManager
        val ft: FragmentTransaction = manager.beginTransaction()

        if(manager.findFragmentByTag(tag) == null){
            ft.add(R.id.mainNaviFragmentContainer, fragment, tag)
        }

        val dashboard = manager.findFragmentByTag(TAG_DASHBOARD)
        val review = manager.findFragmentByTag(TAG_REVIEW)
        val movie = manager.findFragmentByTag(TAG_MOVIE)
        val myPage = manager.findFragmentByTag(TAG_MY_PAGE)

        if(dashboard != null){
            ft.hide(dashboard)
        }

        if(review != null){
            ft.hide(review)
        }

        if(movie != null){
            ft.hide(movie)
        }

        if(myPage != null){
            ft.hide(myPage)
        }

        if(tag == TAG_DASHBOARD){
            if(dashboard != null){
                ft.show(dashboard)
            }
        }
        else if(tag == TAG_REVIEW){
            if(review != null){
                ft.show(review)
            }
        }
        else if(tag == TAG_MOVIE){
            if(movie != null){
                ft.show(movie)
            }
        }
        else if(tag == TAG_MY_PAGE){
            if(myPage != null){
                ft.show(myPage)
            }
        }

        ft.commitAllowingStateLoss()
    }
}