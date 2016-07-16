package com.lchrislee.longjourney.views;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lchrislee.longjourney.R;

/**
 * Created by Chris Lee on 7/16/16.
 */
public class BattleEngageOptionListItemLayout extends LinearLayout
        implements WearableListView.OnCenterProximityListener {

    private ImageView image;
    private TextView text;

    public BattleEngageOptionListItemLayout(Context context) {
        this(context, null);
    }

    public BattleEngageOptionListItemLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BattleEngageOptionListItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        image = (ImageView) findViewById(R.id.item_engage_option_image);
        text = (TextView) findViewById(R.id.item_engage_option_text);
    }

    @Override
    public void onCenterPosition(boolean b) {
        image.getLayoutParams().height = 45;
        image.getLayoutParams().width = 45;
        text.setTextSize(30);
    }

    @Override
    public void onNonCenterPosition(boolean b) {
        image.getLayoutParams().height = 30;
        image.getLayoutParams().width = 30;
        text.setTextSize(24);
    }
}
