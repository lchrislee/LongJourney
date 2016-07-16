package com.lchrislee.longjourney;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.BoxInsetLayout;
import android.view.View;
import android.view.WindowInsets;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lchrislee.longjourney.model.actors.Monster;
import com.lchrislee.longjourney.model.actors.Player;

public class BattleActivity extends Activity {

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

        player = ((LongJourneyApplication) getApplication()).getPlayer();
        monster = ((LongJourneyApplication) getApplication()).getMonster();

        initializeUI();
    }

    private void initializeUI(){final BoxInsetLayout box = (BoxInsetLayout) findViewById(R.id.battle_layout_box);
        box.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
            @Override
            public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
                return insets;
            }
        });

        monsterLevel = (TextView) findViewById(R.id.battle_text_monster_level);
        monsterName = (TextView) findViewById(R.id.battle_text_monster_name);
        monsterImage = (ImageView) findViewById(R.id.battle_image_monster);
        playerImage = (ImageView) findViewById(R.id.battle_image_player);
        monsterHealth = (ProgressBar) findViewById(R.id.battle_progress_monster);
        playerHealth = (ProgressBar) findViewById(R.id.battle_progress_player);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        monsterLevel.setText(String.valueOf(monster.getLevel()));
        monsterName.setText(monster.getName());
        monsterImage.setImageResource(monster.getImage());
        playerImage.setImageResource(player.getWeapon().getImage());
        monsterHealth.setMax(monster.getMaxHealth());
        playerHealth.setMax(player.getMaxHealth());
        updateHealth();
    }

    private void updateHealth(){
        monsterHealth.setProgress(monster.getCurrentHealth());
        playerHealth.setProgress(player.getCurrentHealth());
    }
}
