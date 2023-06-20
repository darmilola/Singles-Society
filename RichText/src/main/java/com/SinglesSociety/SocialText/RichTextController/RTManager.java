/*
 * Copyright (C) 2015-2018 Emanuel Moecklin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.SinglesSociety.SocialText.RichTextController;

import android.content.res.Resources;
import android.os.Handler;
import android.text.Editable;
import android.text.Spannable;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

import com.SinglesSociety.SocialText.RichTextController.api.RTApi;
import com.SinglesSociety.SocialText.RichTextController.converter.tagsoup.util.StringEscapeUtils;
import com.SinglesSociety.SocialText.RichTextController.effects.AbsoluteSizeEffect;
import com.SinglesSociety.SocialText.RichTextController.effects.BulletEffect;
import com.SinglesSociety.SocialText.RichTextController.effects.Effect;
import com.SinglesSociety.SocialText.RichTextController.effects.Effects;
import com.SinglesSociety.SocialText.RichTextController.effects.ItalicEffect;
import com.SinglesSociety.SocialText.RichTextController.effects.NumberEffect;
import com.SinglesSociety.SocialText.RichTextController.effects.SpanCollectMode;
import com.SinglesSociety.SocialText.RichTextController.effects.StrikethroughEffect;
import com.SinglesSociety.SocialText.RichTextController.effects.TypefaceEffect;
import com.SinglesSociety.SocialText.RichTextController.effects.UnderlineEffect;
import com.SinglesSociety.SocialText.RichTextController.fonts.RTTypeface;
import com.SinglesSociety.SocialText.RichTextController.spans.HashTagSpan;
import com.SinglesSociety.SocialText.RichTextController.spans.LinkSpan;
import com.SinglesSociety.SocialText.RichTextController.spans.MentionSpan;
import com.SinglesSociety.SocialText.RichTextController.spans.RTSpan;
import com.SinglesSociety.SocialText.RichTextController.spans.ReferenceSpan;
import com.SinglesSociety.SocialText.RichTextController.utils.Selection;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.SinglesSociety.SocialText.RichTextController.RTManager.ToolbarVisibility.*;

/**
 * The RTManager manages the different components:
 * the toolbar(s), the editor(s) and the Activity/Fragment(s) via the RTProxy.
 * <p>
 * Note: the transient modifier is "misused" here to mark variables that
 * are not saved and restored in onSaveInstanceState / onCreate.
 */
public class RTManager implements RTEditTextListener,RTToolbarListener{

    /*
     * Identifies the link dialog / fragment
     */
    private static final String ID_01_LINK_FRAGMENT = "ID_01_LINK_FRAGMENT";

    /*
     * The toolbar(s) may automatically be shown or hidden when a rich text
     * editor gains or loses focus depending on the ToolbarVisibility setting.
     */
    public enum ToolbarVisibility {
        /*
         * Toolbar(s) are shown/hidden automatically depending on whether an
         * editor that uses rich text gains/loses focus.
         *
         * This is the default.
         */
        AUTOMATIC,

        /*
         * Toolbar(s) are always shown.
         */
        SHOW,

        /*
         * Toolbar(s) are never shown.
         */
        HIDE;
    }

    private ToolbarVisibility mToolbarVisibility = AUTOMATIC;

    /*
     * To set the visibility of the toolbar(s) we call setToolbarVisibility(boolean).
     * To change the visibility we start an animation. Before the animation ends
     * setToolbarVisibility() could have been called multiple times with different
     * visibility parameters. We need to make sure once (one of) the animation
     * ends we use the newest visibility status (that's what this variable stands for).
     *
     * We do clear the animation with each call to setToolbarVisibility but if the
     * animation has already started the onAnimationEnd is still called.
     */
    private boolean mToolbarIsVisible;

    /*
     * When an Activity is started (e.g. to pick an image),
     * we need to know which editor gets the result
     */
    private int mActiveEditor = Integer.MAX_VALUE;

    /*
     * This defines what Selection link operations are applied to
     * (inserting, editing, removing links).
     */
    private Selection mLinkSelection;
    private Selection mMentionSelection;
    private Selection mHashtagSelection;
    private Selection mRefSeelection;

    /*
     * We need these to delay hiding the toolbar after a focus loss of an editor
     */
    transient private Handler mHandler;
    transient private boolean mIsPendingFocusLoss;
    transient private boolean mCancelPendingFocusLoss;

