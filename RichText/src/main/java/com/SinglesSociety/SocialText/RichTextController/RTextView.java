/*
 * Copyright 2015 Emanuel Moecklin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.SinglesSociety.SocialText.RichTextController;

import android.content.Context;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.SinglesSociety.SocialText.RichTextController.api.format.RTFormat;
import com.SinglesSociety.SocialText.RichTextController.api.format.RTText;
import com.SinglesSociety.SocialText.RichTextController.converter.tagsoup.util.StringEscapeUtils;
import com.SinglesSociety.SocialText.RichTextController.spans.HashTagSpan;
import com.SinglesSociety.SocialText.RichTextController.spans.LinkSpan;
import com.SinglesSociety.SocialText.RichTextController.spans.MentionSpan;
import com.SinglesSociety.SocialText.RichTextController.spans.ReferenceSpan;


import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**

 * The actual rich text editor (extending android.widget.EditText).
 */
public class RTextView extends AppCompatTextView implements LinkSpan.LinkSpanListener, ReferenceSpan.ReferenceSpanListener, MentionSpan.MentionSpanListener, HashTagSpan.HashTagSpanListener {

    private MentionClickedListener mentionClickedListener;
    private HashTagClickedListener hashTagClickedListener;

    @Override
    public void onClick(ReferenceSpan referenceSpan) {
        Toast.makeText(getContext(), referenceSpan.getURL(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(LinkSpan linkSpan) {
        Toast.makeText(getContext(), linkSpan.getURL(), Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onClick(HashTagSpan hashTagSpan) {
        Toast.makeText(getContext(), hashTagSpan.getValue(), Toast.LENGTH_SHORT).show();
        if(hashTagClickedListener != null)hashTagClickedListener.onHashTagClicked(hashTagSpan.getValue());
    }

    @Override
    public void onClick(MentionSpan mentionSpan) {
        Toast.makeText(getContext(),StringEscapeUtils.unescapeHtml4(mentionSpan.getValue()), Toast.LENGTH_SHORT).show();
        if(mentionClickedListener != null)mentionClickedListener.onMentionClicked(StringEscapeUtils.unescapeHtml4(mentionSpan.getValue()));
        Log.e(mentionSpan.getValue(), "onClick: ");
    }

    public interface MentionClickedListener{
        public void onMentionClicked(String mentionJson);
    }

    public interface HashTagClickedListener{
        public void onHashTagClicked(String hashtagText);
    }


    public void setHashTagClickedListener(HashTagClickedListener hashTagClickedListener) {

        this.hashTagClickedListener = hashTagClickedListener;
    }

    public void setMentionClickedListener(MentionClickedListener mentionClickedListener) {
        this.mentionClickedListener = mentionClickedListener;
    }

    public RTextView(Context context){
        super(context);
        init();

    }

    public RTextView(Context context,AttributeSet attrs){
        super(context,attrs);
        init();
    }

    public RTextView(Context context,AttributeSet attrs,int defStyle){
        super(context,attrs,defStyle);
        init();
    }

    private void init(){

        setMovementMethod(LinkMovementMethod.getInstance());
    }

    public Spannable cloneSpannable(String mText) {
        String text = mText;
        return new ClonedSpannableString(text != null ? text : "");
    }

    public void setText(RTText rtText){
        if(rtText.getFormat()instanceof RTFormat.Html){
            RTText rtSpanned = rtText.convertTo(RTFormat.SPANNED);
            super.setText(rtSpanned.getText(),BufferType.SPANNABLE);
        }
        else {
            SpannableString spannableString = detectHashtagsAndMention(rtText.getText());
            super.setText(spannableString);
        }
    }
    private SpannableString detectHashtagsAndMention(CharSequence text){
        SpannableString spannableString = new SpannableString(text);
        Matcher tagMatcher = Pattern.compile("#([A-Za-z0-9_-]+)").matcher(text);

        int i = 0;
        while (tagMatcher.find()) {
            int j = i + 1;
            i = j;
            RTTextViewHashTagsSpan RTTextViewHashTagsSpan = new RTTextViewHashTagsSpan(ContextCompat.getColor(getContext(), R.color.hashtag_color)) {
                @Override
                public void onClick(@NonNull View widget) {

                    if(hashTagClickedListener != null)hashTagClickedListener.onHashTagClicked(("(text.subSequence(tagMatcher.start(),tagMatcher.end()))).toString()"));
                }
            };

            spannableString.setSpan(RTTextViewHashTagsSpan, tagMatcher.start(), tagMatcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        }
        Matcher mentionMatcher = Pattern.compile("@([A-Za-z0-9_-\\u00A0]+)").matcher(text);

        int k = -1;
        while (mentionMatcher.find()) {
            int l = k + 1;
            k = l;
            RTTextViewMentionsSpan rtTextViewMentionsSpan = new RTTextViewMentionsSpan(ContextCompat.getColor(getContext(), R.color.mention_color)) {
                @Override
                public void onClick(@NonNull View widget) {

                    if(mentionClickedListener != null && mentionMatcher.find()) mentionClickedListener.onMentionClicked((text.subSequence(mentionMatcher.start(),mentionMatcher.end()).toString()));
                }
            };
            spannableString.setSpan(rtTextViewMentionsSpan, mentionMatcher.start(), mentionMatcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        }
        return spannableString;
    }



    public abstract class RTTextViewHashTagsSpan extends ClickableSpan{

        private int mNormalTextColor;

        public RTTextViewHashTagsSpan(int normalTextColor){
            this.mNormalTextColor = normalTextColor;
        }

        @Override
        public void updateDrawState(TextPaint ds){
            super.updateDrawState(ds);
            ds.setUnderlineText(false);
            ds.setColor(mNormalTextColor);
        }
    }
    public abstract class RTTextViewMentionsSpan extends ClickableSpan{
        private boolean mIsPressed;
        private int mNormalTextColor;

        public RTTextViewMentionsSpan(int normalTextColor){
            this.mNormalTextColor = normalTextColor;
        }

        @Override
        public void updateDrawState(TextPaint ds){
            super.updateDrawState(ds);
            ds.setUnderlineText(false);
            ds.setColor(mNormalTextColor);
        }
    }

}




