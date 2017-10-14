package kekify.io.hackteam;

import io.reactivex.Single;
import kekify.io.hackteam.models.AccessTokenRequest;
import kekify.io.hackteam.models.AccessTokenResponse;
import retrofit2.http.Body;
import retrofit2.http.Field;


public class DataRepository {
    ApiInterface apiInterface;

    public DataRepository(ApiInterface apiInterface) {
        this.apiInterface = apiInterface;
    }

    public DataRepository() {
        this(App.getAppInstance().getApiInterface());
    }

    public Single<AccessTokenResponse> getUserCode(@Field("client_id") String client_id,
                                                   @Field("client_secret") String client_secret,
                                                   @Field("code") String code) {
        return apiInterface.getUserCode(client_id, client_secret, code);
    }
}
