package com.singlesSociety;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.singlesSociety.UiModels.CompleteProfileModel;
import com.singlesSociety.UiModels.Utils.InputDialog;
import com.singlesSociety.UiModels.Utils.ListDialog;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.singlesSociety.databinding.ActivityCompleteProfileBinding;
import com.ss.widgets.SSProfileBottomSheetDialog;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class CompleteProfile extends AppCompatActivity {

    private ActivityCompleteProfileBinding viewBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = ActivityCompleteProfileBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());

        viewBinding.addReligion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SSProfileBottomSheetDialog bottomSheet = new SSProfileBottomSheetDialog();
                bottomSheet.show(CompleteProfile.this.getSupportFragmentManager(),"");
            }
        });
        viewBinding.editInfoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}
