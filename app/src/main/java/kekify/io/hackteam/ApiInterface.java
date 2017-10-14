package kekify.io.hackteam;

import io.reactivex.Completable;
import io.reactivex.Single;
import kekify.io.hackteam.models.AccessTokenRequest;
import kekify.io.hackteam.models.AccessTokenResponse;
import kekify.io.hackteam.models.User;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface ApiInterface {

    @FormUrlEncoded
    @POST("oauth/access_token")
    Single<AccessTokenResponse> getUserCode(@Field("client_id") String client_id,
                                            @Field("client_secret") String client_secret,
                                            @Field("code") String code);

    @POST("http://hack-team.azurewebsites.net/api/Users")
    Completable createUser(@Body User user);

}
