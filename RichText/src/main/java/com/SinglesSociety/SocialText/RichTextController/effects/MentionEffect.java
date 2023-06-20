package com.SinglesSociety.SocialText.RichTextController.effects;

import android.text.Spannable;
import android.text.Spanned;

import com.SinglesSociety.SocialText.RichTextController.RTEditText;
import com.SinglesSociety.SocialText.RichTextController.spans.MentionSpan;
import com.SinglesSociety.SocialText.RichTextController.spans.RTSpan;
import com.SinglesSociety.SocialText.RichTextController.utils.Selection;

import org.json.JSONException;
import org.json.JSONObject;

public class MentionEffect extends CharacterEffect<String, MentionSpan> {

    private String displayName;
    JSONObject jsonObject;
    String mentionJson;

    @Override
    protected RTSpan<String> newSpan(String mentionJson) {
        MentionSpan mentionSpan = new MentionSpan(mentionJson);
        return mentionSpan;
    }

    @Override
    public void applyToSelection(RTEditText editor, String mentionJson) {
        Selection selection = getSelection(editor);
        Spannable str = editor.getText();
        try {
            jsonObject = new JSONObject(mentionJson);
            displayName = jsonObject.getString("displayName");
            this.mentionJson = mentionJson;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (RTSpan<String> span : getSpans(str, selection, SpanCollectMode.EXACT)) {
            str.removeSpan(span);
        }
        str.setSpan(newSpan(mentionJson), selection.start(), selection.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
}