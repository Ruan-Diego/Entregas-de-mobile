package com.example.myandroidapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

public class SegundaTela extends AppCompatActivity {
    private TabLayout tablayout;
    private ViewPager viewrpage;
    Button botaoVoltar;
    MediaPlayer mySound;

    @Override
    protected void onPause() {
        super.onPause();
        mySound.release();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda_tela);
        setTitle("Segunda Tela");

        mySound=MediaPlayer.create(this, R.raw.som);


        tablayout=findViewById(R.id.tab_layout);
        viewrpage=findViewById(R.id.viewr_page);

        tablayout.setupWithViewPager(viewrpage);

        VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdapter.addFragment(new fragmento01(), "Home");
        vpAdapter.addFragment(new fragmento02(), "Chats");
        vpAdapter.addFragment(new fragmento03(), "Settings");

        viewrpage.setAdapter(vpAdapter);

        botaoVoltar=findViewById(R.id.bt_primeiraTela);
        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent botaoVoltar = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(botaoVoltar);
            }
        });
    }

    public void playMusic(View view) {
        mySound.start();
    }
}