    /*
     * Map the registered editors by editor id (RTEditText.getId())
     */
    transient private Map<Integer, RTEditText> mEditors;

    /*
     * Map the registered toolbars by toolbar id (RTToolbar.getId())
     */
    transient private Map<Integer, RTToolbar> mToolbars;

    /*
     * That's our link to "the outside world" to perform operations that need
     * access to a Context or an Activity
     */
    transient private RTApi mRTApi;

    /*
     * The RTOperationManager is used to undo/redo operations
     */
    transient private RTOperationManager mOPManager;

    // ****************************************** Lifecycle Methods *******************************************


    public RTManager(RTApi mRTApi) {

        this.mRTApi = mRTApi;

        mHandler = new Handler();
        mEditors = new ConcurrentHashMap<Integer, RTEditText>();
        mToolbars = new ConcurrentHashMap<Integer, RTToolbar>();
        mOPManager = new RTOperationManager();
        EventBus.getDefault().register(this);
        //EventBus.getDefault().register(this);
    }



    /**
     * Perform any final cleanup before the component is destroyed.
     *
     * @param isSaved True if the text is saved, False if it's dismissed. This is
     *                needed to decide whether media (images etc.) are to be
     *                deleted.
     */
    public void onDestroy(boolean isSaved) {
     /*   EventBus.getDefault().unregister(this);
        for (RTEditText editor : mEditors.values()) {
            editor.unregister();
            editor.onDestroy(isSaved);
        }
        mEditors.clear();*/

        for (RTToolbar toolbar : mToolbars.values()) {
            toolbar.removeToolbarListener();
        }
        mToolbars.clear();

        //mRTApi = null;
    }

    // ****************************************** Public Methods *******************************************

    /**
     * Register a rich text editor.
     * <p>
     * Before using the editor it needs to be registered to an RTManager.
     * Using means any calls to the editor (setText will fail if the editor isn't registered)!
     * MUST be called from the ui thread.
     *
     * @param editor The rich text editor to register.
     */
    public void registerEditor(RTEditText editor, boolean useRichTextEditing) {

        mEditors.put(editor.getId(), editor);
        editor.register(this);
        editor.setRichTextEditing(useRichTextEditing, false);

        updateToolbarVisibility();

    }



    /**
     * Unregister a rich text editor.
     * <p>
     * This method may be called before the component is destroyed to stop any
     * interaction with the editor. Not doing so may result in (asynchronous)
     * calls coming through when the Activity/Fragment is already stopping its
     * operation.
     * <p>
     * Must be called from the ui thread.
     * <p>
     * Important: calling this method is obsolete once the onDestroy(boolean) is
     * called
     *
     * @param editor The rich text editor to unregister.
     */
    public void unregisterEditor(RTEditText editor) {
        mEditors.remove(editor.getId());
        editor.unregister();

        updateToolbarVisibility();
    }

    /**
     * Register a toolbar.
     * <p>
     * Only after doing that can it be used in conjunction with a rich text editor.
     * Must be called from the ui thread.
     *
     * @param toolbarContainer The ViewGroup containing the toolbar.
     *                         This container is used to show/hide the toolbar if needed (e.g. if the RTEditText field loses/gains focus).
     *                         We can't use the toolbar itself because there could be multiple and they could be embedded in a complex layout hierarchy.
     * @param toolbar          The toolbar to register.
     */
    public void registerToolbar(ViewGroup toolbarContainer, RTToolbar toolbar) {
        mToolbars.put(toolbar.getId(), toolbar);
        toolbar.setToolbarListener(this);
        toolbar.setToolbarContainer(toolbarContainer);
        updateToolbarVisibility();
        Log.v("registerToolbar: ","toolbar_registered" );
    }

    /**
     * Unregister a toolbar.
     * <p>
     * This method may be called before the component is destroyed to
     * stop any interaction with the toolbar. Not doing so may result
     * in (asynchronous) calls coming through when the Activity/Fragment
     * is already stopping its operation.
     * <p>
     * Must be called from the ui thread.
     * <p>
     * Important: calling this method is obsolete once the
     * onDestroy(boolean) is called
     *
     * @param toolbar The toolbar to unregister.
     */
    public void unregisterToolbar(RTToolbar toolbar) {
        mToolbars.remove(toolbar.getId());
        toolbar.removeToolbarListener();
        updateToolbarVisibility();
    }

