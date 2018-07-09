package com.fireblend.uitest.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sergio on 8/20/2017.
 */

public class Contact implements Parcelable  {

    public String name;
    public int age;
    public String email;
    public String phone;
    public  String province;

    public Contact(String name, int age, String email, String phone, String province){
        this.name = name;
        this.age = age;
        this.email = email;
        this.phone = phone;
        this.province = province;
    }

    protected Contact(Parcel in) {
        name = in.readString();
        age = in.readInt();
        email = in.readString();
        phone = in.readString();
        province = in.readString();
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
        dest.writeString(email);
        dest.writeString(phone);
        dest.writeString(province);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }








}
