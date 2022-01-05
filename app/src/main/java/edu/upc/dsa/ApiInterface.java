package edu.upc.dsa;

import java.util.List;

import edu.upc.dsa.models.InsigniaModel;
import edu.upc.dsa.models.Insignia;
import edu.upc.dsa.models.Plane;
import edu.upc.dsa.models.PlaneModel;
import edu.upc.dsa.transferObjects.InsigniaTO;
import edu.upc.dsa.transferObjects.InsigniaUserTO;
import edu.upc.dsa.transferObjects.LoginUserTO;
import edu.upc.dsa.transferObjects.PlanePlayerTO;
import edu.upc.dsa.transferObjects.PlaneTO;
import edu.upc.dsa.transferObjects.RegisterUserTO;
import edu.upc.dsa.transferObjects.UserTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {
    @POST("user/register")
    Call<UserTO> addUser(@Body RegisterUserTO user);

    @POST("user/login")
    Call<LoginUserTO> loginUser(@Body LoginUserTO user);

    @GET("user/logout/{userName}")
    Call<Void> logoutUser(@Path("userName") String userName);

    @POST("planes/addPlaneToPlayer")
    Call<Void> addPlaneToUser(@Body PlanePlayerTO planePlayerTO);

    @GET("planes/GetAllPlanes")
    Call<List<PlaneModel>> getAllPlanes();

    @GET("planes/getListPlanesPlayer/{playername}")
    Call<List<PlaneTO>> getListPlanesPlayer(@Path("playername") String playerName);

    @POST("insignias/addInsigniaToPlayer")
    Call<Void> addInsigniaToUser(@Body InsigniaUserTO insigniaUserTO);

    @GET("insignias/GetAllInsignias")
    Call<List<InsigniaModel>> getAllInsignias();

    @GET("insignias/getListInsigniasPlayer/{playername}")
    Call<List<InsigniaTO>> getListInsigniasPlayer(@Path("playername") String playerName);

}
