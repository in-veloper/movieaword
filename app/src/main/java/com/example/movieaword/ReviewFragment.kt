package com.example.movieaword

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.movieaword.databinding.FragmentReviewBinding
import com.kakao.sdk.common.util.Utility

class ReviewFragment : Fragment() {

    val TAG = "ReviewFragment"
    var db : AppDatabase? = null
    var movieList = mutableListOf<Movies>()


//    lateinit var db: AppDatabase
    lateinit var applicationContext: Context
    private lateinit var binding : FragmentReviewBinding
//    private lateinit var searchView: SearchView
//    lateinit var mainActivity: MainActivity

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//
//        mainActivity = context as MainActivity
//
////        applicationContext = mainActivity
//    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applicationContext = requireContext()
        db = AppDatabase.getInstance(requireContext())

// 아래와 같은 방식으로 DB 이용용
//       db.moviesDao().getAll()

//        binding = FragmentReviewBinding.inflate(layoutInflater)

//        val sv = view.getViewById(R.id.sv) as SearchView

//        sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                TODO("Not yet implemented")
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                TODO("Not yet implemented")
//                return false
//            }
//        })
//        var searchViewTextLinstener : SearchView.OnQueryTextListener =
//            object : SearchView.OnQueryTextListener {
//                override fun onQueryTextSubmit(p0: String?): Boolean {
//                    TODO("Not yet implemented")
//                    return false
//                }
//
//                override fun onQueryTextChange(p0: String?): Boolean {
//                    TODO("Not yet implemented")
//                    return false
//                }
//            }

//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                TODO("Not yet implemented")
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                TODO("Not yet implemented")
//                return false
//            }
//        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
//        var view : View = inflater.inflate(R.layout.fragment_review, container, false)
//        binding = FragmentReviewBinding.inflate(inflater, container, false)

        var view : View = inflater.inflate(R.layout.fragment_review, container, false)
        var tv = view.findViewById<TextView>(R.id.edit)

        val savedMovies = db!!.moviesDao()?.getAll()
        if (savedMovies != null) {
            if(savedMovies.isNotEmpty()) {
                movieList.addAll(savedMovies)
            }
        }
        Log.d("movieList", movieList.toString())

        view.findViewById<Button>(R.id.reviewButton).setOnClickListener {view ->
            Log.d("aa", "aa")
            val MoviesName = Movies(0, tv.text.toString(), "path")
            db?.moviesDao()?.insertAll(MoviesName)
        }
//        searchView = view.findViewById(R.id.sv) as SearchView
//
////        searchView.setOnSearchClickListener {
////
////        }
//
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                return true
//            }
//        })

//        searchView = binding.searchView.findViewById(R.id.search_view)
//        searchView = binding.searchView.findViewById(R.id.search_view)

//        val searchView = binding.(R.id.search_view) as SearchView



//        SearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                // 검색 버튼 누를 때 호출
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                // 검색 창에서 글자가 변경이 일어날 때마다 호출
//                return true
//            }
//        })

        return view
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        setOnClickListener()
//    }
//
//    private fun setOnClickListener() {
//        val btnSequence = binding.container.children
//        btnSequence.forEach { btn ->
//            btn.setOnClickListener(this)
//        }
//    }
//
//    override fun onClick(v: View?) {
//        if (v != null) {
//            when(v.id) {
//                R.id.reviewButton -> {
//                    Log.d(TAG, "First Button")
//                }
//            }
//        }
//    }
//
//    companion object {
//        private const val TAG = "ReviewFragment"
//        fun instance() = ReviewFragment()
//    }
}