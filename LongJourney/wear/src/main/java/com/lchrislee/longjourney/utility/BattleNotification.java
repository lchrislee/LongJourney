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

    public static BattleNotification instance()
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

        final NotificationManager notificationManager
            = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }

    void send(@NonNull Context context)
    {
        final int notificationId = notificationNumber.getAndIncrement();

        final WearableExtender extender = new WearableExtender()
            .setContentIntentAvailableOffline(true)
            .addActions(makeActions(context, notificationId));

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

        final NotificationManager notificationManager
            = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId, builder.build());
    }

    public void cancelAll(@NonNull Context context)
    {
        final NotificationManager notificationManager
            = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    private @NonNull NotificationCompat.Action.Builder createAction(
        @NonNull Context context,
        int notificationId,
        @DataPersistence.PlayerLocation int destination,
        int icon,
        int title
    ){
        final Intent battleIntent = new Intent(context, MasterActivity.class);
        battleIntent.putExtra(MasterActivity.NEW_LOCATION, destination);

        final PendingIntent battlePending = PendingIntent.getActivity(
            context,
            notificationId,
            battleIntent,
            PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_CANCEL_CURRENT
        );

        return new NotificationCompat.Action.Builder(
            icon,
            context.getString(title),
            battlePending
        );
    }

    private @NonNull ArrayList<NotificationCompat.Action> makeActions(
        @NonNull Context context,
        int notificationId
    ) {
        ArrayList<NotificationCompat.Action> actions = new ArrayList<>();

        final NotificationCompat.Action.WearableExtender actionExtender
            = new NotificationCompat.Action.WearableExtender()
            .setHintDisplayActionInline(true)
            .setHintLaunchesActivity(true);

        NotificationCompat.Action.Builder battleAction = createAction(
            context,
            notificationId,
            DataPersistence.BATTLE,
            android.R.drawable.sym_action_call,
            R.string.notification_battle_fight
        );
        battleAction.extend(actionExtender);
        actions.add(battleAction.build());

        NotificationCompat.Action.Builder sneakAction = createAction(
            context,
            notificationId,
            DataPersistence.SNEAK,
            android.R.drawable.sym_action_chat,
            R.string.notification_battle_sneak
        );
        sneakAction.extend(actionExtender);
        actions.add(sneakAction.build());

        NotificationCompat.Action.Builder runAction = createAction(
            context,
            notificationId,
            DataPersistence.RUN,
            android.R.drawable.sym_action_email,
            R.string.notification_battle_run
        );
        runAction.extend(actionExtender);
        actions.add(runAction.build());

        return actions;
    }
}
