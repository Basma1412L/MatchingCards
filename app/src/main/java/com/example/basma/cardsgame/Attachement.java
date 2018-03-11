package com.example.basma.cardsgame;

import android.media.MediaPlayer;

/**
 * Created by basma on 06/03/18.
 */

public class Attachement
{
    int image;
    MediaPlayer audio;

    public Attachement(int image,MediaPlayer audio)
    {
        this.image=image;
        this.audio=audio;
    }
    public MediaPlayer getAudio() {
        return audio;
    }

    public int getImage() {
        return image;
    }

    public void setAudio(MediaPlayer audio) {
        this.audio = audio;
    }

    public void setImage(int image) {
        this.image = image;
    }
}