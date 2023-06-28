/*******************************************************************************
 * Copyright 2016 stfalcon.com
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/

package com.singlesSociety.ChatKit.messages;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.TextView;


import com.SinglesSociety.SocialText.RichTextController.MentionHashTagListener;
import com.SinglesSociety.SocialText.RichTextController.RTEditText;
import com.singlesSociety.R;

import java.lang.reflect.Field;

import androidx.core.view.ViewCompat;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;


/**
 * Component for input outcoming messages
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class MessageInput extends LinearLayout
        implements View.OnClickListener, TextWatcher, View.OnFocusChangeListener {

    protected RTEditText messageInput;
    protected ImageView messageSendButton;
    protected ImageView attachmentButton;
    protected ImageView emojiButton;
   // protected Space sendButtonSpace, attachmentButtonSpace;

    private CharSequence input;
    private InputListener inputListener;
    private AttachmentsListener attachmentsListener;
    private boolean isTyping;
    private TypingListener typingListener;
    private int delayTypingStatusMillis;
    private Runnable typingTimerRunnable = new Runnable() {
        @Override
        public void run() {
            if (isTyping) {
                isTyping = false;
                if (typingListener != null) typingListener.onStopTyping();
            }
        }
    };
    private boolean lastFocus;

    public MessageInput(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MessageInput(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * Sets callback for 'submit' button.
     *
     * @param inputListener input callback
     */
    public void setInputListener(InputListener inputListener) {
        this.inputListener = inputListener;
    }

    /**
     * Sets callback for 'add' button.
     *
     * @param attachmentsListener input callback
     */
    public void setAttachmentsListener(AttachmentsListener attachmentsListener) {
        this.attachmentsListener = attachmentsListener;
    }

    /**
     * Returns EditText for messages input
     *
     * @return EditText
     */
    public EditText getInputEditText() {
        return messageInput;
    }

    /**
     * Returns `submit` button
     *
     * @return ImageButton
     */
    public ImageView getButton() {
        return messageSendButton;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.messageSendButton) {
            boolean isSubmitted = onSubmit();
            if (isSubmitted) {
                messageInput.setText("");
            }
            removeCallbacks(typingTimerRunnable);
            post(typingTimerRunnable);
        } else if (id == R.id.attachmentButton) {
            onAddAttachments();
        }
    }

    /**
     * This method is called to notify you that, within s,
     * the count characters beginning at start have just replaced old text that had length before
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int count, int after) {
        input = s;
        messageSendButton.setEnabled(input.length() > 0);
        if (s.length() > 0) {
            if (!isTyping) {
                isTyping = true;
                if (typingListener != null) typingListener.onStartTyping();
            }
            removeCallbacks(typingTimerRunnable);
            postDelayed(typingTimerRunnable, delayTypingStatusMillis);
        }
    }

    /**
     * This method is called to notify you that, within s,
     * the count characters beginning at start are about to be replaced by new text with length after.
     */
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        //do nothing
    }

    /**
     * This method is called to notify you that, somewhere within s, the text has been changed.
     */
    @Override
    public void afterTextChanged(Editable editable) {
        //do nothing
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (lastFocus && !hasFocus && typingListener != null) {
            typingListener.onStopTyping();
        }
        lastFocus = hasFocus;
    }

    private boolean onSubmit() {
        return inputListener != null && inputListener.onSubmit(input);
    }

    private void onAddAttachments() {
        if (attachmentsListener != null) attachmentsListener.onAddAttachments();
    }

    private void init(Context context, AttributeSet attrs) {
        MessageInputStyle style = MessageInputStyle.parse(context, attrs);
        Boolean isTypeMessage = style.getIsTypeMessage();
        init(context,isTypeMessage);
        this.messageInput.setMaxLines(style.getInputMaxLines());
        this.messageInput.setHint(style.getInputHint());
        this.messageInput.setText(style.getInputText());
        this.messageInput.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.getInputTextSize());
        this.messageInput.setTextColor(style.getInputTextColor());
        this.messageInput.setHintTextColor(style.getInputHintColor());
        ViewCompat.setBackground(this.messageInput, style.getInputBackground());
        setCursor(style.getInputCursorDrawable());

        if(isTypeMessage) {
            this.attachmentButton.setVisibility(style.showAttachmentButton() ? VISIBLE : GONE);
            this.attachmentButton.setImageDrawable(style.getAttachmentButtonIcon());
            this.attachmentButton.getLayoutParams().width = style.getAttachmentButtonWidth();
            this.attachmentButton.getLayoutParams().height = style.getAttachmentButtonHeight();
            ViewCompat.setBackground(this.attachmentButton, style.getAttachmentButtonBackground());
        }

        this.emojiButton.setVisibility(style.showAttachmentButton() ? VISIBLE : GONE);
        this.emojiButton.setImageDrawable(style.getEmojiButtonIcon());
        this.emojiButton.getLayoutParams().width = style.getAttachmentButtonWidth();
        this.emojiButton.getLayoutParams().height = style.getAttachmentButtonHeight();
        ViewCompat.setBackground(this.emojiButton, style.getAttachmentButtonBackground());


        this.messageSendButton.setImageDrawable(style.getInputButtonIcon());
        this.messageSendButton.getLayoutParams().width = style.getInputButtonWidth();
        this.messageSendButton.getLayoutParams().height = style.getInputButtonHeight();
        ViewCompat.setBackground(messageSendButton, style.getInputButtonBackground());

        if (getPaddingLeft() == 0
                && getPaddingRight() == 0
                && getPaddingTop() == 0
                && getPaddingBottom() == 0) {
            setPadding(
                    style.getInputDefaultPaddingLeft(),
                    style.getInputDefaultPaddingTop(),
                    style.getInputDefaultPaddingRight(),
                    style.getInputDefaultPaddingBottom()
            );
        }
        this.delayTypingStatusMillis = style.getDelayTypingStatus();
    }

    private void init(Context context, Boolean isTypeMessage) {
        View rootView = null;
        if(isTypeMessage){
            rootView = inflate(context, R.layout.view_message_input, this);
        }
        else{
            rootView = inflate(context, R.layout.view_message_input_type_comment, this);
        }

        messageInput = findViewById(R.id.messageInput);
        messageSendButton =  findViewById(R.id.messageSendButton);
        emojiButton = findViewById(R.id.emojiButton);

        if(isTypeMessage) {
            messageInput.setRichTextEditing(false, false);
            messageInput.setMentionHashTagListener(null);
        }
        else {

            messageInput.setMentionHashTagListener(new MentionHashTagListener() {
                @Override
                public void onMentioning(CharSequence sequence) {

                }

                @Override
                public void onHashTagging(CharSequence sequence) {

                }

                @Override
                public void onStopMentioning() {

                }

                @Override
                public void onStopHashTags() {

                }

                @Override
                public void onMentionStarted() {

                }

                @Override
                public void onHashTagsStarted() {

                }
            });
        }

        messageSendButton.setOnClickListener(this);
        if(isTypeMessage) {
            attachmentButton = findViewById(R.id.attachmentButton);
            attachmentButton.setOnClickListener(this);
        }
        messageInput.addTextChangedListener(this);
        messageInput.setOnFocusChangeListener(this);

            EmojIconActions emojiIcon=new EmojIconActions(context,rootView,messageInput,emojiButton);
            emojiIcon.ShowEmojIcon();



    }

    private void setCursor(Drawable drawable) {
        if (drawable == null) return;

        try {
            final Field drawableResField = TextView.class.getDeclaredField("mCursorDrawableRes");
            drawableResField.setAccessible(true);

            final Object drawableFieldOwner;
            final Class<?> drawableFieldClass;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                drawableFieldOwner = this.messageInput;
                drawableFieldClass = TextView.class;
            } else {
                final Field editorField = TextView.class.getDeclaredField("mEditor");
                editorField.setAccessible(true);
                drawableFieldOwner = editorField.get(this.messageInput);
                drawableFieldClass = drawableFieldOwner.getClass();
            }
            final Field drawableField = drawableFieldClass.getDeclaredField("mCursorDrawable");
            drawableField.setAccessible(true);
            drawableField.set(drawableFieldOwner, new Drawable[]{drawable, drawable});
        } catch (Exception ignored) {
        }
    }

    public void setTypingListener(TypingListener typingListener) {
        this.typingListener = typingListener;
    }

    /**
     * Interface definition for a callback to be invoked when user pressed 'submit' button
     */
    public interface InputListener {

        /**
         * Fires when user presses 'send' button.
         *
         * @param input input entered by user
         * @return if input text is valid, you must return {@code true} and input will be cleared, otherwise return false.
         */
        boolean onSubmit(CharSequence input);
    }

    /**
     * Interface definition for a callback to be invoked when user presses 'add' button
     */
    public interface AttachmentsListener {

        /**
         * Fires when user presses 'add' button.
         */
        void onAddAttachments();
    }

    /**
     * Interface definition for a callback to be invoked when user typing
     */
    public interface TypingListener {

        /**
         * Fires when user presses start typing
         */
        void onStartTyping();

        /**
         * Fires when user presses stop typing
         */
        void onStopTyping();

    }
}
