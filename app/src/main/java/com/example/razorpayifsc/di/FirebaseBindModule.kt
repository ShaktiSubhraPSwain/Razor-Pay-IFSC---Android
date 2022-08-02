package com.example.razorpayifsc.di

import com.example.razorpayifsc.domain.analytics.BankAnalytics
import com.example.razorpayifsc.data.repo.analytics.BankFirebaseAnalytics
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class FirebaseBindModule {

    @Binds
    abstract fun bankAnalytics(bankAnalytics: BankFirebaseAnalytics): BankAnalytics
}
