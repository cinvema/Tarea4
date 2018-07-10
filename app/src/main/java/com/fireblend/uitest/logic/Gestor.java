package com.fireblend.uitest.logic;

import android.content.Context;

public class Gestor {
    //Variable singleton privada, solo accesible por medio del
    //método obtenerServicio()
    private static GestorContactos singleton;

    //Metodo por medio se accesa la instancia singleton
    public static GestorContactos obtenerGestor(Context context){


        if(singleton == null) {
             singleton = new GestorContactos(context);
        }

        //Se retorna la instancia
        return singleton;
    }
}
