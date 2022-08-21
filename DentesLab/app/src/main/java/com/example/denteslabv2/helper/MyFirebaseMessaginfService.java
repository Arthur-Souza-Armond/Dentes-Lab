package com.example.denteslabv2.helper;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.text.style.BulletSpan;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.denteslabv2.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessaginfService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage notificacao) {

        if(notificacao.getNotification() != null){

            String titulo = notificacao.getNotification().getTitle();
            String corpo = notificacao.getNotification().getBody();

            enviarNotificacao(titulo,corpo);

            Log.i("Info","Titulo: "+titulo + " corpo:" + corpo);

        }

    }

    private void enviarNotificacao(String titulo,String corpo){

        String canal = getString(R.string.default_notification_channel_id);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

      //Criar a notificação
        NotificationCompat.Builder notificacao = new NotificationCompat.Builder(this,canal)
                .setContentTitle(titulo)
                .setContentText( corpo )
                .setSmallIcon( R.drawable.ic_notification_24 )
                .setSound(uri);

        //Recupera notificationManager
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //Verificar a versão
        /*if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){



        }*/

        NotificationChannel channel = new NotificationChannel(canal,"canal",NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel( channel );

        //ENviar a notificacao
        notificationManager.notify(0,notificacao.build());

    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
    }



}
