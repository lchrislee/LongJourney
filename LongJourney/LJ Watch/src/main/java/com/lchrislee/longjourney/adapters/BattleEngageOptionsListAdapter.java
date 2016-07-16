package com.lchrislee.longjourney.adapters;

import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lchrislee.longjourney.R;
import com.lchrislee.longjourney.utility.BattleUtility;

/**
 * Created by Chris Lee on 7/16/16.
 */
public class BattleEngageOptionsListAdapter extends WearableListView.Adapter {

    private static final int NUM_OPTIONS = 3;

    private class OptionsViewHolder extends WearableListView.ViewHolder{

        private ImageView image;
        private TextView text;

        public OptionsViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.item_engage_option_image);
            text = (TextView) itemView.findViewById(R.id.item_engage_option_text);
        }
    }

    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OptionsViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_list_item_battle_engage_option, null));
    }

    @Override
    public void onBindViewHolder(WearableListView.ViewHolder holder, int position) {
        OptionsViewHolder option = (OptionsViewHolder) holder;
        int selectedIndex;
        switch(position){
            case 1:
                selectedIndex = BattleUtility.BATTLE_OPTION_SNEAK;
                option.text.setText(R.string.engage_option_sneak);
                option.image.setImageResource(R.drawable.ic_full_cancel);
                break;
            case 2:
                selectedIndex = BattleUtility.BATTLE_OPTION_RUN;
                option.text.setText(R.string.engage_option_run);
                option.image.setImageResource(R.drawable.open_on_phone);
                break;
            default:
                option.image.setImageResource(R.drawable.ic_full_sad);
                option.text.setText(R.string.engage_option_fight);
                selectedIndex = BattleUtility.BATTLE_OPTION_FIGHT;
        }
        option.itemView.setTag(selectedIndex);
    }

    @Override
    public int getItemCount() {
        return NUM_OPTIONS;
    }
}
