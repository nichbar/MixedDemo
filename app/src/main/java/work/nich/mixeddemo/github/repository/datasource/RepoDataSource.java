package work.nich.mixeddemo.github.repository.datasource;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.ItemKeyedDataSource;
import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;
import work.nich.mixeddemo.github.GithubService;
import work.nich.mixeddemo.github.repository.NetworkState;
import work.nich.mixeddemo.github.vo.Repo;
import work.nich.mixeddemo.github.vo.RepoSearchResponse;

public class RepoDataSource extends ItemKeyedDataSource<Integer, Repo> {

    private Integer mPage = 0;
    private String mSearchRepoKey;

    @NonNull
    private final GithubService mGithubService;

    @NonNull
    private final CompositeDisposable mCompositeDisposable;

    @NonNull
    private final MutableLiveData<NetworkState> mNetworkState = new MutableLiveData<>();

    @NonNull
    private final MutableLiveData<NetworkState> mInitialLoad = new MutableLiveData<>();

    /**
     * Keep Completable reference for the retry event
     */
    private Completable mRetryCompletable;

    RepoDataSource(@NonNull GithubService githubService,
                   @NonNull CompositeDisposable compositeDisposable, String key) {
        mGithubService = githubService;
        mCompositeDisposable = compositeDisposable;
        mSearchRepoKey = key;
    }

    public void retry() {
        if (mRetryCompletable != null) {
            mCompositeDisposable.add(mRetryCompletable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> {
                    }, throwable -> Timber.e(throwable.getMessage())));
        }
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Repo> callback) {
        mNetworkState.postValue(NetworkState.LOADING);
        mInitialLoad.postValue(NetworkState.LOADING);

        mCompositeDisposable.add(mapRepo(mGithubService.searchRepos(mSearchRepoKey, mPage))
                .subscribeOn(Schedulers.io())
                .subscribe(
                        repos -> {
                            setRetry(null);
                            mNetworkState.postValue(NetworkState.LOADED);
                            mInitialLoad.postValue(NetworkState.LOADED);
                            mPage = 1;
                            callback.onResult(repos);
                        },
                        throwable -> {
                            setRetry(() -> loadInitial(params, callback));

                            NetworkState error = NetworkState.error(throwable.getMessage());
                            mNetworkState.postValue(error);
                            mInitialLoad.postValue(error);
                        }
                ));
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Repo> callback) {
        mNetworkState.postValue(NetworkState.LOADING);

        mCompositeDisposable.add(mapRepo(mGithubService.searchRepos(mSearchRepoKey, params.key))
                .subscribeOn(Schedulers.io())
                .subscribe(
                        repos -> {
                            setRetry(null);
                            mNetworkState.postValue(NetworkState.LOADED);
                            callback.onResult(repos);
                            mPage++;
                        },
                        throwable -> {
                            setRetry(() -> loadAfter(params, callback));

                            mNetworkState.postValue(NetworkState.error(throwable.getMessage()));
                        }
                )
        );
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Repo> callback) {
        // do nothing
    }

    @NonNull
    @Override
    public Integer getKey(@NonNull Repo item) {
        return mPage;
    }

    private void setRetry(final Action action) {
        if (action == null) {
            mRetryCompletable = null;
        } else {
            mRetryCompletable = Completable.fromAction(action);
        }
    }

    private Single<List<Repo>> mapRepo(Observable<RepoSearchResponse> o) {
        return o.flatMap(it -> Observable.fromIterable(it.getItems())).toList();
    }

    @NonNull
    public LiveData<NetworkState> getNetworkState() {
        return mNetworkState;
    }

    @NonNull
    public LiveData<NetworkState> getInitialLoad() {
        return mInitialLoad;
    }
}
