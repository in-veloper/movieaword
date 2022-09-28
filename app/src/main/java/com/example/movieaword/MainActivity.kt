package com.example.movieaword

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.movieaword.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.kakao.usermgmt.StringSet.email
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception


class MainActivity : AppCompatActivity() {



//    private lateinit var binding : ActivityMainBinding
//    private var TAG = this.javaClass.simpleName
//
//    private lateinit var launcher : ActivityResultLauncher<Intent>
//    private lateinit var firebaseAuth : FirebaseAuth
//
//    private var email : String = ""
//    private var tokenId : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(R.layout.activity_main)

//        firebaseAuth = FirebaseAuth.getInstance()
//        launcher = registerForActivityResult(
//            ActivityResultContracts.StartActivityForResult(), ActivityResultCallback { result ->
//                Log.e(TAG, "resultCode : ${result.resultCode}")
//                Log.e(TAG, "result : $result")
//
//                if(result.resultCode == RESULT_OK) {
//                    val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
//                    try {
//                        task.getResult(ApiException::class.java)?.let { account ->
//                            tokenId = account.idToken
//
//                            if(tokenId != null && tokenId != "") {
//                                val credential : AuthCredential = GoogleAuthProvider.getCredential(account.idToken, null)
//                                firebaseAuth.signInWithCredential(credential)
//                                    .addOnCompleteListener {
//                                        if(firebaseAuth.currentUser != null) {
//                                            val user : FirebaseUser = firebaseAuth.currentUser!!
//                                            email = user.email.toString()
//                                            Log.e(TAG, "email : $email")
//                                            val googleSignInToken = account.idToken ?: ""
//
//                                            if(googleSignInToken != "") {
//                                                Log.e(TAG, "googleSignInToken : $googleSignInToken")
//                                            }else{
//                                                Log.e(TAG, "googleSignInToken이 Null")
//                                            }
//                                        }
//                                    }
//                            }
//                        } ?: throw Exception()
//                    } catch (e : Exception) {
//                        e.printStackTrace()
//                    }
//                }
//            })
////
//        binding.run {
//            google_login_button.setOnClickListener {
//                CoroutineScope(Dispatchers.IO).launch {
//                    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                        .requestIdToken(getString(R.string.default_web_client_id))
//                        .requestEmail()
//                        .build()
//                    val googleSignInClient = GoogleSignIn.getClient(this@MainActivity, gso)
//                    val signInIntent : Intent = googleSignInClient.signInIntent
//                    launcher.launch(signInIntent)
//                }
//            }
//        }








//        setContentView(R.layout.activity_main)

        // 로그인 정보 확인
        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (error != null) {
                Toast.makeText(this, "토큰 정보 보기 실패", Toast.LENGTH_SHORT).show()
            }
            else if (tokenInfo != null) {
                Toast.makeText(this, "토큰 정보 보기 성공", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, NaviActivity::class.java)
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                finish()
            }
        }

        // Key Hash 얻는데 사용
        // val keyHash = Utility.getKeyHash(this)
        // Log.d("Hash", keyHash)

        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                when {
                    error.toString() == AuthErrorCause.AccessDenied.toString() -> {
                        Toast.makeText(this, "접근이 거부 됨(동의 취소)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidClient.toString() -> {
                        Toast.makeText(this, "유효하지 않은 앱", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
                        Toast.makeText(this, "인증 수단이 유효하지 않아 인증할 수 없는 상태", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
                        Toast.makeText(this, "요청 파라미터 오류", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidScope.toString() -> {
                        Toast.makeText(this, "유효하지 않은 scope ID", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.Misconfigured.toString() -> {
                        Toast.makeText(this, "설정이 올바르지 않음(android key hash)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.ServerError.toString() -> {
                        Toast.makeText(this, "서버 내부 에러", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.Unauthorized.toString() -> {
                        Toast.makeText(this, "앱이 요청 권한이 없음", Toast.LENGTH_SHORT).show()
                    }
                    else -> { // Unknown
                        Toast.makeText(this, "기타 에러", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else if (token != null) {
                Toast.makeText(this, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()

                // Login 성공 시 화면 전환되는 부분
                // 여기서 그냥 기존 NaviActivity로 연결시켜주면 됨
                val intent = Intent(this, NaviActivity::class.java)
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                finish()
            }
        }

        val kakao_login_button = findViewById<ImageButton>(R.id.kakao_login_button) // 로그인 버튼

        kakao_login_button.setOnClickListener {
            if(LoginClient.instance.isKakaoTalkLoginAvailable(this)){
                LoginClient.instance.loginWithKakaoTalk(this, callback = callback)
            }else{
                LoginClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
        }
    }
}