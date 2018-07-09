package com.fireblend.uitest.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.fireblend.uitest.R;
import com.fireblend.uitest.data.ContactDB;
import com.fireblend.uitest.entities.Contact;


public class DetailsFragment extends Fragment implements View.OnClickListener {


    TextView tvEdad;
    TextView tvProvincia;
    TextView tvNombre;
    TextView tvTelefono;
    TextView tvCorreo;
    Button botonEliminar;
    Button botonDescargar;

    GestorBotones gestorBotones;

    public ContactDB contact;

    public interface GestorBotones{
        void accionBotonEliminar();
        void accionBotonDescargar();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //Se asigna a la instancia de la interfaz el contexto recibido en este
        //metodo, que corresponde al activity padre de este fragment.
        gestorBotones = (GestorBotones) context;


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Inicializacion del view, de manera similar a un activity.
        View view = inflater.inflate(R.layout.fragment_details,
                container, false);

        tvNombre= (TextView) view.findViewById(R.id.tv_nombre);
        tvEdad=(TextView) view.findViewById(R.id.tv_edad);
        tvCorreo = (TextView) view.findViewById(R.id.tv_correo);
        tvTelefono = (TextView) view.findViewById(R.id.tv_telefono);
        tvProvincia = (TextView) view.findViewById(R.id.tv_provincia);
        botonEliminar = (Button)view.findViewById(R.id.btn_eliminar);
        botonDescargar = (Button)view.findViewById(R.id.btn_descargar);

        //Se le asigna esta clase como listener al evento de click
        //de ambos botones
        botonEliminar.setOnClickListener(this);
        botonDescargar.setOnClickListener(this);

        //Se recupera el parametro de inicializacion especificado en MainActivity.java
        //y se cambia el valor del texto a dicho mensaje.

        contact = getArguments().getParcelable("ContactDB");

        //setear los campos
        tvNombre.setText(contact.name.toString());
        tvEdad.setText(contact.age+"");
        tvCorreo.setText(contact.email);
        tvTelefono.setText(contact.phone);
        tvProvincia.setText(contact.province);

        return view;
    }

    @Override
    public void onClick(View view) {
        //Al hacerse click en alguno de los dos botones, la accion
        //de respuesta es realizada por la instancia de la interfaz
        //(ver MainActivity.java).
        if(view.equals(botonEliminar)){

            gestorBotones.accionBotonEliminar();
        } else {
            gestorBotones.accionBotonDescargar();
        }
    }
}
