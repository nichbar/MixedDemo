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

import work.nich.mixeddemo.AppExecutors;
import work.nich.mixeddemo.github.vo.RepoDao;
import work.nich.mixeddemo.github.vo.RepoSearchResponse;

/**
 * Repository that handles Repo instances.
 *
 * unfortunate naming :/ .
 * Repo - value object name
 * Repository - type of this class.
 */
public class RepoRepository {

    private final GithubDb db;

    private final RepoDao repoDao;

    private final GithubService githubService;

    private final AppExecutors appExecutors;

    public RepoRepository(AppExecutors appExecutors, GithubDb db, RepoDao repoDao,
                          GithubService githubService) {
        this.db = db;
        this.repoDao = repoDao;
        this.githubService = githubService;
        this.appExecutors = appExecutors;
    }

    public RepoSearchResponse search(String query, int page) {
        return githubService.searchRepos(query, page);
    }

//    public LiveData<Resource<List<Repo>>> search(String query) {
//        return new NetworkBoundResource<List<Repo>, RepoSearchResponse>(appExecutors) {
//
//            @Override
//            protected void saveCallResult(@NonNull RepoSearchResponse item) {
//                List<Integer> repoIds = item.getRepoIds();
//                RepoSearchResult repoSearchResult = new RepoSearchResult(
//                        query, repoIds, item.getTotal(), item.getNextPage());
//                db.beginTransaction();
//                try {
//                    repoDao.insertRepos(item.getItems());
//                    repoDao.insert(repoSearchResult);
//                    db.setTransactionSuccessful();
//                } finally {
//                    db.endTransaction();
//                }
//            }
//
//            @Override
//            protected boolean shouldFetch(@Nullable List<Repo> data) {
//                return data == null;
//            }
//
//            @NonNull
//            @Override
//            protected LiveData<List<Repo>> loadFromDb() {
//                return Transformations.switchMap(repoDao.search(query), searchData -> {
//                    if (searchData == null) {
//                        return AbsentLiveData.create();
//                    } else {
//                        return repoDao.loadOrdered(searchData.repoIds);
//                    }
//                });
//            }
//
//            @NonNull
//            @Override
//            protected LiveData<ApiResponse<RepoSearchResponse>> createCall() {
//                return githubService.searchRepos(query, 0);
//            }
//
//            @Override
//            protected RepoSearchResponse processResponse(ApiResponse<RepoSearchResponse> response) {
//                RepoSearchResponse body = response.body;
//                if (body != null) {
//                    body.setNextPage(response.getNextPage());
//                }
//                return body;
//            }
//        }.asLiveData();
//    }
}
