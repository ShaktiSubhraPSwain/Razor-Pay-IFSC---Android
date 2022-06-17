package com.example.razorpayifsc.domain.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.razorpayifsc.data.repo.BankDetailApi
import com.example.razorpayifsc.data.repo.BankDetailDataRepository
import com.example.razorpayifsc.domain.common.NetworkResponse
import com.example.razorpayifsc.domain.common.NetworkResponseAdapterFactory
import com.example.razorpayifsc.utils.MockResponseFileReader

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class BankDetailsApiRepositoryTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val server = MockWebServer()
    private lateinit var repository: BankDetailDataRepository
    private lateinit var mockedResponse: String
    private val gson = GsonBuilder().setLenient().create()

    @Before
    fun init() {
        server.start(8000)
        val BASE_URL = server.url("/")
        val okHttpClient = OkHttpClient
            .Builder()
            .build()
        val apiService = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .baseUrl(BASE_URL).client(okHttpClient).build().create(BankDetailApi::class.java)
        repository = BankDetailDataRepository(apiService)
    }

    @Test
    fun test_Success() {
        mockedResponse = MockResponseFileReader("bankDetailsApi/success.json").content
        server.enqueue(MockResponse().setResponseCode(200).setBody(mockedResponse))
        val response = runBlocking { repository.getBankDetailFromIFSC("ICIC0000361") }
        val json = gson.toJson((response as NetworkResponse.Success).body)

        val resultResponse = JsonParser.parseString(json)
        val expectedResponse =
            JsonParser.parseString(mockedResponse)

        Assert.assertNotNull(resultResponse)
        Assert.assertTrue(resultResponse.equals(expectedResponse))

    }

    @Test
    fun test_Failure() {
        server.enqueue(MockResponse().setResponseCode(400).setBody("Client Error"))
        val response = runBlocking { repository.getBankDetailFromIFSC("ICIC0000361") }
        val json = gson.toJson((response as NetworkResponse.NetworkError).error)
        val resultResponse = JsonParser.parseString(json)
        val expectedResponse =
            JsonParser.parseString("{\"detailMessage\":\"Client Error\",\"stackTrace\":[],\"suppressedExceptions\":[]}")

        Assert.assertNotNull(resultResponse)
        Assert.assertTrue(resultResponse.equals(expectedResponse))
    }

    @After
    fun tearDown() {
        server.shutdown()
    }
}