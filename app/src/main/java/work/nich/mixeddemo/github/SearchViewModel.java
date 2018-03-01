package work.nich.mixeddemo.github;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import java.util.Locale;
import java.util.Objects;

import work.nich.mixeddemo.AppExecutors;
import work.nich.mixeddemo.MixedApplication;
import work.nich.mixeddemo.github.vo.RepoSearchResponse;

public class SearchViewModel extends ViewModel {

    private MutableLiveData<String> mQuery = new MutableLiveData<>();
    private LiveData<RepoSearchResponse> mSearchResult;
//    private LiveData<>

    private final MutableLiveData<String> query = new MutableLiveData<>();


    private RepoRepository mRepository;

    public SearchViewModel() {
        GithubDb db = AppModule.provideDb(MixedApplication.getApp());
        mRepository = new RepoRepository(new AppExecutors(), db, AppModule.provideRepoDao(db), AppModule.provideGithubService());
        mSearchResult = Transformations.map(mQuery, input -> mRepository.search(input, 0));

//        results = Transformations.switchMap(query, search -> {
//            if (search == null || search.trim().length() == 0) {
//                return AbsentLiveData.create();
//            } else {
//                return mRepository.search(search);
//            }
//        });
    }

    public void setQuery(@NonNull String originalInput) {
        String input = originalInput.toLowerCase(Locale.getDefault()).trim();
        if (Objects.equals(input, query.getValue())) {
            return;
        }
        query.setValue(input);
    }

    void refresh() {
        if (query.getValue() != null) {
            query.setValue(query.getValue());
        }
    }

    static class LoadMoreState {
        private final boolean running;
        private final String errorMessage;
        private boolean handledError = false;

        LoadMoreState(boolean running, String errorMessage) {
            this.running = running;
            this.errorMessage = errorMessage;
        }

        boolean isRunning() {
            return running;
        }

        String getErrorMessage() {
            return errorMessage;
        }

        String getErrorMessageIfNotHandled() {
            if (handledError) {
                return null;
            }
            handledError = true;
            return errorMessage;
        }
    }
}