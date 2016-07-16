package com.lchrislee.longjourney.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lchrislee.longjourney.LongJourneyApplication;
import com.lchrislee.longjourney.R;
import com.lchrislee.longjourney.model.actors.Monster;
import com.lchrislee.longjourney.model.actors.Player;
import com.lchrislee.longjourney.utility.BattleUtility;

public class BattleFightActivity extends Activity {
    public static final String FROM = "com.lchrislee.longjourney.activities.BattleFightActivity.FROM";

    private TextView monsterLevel;
    private TextView monsterName;
    private ImageView monsterImage;
    private ImageView playerImage;
    private ProgressBar monsterHealth;
    private ProgressBar playerHealth;

    private Monster monster;
    private Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);

        Intent i = getIntent();
        if (i != null){
            int from = i.getIntExtra(FROM, -1);
            switch(from){
                case BattleUtility.BATTLE_OPTION_SNEAK:
                    Toast.makeText(this, R.string.battle_caught, Toast.LENGTH_SHORT).show();
                    break;
                case BattleUtility.BATTLE_OPTION_FIGHT:
                    Toast.makeText(this, R.string.battle_charge, Toast.LENGTH_SHORT).show();
                default:
                    break;
            }
        }

        player = ((LongJourneyApplication) getApplication()).getPlayer();
        monster = ((LongJourneyApplication) getApplication()).getMonster();

        initializeUI();
    }

    private void initializeUI(){
        final WatchViewStub watchViewStub = (WatchViewStub) findViewById(R.id.battle_layout_stub);
        watchViewStub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub watchViewStub) {
                monsterLevel = (TextView) findViewById(R.id.battle_text_monster_level);
                monsterName = (TextView) findViewById(R.id.battle_text_monster_name);
                monsterImage = (ImageView) findViewById(R.id.battle_image_monster);
                playerImage = (ImageView) findViewById(R.id.battle_image_player);
                monsterHealth = (ProgressBar) findViewById(R.id.battle_progress_monster);
                playerHealth = (ProgressBar) findViewById(R.id.battle_progress_player);
                updateUI();
            }
        });
    }

    private void updateUI() {
        monsterLevel.setText(String.valueOf(monster.getLevel()));
        monsterName.setText(monster.getName());
        monsterImage.setImageResource(monster.getImage());
        playerImage.setImageResource(R.drawable.rubber_chicken);
        monsterHealth.setMax(monster.getMaxHealth());
        playerHealth.setMax(player.getMaxHealth());
        updateHealth();
    }

    private void updateHealth(){
        monsterHealth.setProgress(monster.getCurrentHealth());
        playerHealth.setProgress(player.getCurrentHealth());
    }
}
