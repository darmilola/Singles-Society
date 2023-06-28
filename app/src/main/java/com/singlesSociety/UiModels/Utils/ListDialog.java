package com.singlesSociety.UiModels.Utils;

import android.app.Dialog;
import android.content.Context;


import com.singlesSociety.R;
import com.singlesSociety.uiAdapters.ListDialogAdapter;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class ListDialog {

    private Dialog listDialog;
    private Context mContext;
    private ArrayList<String> itemString;
    private RecyclerView recyclerView;
    private OnItemClickedListener itemClickedListener;

    public interface OnItemClickedListener {
        void onItemClicked(String city);
    }

    public ListDialog(ArrayList<String> itemString, Context context){
        listDialog = new Dialog(context);
        this.mContext = context;
        this.itemString = itemString;
        listDialog.setContentView(R.layout.list_dialog_layout);
        recyclerView = listDialog.findViewById(R.id.list_dialog_recyclerview);
    }

    public void setItemClickedListener(OnItemClickedListener cityClickedListener) {
        this.itemClickedListener = cityClickedListener;
    }

    public void showListDialog(){
        listDialog.show();
        ListDialogAdapter dialogAdapter = new ListDialogAdapter(itemString,mContext);
        dialogAdapter.setListItemClickListener(new ListDialogAdapter.ListItemClickListener() {
            @Override
            public void onItemClick(String item) {
                itemClickedListener.onItemClicked(item);
                listDialog.dismiss();
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(dialogAdapter);
    }

}
