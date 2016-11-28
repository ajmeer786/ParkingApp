package com.example.ajmeer.myapplicationui;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ajmeer.myapplicationui.R;
import com.example.ajmeer.myapplicationui.UserDetails;
import com.firebase.client.Firebase;

import java.security.SecureRandom;
import java.sql.Time;


public class MainActivity extends AppCompatActivity {
    SecureRandom random = new SecureRandom();
    private Firebase ref;
    private Button buttonNext;
    private EditText plateNum;
    private double plate;
    private EditText phone;
    private EditText car;
    private EditText time;
    private EditText name;
    private int counterUser1 = 1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);

        ref = new Firebase("https://userdata-351c5.firebaseio.com");
        buttonNext = (Button) findViewById(R.id.next);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                plateNum = (EditText) findViewById(R.id.plate);

                phone = (EditText) findViewById(R.id.phone);

                car = (EditText) findViewById(R.id.cartype);

                name = (EditText) findViewById(R.id.name);

                time = (EditText) findViewById(R.id.time);

                String plateNumber = String.valueOf(plateNum.getText());
                String phoneNum = String.valueOf(phone.getText());
                String carMake = String.valueOf(car.getText());
                String Driver = String.valueOf(name.getText());
                String Time = String.valueOf(time.getText());

                UserDetails user = new UserDetails(plateNumber,phoneNum,carMake,Time,Driver);

                ref.getRoot().child("Driver Details").child("Driver"+Integer.toString(counterUser1)).setValue(user);
                counterUser1++;


            }
        });

    }

}
