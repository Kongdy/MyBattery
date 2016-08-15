package com.project.kongdy.mybattery;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.project.kongdy.mybattery.view.MyBattery;


public class MainActivity extends AppCompatActivity {

    private MyBattery aaa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aaa = (MyBattery) findViewById(R.id.aaa);

        aaa.animalStart(500);
    }
}
