package com.example.ajmeer.myapplicationui;

import android.widget.EditText;

import java.sql.Time;

/**
 * Created by Ajmeer on 11/28/2016.
 */
public class UserDetails {
    private String plateNum;
    private String phone;
    private String carMake;
    private String time;
    private String name;


       public UserDetails(String plateNum, String phone, String carMake, String time, String name) {
            this.plateNum = plateNum;
            this.phone = phone;
            this.carMake = carMake;
            this.time = time;
            this.name = name;
        }
/**
    public UserDetails(){

    }
    public EditText getPlateNum() {
        return plateNum;
    }

    public void setPlateNum(EditText plateNum) {
        this.plateNum = plateNum;
    }

    public EditText getPhone() {
        return phone;
    }

    public void setPhone(EditText phone) {
        this.phone = phone;
    }

    public EditText getCarMake() {
        return carMake;
    }

    public void setCarMake(EditText carMake) {
        this.carMake = carMake;
    }

    public EditText getTime() {
        return time;
    }

    public void setTime(EditText time) {
        this.time = time;
    }

    public EditText getName() {
        return name;
    }

    public void setName(EditText name) {
        this.name = name;
    }
*/

public UserDetails(){

}
    public String getPlateNum() {
        return plateNum;
    }

    public void setPlateNum(String plateNum) {
        this.plateNum = plateNum;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCarMake() {
        return carMake;
    }

    public void setCarMake(String carMake) {
        this.carMake = carMake;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
