package com.lchrislee.longjourney.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lchrislee.longjourney.LongJourneyApplication;
import com.lchrislee.longjourney.R;
import com.lchrislee.longjourney.model.actors.Monster;
import com.lchrislee.longjourney.model.actors.Player;

public class BattleConclusionActivity extends Activity {

    private TextView levelText;
    private TextView goldText;
    private TextView spoilsText;
    private TextView statusText;
    private ProgressBar xp;

    private Player player;
    private Monster monster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conclusion);

        player = ((LongJourneyApplication) getApplication()).getPlayer();
        monster = ((LongJourneyApplication) getApplication()).getMonster();

        initializeUI();
    }

    private void initializeUI(){
        final WatchViewStub watchViewStub = (WatchViewStub) findViewById(R.id.conclusion_layout_stub);
        watchViewStub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub watchViewStub) {
                levelText = (TextView) findViewById(R.id.conclusion_text_level);
                goldText = (TextView) findViewById(R.id.conclusion_text_gold);
                spoilsText = (TextView) findViewById(R.id.conclusion_text_spoils);
                statusText = (TextView) findViewById(R.id.conclusion_status);

                xp = (ProgressBar) findViewById(R.id.conclusion_progress_xp); // TODO Animate, take a look at http://stackoverflow.com/questions/8035682/animate-progressbar-update-in-android
                xp.setProgress(0);
                xp.setSecondaryProgress(0);
                xp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        xp.setProgress(xp.getProgress() + 10);
                    }
                });
                updateUI();
            }
        });
    }

    private void updateUI(){
        levelText.setText(String.valueOf(player.getLevel()));
        goldText.setText(String.valueOf(player.getGold()));

        xp.setProgress(player.getXp());
        if (monster.getCurrentHealth() == 0){
            spoilsText.setText(String.valueOf(monster.getGold()));
            statusText.setText(R.string.conclusion_victory);
            int xpChange = player.getXp() + monster.getXp();
            if (xpChange >= player.getXpNeeded()){
                xp.setSecondaryProgress(xp.getMax());
                xp.setProgress(xp.getMax());
            }else{
                xp.setSecondaryProgress(xpChange);
                xp.setProgress(xpChange);
            }
        }else{
            spoilsText.setText("0");
            statusText.setText(R.string.conclusion_loss);
        }
    }
}
