package com.SinglesSociety.SocialText.RichTextController.spans;

import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.URLSpan;
import android.view.View;

public class HashTagSpan extends URLSpan implements RTSpan<String> {


    public interface HashTagSpanListener {
        void onClick(HashTagSpan hashTagSpan);
    }

    public HashTagSpan(String hashtagId) {
        super(hashtagId);
    }


    @Override
    public void onClick(View view) {
        if (view instanceof HashTagSpanListener) {
            ((HashTagSpanListener) view).onClick(this);
        }
    }

    @Override
    public String getValue() {
        return getURL();
    }

    @Override
    public void updateDrawState(TextPaint ds){
        super.updateDrawState(ds);
        ds.setUnderlineText(false);
        ds.setColor(Color.parseColor("#044475"));
    }

}
