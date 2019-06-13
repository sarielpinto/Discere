package com.jhpat.discere;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class AdaptarDatos extends RecyclerView.Adapter<AdaptarDatos.ViewHolderDatos> {

    ArrayList<String> listDatos;

    public AdaptarDatos(ArrayList<String> listDatos){
        this.listDatos = listDatos;
    }

    @Override
    public AdaptarDatos.ViewHolderDatos onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_recycler,null,false);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderDatos holder, int position) {
        holder.asignarDatos(listDatos.get(position));
    }

    @Override
    public int getItemCount() {
        return listDatos.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder{

        TextView dato;
        CardView cart1;

        public ViewHolderDatos(View itemView){
            super(itemView);
            dato = (TextView) itemView.findViewById(R.id.idDato);
            cart1 = (CardView) itemView.findViewById(R.id.cart);
        }

        public void asignarDatos(String s) {
            dato.setText(s);
        }
    }
}
