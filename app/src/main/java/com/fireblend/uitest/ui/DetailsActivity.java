package com.fireblend.uitest.ui;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.fireblend.uitest.R;
import com.fireblend.uitest.data.ContactDB;
import com.fireblend.uitest.data.DatabaseHelper;
import com.fireblend.uitest.logic.GestorContactos;
import com.j256.ormlite.dao.Dao;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class DetailsActivity extends AppCompatActivity implements  DetailsFragment.GestorBotones{


    ContactDB contact;
    GestorContactos gestorContactos;
    String fileName = "";
    RelativeLayout realtiveLayoutPadre;

    private static final int PERM_CODE = 1001;
    private SharedPreferences.OnSharedPreferenceChangeListener prefListener;

    //Objeto con el que se van a realizar las transacciones
    //de fragments
    FragmentManager fm;

    //Instancias de los fragmentos que pueden existir dentro del
    //contenedor (R.id.contenedor en activity_main.xml)
    DetailsFragment fragmentDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //La instancia de fragmentUno se inicializa acá a modo de
        //ejemplo. Notar que fragmentDos y fragmentTres se inicializan
        //arriba.
        fragmentDetails = new DetailsFragment();

        //pasarle el contacto vacio
        contact = new ContactDB();
        //El objeto Bundle sirve para enviarle parametros de inicializacion
        //al fragmento
        Bundle argumentos = new Bundle();

//
//        //preferencias del usuario
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//        botonEliminar = (Button)findViewById(R.id.btn_eliminar);
//        Boolean habilitar = prefs.getBoolean("checkboxPref",true);
//
//        if(!habilitar){
//            botonEliminar.setEnabled(false);
//        }else{
//            botonEliminar.setEnabled(true);
//        }
//
//        //color de fondo
//        String color = prefs.getString("listprefcolor","");
//        realtiveLayoutPadre=(RelativeLayout) findViewById(R.id.relative_layout_padre);
//        realtiveLayoutPadre.setBackgroundColor(Color.parseColor(color));


        //get id con el intent
        int id = getIntent().getIntExtra("id", 0);

        try {

            if(gestorContactos == null)
                gestorContactos = new GestorContactos(this);

            contact = gestorContactos.getContacto(id);

            //Mismo diseño de llave-valor
            argumentos.putParcelable("ContactDB", contact);
            //Se le asignan los parametros al fragmento
            fragmentDetails.setArguments(argumentos);

            fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.contenedor, fragmentDetails);
            ft.commit();

        }catch (Exception s){
            Log.d("Error",s.getMessage().toString() );

        }

    }

    //Estos dos metodos corresponden a los dos metodos que esta clase esta
    //obligada a implementar por haberse declarado como implementador de la
    //interfaz declarada en el Fragment (GestorBotones).
    @Override
    public void accionBotonEliminar() {
        new AlertDialog.Builder(DetailsActivity.this)
                .setTitle("Precaución")
                .setMessage("Desea eliminar el registro?.")
                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("MainActivity", "Eliminando registro");
                        //obtener el objeto de la bd, eliminarlo
                        try {
                            gestorContactos.deleteContact(contact);
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }catch (Exception e){

                            Log.d("Error",e.getMessage().toString());
                        }

                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("DelectContact", "Cancelando...");
                    }
                })
                .show();
    }

    @Override
    public void accionBotonDescargar() {
        int permissionCheck = ContextCompat.checkSelfPermission(DetailsActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permissionCheck == PackageManager.PERMISSION_GRANTED){
            //Si tenemos permiso, continuamos
            try {
                continuar();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //Si no, pedimos permiso
            askForPermission();
        }
    }

    /*Metodo para generar el txt*/
    private void continuar() throws IOException {

        if(isExternalStorageWritable()==true){//si tiene espacio

            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOCUMENTS), "DescargasContactos");

            if (file.exists())
            {
                fileName = contact.name+".txt";
                File archivo = new File(file+ "/"+fileName);
                if (!archivo.exists()) {
                    archivo.createNewFile();
                }
                FileOutputStream fileOutputStream = new FileOutputStream(archivo,true);
                fileOutputStream.write(("Nombre: " +contact.getName().toString() +", Edad: "+contact.getAge()+
                        ", Provincia: "+ contact.getProvince() +", Correo: "+ contact.getEmail()+
                        ", Telefono: "+ contact.getPhone()).getBytes());

                Toast.makeText(this, "El archivo se generó con éxito.", Toast.LENGTH_SHORT).show();

            }

        }

    }

    private void askForPermission() {
        //Se solicita permiso. Esta llamada es asincronica, por lo que tenemos que
        //implementar el metodo callback onRequestPermissionResult para procesar la
        //respuesta del usuario (ver abajo)
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PERM_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //Si recibimos al menos un permiso y su valor es igual a PERMISSION_GRANTED, tenemos permiso
        if (grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED){
                accionBotonDescargar();
        } else {
            Toast.makeText(this, ":(", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }



}
