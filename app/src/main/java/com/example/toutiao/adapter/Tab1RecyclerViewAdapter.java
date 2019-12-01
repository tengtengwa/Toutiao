package com.example.toutiao.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.toutiao.R;
import com.example.toutiao.activity.normal.MyApplication;
import com.example.toutiao.activity.normal.NewsDetails;
import com.example.toutiao.bean.News;
import com.example.toutiao.others.MyImageView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Tab1RecyclerViewAdapter extends RecyclerView.Adapter {

    private static final int STYLE_ONE = 1;
    private static final int STYLE_TWO = 2;
    private static final int STYLE_THREE = 3;

    List<News> newsList;
    Activity activity;

    static class OneHolder extends RecyclerView.ViewHolder {
        View style1View;
        @BindView(R.id.tv_item1_title)
        TextView titleItem1;

        @BindView(R.id.tv_item1_author)
        TextView authorItem1;

        @BindView(R.id.tv_item1_time)
        TextView timeItem1;

        @BindView(R.id.ib_item1)
        ImageButton imageButton1;

        TextView title;

        public OneHolder(@NonNull View itemView) {
            super(itemView);
            style1View = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

    static class TwoHolder extends RecyclerView.ViewHolder {
        View style2View;
        @BindView(R.id.tv_item2_title)
        TextView titleItem2;

        @BindView(R.id.iv_item2_image)
        ImageView imageItem2;

        @BindView(R.id.tv_item2_author)
        TextView authorItem2;

        @BindView(R.id.tv_item2_time)
        TextView timeItem2;

        @BindView(R.id.ib_item2)
        ImageButton imageButton2;

        public TwoHolder(@NonNull View itemView) {
            super(itemView);
            style2View = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

    static class ThreeHolder extends RecyclerView.ViewHolder {
        View style3View;
        @BindView(R.id.tv_item3_title)
        TextView titleItem3;

        @BindView(R.id.iv_item3_image)
        ImageView imageItem3;

        @BindView(R.id.tv_item3_time)
        TextView timeItem3;

        @BindView(R.id.tv_item3_author)
        TextView authorItem3;

        @BindView(R.id.ib_item3)
        ImageButton imageButton3;

        public ThreeHolder(@NonNull View itemView) {
            super(itemView);
            style3View = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 3 == 0) {
            return STYLE_ONE;
        } else if (position % 3 == 1) {
            return STYLE_TWO;
        } else {
            return STYLE_THREE;
        }
    }

    public Tab1RecyclerViewAdapter(List<News> newsList, Activity activity) {
        this.newsList = newsList;
        this.activity = activity;
        Log.d("ttw", "newsList: " + newsList);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == STYLE_ONE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item1, parent, false);
            final OneHolder holder1 = new OneHolder(view);
            holder1.style1View.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //点击新闻跳转详情页面(活动)
                    int position = holder1.getAdapterPosition();
                    News news = newsList.get(position);
                    try {
                        String url = news.getmUrl();
                        if (url != null) {
                            Intent intent = new Intent(MyApplication.getContext(), NewsDetails.class);
                            intent.putExtra("newsUrl", url);
                            activity.startActivity(intent);
                        } else {
                            Toast.makeText(MyApplication.getContext(), "此条新闻没有详情页面",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            holder1.imageButton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder1.getAdapterPosition();
                    newsList.remove(position);

                }
            });
            return holder1;
        } else if (viewType == STYLE_TWO) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item2, parent, false);
            final TwoHolder holder2 = new TwoHolder(view);
            holder2.imageButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder2.getAdapterPosition();
                    newsList.remove(position);
                }
            });
            holder2.style2View.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳转新闻详情
                    int position = holder2.getAdapterPosition();
                    News news = newsList.get(position);
                    try {
                        String url = news.getmUrl();
                        if (url != null) {
                            Intent intent = new Intent(MyApplication.getContext(), NewsDetails.class);
                            intent.putExtra("newsUrl", url);
                            activity.startActivity(intent);
                        } else {
                            Toast.makeText(MyApplication.getContext(), "此条新闻没有详情页面",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            return holder2;
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item3, parent, false);
            final ThreeHolder holder3 = new ThreeHolder(view);
            holder3.style3View.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳转详情页面
                    int position = holder3.getAdapterPosition();
                    News news = newsList.get(position);
                    try {
                        String url = news.getmUrl();
                        if (url != null) {
                            Intent intent = new Intent(MyApplication.getContext(), NewsDetails.class);
                            intent.putExtra("newsUrl", url);
                            activity.startActivity(intent);
                        } else {
                            Toast.makeText(MyApplication.getContext(), "此条新闻没有详情页面",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            holder3.imageButton3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder3.getAdapterPosition();
                    newsList.remove(position);
                }
            });
            return holder3;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        News news = newsList.get(position);
        if (holder instanceof OneHolder) {
            OneHolder holder1 = ((OneHolder) holder);
            holder1.titleItem1.setText(news.getmTitle());
            holder1.authorItem1.setText(news.getmSource());
            holder1.timeItem1.setText(news.getmTime());
        } else if (holder instanceof TwoHolder) {
            TwoHolder holder2 = ((TwoHolder) holder);
            holder2.titleItem2.setText(news.getmTitle());
            holder2.authorItem2.setText(news.getmSource());
            holder2.timeItem2.setText(news.getmTime());
            Glide.with(MyApplication.getContext()).load(news.getmImage()).into(holder2.imageItem2);
        } else {
            ThreeHolder holder3 = (ThreeHolder) holder;
            holder3.titleItem3.setText(news.getmTitle());
            Glide.with(MyApplication.getContext()).load(news.getmImage()).into(holder3.imageItem3);
            holder3.timeItem3.setText(news.getmTime());
            holder3.authorItem3.setText(news.getmSource());
        }

    }

    @Override
    public int getItemCount() {
        return newsList == null ? 0 : newsList.size();
    }


/*    public void refreshData(ArrayList<News> mNewsList) {

    }

    public void loadMore(ArrayList<News> mNewsList) {

    }*/
}
