package com.aure.UiModels;

import android.content.Context;

import com.aure.Arvi.widget.PlayableItemsRecyclerView;

public class CustomPlayableItemRecyclerView extends PlayableItemsRecyclerView {
    public CustomPlayableItemRecyclerView(Context context) {
        super(context);
    }

    @Override
    public void setOnReadyState(boolean onReadyState) {
        super.setOnReadyState(onReadyState);
    }
}
