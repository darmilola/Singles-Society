package com.aure;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aure.UiModels.CompleteProfileModel;
import com.aure.UiModels.Utils.InputDialog;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MyListings extends AppCompatActivity {

    ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
    ViewPager viewPager;
    TabLayout tabLayout;
    TextView  mPhone;
    String mPhoneText;
    ImageView phoneSelect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_listings);
        initView();
    }

    private void initView(){
        mPhone = findViewById(R.id.my_listing_phone);
        phoneSelect = findViewById(R.id.my_listing_phone_select);
        viewPager = findViewById(R.id.listings_viewpager);
        tabLayout = findViewById(R.id.mylistings_tabs);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        mPhoneText = preferences.getString("phonenumber","");

        if(mPhoneText.equalsIgnoreCase("null")){
            mPhone.setText("Not Available");
        }
        else{
            mPhone.setText(mPhoneText);
        }

        phoneSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputDialog inputDialog = new InputDialog(MyListings.this,"Phonenumber","");
                inputDialog.showInputDialog();
                inputDialog.setDialogActionClickListener(new InputDialog.OnDialogActionClickListener() {
                    @Override
                    public void saveClicked(String text) {
                        mPhone.setText(text);
                        CompleteProfileModel mModel = new CompleteProfileModel("phonenumber",text,MyListings.this);
                        mModel.SaveUserInfo();
                        mModel.setSaveInfoListener(new CompleteProfileModel.SaveInfoListener() {
                            @Override
                            public void onSuccess() {
                                preferences.edit().putString("phonenumber",text).apply();
                                Toast.makeText(MyListings.this, "Phone saved successfully", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onImageUploaded(String imaeUrl) {

                            }
                        });

                    }

                    @Override
                    public void cancelClicked() {

                    }
                });
            }
        });
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();


        public ViewPagerAdapter(FragmentManager fm) {
            super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }


        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            switch (position) {

                case 0:
                    return new MyListingProducts();
                case 1:
                    return new SponsoredListings();
                case 2:
                    return new UnderReviewAds();
                case 3:
                    return new RejectedAds();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }


        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }

    void setupViewPager(ViewPager viewPager) {
        adapter.addFragment(new MyListingProducts(), "All");
        adapter.addFragment(new SponsoredListings(), "Sponsored");
        adapter.addFragment(new UnderReviewAds(), "Pending Approval");
        adapter.addFragment(new UnderReviewAds(), "Rejected");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.special_activity_background));
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.special_activity_background));
            // getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS );
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
            // getWindow().setFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS,WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        }
    }
}
