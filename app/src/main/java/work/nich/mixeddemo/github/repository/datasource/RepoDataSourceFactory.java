package work.nich.mixeddemo.github.repository.datasource;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import io.reactivex.disposables.CompositeDisposable;
import work.nich.mixeddemo.github.GithubService;
import work.nich.mixeddemo.github.vo.Repo;

public class RepoDataSourceFactory implements DataSource.Factory<Integer, Repo> {

    private final GithubService mGithubService;

    private final CompositeDisposable mCompositeDisposable;

    private final MutableLiveData<RepoDataSource> mRepoDataSourceMutableLiveData = new MutableLiveData<>();

    private String mKey;

    public RepoDataSourceFactory(GithubService githubService, CompositeDisposable compositeDisposable, String key) {
        mGithubService = githubService;
        mCompositeDisposable = compositeDisposable;
        mKey = key;
    }

    @Override
    public DataSource<Integer, Repo> create() {
        RepoDataSource repoDataSource = new RepoDataSource(mGithubService, mCompositeDisposable, mKey);
        mRepoDataSourceMutableLiveData.postValue(repoDataSource);
        return repoDataSource;
    }

    public LiveData<RepoDataSource> getRepoDataSourceMutableLiveData() {
        return mRepoDataSourceMutableLiveData;
    }
}
