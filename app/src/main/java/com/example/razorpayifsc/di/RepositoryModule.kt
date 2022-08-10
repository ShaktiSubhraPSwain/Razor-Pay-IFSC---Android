package com.example.razorpayifsc.di

import com.example.razorpayifsc.data.repo.BankDetailRemoteRepositoryImpl
import com.example.razorpayifsc.domain.bankdetails.repository.BankDetailRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideBankDetailRepo(
        repository: BankDetailRemoteRepositoryImpl
    ): BankDetailRepository
}
