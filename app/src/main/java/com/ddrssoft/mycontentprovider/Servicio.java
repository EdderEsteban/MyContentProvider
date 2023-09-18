package com.ddrssoft.mycontentprovider;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Telephony;
import android.util.Log;

public class Servicio extends Service {
    private static final String TAG = "MyService";
    private Handler handler;

    public Servicio() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Crear un hilo para realizar la tarea de acceso al Content Provider
        Runnable tarea = new Runnable() {
            @Override
            public void run() {
                // Acceder al Content Provider de mensajes (SMS)
                Uri uri = Uri.parse("content://sms/inbox");
                ContentResolver cr = getContentResolver();
                Cursor cursor = cr.query(uri, null, null, null, "date DESC LIMIT 5");

                if (cursor != null) {
                    int id = 1;
                    while (cursor.moveToNext()) {
                        int fecha = cursor.getColumnIndex(Telephony.Sms.DATE);
                        int contacto = cursor.getColumnIndex(Telephony.Sms.ADDRESS);
                        int mensaje = cursor.getColumnIndex(Telephony.Sms.BODY);

                        String date = cursor.getString(fecha);
                        String address = cursor.getString(contacto);
                        String body = cursor.getString(mensaje);

                        // Mostrar datos en la consola de depuración
                        Log.d(TAG, "Mensaje N°: " + id);
                        Log.d(TAG, "Fecha: " + date);
                        Log.d(TAG, "Número: " + address);
                        Log.d(TAG, "Contenido: " + body);
                        id++;
                    }
                    cursor.close();
                }
            }
        };

        // Ejecutar la tarea cada 9000 milisegundos (9 segundos)
        handler.postDelayed(tarea, 9000);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Detener el servicio
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

}