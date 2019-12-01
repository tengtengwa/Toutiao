package com.example.toutiao.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.toutiao.R;
import com.example.toutiao.activity.normal.MyApplication;
import com.example.toutiao.activity.normal.NewsDetails;
import com.example.toutiao.bean.News;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Tab3RecyclerViewAdapter extends RecyclerView.Adapter {

    private ArrayList<News> newsList;
    private Activity activity;
    private static final int STYLE_ONE = 1;
    private static final int STYLE_TWO = 2;
    private static final int STYLE_THREE = 3;

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

    static class HolderOne extends RecyclerView.ViewHolder {
        View style1View;

        @BindView(R.id.tv_item1_title)
        TextView title1;

        @BindView(R.id.tv_item1_author)
        TextView author1;

        @BindView(R.id.tv_item1_time)
        TextView time1;

        public HolderOne(@NonNull View itemView) {
            super(itemView);
            style1View = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

    static class HolderTwo extends RecyclerView.ViewHolder {
        View style2View;

        @BindView(R.id.tv_item2_title)
        TextView title2;

        @BindView(R.id.tv_item2_author)
        TextView author2;

        @BindView(R.id.tv_item2_time)
        TextView time2;

        @BindView(R.id.iv_item2_image)
        ImageView image2;

        @BindView(R.id.ib_item2)
        ImageButton button2;

        public HolderTwo(@NonNull View itemView) {
            super(itemView);
            style2View = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

    static class HolderThree extends RecyclerView.ViewHolder {
        View style3View;

        @BindView(R.id.tv_item3_title)
        TextView title3;

        @BindView(R.id.tv_item3_author)
        TextView author3;

        @BindView(R.id.tv_item3_time)
        TextView time3;

        @BindView(R.id.iv_item3_image)
        ImageView image3;

        @BindView(R.id.ib_item3)
        ImageButton button3;

        public HolderThree(@NonNull View itemView) {
            super(itemView);
            style3View = itemView;
            ButterKnife.bind(this, itemView);
        }
    }


    public Tab3RecyclerViewAdapter(ArrayList<News> sportsNewsList, FragmentActivity activity) {
        this.newsList = sportsNewsList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == STYLE_ONE) {
            View view = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.rv_item1, parent, false);
            final HolderOne holder1 = new HolderOne(view);
            holder1.style1View.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder1.getAdapterPosition();
                    News news = newsList.get(position);
                    String url = news.getmUrl();
                    if (url != null) {
                        Intent intent = new Intent(MyApplication.getContext(), NewsDetails.class);
                        intent.putExtra("newsUrl", url);
                        activity.startActivity(intent);
                    } else {
                        Toast.makeText(MyApplication.getContext(), "此新闻没有详情页面", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            return holder1;
        } else if (viewType == STYLE_TWO) {
            View view = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.rv_item2, parent, false);
            final HolderTwo holder2 = new HolderTwo(view);
            holder2.style2View.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder2.getAdapterPosition();
                    News news = newsList.get(position);
                    String url = news.getmUrl();
                    if (url != null) {
                        Intent intent = new Intent(MyApplication.getContext(), NewsDetails.class);
                        intent.putExtra("newsUrl", url);
                        activity.startActivity(intent);
                    } else {
                        Toast.makeText(MyApplication.getContext(), "此新闻没有详情页面", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            holder2.button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            return holder2;
        } else {
            View view = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.rv_item3, parent, false);
            final HolderThree holder3 = new HolderThree(view);
            holder3.style3View.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder3.getAdapterPosition();
                    News news = newsList.get(position);
                    String url = news.getmUrl();
                    if (url != null) {
                        Intent intent = new Intent(MyApplication.getContext(), NewsDetails.class);
                        intent.putExtra("newsUrl", url);
                        activity.startActivity(intent);
                    } else {
                        Toast.makeText(MyApplication.getContext(), "此新闻没有详情页面", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            holder3.button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            return holder3;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        News news = newsList.get(position);
        if (holder instanceof HolderOne) {
            HolderOne holder1 = (HolderOne) holder;
            holder1.title1.setText(news.getmTitle());
            holder1.author1.setText(news.getmSource());
            holder1.time1.setText(news.getmTime());
        } else if (holder instanceof HolderTwo) {
            HolderTwo holder2 = (HolderTwo) holder;
            holder2.title2.setText(news.getmTitle());
            holder2.author2.setText(news.getmSource());
            holder2.time2.setText(news.getmTime());
            Glide.with(MyApplication.getContext()).load(news.getmImage()).into(holder2.image2);
        } else if (holder instanceof HolderThree) {
            HolderThree holder3 = (HolderThree) holder;
            holder3.title3.setText(news.getmTitle());
            holder3.author3.setText(news.getmSource());
            holder3.time3.setText(news.getmTime());
            Glide.with(MyApplication.getContext()).load(news.getmImage()).into(holder3.image3);
        }
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

/*    public void refreshData(ArrayList<News> sportsNewsList) {

    }

    public void loadMore(ArrayList<News> sportsNewsList) {

    }*/
}
