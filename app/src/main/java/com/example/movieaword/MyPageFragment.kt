package com.example.movieaword

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.findFragment
import com.kakao.sdk.user.UserApiClient

class MyPageFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        // Fragment에서 Activity로 화면 전환할 때 사용
        // val intent = Intent(activity, SecondActivity::class.java)
        // startActivity(intent)

        var view : View = inflater.inflate(R.layout.fragment_my_page, container, false)
        val nickname = view.findViewById<TextView>(R.id.nickname)

        UserApiClient.instance.me { user, error ->
            nickname.text = "닉네임 : ${user?.kakaoAccount?.profile?.nickname}"
        }

        val kakao_logout_button = view.findViewById<Button>(R.id.kakao_logout_button)

        kakao_logout_button.setOnClickListener {
            UserApiClient.instance.logout { error ->
                if(error != null) {
                    Toast.makeText(requireContext(), "로그아웃 실패 $error", Toast.LENGTH_SHORT).show()
                }else{
                    // 혹시 터진다면 requrieContext()를 ReviewFragment에서와 동일하게 구현
                    Toast.makeText(requireContext(), "로그아웃 성공", Toast.LENGTH_SHORT).show()
                }

                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            }
        }

        val kakao_unlink_button = view.findViewById<Button>(R.id.kakao_unlink_button)

        kakao_unlink_button.setOnClickListener {
            UserApiClient.instance.unlink { error ->
                if(error != null) {
                    Toast.makeText(requireContext(), "회원 탈퇴 실패 $error", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(requireContext(), "회원 탈퇴 성공", Toast.LENGTH_SHORT).show()
                }

                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            }
        }

        return view
    }
}