package mumtaz.binar.challangechapterenam.network

import mumtaz.binar.challangechapterenam.model.GetAllFilmItem
import mumtaz.binar.challangechapterenam.model.GetAllUserItem
import mumtaz.binar.challangechapterenam.model.ResponseLogin
import mumtaz.binar.challangechapterenam.model.ResponseRegister
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("apifilm.php")
    fun getAllFilm(): Call<List<GetAllFilmItem>>

    @GET("apiuser.php")
    fun getAllUser(): Call<List<GetAllUserItem>>


    @POST("updateuser.php")
    @FormUrlEncoded
    fun updateUser(
        @Field("id") id: Int,
        @Field("username") username: String,
        @Field("complete_name") completename: String,
        @Field("address") address: String,
        @Field("dateofbirth") dateofbirth: String,
    ): Call<List<GetAllUserItem>>


    @POST("register.php/")
    @FormUrlEncoded
    fun register(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String,

        ): Call<ResponseRegister>

    @POST("login.php")
    @FormUrlEncoded
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<ResponseLogin>
}