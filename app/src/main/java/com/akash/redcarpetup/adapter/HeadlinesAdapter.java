package com.akash.redcarpetup.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akash.redcarpetup.R;
import com.akash.redcarpetup.model.Article;
import com.bumptech.glide.Glide;

import java.util.List;

public class HeadlinesAdapter extends RecyclerView.Adapter<HeadlinesAdapter.HeadLinesViewHolder> {

    private Context context;
    private List<Article> articleList;
    private int res;
    private OnItemClickListener listener;

    public interface OnItemClickListener{
        void onNewsClick(int position);
    }
    public HeadlinesAdapter(Context context, int res, List<Article> articleList) {
        this.context = context;
        this.res = res;
        this.articleList = articleList;
    }

    public void setOnItemClickListener(OnItemClickListener mlistener){
        listener = mlistener;
    }



    @NonNull
    @Override
    public HeadLinesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(res,parent,false);
        return new HeadLinesViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull HeadLinesViewHolder holder, int position) {

        Article model = articleList.get(position);
        holder.headline_text.setText(model.getTitle());
        Glide.with(context).load(model.getUrlToImage()).into(holder.news_image);
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public class HeadLinesViewHolder extends RecyclerView.ViewHolder{
        private TextView headline_text;
        private ImageView news_image;
        public HeadLinesViewHolder(@NonNull View itemView, final OnItemClickListener listeners) {
            super(itemView);

            headline_text = itemView.findViewById(R.id.headline_txt);
            news_image = itemView.findViewById(R.id.headline_imageView);

            headline_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listeners != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listeners.onNewsClick(position);
                        }
                    }
                }
            });
        }
    }
}
