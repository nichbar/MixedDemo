package work.nich.mixeddemo.github.repository;

import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;

public class Listing<T> {

    private LiveData<PagedList<T>> mPagedList;

    private LiveData<NetworkState> mNetworkState;

    private LiveData<NetworkState> mRefreshState;

    public Listing(LiveData<PagedList<T>> pagedList, LiveData<NetworkState> networkState, LiveData<NetworkState> refreshState) {
        mPagedList = pagedList;
        mNetworkState = networkState;
        mRefreshState = refreshState;
    }

    public LiveData<PagedList<T>> getPagedList() {
        return mPagedList;
    }

    public LiveData<NetworkState> getNetworkState() {
        return mNetworkState;
    }

    public LiveData<NetworkState> getRefreshState() {
        return mRefreshState;
    }

}
