package edu.upc.dsa;

import edu.upc.dsa.models.*;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("user/register")
    Call<UserTO> addUser(@Body RegisterUserTO user);
    @POST("user/login")
    Call<LoginUserTO> loginUser(@Body LoginUserTO user);
}
