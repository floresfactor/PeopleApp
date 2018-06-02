package com.uach.peopleapp.modelo;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.google.gson.Gson;
import com.uach.peopleapp.provider.Contrato;
import com.uach.peopleapp.utilidades.UConsultas;
import com.uach.peopleapp.utilidades.UDatos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Actua como un transformador desde SQLite a JSON para enviar contactos al servidor
 */
public class ProcesadorRemoto {
    private static final String TAG = ProcesadorRemoto.class.getSimpleName();

    // Campos JSON
    private static final String INSERCIONES = "inserciones";
    private static final String MODIFICACIONES = "modificaciones";
    private static final String ELIMINACIONES = "eliminaciones";

    private Gson gson = new Gson();

    private interface ConsultaContactos {

        // Proyección para consulta de contactos
        String[] PROYECCION = {
                Contrato.Contactos.ID_CONTACTO,
                Contrato.Contactos.PRIMER_NOMBRE,
                Contrato.Contactos.PRIMER_APELLIDO,
                Contrato.Contactos.TELEFONO,
                Contrato.Contactos.CORREO,
                Contrato.Contactos.VERSION
        };
    }


    public String crearPayload(ContentResolver cr) {
        HashMap<String, Object> payload = new HashMap<>();

        List<Map<String, Object>> inserciones = obtenerInserciones(cr);
        List<Map<String, Object>> modificaciones = obtenerModificaciones(cr);
        List<String> eliminaciones = obtenerEliminaciones(cr);

        // Verificación: ¿Hay cambios locales?
        if (inserciones == null && modificaciones == null && eliminaciones == null) {
            return null;
        }

        payload.put(INSERCIONES, inserciones);
        payload.put(MODIFICACIONES, modificaciones);
        payload.put(ELIMINACIONES, eliminaciones);

        return gson.toJson(payload);
    }

    public List<Map<String, Object>> obtenerInserciones(ContentResolver cr) {
        List<Map<String, Object>> ops = new ArrayList<>();

        // Obtener contactos donde 'insertado' = 1
        Cursor c = cr.query(Contrato.Contactos.URI_CONTENIDO,
                ConsultaContactos.PROYECCION,
                Contrato.Contactos.INSERTADO + "=?",
                new String[]{"1"}, null);

        // Comprobar si hay trabajo que realizar
        if (c != null && c.getCount() > 0) {

            Log.d(TAG, "Inserciones remotas: " + c.getCount());

            // Procesar inserciones
            while (c.moveToNext()) {
                ops.add(mapearInsercion(c));
            }

            return ops;

        } else {
            return null;
        }

    }

    public List<Map<String, Object>> obtenerModificaciones(ContentResolver cr) {

        List<Map<String, Object>> ops = new ArrayList<>();

        // Obtener contactos donde 'modificado' = 1
        Cursor c = cr.query(Contrato.Contactos.URI_CONTENIDO,
                ConsultaContactos.PROYECCION,
                Contrato.Contactos.MODIFICADO + "=?",
                new String[]{"1"}, null);

        // Comprobar si hay trabajo que realizar
        if (c != null && c.getCount() > 0) {

            Log.d(TAG, "Existen " + c.getCount() + " modificaciones de contactos");

            // Procesar operaciones
            while (c.moveToNext()) {
                ops.add(mapearActualizacion(c));
            }

            return ops;

        } else {
            return null;
        }

    }

    public List<String> obtenerEliminaciones(ContentResolver cr) {

        List<String> ops = new ArrayList<>();

        // Obtener contactos donde 'eliminado' = 1
        Cursor c = cr.query(Contrato.Contactos.URI_CONTENIDO,
                ConsultaContactos.PROYECCION,
                Contrato.Contactos.ELIMINADO + "=?",
                new String[]{"1"}, null);

        // Comprobar si hay trabajo que realizar
        if (c != null && c.getCount() > 0) {

            Log.d(TAG, "Existen " + c.getCount() + " eliminaciones de contactos");

            // Procesar operaciones
            while (c.moveToNext()) {
                ops.add(UConsultas.obtenerString(c, Contrato.Contactos.ID_CONTACTO));
            }

            return ops;

        } else {
            return null;
        }

    }


    /**
     * Desmarca los contactos locales que ya han sido sincronizados
     *
     * @param cr content resolver
     */
    public void desmarcarContactos(ContentResolver cr) {
        // Establecer valores de la actualización
        ContentValues valores = new ContentValues();
        valores.put(Contrato.Contactos.INSERTADO, 0);
        valores.put(Contrato.Contactos.MODIFICADO, 0);

        String seleccion = Contrato.Contactos.INSERTADO + " = ? OR " +
                Contrato.Contactos.MODIFICADO + "= ?";
        String[] argumentos = {"1", "1"};

        // Modificar banderas de insertados y modificados
        cr.update(Contrato.Contactos.URI_CONTENIDO, valores, seleccion, argumentos);

        seleccion = Contrato.Contactos.ELIMINADO + "=?";
        // Eliminar definitivamente
        cr.delete(Contrato.Contactos.URI_CONTENIDO, seleccion, new String[]{"1"});

    }

    private Map<String, Object> mapearInsercion(Cursor c) {
        // Nuevo mapa para reflejarlo en JSON
        Map<String, Object> mapaContacto = new HashMap<String, Object>();

        // Añadir valores de columnas como atributos
        UDatos.agregarStringAMapa(mapaContacto, Contrato.Contactos.ID_CONTACTO, c);
        UDatos.agregarStringAMapa(mapaContacto, Contrato.Contactos.PRIMER_NOMBRE, c);
        UDatos.agregarStringAMapa(mapaContacto, Contrato.Contactos.PRIMER_APELLIDO, c);
        UDatos.agregarStringAMapa(mapaContacto, Contrato.Contactos.TELEFONO, c);
        UDatos.agregarStringAMapa(mapaContacto, Contrato.Contactos.CORREO, c);
        UDatos.agregarStringAMapa(mapaContacto, Contrato.Contactos.VERSION, c);

        return mapaContacto;
    }

    private Map<String, Object> mapearActualizacion(Cursor c) {
        // Nuevo mapa para reflejarlo en JSON
        Map<String, Object> mapaContacto = new HashMap<String, Object>();

        // Añadir valores de columnas como atributos
        UDatos.agregarStringAMapa(mapaContacto, Contrato.Contactos.ID_CONTACTO, c);
        UDatos.agregarStringAMapa(mapaContacto, Contrato.Contactos.PRIMER_NOMBRE, c);
        UDatos.agregarStringAMapa(mapaContacto, Contrato.Contactos.PRIMER_APELLIDO, c);
        UDatos.agregarStringAMapa(mapaContacto, Contrato.Contactos.TELEFONO, c);
        UDatos.agregarStringAMapa(mapaContacto, Contrato.Contactos.CORREO, c);
        UDatos.agregarStringAMapa(mapaContacto, Contrato.Contactos.VERSION, c);

        return mapaContacto;
    }
}
