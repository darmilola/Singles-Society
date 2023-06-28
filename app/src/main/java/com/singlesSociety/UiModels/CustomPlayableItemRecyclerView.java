package com.singlesSociety.UiModels;

import android.content.Context;

import com.singlesSociety.Arvi.widget.PlayableItemsRecyclerView;

public class CustomPlayableItemRecyclerView extends PlayableItemsRecyclerView {
    public CustomPlayableItemRecyclerView(Context context) {
        super(context);
    }

    @Override
    public void setOnReadyState(boolean onReadyState) {
        super.setOnReadyState(onReadyState);
    }
}
