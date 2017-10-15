package kekify.io.hackteam;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import kekify.io.hackteam.models.AccessTokenRequest;
import kekify.io.hackteam.models.AccessTokenResponse;
import kekify.io.hackteam.models.Invitation;
import kekify.io.hackteam.models.Project;
import kekify.io.hackteam.models.TwistUser;
import kekify.io.hackteam.models.User;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface ApiInterface {

    @FormUrlEncoded
    @POST("oauth/access_token")
    Single<AccessTokenResponse> getUserCode(@Field("client_id") String client_id,
                                            @Field("client_secret") String client_secret,
                                            @Field("code") String code);

    @POST("http://hack-team.azurewebsites.net/api/Users")
    Single<Integer> createUser(@Body User user);

    @GET("api/v2/users/getone")
    Single<TwistUser> getUserInfo(@Header("Authorization") String access_token);

    @POST("http://hack-team.azurewebsites.net/api/projects")
    Single<Integer> createProject(@Body Project project, @Query("user") int id);

    @GET("http://hack-team.azurewebsites.net/api/Search")
    Single<List<User>> searchCandidates(@Query("id") Integer projectId);

    @GET("http://hack-team.azurewebsites.net/api/invations")
    Single<List<Invitation>> getInvitations(@Query("user") int id);

}
