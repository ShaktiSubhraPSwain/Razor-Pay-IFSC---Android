package com.example.razorpayifsc.di.analytics

import android.content.Context
import com.example.razorpayifsc.data.repo.analytics.BankAnalytics
import com.example.razorpayifsc.domain.analytics.BankFirebaseAnalytics
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FirebaseModule {

    @Singleton
    @Provides
    fun provideFirebaseAnalytics(@ApplicationContext context: Context): BankAnalytics {
        return BankFirebaseAnalytics(FirebaseAnalytics.getInstance(context))
    }
}
