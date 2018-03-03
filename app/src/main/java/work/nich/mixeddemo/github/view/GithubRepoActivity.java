package work.nich.mixeddemo.github.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import work.nich.mixeddemo.BaseActivity;
import work.nich.mixeddemo.R;
import work.nich.mixeddemo.github.SearchViewModel;

public class GithubRepoActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    protected RecyclerView mRecyclerView;
    @BindView(R.id.et)
    protected TextView mEditText;

    protected RepoAdapter mRepoAdapter;

    private SearchViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github);

        ButterKnife.bind(this);
        initViewModel();

        mRepoAdapter = new RepoAdapter(new RepoAdapter.DiffCallback());
        mRecyclerView.setAdapter(mRepoAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
    }

    public void doSearch(View view) {
        mViewModel.search(mEditText.getText().toString());
        mViewModel.getPagedList().observe(this, pagedList -> mRepoAdapter.submitList(pagedList));
    }
}
