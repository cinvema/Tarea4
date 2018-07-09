package com.fireblend.uitest.logic;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fireblend.uitest.R;
import com.fireblend.uitest.data.ContactDB;
import com.fireblend.uitest.ui.DetailsActivity;

import java.util.List;


/**
 * Created by Cinthya V.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    private List<ContactDB> mData;
    private LayoutInflater mInflater;
    private Context context;
    LinearLayout layoutPadre;

    // data is passed into the constructor
   public MyAdapter(Context context, List<ContactDB> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context=context;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item,parent,false);
        ViewHolder vh = new ViewHolder(view, this.context);
        return vh;
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

       try {
           holder.tvNombre.setText(mData.get(position).name);
           holder.tvEdad.setText(mData.get(position).age + "");
           holder.tvCorreo.setText(mData.get(position).email);
           holder.tvTelefono.setText(mData.get(position).phone);
           holder.tvProvincia.setText(mData.get(position).province);
           holder.id = mData.get(position).contactId;

       }catch (Exception e ){
           Log.d("mensaje", e.getMessage().toString());
       }

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // el view holder lo que permite es mejorar el procesamiento en cuanto a rapidez en las listas,
    // esto hace que la visualizacion sea mejor y por consiguiente la ezperiencia del usuario.

     class ViewHolder extends RecyclerView.ViewHolder {
         Context contexto;

         TextView tvNombre,tvEdad,tvCorreo,tvTelefono, tvProvincia;
         Button botonAgregar;
         Button botonDetalles;
         public int id=0;

         ViewHolder(View itemView, final Context context) {
            super(itemView);
            this.contexto=context;
            tvNombre = (TextView)itemView.findViewById(R.id.name);
            tvEdad = (TextView)itemView.findViewById(R.id.age);
            tvCorreo = (TextView)itemView.findViewById(R.id.email);
            tvTelefono = (TextView)itemView.findViewById(R.id.phone);
            tvProvincia = (TextView)itemView.findViewById(R.id.province);

             //setear segun las preferencias del usuario
             SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(contexto);

             ///colores
            /* Integer tamanoLetra = Integer.parseInt(prefs.getString("tamano_letra", ""));
             tvNombre.setTextSize(tamanoLetra);
             tvCorreo.setTextSize(tamanoLetra);
             tvEdad.setTextSize(tamanoLetra);
             tvTelefono.setTextSize(tamanoLetra);
             tvProvincia.setTextSize(tamanoLetra);*/

             botonDetalles=(Button)itemView.findViewById(R.id.row_btnEliminar);
             botonDetalles.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {

                     ///llamar activity de eliminar registro y pasarle el id
                     Intent intent = new Intent(contexto, DetailsActivity.class);
                     intent.putExtra("id",id);
                     contexto.startActivity(intent);
                     Log.d("ver", "ver");

                 }
             });
        }

    }

    // convenience method for getting data at click position
    ContactDB getItem(int id) {
        return mData.get(id);
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}

