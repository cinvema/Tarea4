package com.fireblend.uitest.ui;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fireblend.uitest.R;
import com.fireblend.uitest.data.ContactDB;
import com.fireblend.uitest.data.DatabaseHelper;
import com.fireblend.uitest.logic.GestorContactos;
import com.fireblend.uitest.logic.MyAdapter;
import com.j256.ormlite.dao.Dao;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {

     private RecyclerView mRecyclerView;
     RecyclerView.Adapter mAdapter;
     RecyclerView.LayoutManager mLayoutManager;
     Button botonAgregarContacto;
     GestorContactos gestorContactos;


    private SharedPreferences prefs;

    DatabaseHelper bdHelper;
    ArrayList<ContactDB> contactDBS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //preferencias del usuario
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        //para que al darle atras  se devuelva al main
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        try {

            if(gestorContactos == null)
                gestorContactos = new GestorContactos(this);

            contactDBS = gestorContactos.querryAll();

        }catch (Exception e){

            Log.d("Error", "Error" + e.getMessage().toString());
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.lista_contactos);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemViewCacheSize(5);
        mRecyclerView.setDrawingCacheEnabled(true);
        mRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        //obtener para saber si son columnas o filas
        Integer valor = Integer.parseInt(prefs.getString("listprefvisual","1"));
        if(valor == 2){//si son dos columnas
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        }else {

            mLayoutManager = new LinearLayoutManager(getApplicationContext()); //crear manager
            mRecyclerView.setLayoutManager(mLayoutManager);
        }
        mAdapter = new MyAdapter(this, contactDBS);
        mRecyclerView.setAdapter(mAdapter);

        //botonAgregarContacto
        botonAgregarContacto =  (Button)findViewById(R.id.agregar_contacto);
        botonAgregarContacto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Contact_Activity.class );
                startActivity(intent);
                finish();
            }
        });
    }


    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(MainActivity.this,
                        PreferencesActivity.class);
                startActivity(intent);
                //finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
