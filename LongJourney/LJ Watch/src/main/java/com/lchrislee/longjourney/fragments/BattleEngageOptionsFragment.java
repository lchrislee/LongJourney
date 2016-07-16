package com.lchrislee.longjourney.fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lchrislee.longjourney.R;
import com.lchrislee.longjourney.activities.BattleConclusionActivity;
import com.lchrislee.longjourney.activities.BattleFightActivity;
import com.lchrislee.longjourney.activities.BattleSneakActivity;
import com.lchrislee.longjourney.adapters.BattleEngageOptionsListAdapter;
import com.lchrislee.longjourney.utility.BattleUtility;

public class BattleEngageOptionsFragment extends Fragment implements WearableListView.ClickListener{

    public BattleEngageOptionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_engage_options, container, false);

        final WearableListView list = (WearableListView) v.findViewById(R.id.engage_options_list);
        list.setAdapter(new BattleEngageOptionsListAdapter());
        list.setClickListener(this);
        list.setGreedyTouchMode(true);

        return v;
    }

    @Override
    public void onClick(WearableListView.ViewHolder viewHolder) {
        Intent nextActivity;
        int type = (Integer) viewHolder.itemView.getTag();
        switch(type){
            case BattleUtility.BATTLE_OPTION_FIGHT:
                nextActivity = new Intent(getActivity(), BattleFightActivity.class);
                nextActivity.putExtra(BattleFightActivity.FROM, BattleUtility.BATTLE_OPTION_FIGHT);
                break;
            case BattleUtility.BATTLE_OPTION_SNEAK:
                nextActivity = new Intent(getActivity(), BattleSneakActivity.class);
                break;
            case BattleUtility.BATTLE_OPTION_RUN:
                nextActivity = new Intent(getActivity(), BattleConclusionActivity.class);
                nextActivity.putExtra(BattleConclusionActivity.CONCLUSION, BattleUtility.BATTLE_OPTION_RUN);
                break;
            default:
                nextActivity = null;
        }
        if (nextActivity != null){
            startActivity(nextActivity);
        }
    }

    @Override
    public void onTopEmptyRegionClick() {

    }
}
