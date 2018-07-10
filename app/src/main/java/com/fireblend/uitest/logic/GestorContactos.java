package com.fireblend.uitest.logic;

import android.content.Context;

import com.fireblend.uitest.data.ContactDB;
import com.fireblend.uitest.data.DatabaseHelper;
import com.fireblend.uitest.ui.DetailsActivity;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;

public class GestorContactos {

    private Dao<ContactDB, Integer> mContactDao = null;
    DatabaseHelper bdHelper;

    public GestorContactos(Context context){

        try {
            //Inicializamos el DBHelper
            if(bdHelper == null) {
                bdHelper = new DatabaseHelper(context);
            }
            mContactDao = bdHelper.getContactDao();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }//final constructor


    public  ContactDB getContacto(int id){
        ContactDB contacto = null;
        try {
             contacto = mContactDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return  contacto;
    }

    public void deleteContact(ContactDB contactDB){

        try {
            mContactDao.delete(contactDB);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<ContactDB> querryAll(){

        ArrayList<ContactDB> contactDBS= null;
        try {
             contactDBS = (ArrayList)mContactDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return  contactDBS;

    }



}
