package com.example.basma.cardsgame;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;
import android.widget.ImageView;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import java.text.DateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;
import android.media.MediaPlayer.OnCompletionListener;




public class MainActivity extends AppCompatActivity {
    ImageButton buttonsWrapper[];

    private TextView timerValue;
    private TextView userName;

    String user_Name;

    private long startTime = 0L;
    private Handler customHandler = new Handler();

    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    int mins=0;
    int secs=0;

    public void hidecards(final ImageView cardOne,final int tag,int delay) {
        Handler ha = new Handler();
        Runnable rn = new Runnable() {
            @Override
            public void run() {
                cardOne.setImageResource(tag);
            }
        };
        ha.postDelayed(rn, delay);
    }



    private Runnable updateTimerThread = new Runnable() {

        public void run() {

            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;

            updatedTime = timeSwapBuff + timeInMilliseconds;

             secs = (int) (updatedTime / 1000);
             mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            timerValue.setText("" + mins + ":"
                    + String.format("%02d", secs) + ":"
                    + String.format("%03d", milliseconds));
            customHandler.postDelayed(this, 0);
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timerValue = (TextView) findViewById(R.id.timerID);
        user_Name = getIntent().getStringExtra("username");
        userName=(TextView)findViewById(R.id.nameTXT);
        userName.setText(user_Name);

        int[] topImages={R.drawable.bird,R.drawable.hamester,R.drawable.hippo,R.drawable.dog};
        MediaPlayer[] pro = {MediaPlayer.create(this,R.raw.bird),MediaPlayer.create(this,R.raw.hamster),MediaPlayer.create(this,R.raw.hippo),MediaPlayer.create(this,R.raw.dog) };
        for (int i=0;i<4;i++)
        {
            pro[i].setAudioStreamType(AudioManager.STREAM_MUSIC);
        }

        final Attachement[] attachements={new Attachement(topImages[0],pro[0]),new Attachement(topImages[1],pro[1]),new Attachement(topImages[2],pro[2]),new Attachement(topImages[3],pro[3])};

        boolean[] topFlags={false,false,false,false};
        boolean[] bottomFlags={false,false,false,false};
        buttonsWrapper=new ImageButton[8];
        buttonsWrapper[0]=(ImageButton)findViewById(R.id.first);
        buttonsWrapper[1]=(ImageButton)findViewById(R.id.second);
        buttonsWrapper[2]=(ImageButton)findViewById(R.id.third);
        buttonsWrapper[3]=(ImageButton)findViewById(R.id.fourth);
        buttonsWrapper[4]=(ImageButton)findViewById(R.id.fifth);
        buttonsWrapper[5]=(ImageButton)findViewById(R.id.sixth);
        buttonsWrapper[6]=(ImageButton)findViewById(R.id.seventh);
        buttonsWrapper[7]=(ImageButton)findViewById(R.id.eighth);

        for(int i = 0 ; i<4;i++)
        {
            int max = 4;
            Random r = new Random();

            int randomNum = r.nextInt(max)+1;
            while(topFlags[randomNum-1])
            {
                randomNum = r.nextInt(max)+1;
            }
            buttonsWrapper[i].setTag(randomNum-1);
            topFlags[randomNum-1]=true;
        }

        for(int i = 4 ; i<8;i++)
        {
            int max = 4;
            Random r = new Random();

            int randomNum = r.nextInt(max)+1;
            while(bottomFlags[randomNum-1])
            {
                randomNum = r.nextInt(max)+1;
            }
            buttonsWrapper[i].setTag(randomNum-1);
            bottomFlags[randomNum-1]=true;
        }



        final boolean[] one_opened = {false};
        final boolean[] two_opened = {false};

        final int[] last_Tag = {-1543290876};

        final int[] score = {0};

        final int[] last_Index = {-1};

        final int[] done={0};

        startTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(updateTimerThread, 0);



        /*
        *
        * }

        * */


        for(int i =0 ;i<8;i++)
        {
            final int z=i;
            buttonsWrapper[z].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {


                    if (!two_opened[0]) {
                        String t = buttonsWrapper[z].getTag().toString();
                        int tag = Integer.parseInt(t);

                        if(tag!=-40 && (z!=last_Index[0]))
                        {


                            hidecards(buttonsWrapper[z],attachements[tag].getImage(),100);
                            attachements[tag].getAudio().start();


                            for(int tt=0;tt<8;tt++)
                            {
                                buttonsWrapper[tt].setEnabled(false);
                            }

                            attachements[tag].getAudio().setOnCompletionListener(new OnCompletionListener() {

                                public void onCompletion(MediaPlayer mp) {
                                    for(int tt=0;tt<8;tt++)
                                    {
                                        buttonsWrapper[tt].setEnabled(true);
                                    }
                                }
                            });


                            if (one_opened[0]) {


                                two_opened[0] = true;

                                if ((tag == last_Tag[0])) {

                                    Toast.makeText(MainActivity.this, "correct", Toast.LENGTH_LONG).show();

                                    score[0] += 10;

                                    buttonsWrapper[z].setTag(-40);
                                    buttonsWrapper[last_Index[0]].setTag(-40);
                                    done[0]++;
                                    if (done[0]>=4)
                                    {
                                        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                                        String date = df.format(Calendar.getInstance().getTime());
                                        int finalScore=(score[0]/((mins+1)+secs));
                                        Intent intent = new Intent(MainActivity.this,scoreActivity.class);
                                        Bundle extras = new Bundle();
                                        extras.putString("scoreID", finalScore+"");
                                        extras.putString("userName", user_Name+"");
                                        extras.putString("date", date+"");
                                        intent.putExtras(extras);
                                        startActivity(intent);

                                    }

                                    two_opened[0] = false;
                                    one_opened[0] = false;
                                    last_Tag[0] = -1543290876;
                                    last_Index[0] = -1;
                                }

                                else {




                                    new CountDownTimer(3000, 1000) {

                                        public void onTick(long millisUntilFinished) {

                                            Toast.makeText(MainActivity.this,
                                                    "wrong", Toast.LENGTH_LONG).show();
                                        }

                                        public void onFinish() {

                                            hidecards(buttonsWrapper[last_Index[0]], R.drawable.ic_launcher_foreground, 100);
                                            hidecards(buttonsWrapper[z], R.drawable.ic_launcher_foreground, 100);

                                            two_opened[0] = false;
                                            one_opened[0] = false;
                                            last_Tag[0] = -1543290876;
                                            last_Index[0] = -1;
                                        }

                                    }.start();


                                }





                            }

                            else {
                                last_Tag[0] = tag;
                                last_Index[0] = z;
                                one_opened[0] = true;
                            }

                        }

                    }
                }
            });
        }
    }


}
