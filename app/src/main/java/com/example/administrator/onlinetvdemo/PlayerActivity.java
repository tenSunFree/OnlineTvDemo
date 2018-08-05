package com.example.administrator.onlinetvdemo;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class PlayerActivity extends YouTubeBaseActivity
        implements YouTubePlayer.OnInitializedListener, YouTubePlayer.OnFullscreenListener {

    public static final String VIDEO_ID = "Jf26kc01xLg";                                           // https://www.youtube.com/watch?v=<video_id>
    private YouTubePlayer youTubePlayer;
    private YouTubePlayerView youTubePlayerView;
    private Button fullScreenButton;
    private boolean isFullscreen;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_player);

        /** 初始化youTubePlayerView相關 */
        isFullscreen = false;
        youTubePlayerView = findViewById(R.id.youTubePlayerView);
        youTubePlayerView.initialize(DeveloperKey.DEVELOPER_KEY, this);

        fullScreenButton = findViewById(R.id.fullScreenButton);
        fullScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /** 改成全螢幕顯示 */
                youTubePlayer.setFullscreen(true);
            }
        });
    }

    @Override
    public void onBackPressed() {

        /** 如果目前是全螢幕顯示, 就改成非全螢幕顯示; 如果是非全螢幕顯示, 則退出app */
        if (isFullscreen) {
            youTubePlayer.setFullscreen(false);
        } else {
            super.onBackPressed();
        }
    }

    /** 在youTubePlayerView 初始化成功後, 想做哪些事情 */
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer player, boolean b) {
        this.youTubePlayer = player;

        /** 設置是否切換成全螢幕的監聽 */
        youTubePlayer.setOnFullscreenListener(this);

        /** 判斷是否第一次調用onInitializationSuccess() */
        if (!b) {

            /** 設置讀取影片的部分網址 與設置介面風格 */
            player.loadVideo(VIDEO_ID);
            player.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);                              // 設置介面風格
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    player.play();
                }
            }, 200);
        } else {

            /** 在切換成全螢幕之後, 可以自動繼續播放影片 */
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    player.play();
                }
            }, 200);
        }
    }

    /** 在youTubePlayerView 初始化失敗後, 想做哪些事情 */
    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult result) {
        Toast.makeText(this, "onInitializationFailure", Toast.LENGTH_SHORT).show();
    }

    /** 針對是否全螢幕狀態顯示的監聽 */
    @Override
    public void onFullscreen(boolean b) {
        if (b) {
            isFullscreen = true;
        } else {
            isFullscreen = false;
        }
    }
}
