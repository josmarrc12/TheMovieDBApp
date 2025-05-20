package com.osmar.themoviedbapp.data.network

import retrofit2.Response
import javax.inject.Inject

class ApiResponseHandler @Inject constructor(){

    fun <T> handleApiResponse(response: Response<T>): ResultResponse<T>{
        return if (response.isSuccessful){
            response.body()?.let { body ->
                ResultResponse.Success(body)
            } ?: ResultResponse.Error("Empty response body")
        }else{
            ResultResponse.Error("Response Error -> Code: ${response.code()}, Message: ${response.message()}")
        }
    }
}