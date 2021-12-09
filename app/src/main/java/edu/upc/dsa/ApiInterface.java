package edu.upc.dsa;

import java.util.List;

import edu.upc.dsa.models.Plane;
import edu.upc.dsa.transferObjects.LoginUserTO;
import edu.upc.dsa.transferObjects.RegisterUserTO;
import edu.upc.dsa.transferObjects.UserTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("user/register")
    Call<UserTO> addUser(@Body RegisterUserTO user);
    @POST("user/login")
    Call<LoginUserTO> loginUser(@Body LoginUserTO user);
    @GET("planes/GetAllPlanes")
    Call<List<Plane>> getAllPlanes();
}
