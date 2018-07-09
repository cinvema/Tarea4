package com.fireblend.uitest.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "contacts")
public class ContactDB implements Parcelable {


    @DatabaseField( columnName = "name", canBeNull = false)
    public  String name;

    @DatabaseField( columnName = "age", canBeNull = false)
    public  int age;

    @DatabaseField( columnName = "email", canBeNull = false)
    public  String  email;

    @DatabaseField( columnName = "phone", canBeNull = false)
    public  String phone;

    @DatabaseField( columnName = "province", canBeNull = false)
    public  String province;

    @DatabaseField(generatedId = true, columnName = "contactId", canBeNull = false)
    public int contactId;


    public  ContactDB(){}

    public ContactDB(String name, int age, String email, String phone, String province, int contactId){
        this.name = name;
        this.age = age;
        this.email = email;
        this.phone = phone;
        this.province = province;
        this.contactId = contactId;

    }

    protected ContactDB(Parcel in) {
        name = in.readString();
        age = in.readInt();
        email = in.readString();
        phone = in.readString();
        province = in.readString();
        contactId = in.readInt();
    }

    public static final Creator<ContactDB> CREATOR = new Creator<ContactDB>() {
        @Override
        public ContactDB createFromParcel(Parcel in) {
            return new ContactDB(in);
        }

        @Override
        public ContactDB[] newArray(int size) {
            return new ContactDB[size];
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
        dest.writeInt(contactId);
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

    public int getId() {
        return contactId;
    }

    public void setId(int contactId) {
        this.contactId = contactId;
    }

}
