package com.lchrislee.longjourney.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.lchrislee.longjourney.R;
import com.lchrislee.longjourney.utility.BattleUtility;
import com.lchrislee.longjourney.utility.NotificationUtility;

public class BattleSneakActivity extends Activity {

    private ImageView monster;
    private Button left;
    private Button right;

    private SneakButtonListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sneak);

        Intent i = getIntent();
        if (i != null){
            NotificationUtility.cancelNotification(this);
        }

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.sneak_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                monster = (ImageView) stub.findViewById(R.id.sneak_image_monster);
                left = (Button) stub.findViewById(R.id.sneak_button_left);
                left.setTag(BattleUtility.BATTLE_SNEAK_OPTION_LEFT);
                right = (Button) stub.findViewById(R.id.sneak_button_right);
                right.setTag(BattleUtility.BATTLE_SNEAK_OPTION_RIGHT);

                updateUI();
            }
        });
        listener = new SneakButtonListener();
    }

    private void updateUI(){
        left.setOnClickListener(listener);
        right.setOnClickListener(listener);
        monster.setImageDrawable(BattleUtility.getMonsterDrawable(this));
    }

    private class SneakButtonListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent nextActivity;
            if(BattleUtility.determineSneakSuccess(v.getTag())){
                nextActivity = new Intent(v.getContext(), SpoilsActivity.class);
                nextActivity.putExtra(SpoilsActivity.CONCLUSION, BattleUtility.BATTLE_OPTION_SNEAK);
            }else{
                nextActivity = new Intent(v.getContext(), BattleFightActivity.class);
                nextActivity.putExtra(BattleFightActivity.FROM, BattleUtility.BATTLE_OPTION_SNEAK);
            }
            startActivity(nextActivity);
        }
    }
}
