package work.nich.mixeddemo.github;

import retrofit2.http.GET;
import retrofit2.http.Query;
import work.nich.mixeddemo.github.vo.RepoSearchResponse;

public interface GithubService {
    @GET("search/repositories")
    RepoSearchResponse searchRepos(@Query("q") String query, @Query("page") int page);
}
