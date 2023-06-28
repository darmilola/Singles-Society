package com.SinglesSociety.SocialText.RichTextController.spans;

import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.URLSpan;
import android.view.View;

public class MentionSpan extends URLSpan implements RTSpan<String> {


    private String mentionJson;

public interface MentionSpanListener {
    void onClick(MentionSpan mentionSpan);
}

    public MentionSpan(String mentionJson) {
         super(mentionJson);
         this.mentionJson = mentionJson;
    }

    public void setMentionJson(String mentionJson) {
        this.mentionJson = mentionJson;
    }

    public String getMentionJson() {
        return mentionJson;
    }

    @Override
    public void onClick(View view) {
        if (view instanceof MentionSpanListener) {
            ((MentionSpanListener) view).onClick(this);
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
