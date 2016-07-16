package com.lchrislee.longjourney.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lchrislee.longjourney.LongJourneyApplication;
import com.lchrislee.longjourney.R;
import com.lchrislee.longjourney.model.actors.Monster;
import com.lchrislee.longjourney.utility.BattleUtility;

public class BattleEngageDetailFragment extends Fragment {
    private static final String ENTRANCE = "com.lchrislee.longjourney.fragments.BattleEngageDetailFragment.ENTRANCE";

    private TextView monsterName;
    private TextView monsterLevel;
    private ImageView monsterImage;

    private int entrance;

    public BattleEngageDetailFragment() {
        // Required empty public constructor
    }

    public static BattleEngageDetailFragment newInstance(int entrance){
        Bundle b = new Bundle();
        b.putInt(ENTRANCE, entrance);
        BattleEngageDetailFragment frag = new BattleEngageDetailFragment();
        frag.setArguments(b);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null){
            entrance = arguments.getInt(ENTRANCE, -1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        WatchViewStub stub = (WatchViewStub) inflater.inflate(R.layout.fragment_engage_detail, container, false);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub watchViewStub) {
                monsterName = (TextView) watchViewStub.findViewById(R.id.engage_text_monster_name);
                monsterLevel = (TextView) watchViewStub.findViewById(R.id.engage_text_monster_level);
                monsterImage = (ImageView) watchViewStub.findViewById(R.id.engage_image_monster);
                updateUI();
            }
        });

        return stub;
    }

    private void updateUI(){
        final Monster monster = ((LongJourneyApplication) (getActivity().getApplication())).getMonster();
        monsterName.setText(monster.getName());
        monsterLevel.setText(String.valueOf(monster.getLevel()));
        if (entrance == BattleUtility.BATTLE_FROM_TRAVEL){
            monsterImage.setImageDrawable(BattleUtility.getMonsterDrawable(monsterImage.getContext()));
        }else{
            monsterImage.setVisibility(View.GONE);
        }
    }

}
