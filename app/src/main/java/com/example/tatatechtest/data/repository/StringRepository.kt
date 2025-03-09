package com.example.tatatechtest.data.repository

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import com.example.tatatechtest.data.models.RandomText
import com.example.tatatechtest.data.models.RandomTextResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StringRepository(private val context: Context) {
    suspend fun fetchRandomString(maxLength: Int): RandomText? {
        return withContext(Dispatchers.IO) {
            try {
                val uri = Uri.parse("content://com.iav.contestdataprovider/text")
                val bundle = Bundle().apply {
                    putInt(ContentResolver.QUERY_ARG_LIMIT, maxLength)
                }
                val cursor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.contentResolver.query(uri, null, bundle, null)
                } else {
                    TODO("VERSION.SDK_INT < O")
                }
                cursor?.use {
                    if (it.moveToFirst()) {
                        val json = it.getString(it.getColumnIndexOrThrow("data"))
                        val response = Gson().fromJson(json, RandomTextResponse::class.java)
                        response.randomText
                    } else null
                }
            } catch (e: Exception) {
                throw e // Let ViewModel handle the error
            }
        }
    }
}