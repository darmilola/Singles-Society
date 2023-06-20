package com.SinglesSociety.SocialText.RichTextController.spans;

import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.URLSpan;
import android.view.View;

public class ReferenceSpan extends URLSpan implements RTSpan<String> {


    public interface ReferenceSpanListener {
        void onClick(ReferenceSpan referenceSpan);
    }

    public ReferenceSpan(String postId) {
        super(postId);
    }


    @Override
    public void onClick(View view) {
        if (view instanceof ReferenceSpanListener) {
            ((ReferenceSpanListener) view).onClick(this);
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
