package com.uach.peopleapp.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.uach.peopleapp.utilidades.UTiempo;

/**
 * Clase auxiliar para controlar accesos a la base de datos SQLite
 */
public class HelperContactos extends SQLiteOpenHelper {

    static final int VERSION = 1;

    static final String NOMBRE_BD = "people_app.db";


    interface Tablas {
        String CONTACTO = "contacto";
    }

    public HelperContactos(Context context) {
        super(context, NOMBRE_BD, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + Tablas.CONTACTO + "("
                        + Contrato.Contactos._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + Contrato.Contactos.ID_CONTACTO + " TEXT UNIQUE,"
                        + Contrato.Contactos.PRIMER_NOMBRE + " TEXT NOT NULL,"
                        + Contrato.Contactos.PRIMER_APELLIDO + " TEXT,"
                        + Contrato.Contactos.TELEFONO + " TEXT,"
                        + Contrato.Contactos.CORREO + " TEXT,"
                        + Contrato.Contactos.VERSION + " DATE DEFAULT CURRENT_TIMESTAMP,"
                        + Contrato.Contactos.INSERTADO + " INTEGER DEFAULT 1,"
                        + Contrato.Contactos.MODIFICADO + " INTEGER DEFAULT 0,"
                        + Contrato.Contactos.ELIMINADO + " INTEGER DEFAULT 0)");

        // Registro ejemplo #1
        ContentValues valores = new ContentValues();
        valores.put(Contrato.Contactos.ID_CONTACTO, Contrato.Contactos.generarIdContacto());
        valores.put(Contrato.Contactos.PRIMER_NOMBRE, "Roberto");
        valores.put(Contrato.Contactos.PRIMER_APELLIDO, "Gomez");
        valores.put(Contrato.Contactos.TELEFONO, "4444444");
        valores.put(Contrato.Contactos.CORREO, "robertico@mail.com");
        valores.put(Contrato.Contactos.VERSION, UTiempo.obtenerTiempo());

        db.insertOrThrow(Tablas.CONTACTO, null, valores);

        // Registro ejemplo #2
        valores.clear();
        valores.put(Contrato.Contactos.ID_CONTACTO, Contrato.Contactos.generarIdContacto());
        valores.put(Contrato.Contactos.PRIMER_NOMBRE, "Pablo");
        valores.put(Contrato.Contactos.PRIMER_APELLIDO, "Catatumbo");
        valores.put(Contrato.Contactos.TELEFONO, "5555555");
        valores.put(Contrato.Contactos.CORREO, "pablito@mail.com");
        valores.put(Contrato.Contactos.VERSION, UTiempo.obtenerTiempo());
        db.insertOrThrow(Tablas.CONTACTO, null, valores);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        try {
            db.execSQL("DROP TABLE IF EXISTS " + Tablas.CONTACTO);
        } catch (SQLiteException e) {
            // Manejo de excepciones
        }
        onCreate(db);
    }
}
