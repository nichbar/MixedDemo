package work.nich.mixeddemo.github;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;

import work.nich.mixeddemo.AppExecutors;
import work.nich.mixeddemo.MixedApplication;
import work.nich.mixeddemo.github.repository.Listing;
import work.nich.mixeddemo.github.repository.NetworkState;
import work.nich.mixeddemo.github.vo.Repo;

public class SearchViewModel extends ViewModel {

    private LiveData<PagedList<Repo>> mPagedList;

    private RepoRepository mRepoRepository;

    private Listing<Repo> mRepoListing;

    public SearchViewModel() {
        GithubDb db = AppModule.provideDb(MixedApplication.getApp());
        mRepoRepository = new RepoRepository(new AppExecutors(), db, AppModule.provideRepoDao(db), AppModule.provideGithubService());
    }

    public LiveData<NetworkState> getNetworkState() {
        return mRepoListing.getNetworkState();
    }

    public LiveData<NetworkState> getRefreshState() {
        return mRepoListing.getRefreshState();
    }

    public void search(String key) {
        mRepoListing = mRepoRepository.fetchRepos(key);
        mPagedList = mRepoListing.getPagedList();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mRepoRepository.clear();
    }

    public LiveData<PagedList<Repo>> getPagedList() {
        return mPagedList;
    }
}