package com.ins.common.common;

/**
 * Created by liaoinstan on 2016/5/27 0027.
 * MediaPlayer实现的播放在线音频的播放器
 */

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.util.Log;

public class AudioPlayer implements OnCompletionListener, MediaPlayer.OnPreparedListener,MediaPlayer.OnErrorListener {
    public MediaPlayer mediaPlayer;
    private String videoUrl;
    private boolean pause;

    public void setUrl(String url) {
        this.videoUrl = url;
    }

    public AudioPlayer() {
        this(null);
    }

    public AudioPlayer(String videoUrl) {
        this.videoUrl = videoUrl;
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        } catch (Exception e) {
            Log.e("mediaPlayer", "error", e);
        }
    }

    /**
     * 播放
     */
    public void play() {
        if (!mediaPlayer.isPlaying()) {
            playNet();
        }
    }

    /**
     * 重播
     */
    public void replay() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(0);// 从开始位置开始播放音乐
        } else {
            playNet();
        }
    }

    /**
     * 暂停
     */
    public boolean pause() {
        if (mediaPlayer.isPlaying()) {// 如果正在播放
            mediaPlayer.pause();// 暂停
            pause = true;
        } else {
            if (pause) {// 如果处于暂停状态
                mediaPlayer.start();// 继续播放
                pause = false;
            }
        }
        return pause;
    }

    /**
     * 停止
     */
    public void stop() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }

    /**
     * 播放音乐
     */
    private void playNet() {
        try {
            mediaPlayer.reset();// 把各项参数恢复到初始状态
            /**
             * 通过MediaPlayer.setDataSource()
             * 的方法,将URL或文件路径以字符串的方式传入.使用setDataSource ()方法时,要注意以下三点:
             * 1.构建完成的MediaPlayer 必须实现Null 对像的检查.
             * 2.必须实现接收IllegalArgumentException 与IOException
             * 等异常,在很多情况下,你所用的文件当下并不存在. 3.若使用URL 来播放在线媒体文件,该文件应该要能支持pragressive
             * 下载.
             */
            mediaPlayer.setDataSource(videoUrl);
            mediaPlayer.prepareAsync();// 进行缓冲
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setOnErrorListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();// 开始播放
        if (onPlayerListener != null) {
            onPlayerListener.onStart();
        }
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        if (onPlayerListener != null) {
            onPlayerListener.onCompleted();
        }
    }

    private OnPlayerListener onPlayerListener;

    public void setOnPlayerListener(OnPlayerListener onPlayerListener) {
        this.onPlayerListener = onPlayerListener;
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return true;
    }

    public interface OnPlayerListener {
        void onStart();

        void onCompleted();
    }

    /**
     * 释放资源
     */
    public void onDestory() {
        mediaPlayer.release();
    }
}
