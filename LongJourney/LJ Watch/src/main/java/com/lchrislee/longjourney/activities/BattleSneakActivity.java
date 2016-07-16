package com.lchrislee.longjourney.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.Button;

import com.lchrislee.longjourney.R;
import com.lchrislee.longjourney.utility.BattleUtility;

public class BattleSneakActivity extends Activity {

    private PercentRelativeLayout monster;
    private Button left;
    private Button right;

    private SneakButtonListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sneak);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                monster = (PercentRelativeLayout) stub.findViewById(R.id.sneak_layout_monster);
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
        monster.setBackground(BattleUtility.getMonsterDrawable(this));
    }

    private class SneakButtonListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent nextActivity;
            if(BattleUtility.determineSneakSuccess(v.getTag())){
                nextActivity = new Intent(v.getContext(), BattleConclusionActivity.class);
                nextActivity.putExtra(BattleConclusionActivity.CONCLUSION, BattleUtility.BATTLE_CONCLUSION_SNEAK);
            }else{
                nextActivity = new Intent(v.getContext(), BattleFightActivity.class);
                nextActivity.putExtra(BattleFightActivity.FROM, BattleUtility.BATTLE_CONCLUSION_SNEAK);
            }
            startActivity(nextActivity);
        }
    }
}
