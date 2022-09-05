package com.example.movieaword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import com.example.movieaword.databinding.FragmentReviewBinding

class ReviewFragment : Fragment() {

    private lateinit var binding : FragmentReviewBinding
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
}