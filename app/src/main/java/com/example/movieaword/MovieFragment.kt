package com.example.movieaword

import android.content.Context
import android.inputmethodservice.InputMethodService
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.fragment.app.findFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.annotations.SerializedName
import com.kakao.auth.StringSet.api
import io.reactivex.internal.operators.observable.ObservableAll
import okhttp3.FormBody
import okhttp3.Request
import org.intellij.lang.annotations.Language
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap
import java.net.URI.create
import java.net.URL
import java.net.URLEncoder
import java.util.*

class MovieFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        var view : View = inflater.inflate(R.layout.fragment_movie, container, false)

        var button_search = view.findViewById<Button>(R.id.button_search)
        var editText = view.findViewById<EditText>(R.id.edit)
        var recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        button_search.setOnClickListener({
            if(editText.text.isEmpty()) {
                return@setOnClickListener
            }

            recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            recyclerView.setHasFixedSize(true)

            MoviesRespository.getPopularMovies()

//            recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        })

//        MoviesRespository.getPopularMovies()

        return view
    }
}

data class Movie(
    @SerializedName("id") val id : Long,
    @SerializedName("title") val title : String,
    @SerializedName("overview") val overview : String,
    @SerializedName("poster_path") val poster_path: String
)

data class GetMoviesResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val movies: List<Movie>,
    @SerializedName("totla_pages") val pages: Int
)

interface Api {

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = "93e1795e08fcfc085ac628ed242f4645",
        @Query("title") title: String = "토이스토리",
        @Query("page") page : Int,
        @Query("language") language: String = "ko"
    ): Call<GetMoviesResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String = "93e1795e08fcfc085ac628ed242f4645",
        @Query("page") page: Int,
        @Query("language") language: String = "ko"
    ) : Call<GetMoviesResponse>

    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("api_key") apiKey: String = "93e1795e08fcfc085ac628ed242f4645",
        @Query("page") page : Int,
        @Query("language") language : String = "ko"
    ): Call<GetMoviesResponse>
}

object MoviesRespository {
    private val api: Api //인터페이스 구현

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(Api::class.java)
    }

    // add method
    fun getPopularMovies(page: Int = 1){
        api.getPopularMovies(page = page)
            .enqueue(object : Callback<GetMoviesResponse> {
                override fun onResponse(
                    call: Call<GetMoviesResponse>,
                    response: Response<GetMoviesResponse>
                ) {
                    if(response.isSuccessful) {
                        val responseBody = response.body()
                        if(responseBody != null) {
                            Log.d("Repository", "Movies: ${responseBody.movies}")
                        } else {
                            Log.d("Repository", "Failed to get response")
                        }
                    }
                }

                override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
                    Log.e("Repository", "onFailure", t)
                }
            })
    }
}