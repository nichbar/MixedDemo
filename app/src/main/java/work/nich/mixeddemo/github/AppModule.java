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

import android.app.Application;
import android.arch.persistence.room.Room;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import work.nich.mixeddemo.github.util.LiveDataCallAdapterFactory;
import work.nich.mixeddemo.github.vo.RepoDao;

class AppModule {
    public static GithubService provideGithubService() {
        return new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .build()
                .create(GithubService.class);
    }

    public static GithubDb provideDb(Application app) {
        return Room.databaseBuilder(app, GithubDb.class,"github.db").build();
    }


    public static RepoDao provideRepoDao(GithubDb db) {
        return db.repoDao();
    }
}