    /**
     * Set the auto show/hide toolbar mode.
     */
    public void setToolbarVisibility(ToolbarVisibility toolbarVisibility) {
        if (mToolbarVisibility != toolbarVisibility) {
            mToolbarVisibility = toolbarVisibility;
            updateToolbarVisibility();
        }
    }

    private void updateToolbarVisibility() {
        boolean showToolbars = mToolbarVisibility == SHOW;

        if (mToolbarVisibility == AUTOMATIC) {
            RTEditText editor = getActiveEditor();
            showToolbars = editor != null && editor.usesRTFormatting();
        }

        for (RTToolbar toolbar : mToolbars.values()) {

            final ViewGroup toolbarContainer = toolbar.getToolbarContainer();
            toolbarContainer.setVisibility(View.VISIBLE);
            //setToolbarVisibility(toolbar, showToolbars);
        }
    }

    private void setToolbarVisibility(final RTToolbar toolbar, final boolean visible) {
        mToolbarIsVisible = visible;

        final ViewGroup toolbarContainer = toolbar.getToolbarContainer();
        int visibility = View.VISIBLE;
        synchronized (toolbarContainer) {
            visibility = toolbarContainer.getVisibility();
        }

        // only change visibility if we actually have to
        if ((visibility == View.GONE && visible) || (visibility == View.VISIBLE && !visible)) {

            AlphaAnimation fadeAnimation = visible ? new AlphaAnimation(0.0f, 1.0f) : new AlphaAnimation(1.0f, 0.0f);
            fadeAnimation.setDuration(200);
            fadeAnimation.setAnimationListener(new AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    synchronized (toolbarContainer) {
                        toolbarContainer.setVisibility(mToolbarIsVisible ? View.VISIBLE : View.VISIBLE);
                    }
                }
            });

