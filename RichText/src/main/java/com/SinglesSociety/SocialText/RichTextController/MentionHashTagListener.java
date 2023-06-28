package com.SinglesSociety.SocialText.RichTextController;

public interface MentionHashTagListener {

    void onMentioning(CharSequence sequence);
    void onHashTagging(CharSequence sequence);
    void onStopMentioning();
    void onStopHashTags();
    void onMentionStarted();
    void onHashTagsStarted();
}
