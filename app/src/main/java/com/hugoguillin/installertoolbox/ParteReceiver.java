package com.hugoguillin.installertoolbox;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class ParteReceiver extends BroadcastReceiver {

    private static final int NOTIFICATION_ID = 0;
    private static final String CANAL_PARTES_ID = "canal_notificaciones_partes";
    private NotificationManager manager;

    @Override
    public void onReceive(Context context, Intent intent) {
        manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        enviarNotificacion(context);
    }

    private void enviarNotificacion(Context context){
        Intent intent = new Intent(context, Parte_horas.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CANAL_PARTES_ID)
                .setSmallIcon(R.drawable.ic_installertb)
                .setContentTitle("Cubre el parte de horas")
                .setContentText("Es aconsejable que cubras el parte a diario")
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        manager.notify(NOTIFICATION_ID, builder.build());
    }
}
