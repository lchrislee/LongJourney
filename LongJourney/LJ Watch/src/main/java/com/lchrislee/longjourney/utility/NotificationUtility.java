package com.lchrislee.longjourney.utility;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.lchrislee.longjourney.R;
import com.lchrislee.longjourney.activities.BattleActivity;
import com.lchrislee.longjourney.activities.BattleConclusionActivity;

import java.util.ArrayList;

/**
 * Created by Chris Lee on 7/16/16.
 */
public class NotificationUtility {

    public static final int NOTIFICATION_IDENTIFIER_BATTLE = 0; // TODO MAKE THESE BETTER
    public static final int NOTIFICATION_IDENTIFIER_BATTLE_FIGHT = 1;
    public static final int NOTIFICATION_IDENTIFIER_BATTLE_SNEAK = 2;
    public static final int NOTIFICATION_IDENTIFIER_BATTLE_RUN = 3;

    public static void launchBattleNotification(@NonNull Context context, @DrawableRes int monsterBackground){
        Intent startBattle = new Intent(context, BattleActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_IDENTIFIER_BATTLE, startBattle, 0);

        Log.d("TEST", "Pending Intent created.");

        ArrayList<NotificationCompat.Action> potentialActions = generateBattleActions(context);

        NotificationCompat.WearableExtender extender = new NotificationCompat.WearableExtender();
        extender.setDisplayIntent(pendingIntent);
        extender.setContentIntentAvailableOffline(true);
        extender.setBackground(BitmapFactory.decodeResource(context.getResources(), monsterBackground));
        extender.addActions(potentialActions);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
        notificationBuilder.setSmallIcon(R.drawable.common_full_open_on_phone)
                .setContentTitle("A monster approaches!")
                .setLocalOnly(true)
                .extend(extender);

        Log.d("TEST", "Notification builder setup.");

        NotificationManagerCompat.from(context).notify(NOTIFICATION_IDENTIFIER_BATTLE, notificationBuilder.build());
        Log.d("TEST", "Notification launched.");
    }

    private static @NonNull ArrayList<NotificationCompat.Action> generateBattleActions(@NonNull Context context){
        ArrayList<NotificationCompat.Action> battleActions = new ArrayList<>();

        Intent startBattle = new Intent(context, BattleActivity.class);
        PendingIntent pendingFightIntent = PendingIntent.getActivity(context,
                NOTIFICATION_IDENTIFIER_BATTLE_FIGHT, startBattle, 0);

        NotificationCompat.Action fightAction = new NotificationCompat.Action.Builder(
                R.drawable.common_plus_signin_btn_icon_light_normal,
                "Fight",
                pendingFightIntent)
                .build();

        Intent sneakBattle = new Intent(context, BattleConclusionActivity.class);
        PendingIntent pendingSneakIntent = PendingIntent.getActivity(context,
                NOTIFICATION_IDENTIFIER_BATTLE_SNEAK, sneakBattle, 0);

        NotificationCompat.Action sneakAction = new NotificationCompat.Action.Builder(
                R.drawable.common_google_signin_btn_icon_dark,
                "Sneak",
                pendingSneakIntent)
                .build();

        Intent runBattle = new Intent(context, BattleConclusionActivity.class);
        PendingIntent pendingRunIntent = PendingIntent.getActivity(context,
                NOTIFICATION_IDENTIFIER_BATTLE_RUN, runBattle, 0);

        NotificationCompat.Action runAction = new NotificationCompat.Action.Builder(
                R.drawable.ic_full_sad,
                "Run",
                pendingRunIntent)
                .build();

        battleActions.add(fightAction);
        battleActions.add(sneakAction);
        battleActions.add(runAction);
        return battleActions;
    }
}
