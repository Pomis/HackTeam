package kekify.io.hackteam;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import kekify.io.hackteam.models.AccessTokenRequest;
import kekify.io.hackteam.models.AccessTokenResponse;
import kekify.io.hackteam.models.Project;
import kekify.io.hackteam.models.TwistUser;
import kekify.io.hackteam.models.User;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.Query;


public class DataRepository {

    private ApiInterface apiInterface;

    private DataRepository(ApiInterface apiInterface) {
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

    public Single<Integer> createUser(User user) {
        return apiInterface.createUser(user);
    }

    public Single<TwistUser> getUserInfo(String access_token) {
        return apiInterface.getUserInfo("Bearer " + access_token);
    }

    public Single<Integer> createProject(Project project, int twistId) {
        return apiInterface.createProject(project, twistId);
    }

    public Single<List<User>> searchCandidates(Integer projectId) {
        return apiInterface.searchCandidates(projectId);
    }

}
