package com.aure;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.aure.UiModels.Utils.InputDialog;
import com.aure.UiModels.Utils.ListDialog;
import com.google.android.material.button.MaterialButton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class CompleteProfile extends AppCompatActivity {

    private static final int PICK_IMAGE_1 = 1;
    private static final int PICK_IMAGE_2 = 1;
    private static final int PICK_IMAGE_3 = 1;
    LinearLayout previewProfile;
    LinearLayout aboutSelect,statusSelect,languageSelect,citySelect,occupationSelect,goalSelect,educationSelect,workplaceSelect,drinkingSelect,smokingSelect,genderSelect,quoteSelect,religionSelect;
    TextView aboutTextView,ageTextview,statusTextview,languageTextview,cityTextview,occupationTextview,goalTextview,educationTextview,workplaceTextview,drinkingTextview,smokingTextview,genderTextview,quoteTextview,religionTextview;
    InputDialog inputDialog;
    ListDialog listDialog;
    SeekBar ageSelect;
    ImageView image1,image2,image3;
    MaterialButton upload1,upload2,upload3;
    ArrayList<String> maritalItems = new ArrayList<>();
    ArrayList<String> languageItems = new ArrayList<>();
    ArrayList<String> goalsItems = new ArrayList<>();
    ArrayList<String> educationLevelItems = new ArrayList<>();
    ArrayList<String> drinkingItems = new ArrayList<>();
    ArrayList<String> smokingItems = new ArrayList<>();
    ArrayList<String> genderItems = new ArrayList<>();
    ArrayList<String> religionItem = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profile);
        initView();

    }

    private void initView(){
        religionSelect = findViewById(R.id.profile_religion_select);
        religionTextview = findViewById(R.id.religion_select_textview);
        aboutSelect = findViewById(R.id.profile_about_select);
        aboutTextView = findViewById(R.id.about_select_textview);
        ageSelect = findViewById(R.id.age_seek_bar);
        ageTextview = findViewById(R.id.complete_profile_age);
        statusSelect = findViewById(R.id.status_select);
        languageSelect = findViewById(R.id.profile_language_select);
        citySelect = findViewById(R.id.profile_city_select);
        occupationSelect = findViewById(R.id.occupation_select);
        goalSelect = findViewById(R.id.profile_marriage_goals_select);
        educationSelect = findViewById(R.id.profile_education_level_select);
        workplaceSelect = findViewById(R.id.profile_workplace_select);
        drinkingSelect = findViewById(R.id.drinking_select);
        smokingSelect = findViewById(R.id.smoking_select);
        genderSelect = findViewById(R.id.gender_select);
        quoteSelect = findViewById(R.id.quote_select);
        statusTextview = findViewById(R.id.status_select_textview);
        languageTextview = findViewById(R.id.language_select_textview);
        cityTextview = findViewById(R.id.city_select_textview);
        occupationTextview = findViewById(R.id.occupation_textview);
        goalTextview = findViewById(R.id.marriage_goals_select_textview);
        educationTextview = findViewById(R.id.education_level_select_textview);
        workplaceTextview = findViewById(R.id.workplace_select_textview);
        drinkingTextview = findViewById(R.id.drinking_select_textview);
        smokingTextview = findViewById(R.id.smoking_select_textview);
        genderTextview = findViewById(R.id.gender_select_textview);
        quoteTextview = findViewById(R.id.quote_select_textview);
        image1 = findViewById(R.id.first_image_view);
        image2 = findViewById(R.id.second_image_view);
        image3 = findViewById(R.id.third_image_view);
        upload1 = findViewById(R.id.first_image_upload);
        upload2 = findViewById(R.id.second_image_upload);
        upload3 = findViewById(R.id.third_image_upload);
        populateItems();

        ageSelect.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ageTextview.setText("Age("+Integer.toString(progress)+")");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        aboutSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputDialog = new InputDialog(CompleteProfile.this,"About Me",aboutTextView.getText().toString());
                inputDialog.showInputDialog();
                inputDialog.setDialogActionClickListener(new InputDialog.OnDialogActionClickListener() {
                    @Override
                    public void saveClicked(String text) {
                        aboutTextView.setText(text);
                    }

                    @Override
                    public void cancelClicked() {

                    }
                });
            }
        });
        statusSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listDialog = new ListDialog(maritalItems,CompleteProfile.this);
                listDialog.showListDialog();
                listDialog.setItemClickedListener(new ListDialog.OnItemClickedListener() {
                    @Override
                    public void onItemClicked(String item) {
                        statusTextview.setText(item);
                    }
                });
            }
        });
        languageSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listDialog = new ListDialog(languageItems,CompleteProfile.this);
                listDialog.showListDialog();
                listDialog.setItemClickedListener(new ListDialog.OnItemClickedListener() {
                    @Override
                    public void onItemClicked(String item) {
                        languageTextview.setText(item);
                    }
                });
            }
        });
        citySelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputDialog = new InputDialog(CompleteProfile.this,"City",aboutTextView.getText().toString());
                inputDialog.showInputDialog();
                inputDialog.setDialogActionClickListener(new InputDialog.OnDialogActionClickListener() {
                    @Override
                    public void saveClicked(String text) {
                        cityTextview.setText(text);
                    }

                    @Override
                    public void cancelClicked() {

                    }
                });
            }
        });

        occupationSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputDialog = new InputDialog(CompleteProfile.this,"Occupation",aboutTextView.getText().toString());
                inputDialog.showInputDialog();
                inputDialog.setDialogActionClickListener(new InputDialog.OnDialogActionClickListener() {
                    @Override
                    public void saveClicked(String text) {
                        occupationTextview.setText(text);
                    }

                    @Override
                    public void cancelClicked() {

                    }
                });
            }
        });

        goalSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listDialog = new ListDialog(goalsItems,CompleteProfile.this);
                listDialog.showListDialog();
                listDialog.setItemClickedListener(new ListDialog.OnItemClickedListener() {
                    @Override
                    public void onItemClicked(String item) {
                        goalTextview.setText(item);
                    }
                });
            }
        });

        educationSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listDialog = new ListDialog(educationLevelItems,CompleteProfile.this);
                listDialog.showListDialog();
                listDialog.setItemClickedListener(new ListDialog.OnItemClickedListener() {
                    @Override
                    public void onItemClicked(String item) {
                        educationTextview.setText(item);
                    }
                });
            }
        });

        workplaceSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputDialog = new InputDialog(CompleteProfile.this,"Workplace",aboutTextView.getText().toString());
                inputDialog.showInputDialog();
                inputDialog.setDialogActionClickListener(new InputDialog.OnDialogActionClickListener() {
                    @Override
                    public void saveClicked(String text) {
                        workplaceTextview.setText(text);
                    }

                    @Override
                    public void cancelClicked() {

                    }
                });
            }
        });

        drinkingSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listDialog = new ListDialog(drinkingItems,CompleteProfile.this);
                listDialog.showListDialog();
                listDialog.setItemClickedListener(new ListDialog.OnItemClickedListener() {
                    @Override
                    public void onItemClicked(String item) {
                        drinkingTextview.setText(item);
                    }
                });

            }
        });

        smokingSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listDialog = new ListDialog(smokingItems,CompleteProfile.this);
                listDialog.showListDialog();
                listDialog.setItemClickedListener(new ListDialog.OnItemClickedListener() {
                    @Override
                    public void onItemClicked(String item) {
                        smokingTextview.setText(item);
                    }
                });
            }
        });

        genderSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listDialog = new ListDialog(genderItems,CompleteProfile.this);
                listDialog.showListDialog();
                listDialog.setItemClickedListener(new ListDialog.OnItemClickedListener() {
                    @Override
                    public void onItemClicked(String item) {
                        genderTextview.setText(item);
                    }
                });
            }
        });

        quoteSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputDialog = new InputDialog(CompleteProfile.this,"Quote",aboutTextView.getText().toString());
                inputDialog.showInputDialog();
                inputDialog.setDialogActionClickListener(new InputDialog.OnDialogActionClickListener() {
                    @Override
                    public void saveClicked(String text) {
                        cityTextview.setText(text);
                    }
                    @Override
                    public void cancelClicked() {

                    }
                });

            }
        });

        religionSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listDialog = new ListDialog(religionItem,CompleteProfile.this);
                listDialog.showListDialog();
                listDialog.setItemClickedListener(new ListDialog.OnItemClickedListener() {
                    @Override
                    public void onItemClicked(String item) {
                        religionTextview.setText(item);
                    }
                });
            }
        });

        upload1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_1);
            }
        });
        upload2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_2);
            }
        });
        upload3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_3);
            }
        });


        previewProfile = findViewById(R.id.profile_preview);
        previewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CompleteProfile.this,PreviewProfile.class));
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_1 && resultCode == RESULT_OK && null != data) {
            Uri selectedImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                String imageString = BitmapToString(bitmap);
                //startImageUpload(imageString);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if (requestCode == PICK_IMAGE_2 && resultCode == RESULT_OK && null != data) {
            Uri selectedImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                String imageString = BitmapToString(bitmap);
                //startImageUpload(imageString);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if (requestCode == PICK_IMAGE_3 && resultCode == RESULT_OK && null != data) {
            Uri selectedImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                String imageString = BitmapToString(bitmap);
                //startImageUpload(imageString);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public String BitmapToString(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 60, baos);
        byte[] b = baos.toByteArray();
        String imgString = Base64.encodeToString(b, Base64.DEFAULT);
        return imgString;
    }

    @Override
    public void onResume() {

        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.ixpecial));
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.ixpecial));
            // getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS );
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            // getWindow().setFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS,WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }
    }

    private void populateItems(){

        maritalItems.add("Single");
        maritalItems.add("Married");
        maritalItems.add("Divorced");

        goalsItems.add("Ready to marry in 3-6 months");
        goalsItems.add("Ready to marry in 6-12 months");
        goalsItems.add("Ready to marry in 1-2 years");

        languageItems.add("Hausa and English");
        languageItems.add("Ibo and English");
        languageItems.add("Yoruba and English");
        languageItems.add("English Only");

        educationLevelItems.add("Masters");
        educationLevelItems.add("Bachelors");
        educationLevelItems.add("Secondary School");
        educationLevelItems.add("Uneducated");

        drinkingItems.add("d'ont drink");
        drinkingItems.add("drinks occasionally");
        drinkingItems.add("drink always");

        smokingItems.add("d'ont smoke");
        smokingItems.add("Smokes occasionally");
        smokingItems.add("Smoke always");

        genderItems.add("Male");
        genderItems.add("Female");

        religionItem.add("Muslim");
        religionItem.add("Christian");
        religionItem.add("Traditional");
    }
}
