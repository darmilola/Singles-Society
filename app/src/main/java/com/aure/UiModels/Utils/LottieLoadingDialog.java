package com.aure.UiModels.Utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aure.R;

public class LottieLoadingDialog {

    private Dialog loadingDialog;
    private Context mContext;

    public LottieLoadingDialog(Context context){
        this.mContext = context;
        loadingDialog = new Dialog(mContext);
        loadingDialog.setContentView(R.layout.lottie_loading);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void showLoadingDialog(){
        loadingDialog.show();
    }
    public void cancelLoadingDialog(){
        loadingDialog.dismiss();
    }
}