            toolbarContainer.startAnimation(fadeAnimation);
        } else {
            toolbarContainer.clearAnimation();
        }
    }


    @Override
    /* @inheritDoc */
    public void onCreateLink() {
        RTEditText editor = getActiveEditor();

        if (editor != null) {
            String url = null;
            String linkText = null;

            List<RTSpan<String>> links = Effects.LINK.getSpans(editor.getText(), new Selection(editor), SpanCollectMode.EXACT);
            if (links.isEmpty()) {
                // default values if no link is found at selection
                linkText = editor.getSelectedText();
                try {
                    // if this succeeds we have a valid URL and will use it for the link
                    new URL(linkText);
                    url = linkText;
                } catch (MalformedURLException ignore) {
                }
                mLinkSelection = editor.getSelection();
            } else {
                // values if a link already exists
                RTSpan<String> linkSpan = links.get(0);
                url = linkSpan.getValue();
                linkText = getLinkText(editor, linkSpan);
            }

            mRTApi.openDialogFragment(ID_01_LINK_FRAGMENT, LinkFragment.newInstance(linkText, url));
        }
    }

    // ****************************************** RTToolbarListener *******************************************

    @Override
    /* @inheritDoc */
    public <V, C extends RTSpan<V>> void onEffectSelected(Effect<V, C> effect, V value) {
        RTEditText editor = getActiveEditor();
        if (editor != null) {
            editor.applyEffect(effect, value);
        }
    }


    @Override
    /* @inheritDoc */
    public void onClearFormatting() {
        RTEditText editor = getActiveEditor();
        if (editor != null) {
            int selStartBefore = editor.getSelectionStart();
            int selEndBefore = editor.getSelectionEnd();
            Spannable oldSpannable = editor.cloneSpannable();
            for (Effect effect : Effects.FORMATTING_EFFECTS) {
                effect.clearFormattingInSelection(editor);
            }
            int selStartAfter = editor.getSelectionStart();
            int selEndAfter = editor.getSelectionEnd();
            Spannable newSpannable = editor.cloneSpannable();
            mOPManager.executed(editor, new RTOperationManager.TextChangeOperation(oldSpannable, newSpannable,
                    selStartBefore, selEndBefore,
                    selStartAfter, selEndAfter));
        }
    }


    @Override
    /* @inheritDoc */
    public void onUndo() {
        RTEditText editor = getActiveEditor();
        if (editor != null) {
            mOPManager.undo(editor);
        }
    }

    @Override
    /* @inheritDoc */
    public void onRedo() {
        RTEditText editor = getActiveEditor();
        if (editor != null) {
            mOPManager.redo(editor);
        }
    }





    private RTEditText getActiveEditor() {
        for (RTEditText editor : mEditors.values()) {
            if (editor.hasFocus()) {
                return editor;
            }
        }
        return null;
    }

    // ****************************************** RTEditTextListener *******************************************



    @Override
    /* @inheritDoc */
    public void onFocusChanged(RTEditText editor, boolean focused) {
        if (editor.usesRTFormatting()) {
            synchronized (this) {
                // if a focus loss is pending then we cancel it
                if (mIsPendingFocusLoss) {
                    mCancelPendingFocusLoss = true;
                }
            }
            if (focused) {
                changeFocus();
            } else {
                mIsPendingFocusLoss = true;
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        changeFocus();
                    }
                }, 10);
            }
        }
    }

    private void changeFocus() {
        synchronized (this) {
            if (!mCancelPendingFocusLoss) {
                updateToolbarVisibility();
            }
            mCancelPendingFocusLoss = false;
            mIsPendingFocusLoss = false;
        }
    }

    @Override
    public void onRestoredInstanceState(RTEditText editor) {
        /*
         * We need to process pending sticky MediaEvents once the editors are registered with the
         * RTManager and are fully restored.
         */
        RTApi.ReferenceEvent referenceEvent = EventBus.getDefault().getStickyEvent(RTApi.ReferenceEvent.class);
        if(referenceEvent != null){
            onEventMainThread(referenceEvent);
        }
        LinkFragment.LinkEvent event = EventBus.getDefault().getStickyEvent(LinkFragment.LinkEvent.class);
        if (event != null) {
            onEventMainThread(event);
        }
        RTApi.MentionEvent mentionEvent = EventBus.getDefault().getStickyEvent(RTApi.MentionEvent.class);
        if(mentionEvent != null){
            onEventMainThread(mentionEvent);
        }
        RTApi.HashTagEvent hashTagEvent = EventBus.getDefault().getStickyEvent(RTApi.HashTagEvent.class);
        if(hashTagEvent != null){
            onEventMainThread(hashTagEvent);
        }
    }

    @Override
    /* @inheritDoc */
    public void onSelectionChanged(RTEditText editor, int start, int end) {
        if (editor == null) return;

        // default values
        boolean isBold = false;
        boolean isItalic = false;
        boolean isUnderLine = false;
        boolean isStrikethrough = false;
        boolean isBullet = false;
        boolean isNumber = false;
        boolean isSizeInc = false;
        List<Integer> sizes = null;
        List<RTTypeface> typefaces = null;


        // check if effect exists in selection
        for (Effect effect : Effects.ALL_EFFECTS) {
            if (effect.existsInSelection(editor)) {
              if (effect instanceof ItalicEffect) {
                    isItalic = true;
                } else if (effect instanceof UnderlineEffect) {
                    isUnderLine = true;
                } else if (effect instanceof TypefaceEffect) {
                    typefaces = Effects.TYPEFACE.valuesInSelection(editor);
                    isBold = true;
                }else if (effect instanceof StrikethroughEffect) {
                    isStrikethrough = true;
                }
                else if (effect instanceof BulletEffect) {
                    isBullet = true;
                } else if (effect instanceof NumberEffect) {
                    isNumber = true;
                } else if (effect instanceof AbsoluteSizeEffect) {
                    sizes = Effects.FONTSIZE.valuesInSelection(editor);
                    isSizeInc = true;
                }

            }
        }

        // update toolbar(s)
        for (RTToolbar toolbar : mToolbars.values()) {
            toolbar.setItalic(isItalic);
            toolbar.setUnderline(isUnderLine);
            toolbar.setStrikethrough(isStrikethrough);
            toolbar.setBullet(isBullet);
            toolbar.setNumber(isNumber);


           if(isBold){


               if(typefaces.get(0).getName().equalsIgnoreCase("Air Soft W00 Regular")){

                   toolbar.setBold(false);
               }
               else{

                   toolbar.setBold(true);
               }
           }

            if(isSizeInc) {

                if(sizes.get(0) == dpToPx(17)){

                    toolbar.setSizeInc(sizes.get(0),false);
                }
                else{

                    toolbar.setSizeInc(sizes.get(0),true);
                }
            }
            else {

            }
        }

    }

    public static int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }
    @Override
    /* @inheritDoc */
    public void onTextChanged(RTEditText editor, Spannable before, Spannable after,
                              int selStartBefore, int selEndBefore, int selStartAfter, int selEndAfter) {





        RTOperationManager.TextChangeOperation op = new RTOperationManager.TextChangeOperation(before, after,
                selStartBefore, selEndBefore,
                selStartAfter, selEndAfter);
            mOPManager.executed(editor, op);
    }


    private String getLinkText(RTEditText editor, RTSpan<String> span) {
        Spannable text = editor.getText();
        final int spanStart = text.getSpanStart(span);
        final int spanEnd = text.getSpanEnd(span);
        String linkText = null;
        if (spanStart >= 0 && spanEnd >= 0 && spanEnd <= text.length()) {
            linkText = text.subSequence(spanStart, spanEnd).toString();
            mLinkSelection = new Selection(spanStart, spanEnd);
        } else {
            mLinkSelection = editor.getSelection();
        }
        return linkText;
    }
    private String getRefText(RTEditText editor, RTSpan<String> span) {
        Spannable text = editor.getText();
        final int spanStart = text.getSpanStart(span);
        final int spanEnd = text.getSpanEnd(span);
        String refText = null;
        if (spanStart >= 0 && spanEnd >= 0 && spanEnd <= text.length()) {
            refText = text.subSequence(spanStart, spanEnd).toString();
            mRefSeelection = new Selection(spanStart, spanEnd);
        } else {
            mRefSeelection = editor.getSelection();
        }
        return refText;
    }

    private String getMentionText(RTEditText editor, RTSpan<String> span) {
        Spannable text = editor.getText();
        final int spanStart = text.getSpanStart(span);
        final int spanEnd = text.getSpanEnd(span);
        String mentionText = null;
        if (spanStart >= 0 && spanEnd >= 0 && spanEnd <= text.length()) {
            mentionText = text.subSequence(spanStart, spanEnd).toString();
            mMentionSelection = new Selection(spanStart, spanEnd);
        } else {
            mMentionSelection = editor.getSelection();
        }
        return mentionText;
    }

    private String getHashtagText(RTEditText editor, RTSpan<String> span) {
        Spannable text = editor.getText();
        final int spanStart = text.getSpanStart(span);
        final int spanEnd = text.getSpanEnd(span);
        String hashtagText = null;
        if (spanStart >= 0 && spanEnd >= 0 && spanEnd <= text.length()) {
            hashtagText = text.subSequence(spanStart, spanEnd).toString();
            mHashtagSelection = new Selection(spanStart, spanEnd);
        } else {
            mHashtagSelection = editor.getSelection();
        }
        return hashtagText;
    }


    /**
     * LinkFragment has closed -> process the result.
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(LinkFragment.LinkEvent event) {
        final String fragmentTag = event.getFragmentTag();
        mRTApi.removeFragment(fragmentTag);

        if (!event.wasCancelled() && ID_01_LINK_FRAGMENT.equals(fragmentTag)) {

            RTEditText editor = getActiveEditor();
            if (editor != null) {

                LinkFragment.Link link = event.getLink();
                String url = null;
                if (link != null && link.isValid()) {

                    // the mLinkSelection.end() <= editor.length() check is necessary since
                    // the editor text can change when the link fragment is open
                    Selection selection = mLinkSelection != null && mLinkSelection.end() <= editor.length() ? mLinkSelection : new Selection(editor);

                    String linkText = link.getLinkText();

                    // if no text is selected this inserts the entered link text
                    // if text is selected we replace it by the link text
                    Editable str = editor.getText();
                    str.replace(selection.start(), selection.end(), linkText);
                    editor.setSelection(selection.start(), selection.start() + linkText.length());
                    url = link.getUrl();
                }
                editor.applyEffect(Effects.LINK, url);    // if url == null -> remove the link

            }

        }
    }

             //add new post reference
            @Subscribe(threadMode = ThreadMode.MAIN)
            public void onEventMainThread(RTApi.ReferenceEvent event) {

                JSONObject jsonObject;
                String postId = "";
                String postRefText = "";
                RTEditText editor = getActiveEditor();
                String postJson = event.getPostJson();
                try {
                    jsonObject = new JSONObject(postJson);
                    postId = jsonObject.getString("id");
                    postRefText = jsonObject.getString("refText");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (editor != null) {
                    Editable str = editor.getText();
                    Selection selection = mRefSeelection != null && mRefSeelection.end() <= editor.length() ? mRefSeelection : new Selection(editor);
                    str.replace(selection.start(), selection.end(),postRefText);
                    editor.setSelection(selection.start(), selection.start() + postRefText.length());
                }
                if(editor != null)editor.applyEffect(Effects.POSTREF, postJson);
                if(editor != null && editor.getText() != null)editor.getText().insert(editor.getSelectionEnd(), " ");
    }



    //add new MENTION
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RTApi.MentionEvent event) {

        JSONObject jsonObject = null;
        String displayName = "";
        RTEditText editor = getActiveEditor();
        String mentionJson = event.getMentionJson();
        try {
            jsonObject = new JSONObject(mentionJson);
            displayName = jsonObject.getString("displayName");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (editor != null) {
            Editable str = editor.getText();
            Selection selection = mMentionSelection != null && mMentionSelection.end() <= editor.length() ? mMentionSelection : new Selection(editor);
            str.replace(selection.start(), selection.end(),displayName);
            editor.setSelection(selection.start(), selection.start() + displayName.length());
        }
        if(editor != null)editor.applyEffect(Effects.MENTION, StringEscapeUtils.escapeHtml4(mentionJson));
        if(editor != null && editor.getText() != null)editor.getText().insert(editor.getSelectionEnd(), " ");
    }




    //add new HASHTAG
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RTApi.HashTagEvent event) {

        JSONObject jsonObject;
        String hashtagID = "";
        String hashtagText = "";
        RTEditText editor = getActiveEditor();
        String hashtagJson = event.getHashTagJson();
        try {
            jsonObject = new JSONObject(hashtagJson);
            hashtagID = jsonObject.getString("id");
            hashtagText = jsonObject.getString("hashtagText");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (editor != null) {
            Editable str = editor.getText();
            Selection selection = mHashtagSelection != null && mHashtagSelection.end() <= editor.length() ? mHashtagSelection : new Selection(editor);
            str.replace(selection.start(), selection.end(),hashtagText);
            editor.setSelection(selection.start(), selection.start() + hashtagText.length());
        }
        if(editor != null)editor.applyEffect(Effects.HASHTAG, hashtagJson);
        if(editor != null && editor.getText() != null)editor.getText().insert(editor.getSelectionEnd(), " ");
    }




    @Override
    /* @inheritDoc */
    public void onClick(RTEditText editor, LinkSpan span) {
        if (editor != null) {
            String linkText = getLinkText(editor, span);
            mRTApi.openDialogFragment(ID_01_LINK_FRAGMENT, LinkFragment.newInstance(linkText, span.getURL()));
        }
    }

    @Override
    public void onClick(RTEditText editor, ReferenceSpan span) {
        getRefText(editor,span);
    }

    @Override
    public void onClick(RTEditText editor, MentionSpan span) {

        getMentionText(editor,span);
    }

    @Override
    public void onClick(RTEditText editor, HashTagSpan span) {

        getHashtagText(editor,span);
    }

    @Override
    public void onRichTextEditingChanged(RTEditText editor, boolean useRichText) {
        updateToolbarVisibility();
    }


    @Override
    public void onMentionSelected() {
        Log.e( "onMentioning: "," mention" );
        //onEffectSelected(Effects.FONTCOLOR, Color.parseColor("#fa2d65"));
    }

    @Override
    public void onHashTagSelected() {

//        onEffectSelected(Effects.FONTCOLOR,Color.parseColor("#465ED3"));
          //getActiveEditor().append("@",getActiveEditor().getSelectionStart(),getActiveEditor().getSelectionStart()+1);

    }

    @Override
    public void onStopMentioning() {

        //onEffectSelected(Effects.FONTCOLOR,Color.parseColor("#000000"));

        Log.e( "onStopMentionSelected: "," mention stop" );
    }

    @Override
    public void onStopHashTags() {

        Log.e( "onStopHashtagSelected: "," hashtag" );
        //onEffectSelected(Effects.FONTCOLOR,Color.parseColor("#000000"));
    }


}