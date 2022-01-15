package edu.upc.dsa;

import java.util.List;

import edu.upc.dsa.models.ForumEntry;
import edu.upc.dsa.models.InsigniaModel;
import edu.upc.dsa.models.Insignia;
import edu.upc.dsa.models.Plane;
import edu.upc.dsa.models.PlaneModel;
import edu.upc.dsa.models.Upgrade;
import edu.upc.dsa.transferObjects.InsigniaTO;
import edu.upc.dsa.transferObjects.InsigniaUserTO;
import edu.upc.dsa.transferObjects.LoginUserTO;
import edu.upc.dsa.transferObjects.PlanePlayerTO;
import edu.upc.dsa.transferObjects.PlaneTO;
import edu.upc.dsa.transferObjects.RankingTO;
import edu.upc.dsa.transferObjects.RegisterUserTO;
import edu.upc.dsa.transferObjects.UserTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    /** USER *******************/

    @POST("user/register")
    Call<UserTO> addUser(@Body RegisterUserTO user);

    @POST("user/login")
    Call<LoginUserTO> loginUser(@Body LoginUserTO user);

    @GET("user/logout/{userName}")
    Call<Void> logoutUser(@Path("userName") String userName);

    @GET("user/{userName}")
    Call<UserTO> getUser(@Path("userName") String userName);

    @DELETE("user/{userName}")
    Call<Void> deleteUser(@Path("userName") String userName);


    @POST("user/update/{oldUserName}")
    Call<UserTO> updateUser(@Body RegisterUserTO user,@Path("oldUserName") String userName);

    /** PLANES *******************/

    @POST("planes/addPlaneToPlayer")
    Call<Void> addPlaneToUser(@Body PlanePlayerTO planePlayerTO);

    @GET("planes/GetAllPlanes")
    Call<List<PlaneModel>> getAllPlanes();

    @GET("planes/getListPlanesPlayer/{playername}")
    Call<List<PlaneTO>> getListPlanesPlayer(@Path("playername") String playerName);

    @GET("planes/getPlaneByModel/{planeModel}")
    Call<PlaneModel> getPlaneByModel(@Path("planeModel") String planeModel);

    @POST("planes/addUpgradeToPlayer")
    Call<Void> addUpgradeToPlayer(@Body Upgrade upgrade);

    @GET("planes/getAllUpgradesFromPlayer/{playername}")
    Call<List<Upgrade>> getAllUpgradesFromPlayer(@Path("playername") String playerName);


    /** INSIGNIAS *******************/

    @POST("insignias/addInsigniaToPlayer")
    Call<Void> addInsigniaToUser(@Body InsigniaUserTO insigniaUserTO);

    @GET("insignias/GetAllInsignias")
    Call<List<InsigniaModel>> getAllInsignias();

    @GET("insignias/getListInsigniasPlayer/{playername}")
    Call<List<InsigniaTO>> getListInsigniasPlayer(@Path("playername") String playerName);


    /** FORUM *******************/

    @POST("forum/addEntry")
    Call<Void> addEntry(@Body ForumEntry entry);

    @GET("forum/GetAllEntries")
    Call<List<ForumEntry>> getAllEntries();

    /** RANKING *********************/
    @GET("user/getByRol/{playername}")
    Call<RankingTO> getRol(@Path("playername") String playerName);

    @GET("user/getByDistance/{playername}")
    Call<RankingTO> getDistance(@Path("playername") String playerName);

    @GET("user/getByTime/{playername}")
    Call<RankingTO> getTime(@Path("playername") String playerName);

    @GET("user/getByMoney/{playername}")
    Call<RankingTO> getMoney(@Path("playername") String playerName);

    @GET("user/getByDistance")
    Call<List<RankingTO>> getByDistance();

    @GET("user/getByMoney")
    Call<List<RankingTO>> getByMoney();

    @GET("user/getByRol")
    Call<List<RankingTO>> getByRol();

    @GET("user/getByTime")
    Call<List<RankingTO>> getByTime();
}
