package com.example.movieaword

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Login API 사용하려면 Sdk 초기화 필요 -> Hash key로 초기화 처리 부분
        KakaoSdk.init(this, "26a79b80c2f28fb0d08db3e6a183993d")
    }
}