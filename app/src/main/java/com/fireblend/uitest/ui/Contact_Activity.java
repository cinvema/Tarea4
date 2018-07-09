package com.fireblend.uitest.ui;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fireblend.uitest.R;
import com.fireblend.uitest.data.ContactDB;
import com.fireblend.uitest.data.DatabaseHelper;
import com.fireblend.uitest.entities.Contact;
import com.j256.ormlite.dao.Dao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cinthya V.
 */

public class Contact_Activity extends AppCompatActivity  {

    SeekBar seekbarEdad;
    TextView tvEdad;
    Spinner spinnerProvincia;
    Button insertarContacto;
    EditText etName;
    EditText etPhone;
    EditText etEmail;

    DatabaseHelper bdHelper;
   // ArrayList<ContactDB> arrayContactos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);


        tvEdad=(TextView)findViewById(R.id.edad);
        seekbarEdad=(SeekBar)findViewById(R.id.sekbar_edad);
        seekbarEdad.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int progres = 1;

            @Override
            public void onProgressChanged(SeekBar seekBar, int avanceBarra, boolean b) {
                if(avanceBarra >= 1) {
                    progres = avanceBarra;
                    tvEdad.setText(progres + "");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //arreglo de provincias String
        String[] provinciasDeterminadas = new String[]{
                "San José",
                "Alajuela",
                "Cartago",
                "Heredia",
                "Guanacaste",
                "Puntarenas",
                "Limón",
                };

        spinnerProvincia=(Spinner)findViewById(R.id.spinner);
        etName=(EditText) findViewById(R.id.nombre);
        etPhone=(EditText)findViewById(R.id.telefono);
        etEmail=(EditText)findViewById(R.id.correo);

        //manejar Spinner mediante array adaptaer
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,android.R.layout.simple_spinner_dropdown_item,provinciasDeterminadas);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProvincia.setAdapter(adapter);

        insertarContacto=(Button)findViewById(R.id.agregar);
        insertarContacto.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        try {
                            String strName = etName.getText().toString().trim();
                            int edad =  Integer.parseInt(tvEdad.getText().toString().trim());
                            String email = etEmail.getText().toString();
                            String phone = etPhone.getText().toString();
                            String provincia = spinnerProvincia.getSelectedItem().toString();

                            //Inicializamos el DBHelper
                            if(bdHelper == null) {
                                bdHelper = new DatabaseHelper(Contact_Activity.this);
                            }

                            //Recuperamos el dao
                            //Recuperamos el dao
                            Dao<ContactDB, Integer> userDao = bdHelper.getContactDao();

                            ContactDB nuevo = new ContactDB();
                            nuevo.name = strName;
                            nuevo.age = edad;
                            nuevo.email = email;
                            nuevo.phone = phone;
                            nuevo.province = provincia;

                            userDao.create(nuevo);
                            finish();
                        } catch (Exception e){
                            Toast.makeText(Contact_Activity.this, "Error creando cuenta.", Toast.LENGTH_SHORT).show();
                        }

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent); //iniciar nuevo intent
                    }
                }
        );

    }

}
