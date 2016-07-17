package com.lchrislee.longjourney.utility;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.lchrislee.longjourney.R;
import com.lchrislee.longjourney.activities.BattleFightActivity;
import com.lchrislee.longjourney.activities.BattleSneakActivity;
import com.lchrislee.longjourney.activities.SpoilsActivity;

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
        ArrayList<NotificationCompat.Action> potentialActions = generateBattleActions(context);

        NotificationCompat.WearableExtender extender = new NotificationCompat.WearableExtender();
        extender.setContentIntentAvailableOffline(true);
        extender.setBackground(BitmapFactory.decodeResource(context.getResources(), monsterBackground));
        extender.addActions(potentialActions);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("A monster approaches!")
                .setContentText("What will you do?")
                .setLocalOnly(true)
                .setAutoCancel(true)
                .setVibrate(new long[]{200, 200, 200, 200, 600})
                .extend(extender);

        NotificationManagerCompat.from(context).notify(NOTIFICATION_IDENTIFIER_BATTLE, notificationBuilder.build());
    }

    private static @NonNull ArrayList<NotificationCompat.Action> generateBattleActions(@NonNull Context context){
        ArrayList<NotificationCompat.Action> battleActions = new ArrayList<>();

        Intent startBattle = new Intent(context, BattleFightActivity.class);
        startBattle.putExtra(BattleFightActivity.FROM, BattleUtility.BATTLE_OPTION_FIGHT);
        PendingIntent pendingFightIntent = PendingIntent.getActivity(context,
                NOTIFICATION_IDENTIFIER_BATTLE_FIGHT, startBattle,
                PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Action fightAction = new NotificationCompat.Action.Builder(
                R.drawable.common_plus_signin_btn_icon_light_normal,
                "Fight",
                pendingFightIntent)
                .build();

        Intent sneakBattle = new Intent(context, BattleSneakActivity.class);
        PendingIntent pendingSneakIntent = PendingIntent.getActivity(context,
                NOTIFICATION_IDENTIFIER_BATTLE_SNEAK, sneakBattle,
                PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Action sneakAction = new NotificationCompat.Action.Builder(
                R.drawable.common_google_signin_btn_icon_dark,
                "Sneak",
                pendingSneakIntent)
                .build();

        Intent runBattle = new Intent(context, SpoilsActivity.class);
        runBattle.putExtra(SpoilsActivity.CONCLUSION, BattleUtility.BATTLE_OPTION_RUN);
        PendingIntent pendingRunIntent = PendingIntent.getActivity(context,
                NOTIFICATION_IDENTIFIER_BATTLE_RUN, runBattle,
                PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_CANCEL_CURRENT);

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
