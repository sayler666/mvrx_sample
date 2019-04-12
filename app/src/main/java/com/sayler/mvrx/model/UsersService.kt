package com.sayler.mvrx.model

import com.sayler.mvrx.model.User
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface UsersService {

    @GET("users")
    fun users(): Single<List<User>>

    @GET("users/{id}")
    fun user(@Path("id") id: Long): Single<User>
}
