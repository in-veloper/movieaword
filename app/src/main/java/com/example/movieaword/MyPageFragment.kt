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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.kakao.sdk.user.UserApiClient

class MyPageFragment : Fragment() {

    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
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
        val kakao_logout_button = view.findViewById<Button>(R.id.kakao_logout_button)
        val google_logout_button = view.findViewById<Button>(R.id.google_logout_button)

        // Google 로그인의 경우 계정 정보
        var auth = FirebaseAuth.getInstance()

        // Google로 로그인한 계정 정보가 없을 경우 Kakao 의 경우로 조건 설정
        if(auth.currentUser == null) {
            UserApiClient.instance.me { user, error ->
                nickname.text = "닉네임 : ${user?.kakaoAccount?.profile?.nickname}"
            }
            google_logout_button.visibility = View.GONE
        // Google로 로그인한 경우 계정 정보 출력
        }else if(auth.currentUser != null) {
            nickname.text = "닉네임 : ${auth.currentUser?.displayName.toString()}"

            kakao_logout_button.visibility = View.GONE
        }

        google_logout_button.setOnClickListener {
            signOut()
        }

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

    private fun signOut() {
        FirebaseAuth.getInstance().signOut()

        googleSignInClient.signOut().addOnCompleteListener {

        }

        val intent = Intent(activity, MainActivity::class.java)
        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
    }
}