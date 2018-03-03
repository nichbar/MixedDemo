package work.nich.mixeddemo.github;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import work.nich.mixeddemo.github.vo.RepoSearchResponse;

public interface GithubService {
    @GET("search/repositories")
    Observable<RepoSearchResponse> searchRepos(@Query("q") String key, @Query("page") int page);
}
