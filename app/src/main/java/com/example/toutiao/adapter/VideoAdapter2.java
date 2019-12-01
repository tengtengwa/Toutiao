package com.example.toutiao.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.toutiao.R;
import com.example.toutiao.activity.normal.MyApplication;
import com.example.toutiao.bean.Video;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.shuyu.gsyvideoplayer.GSYVideoManager.TAG;

public class VideoAdapter2 extends ArrayAdapter<Video> {
    private ArrayList<Video> videoList = new ArrayList<>();
    private int resourceId;
    private Context context;
    private OrientationUtils orientationUtils;

    public VideoAdapter2(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.context = context;
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Video video = getItem(position);
        View view;
        final MyViewHolder holder;
        if (convertView == null) {
            view = LayoutInflater.from(MyApplication.getContext()).inflate(resourceId, parent, false);
            holder = new MyViewHolder();
            holder.videoPlayer = view.findViewById(R.id.video_player);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (MyViewHolder) view.getTag();
        }
        assert video != null;
        holder.videoPlayer.setUpLazy(video.getUrl(), true, null,
                null, video.getTitle());
        ImageView imageView = new ImageView(context);
        Glide.with(context).load(video.getFace()).into(imageView);
        holder.videoPlayer.setThumbImageView(imageView);
        holder.videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orientationUtils.resolveByClick();
            }
        });
//增加title
        holder.videoPlayer.getTitleTextView().setVisibility(View.VISIBLE);
//设置返回键
        holder.videoPlayer.getBackButton().setVisibility(View.GONE);
//设置全屏按键功能
/*        holder.videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.videoPlayer.startWindowFullscreen(context, false, true);
                Log.d("ttw", "url: " + video.getUrl());
            }
        });*/
//防止错位设置
        holder.videoPlayer.setPlayTag(TAG);
        holder.videoPlayer.setPlayPosition(position);
//是否根据视频尺寸，自动选择竖屏全屏或者横屏全屏
        holder.videoPlayer.setAutoFullWithSize(true);
//音频焦点冲突时是否释放
        holder.videoPlayer.setReleaseWhenLossAudio(false);
//全屏动画
        holder.videoPlayer.setShowFullAnimation(true);
//小屏时不触摸滑动
        holder.videoPlayer.setIsTouchWiget(false);
        return view;
    }

    class MyViewHolder {
        @BindView(R.id.video_player)
        StandardGSYVideoPlayer videoPlayer;
    }

}
