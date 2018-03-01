package work.nich.mixeddemo.github.view;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import work.nich.mixeddemo.R;
import work.nich.mixeddemo.github.vo.Repo;

public class RepoAdapter extends PagedListAdapter<Repo, RepoAdapter.RepoViewHolder> {

    protected RepoAdapter(DiffCallback callback) {
        super(callback);
    }

    @NonNull
    @Override
    public RepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RepoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repo, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RepoViewHolder holder, int position) {
        holder.bindView(getItem(position));
    }

    class RepoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView title;
        @BindView(R.id.tv_author)
        TextView author;
        @BindView(R.id.tv_count)
        TextView count;

        public RepoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(Repo repo) {
            title.setText(repo.name);
            author.setText(repo.description);
            count.setText(repo.fullName);
        }
    }

    public static class DiffCallback extends DiffUtil.ItemCallback<Repo> {

        @Override
        public boolean areItemsTheSame(Repo oldItem, Repo newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(Repo oldItem, Repo newItem) {
            return oldItem == newItem;
        }
    }
}
