/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package work.nich.mixeddemo.github;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import io.reactivex.disposables.CompositeDisposable;
import work.nich.mixeddemo.AppExecutors;
import work.nich.mixeddemo.github.repository.Listing;
import work.nich.mixeddemo.github.repository.datasource.RepoDataSource;
import work.nich.mixeddemo.github.repository.datasource.RepoDataSourceFactory;
import work.nich.mixeddemo.github.vo.Repo;
import work.nich.mixeddemo.github.vo.RepoDao;

/**
 * Repository that handles Repo instances.
 * <p>
 * unfortunate naming :/ .
 * Repo - value object name
 * Repository - type of this class.
 */
public class RepoRepository {

    private final GithubDb mGithubDb;

    private final RepoDao mRepoDao;

    private final GithubService mGithubService;

    private final AppExecutors mAppExecutors;

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    private RepoDataSourceFactory mRepoDataSourceFactory;

    public RepoRepository(AppExecutors appExecutors, GithubDb mGithubDb, RepoDao repoDao,
                          GithubService githubService) {
        this.mGithubDb = mGithubDb;
        this.mRepoDao = repoDao;
        this.mGithubService = githubService;
        this.mAppExecutors = appExecutors;
    }

    public Listing<Repo> fetchRepos(String repoName) {
        mRepoDataSourceFactory = new RepoDataSourceFactory(mGithubService, mCompositeDisposable, repoName);

        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(5)
                .setInitialLoadSizeHint(10)
                .setEnablePlaceholders(false)
                .build();

        LiveData<PagedList<Repo>> pagedList = new LivePagedListBuilder<>(mRepoDataSourceFactory, config).build();

        return new Listing<>(
                pagedList,
                Transformations.switchMap(mRepoDataSourceFactory.getRepoDataSourceMutableLiveData(), RepoDataSource::getNetworkState),
                Transformations.switchMap(mRepoDataSourceFactory.getRepoDataSourceMutableLiveData(), RepoDataSource::getInitialLoad)
        );
    }

    public void clear() {
        mCompositeDisposable.clear();
    }
}
