package com.example.movieaword

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.movieaword.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.kakao.sdk.user.UserApiClient
import com.kakao.usermgmt.StringSet.*
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_my_page.*

class MyPageFragment : Fragment() {

    private lateinit var googleSignInClient: GoogleSignInClient

    private val TAG = this.javaClass.simpleName

    private var isNaver : Boolean = false

    private lateinit var google_logout_button : Button
    private lateinit var kakao_logout_button : Button
    private lateinit var naver_logout_button : Button
    private lateinit var google_unlink_button : Button
    private lateinit var kakao_unlink_button : Button
    private lateinit var naver_unlink_button : Button

    private var email: String = ""
    private var gender: String = ""
    private var name: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(NaverIdLoginSDK.getState().toString() == "OK") {
            isNaver = true
        }

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
        naver_logout_button = view.findViewById<Button>(R.id.naver_logout_button)
        kakao_logout_button = view.findViewById<Button>(R.id.kakao_logout_button)
        google_logout_button = view.findViewById<Button>(R.id.google_logout_button)
        naver_unlink_button = view.findViewById<Button>(R.id.naver_unlink_button)
        kakao_unlink_button = view.findViewById<Button>(R.id.kakao_unlink_button)
        google_unlink_button = view.findViewById<Button>(R.id.google_unlink_button)


        google_unlink_button.setOnClickListener {
            revokeAccess()
        }
        // Google 로그인의 경우 계정 정보
        var auth = FirebaseAuth.getInstance()

        // Google로 로그인한 계정 정보가 없을 경우 Kakao 의 경우로 조건 설정
        if(auth.currentUser == null && !isNaver) {
            UserApiClient.instance.me { user, error ->
                nickname.text = "평론가 : ${user?.kakaoAccount?.profile?.nickname}"
            }
            setLayoutState("Kakao")
        // Google로 로그인한 경우 계정 정보 출력
        }else if(auth.currentUser != null && !isNaver) {
            nickname.text = "평론가 : ${auth.currentUser?.displayName.toString()}"
            setLayoutState("Google")
        }

        if(isNaver) {
            setLayoutState("Naver")
            val oAuthLoginCallback = object : OAuthLoginCallback {
                override fun onSuccess() {
                    // 네이버 로그인 API 호출 성공 시 유저 정보를 가져온다
                    NidOAuthLogin().callProfileApi(object : NidProfileCallback<NidProfileResponse> {
                        override fun onSuccess(result: NidProfileResponse) {
                            name = result.profile?.name.toString()
                            nickname.text = "평론가 : ${name.toString()}"

                            email = result.profile?.email.toString()
                            gender = result.profile?.gender.toString()
                            Log.e(TAG, "네이버 로그인한 유저 정보 - 이름 : $name")
                            Log.e(TAG, "네이버 로그인한 유저 정보 - 이메일 : $email")
                            Log.e(TAG, "네이버 로그인한 유저 정보 - 성별 : $gender")
                            isNaver = true
                        }

                        override fun onError(errorCode: Int, message: String) {
                            //
                        }

                        override fun onFailure(httpStatus: Int, message: String) {
                            //
                        }
                    })
                }

                override fun onError(errorCode: Int, message: String) {
                    val naverAccessToken = NaverIdLoginSDK.getAccessToken()
                    Log.e(TAG, "naverAccessToken : $naverAccessToken")
                }

                override fun onFailure(httpStatus: Int, message: String) {
                    //
                }
            }
            NaverIdLoginSDK.authenticate(requireContext(), oAuthLoginCallback)
        }

        naver_logout_button.setOnClickListener {
            naverLogOut()
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

//        val kakao_unlink_button = view.findViewById<Button>(R.id.kakao_unlink_button)

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

//        val naver_unlink_button = view.findViewById<Button>(R.id.naver_unlink_button)

        naver_unlink_button.setOnClickListener {
            startNaverDeleteToken()

            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
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

    private fun naverLogOut() {
        NaverIdLoginSDK.logout()
        NidOAuthLogin().callDeleteTokenApi(requireContext(), object : OAuthLoginCallback {
            override fun onSuccess() {

            }

            override fun onFailure(httpStatus: Int, message: String) {

            }

            override fun onError(errorCode: Int, message: String) {

            }
        })

        val intent = Intent(activity, MainActivity::class.java)
        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
    }

    private fun setLayoutState(login : String) {
        if(login == "Naver") {
            kakao_logout_button.visibility = View.GONE
            google_logout_button.visibility = View.GONE
            kakao_unlink_button.visibility = View.GONE
            google_unlink_button.visibility = View.GONE
        }else if(login == "Kakao") {
            naver_logout_button.visibility = View.GONE
            google_logout_button.visibility = View.GONE
            naver_unlink_button.visibility = View.GONE
            google_unlink_button.visibility = View.GONE
        }else if(login == "Google") {
            naver_logout_button.visibility = View.GONE
            kakao_logout_button.visibility = View.GONE
            naver_unlink_button.visibility = View.GONE
            kakao_unlink_button.visibility = View.GONE
        }
    }

    private fun startNaverDeleteToken(){
        NidOAuthLogin().callDeleteTokenApi(requireContext(), object : OAuthLoginCallback {
            override fun onSuccess() {
                //서버에서 토큰 삭제에 성공한 상태입니다.
                Toast.makeText(requireContext(), "네이버 아이디 토큰삭제 성공!", Toast.LENGTH_SHORT).show()
            }
            override fun onFailure(httpStatus: Int, message: String) {
                // 서버에서 토큰 삭제에 실패했어도 클라이언트에 있는 토큰은 삭제되어 로그아웃된 상태입니다.
                // 클라이언트에 토큰 정보가 없기 때문에 추가로 처리할 수 있는 작업은 없습니다.
                Log.d("naver", "errorCode: ${NaverIdLoginSDK.getLastErrorCode().code}")
                Log.d("naver", "errorDesc: ${NaverIdLoginSDK.getLastErrorDescription()}")
            }
            override fun onError(errorCode: Int, message: String) {
                // 서버에서 토큰 삭제에 실패했어도 클라이언트에 있는 토큰은 삭제되어 로그아웃된 상태입니다.
                // 클라이언트에 토큰 정보가 없기 때문에 추가로 처리할 수 있는 작업은 없습니다.
                onFailure(errorCode, message)
            }
        })
    }

    private fun revokeAccess() {
        FirebaseAuth.getInstance().currentUser!!.delete()
        FirebaseAuth.getInstance().signOut()
        googleSignInClient.revokeAccess().addOnCompleteListener(requireActivity()) {

        }
        val intent = Intent(activity, MainActivity::class.java)
        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
//        FirebaseAuth.getInstance().currentUser!!.delete().addOnCompleteListener {
//            task ->
//            if(task.isSuccessful) {
//                Toast.makeText(requireContext(), "구글 아이디 탈퇴가 완료되었습니다.", Toast.LENGTH_SHORT).show()
//                FirebaseAuth.getInstance().signOut()
//
//                val intent = Intent(activity, MainActivity::class.java)
//                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
//            }else{
//                Toast.makeText(requireContext(), task.exception.toString(), Toast.LENGTH_SHORT).show()
//            }
//        }
    }
}