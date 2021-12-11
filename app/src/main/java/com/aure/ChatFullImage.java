package com.aure;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

public class ChatFullImage extends AppCompatActivity {

    ImageView fullImage;
    String url;
    LinearLayout exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_full_image);
        fullImage = findViewById(R.id.chat_full_image);
        url = getIntent().getStringExtra("imageUrl");
        Picasso.get().load(url).into(fullImage);
        exit = findViewById(R.id.full_image_exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
