package com.sun.weather.data.repository.source.remote.api.error

import com.example.weather.utils.ext.notNull
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.sun.weather.utils.LogUtils
import retrofit2.HttpException
import java.io.IOException
import java.text.ParseException

data class ErrorResponse(
    @SerializedName("status")
    @Expose
    val status: Int,
    @SerializedName("messages")
    @Expose
    val messages: String?,
) {
    companion object {
        private const val TAG = "ErrorResponse"

        @Suppress("ReturnCount")
        fun convertToRetrofitException(throwable: Throwable): RetrofitException {
            if (throwable is RetrofitException) {
                return throwable
            }
            if (throwable is IOException) {
                return RetrofitException.toNetworkError(throwable)
            }
            if (
                throwable is HttpException
            ) {
                val response =
                    throwable.response() ?: return RetrofitException.toUnexpectedError(
                        throwable,
                    )

                response.errorBody().notNull {
                    return try {
                        val errorResponse =
                            Gson().fromJson(it.string(), ErrorResponse::class.java)

                        if (errorResponse != null && !errorResponse.messages.isNullOrBlank()) {
                            RetrofitException.toServerError(errorResponse)
                        } else {
                            RetrofitException.toHttpError(response)
                        }
                    } catch (e: IOException) {
                        LogUtils.e(TAG, e.message.toString())
                        RetrofitException.toUnexpectedError(throwable)
                    } catch (e: ParseException) {
                        LogUtils.e(TAG, e.message.toString())
                        RetrofitException.toUnexpectedError(throwable)
                    } catch (e: JsonSyntaxException) {
                        LogUtils.e(TAG, e.message.toString())
                        RetrofitException.toUnexpectedError(throwable)
                    }
                }

                return RetrofitException.toHttpError(response)
            }
            return RetrofitException.toUnexpectedError(throwable)
        }
    }
}
