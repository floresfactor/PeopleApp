package com.uach.peopleapp.ui;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uach.peopleapp.provider.Contrato;
import com.uach.peopleapp.utilidades.UConsultas;
import com.uach.peopleapp.R;

/**
 * Adaptador para la lista de contactos
 */
public class AdaptadorContactos extends RecyclerView.Adapter<AdaptadorContactos.ViewHolder> {
    private Cursor items;

    // Instancia de escucha
    private OnItemClickListener escucha;

    /**
     * Interfaz para escuchar clicks del recycler
     */
    interface OnItemClickListener {
        public void onClick(ViewHolder holder, String idContacto);
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        // Campos respectivos de un item
        public TextView nombre;

        public ViewHolder(View v) {
            super(v);
            nombre = (TextView) v.findViewById(R.id.nombre_contacto);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            escucha.onClick(this, obtenerIdContacto(getAdapterPosition()));
        }
    }

    /**
     * Obtiene el valor de la columna 'idContacto' basado en la posición actual del cursor
     * @param posicion Posición actual del cursor
     * @return Identificador del contacto
     */
    private String obtenerIdContacto(int posicion) {
        if (items != null) {
            if (items.moveToPosition(posicion)) {
                return UConsultas.obtenerString(items, Contrato.Contactos.ID_CONTACTO);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public AdaptadorContactos(OnItemClickListener escucha) {
        this.escucha = escucha;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contacto, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        items.moveToPosition(position);

        String primerNombre;
        String primerApellido;

        primerNombre = UConsultas.obtenerString(items, Contrato.Contactos.PRIMER_NOMBRE);
        primerApellido = UConsultas.obtenerString(items, Contrato.Contactos.PRIMER_APELLIDO);

        holder.nombre.setText(String.format("%s %s", primerNombre, primerApellido));
    }

    @Override
    public int getItemCount() {
        if (items != null)
            return items.getCount();
        return 0;
    }

    public void swapCursor(Cursor nuevoCursor) {
        if (nuevoCursor != null) {
            items = nuevoCursor;
            notifyDataSetChanged();
        }
    }

    public Cursor getCursor() {
        return items;
    }

}
