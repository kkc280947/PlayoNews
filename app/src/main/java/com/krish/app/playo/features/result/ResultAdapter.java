package com.krish.app.playo.features.result;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.krish.app.playo.R;
import com.krish.app.playo.data.models.Hit;
import com.krish.app.playo.databinding.ItemNewsBinding;

import java.util.ArrayList;
import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {

    private List<Hit> newsList = new ArrayList<>();
    private OnListItemClicked onListItemClicked;

    public ResultAdapter(OnListItemClicked onListItemClicked){
        this.onListItemClicked = onListItemClicked;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNewsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_news,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindView(newsList.get(position));
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public void addResult(List<Hit> hitsList){
        newsList = hitsList;
        notifyDataSetChanged();
    }

    public void addNewResult(List<Hit> hits) {
        newsList.addAll(hits);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemNewsBinding binding;
        public ViewHolder(ItemNewsBinding itemNewsBinding) {
            super(itemNewsBinding.getRoot());
            this.binding = itemNewsBinding;
            binding.cardRoot.setOnClickListener(v -> {
                if(onListItemClicked!=null){
                    onListItemClicked.openUrl(newsList.get(getAdapterPosition()).getUrl(),newsList.get(getAdapterPosition()).getTitle());
                }
            });
        }

        public void bindView(Hit hit) {
            binding.textTitle.setText(hit.getTitle());
            binding.textAuthor.setText(String.format("Author: %s", hit.getAuthor()));
        }
    }

    public interface OnListItemClicked{
        void openUrl(String url, String title);
    }
}
