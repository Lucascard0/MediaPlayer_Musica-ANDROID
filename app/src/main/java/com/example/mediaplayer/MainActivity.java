package com.example.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer playerProj;
    private SeekBar seekVolume;
    private AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startSeekBar();
    }

    private void startSeekBar(){

        seekVolume = findViewById(R.id.seekVolume);

        //Configura o audio manager
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        //Recuperando os valores de volume máximo e atual
        int volumeMaximo = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int volumeAtual = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        //Configura o valor máximo para o Seekbar
        seekVolume.setMax(volumeMaximo);

        //Configura o valor atual do Seekbar
        seekVolume.setProgress(volumeAtual);

        //Configura a opção de aumentar e diminuir volume
        seekVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }


    public void executarSom(View view){
        if(playerProj == null){
            playerProj = MediaPlayer.create(this, R.raw.teste);
        }
        playerProj.start();
    }

    public void pausarSom(View view){
        if(playerProj.isPlaying()){
            playerProj.pause();
        }
    }

    public void pararSom(View view){
        if (playerProj.isPlaying()){
            playerProj.stop();
            playerProj = MediaPlayer.create(this, R.raw.teste);
        }
    }

    //Limpar o espaço de memória alocado pela música caso no ciclo OnDestroy
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(playerProj != null && playerProj.isPlaying()){
            playerProj.stop();
            playerProj.release();
            playerProj = null;
        }
    }

    //Pausar música qnd o usuário sai do aplicativo (Ciclo OnStop)
    @Override
    protected void onStop() {
        super.onStop();
        if(playerProj.isPlaying()){
            playerProj.pause();
        }
    }
}