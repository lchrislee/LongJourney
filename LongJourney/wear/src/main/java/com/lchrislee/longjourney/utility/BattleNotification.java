package com.lchrislee.longjourney.utility;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.WearableExtender;

import com.lchrislee.longjourney.R;
import com.lchrislee.longjourney.activities.MasterActivity;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

// https://developer.android.com/training/wearables/notifications/creating.html
public class BattleNotification {

    private static final String TAG = "BattleNotification";
    private static final String CHANNEL_ID = "LONG_JOURNEY_NOTIFICATION_CHANNEL";
    private static final String CHANNEL_NAME = "BATTLE_NOTIFICATION";
    private static BattleNotification instance;

    private final AtomicInteger notificationNumber;

    private BattleNotification()
    {
        notificationNumber = new AtomicInteger(0);
    }

    public static BattleNotification get()
    {
        if (instance == null)
        {
            instance = new BattleNotification();
        }
        return instance;
    }

    /*
     * Notifications in Android O onwards needs to be in channels for level of importance instead
     * of a numerical level on a per-notification basis.
     * https://developer.android.com/preview/features/notification-channels.html
     */
    @RequiresApi(Build.VERSION_CODES.O)
    public void createNotificationChannel(@NonNull Context context)
    {
        NotificationChannel channel = new NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        );

        channel.enableVibration(true);

        NotificationManager notificationManager
                = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }

    void triggerBattleNotification(@NonNull Context context)
    {
        int notificationId = notificationNumber.getAndIncrement();

        WearableExtender extender = new WearableExtender()
            .setContentIntentAvailableOffline(true)
            .addActions(generateBattleActions(context, notificationId));

        NotificationCompat.Builder builder;

        builder = new NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(context.getString(R.string.notification_battle_title))
            .setContentText(context.getString(R.string.notification_battle_text))
            .setLocalOnly(true)
            .setAutoCancel(true)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .extend(extender)
            .setSmallIcon(android.R.drawable.ic_menu_zoom);

        NotificationManager notificationManager
                = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId, builder.build());
    }

    public void cancelNotification(@NonNull Context context)
    {
        NotificationManager notificationManager
                = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    private @NonNull
    ArrayList<NotificationCompat.Action> generateBattleActions(
        @NonNull Context context,
        int notificationId
    )
    {
        ArrayList<NotificationCompat.Action> actions = new ArrayList<>();

        Intent battleIntent = new Intent(context, MasterActivity.class);
        battleIntent.putExtra(MasterActivity.NEW_LOCATION, DataPersistence.BATTLE);

        PendingIntent battlePending = PendingIntent.getActivity(
            context,
            notificationId,
            battleIntent,
            PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_CANCEL_CURRENT
        );

        NotificationCompat.Action.WearableExtender actionExtender
                = new NotificationCompat.Action.WearableExtender()
                .setHintDisplayActionInline(true)
                .setHintLaunchesActivity(true);

        NotificationCompat.Action.Builder battleAction = new NotificationCompat.Action.Builder(
            android.R.drawable.sym_action_call,
            context.getString(R.string.notification_battle_fight),
            battlePending
        );

        Intent sneakIntent = new Intent(context, MasterActivity.class);
        sneakIntent.putExtra(MasterActivity.NEW_LOCATION, DataPersistence.SNEAK);

        PendingIntent sneakPending = PendingIntent.getActivity(
                context,
                notificationId,
                battleIntent,
                PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_CANCEL_CURRENT
        );

        NotificationCompat.Action.Builder sneakAction = new NotificationCompat.Action.Builder(
                android.R.drawable.sym_action_chat,
                context.getString(R.string.notification_battle_sneak),
                sneakPending
        );

        Intent runIntent = new Intent(context, MasterActivity.class);
        runIntent.putExtra(MasterActivity.NEW_LOCATION, DataPersistence.RUN);

        PendingIntent runPending = PendingIntent.getActivity(
                context,
                notificationId,
                battleIntent,
                PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_CANCEL_CURRENT
        );

        NotificationCompat.Action.Builder runAction = new NotificationCompat.Action.Builder(
                android.R.drawable.sym_action_email,
                context.getString(R.string.notification_battle_run),
                runPending
        );

        battleAction.extend(actionExtender);
        sneakAction.extend(actionExtender);
        runAction.extend(actionExtender);

        actions.add(battleAction.build());
        actions.add(sneakAction.build());
        actions.add(runAction.build());
        return actions;
    }
}
