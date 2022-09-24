package com.aure.UiModels.Utils;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.aure.R;
import com.daimajia.numberprogressbar.NumberProgressBar;



public class ImageUploadDialog {

    private Context mContext;
    private Dialog uploadDialog;
    private NumberProgressBar  numberProgressBar;

    public ImageUploadDialog(Context context){
        this.mContext = context;
        uploadDialog = new Dialog(mContext);
        uploadDialog.setContentView(R.layout.image_upload_dialog);
        uploadDialog.setCancelable(false);
        numberProgressBar = uploadDialog.findViewById(R.id.image_upload_progress_bar);
        numberProgressBar.setMax(100);
    }

    public void showDialog(){
        uploadDialog.show();
    }

    public void cancelDialog(){
        uploadDialog.dismiss();
    }

    public void updateProgress(float progress){
        numberProgressBar.setProgress((int) progress);
    }
}
