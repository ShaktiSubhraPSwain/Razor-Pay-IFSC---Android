package com.example.razorpayifsc.di

import com.example.razorpayifsc.data.repo.BankDetailDataRepository
import com.example.razorpayifsc.domain.bank_details.repository.BankDetailRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideBankDetailRepo(repository: BankDetailDataRepository): BankDetailRepository
}
