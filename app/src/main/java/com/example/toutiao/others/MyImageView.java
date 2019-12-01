package com.example.toutiao.others;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.toutiao.activity.normal.MyApplication;

import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyImageView extends ImageView {
    public static final int TYPE_SUCCESS = 0;
    public static final int TYPE_FAIL = 1;

    public MyImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyImageView(Context context, Myhandler handler) {
        super(context);
        this.handler = handler;
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs, Myhandler handler) {
        super(context, attrs);
        this.handler = handler;
    }

    private class Myhandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case TYPE_SUCCESS:
                    Bitmap bitmap = (Bitmap) msg.obj;
                    setImageBitmap(bitmap);
                    break;
                case TYPE_FAIL:
                    Toast.makeText(MyApplication.getContext(), "加载失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    Myhandler handler = new Myhandler();

    public void setImageByUrl(final String path) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(path);
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(url)
                            .build();
                    Response response = client.newCall(request).execute();
                    if (response != null) {
                        Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                        Message message = Message.obtain();
                        message.what = TYPE_SUCCESS;
                        message.obj = bitmap;
                        handler.sendMessage(message);
                    } else {
                        handler.sendEmptyMessage(TYPE_FAIL);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

}
