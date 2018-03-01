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

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import work.nich.mixeddemo.github.vo.Repo;
import work.nich.mixeddemo.github.vo.RepoDao;
import work.nich.mixeddemo.github.vo.RepoSearchResult;

/**
 * Main database description.
 */
@Database(entities = {Repo.class, RepoSearchResult.class}, version = 3, exportSchema = false)
public abstract class GithubDb extends RoomDatabase {
    abstract public RepoDao repoDao();
}
