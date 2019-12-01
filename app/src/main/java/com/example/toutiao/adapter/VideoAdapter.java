package com.example.toutiao.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toutiao.R;
import com.example.toutiao.activity.normal.MyApplication;
import com.example.toutiao.bean.Video;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.video.ListGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.shuyu.gsyvideoplayer.GSYVideoManager.TAG;

public class VideoAdapter extends RecyclerView.Adapter {

    ArrayList<Video> urlList;

    public VideoAdapter(ArrayList<Video> urlList) {
        this.urlList = urlList;
    }

    static class videoHolder extends RecyclerView.ViewHolder {
        View itemView;

        @BindView(R.id.video_player)
        StandardGSYVideoPlayer videoPlayer;

        public videoHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.video_item, parent, false);
        final videoHolder holder = new videoHolder(view);
        int pos = holder.getAdapterPosition();
        holder.itemView = view;
        Video video = urlList.get(pos);
        holder.videoPlayer.setUpLazy(video.getUrl(), true, null,
                null, video.getTitle());
        holder.videoPlayer.getTitleTextView().setVisibility(View.GONE);
        holder.videoPlayer.getBackButton().setVisibility(View.GONE);
        holder.videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.videoPlayer.startWindowFullscreen(MyApplication.getContext(), false, true);
            }
        });
        holder.videoPlayer.setPlayTag(TAG);     //??是否是此类的TAG
        holder.videoPlayer.setPlayPosition(GSYVideoManager.instance().getPlayPosition());   //??
        holder.videoPlayer.setAutoFullWithSize(true);
        holder.videoPlayer.setReleaseWhenLossAudio(false);
        holder.videoPlayer.setShowFullAnimation(true);
        holder.videoPlayer.setIsTouchWiget(false);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
