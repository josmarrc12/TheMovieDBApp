package com.osmar.themoviedbapp.data.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.osmar.themoviedbapp.datastore.LanguagePreferences
import java.io.InputStream
import java.io.OutputStream

object LanguagePreferencesSerializer : Serializer<LanguagePreferences> {
    override val defaultValue: LanguagePreferences = LanguagePreferences.newBuilder()
//        .setDefaultLanguage(LanguagePreferences.Languages.ENGLISH)
        .setChangeLanguage(false)
        .build()

    override suspend fun readFrom(input: InputStream): LanguagePreferences {
        try {
            return LanguagePreferences.parseFrom(input)
        }catch (exception : InvalidProtocolBufferException){
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: LanguagePreferences, output: OutputStream) = t.writeTo(output)
}