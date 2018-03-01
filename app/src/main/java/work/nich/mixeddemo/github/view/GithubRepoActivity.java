package work.nich.mixeddemo.github.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import work.nich.mixeddemo.BaseActivity;
import work.nich.mixeddemo.R;
import work.nich.mixeddemo.github.SearchViewModel;
import work.nich.mixeddemo.github.vo.Repo;

public class GithubRepoActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    protected RecyclerView mRecyclerView;
    @BindView(R.id.et)
    protected TextView mEditText;

    protected RepoAdapter mRepoAdapter;

    private SearchViewModel mViewModel;
    private List<Repo> mRepoList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github);

        ButterKnife.bind(this);
        initViewModel();

        mRepoList = new ArrayList<>();
        mRepoAdapter = new RepoAdapter(new RepoAdapter.DiffCallback());
        mRecyclerView.setAdapter(mRepoAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
//        mViewModel.getResults().observe(this, new Observer<Resource<List<Repo>>>() {
//            @Override
//            public void onChanged(@Nullable Resource<List<Repo>> listResource) {
//                if (listResource == null) return;
//
//                if (listResource.status == Status.SUCCESS) {
//                    mRepoAdapter.submitList(listResource.data);
//                } else {
//                    Timber.d(listResource.message);
//                }
//            }
//        });
    }

    public void doSearch(View view) {
        mViewModel.setQuery(mEditText.getText().toString());
    }
}
