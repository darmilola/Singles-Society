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

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.View;


import com.SinglesSociety.SocialText.R;
import com.SinglesSociety.SocialText.RichTextController.utils.Helper;
import com.SinglesSociety.SocialText.RichTextController.utils.validator.EmailValidator;
import com.SinglesSociety.SocialText.RichTextController.utils.validator.UrlValidator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.Locale;
import java.util.Random;

import androidx.appcompat.app.AlertDialog;

/**
 * A DialogFragment to add, modify or remove links from Spanned text.
 */
public class LinkFragment extends DialogFragment {

    private static final String LINK_ADDRESS = "link_address";
    private static final String LINK_TEXT = "link_text";
    TextView linkTitle;
    TextInputEditText linkText,linkUrl;
    TextInputLayout linkTextLayout,linkUrlLayout;
    TextView cancel,save,remove;
    Dialog dialog;

    /**
     * The Link class describes a link (link text and an URL).
     */
    static class Link {
        final private String mLinkText;
        final private String mUrl;

        private Link(String linkText, String url) {
            mLinkText = linkText;
            mUrl = url;
        }

        public String getLinkText() {
            return mLinkText;
        }

        public String getUrl() {
            return mUrl;
        }

        public boolean isValid() {
            return mUrl != null && mUrl.length() > 0 && mLinkText != null && mLinkText.length() > 0;
        }
    }

    /**
     * This event is broadcast via EventBus when the dialog closes.
     * It's received by the RTManager to update the active editor.
     */
    static class LinkEvent {
        private final String mFragmentTag;
        private final Link mLink;
        private final boolean mWasCancelled;

        public LinkEvent(Fragment fragment, Link link, boolean wasCancelled) {
            mFragmentTag = fragment.getTag();
            mLink = link;
            mWasCancelled = wasCancelled;
        }

        protected String generateFragmentTag(){
            String SALTCHARS = "ABCDEFGHIJLMNOPQRSTUVWXYZ123456890";
            StringBuilder salt = new StringBuilder();
            Random random = new Random();
            while (salt.length() < 18){
                int index = (int)(random.nextFloat() * SALTCHARS.length());
                salt.append(SALTCHARS.charAt(index));
            }
            String saltr = salt.toString();
            return  saltr;
        }

        public String getFragmentTag() {
            return mFragmentTag;
        }

        public Link getLink() {
            return mLink;
        }

        public boolean wasCancelled() {
            return mWasCancelled;
        }
    }

    private static final UrlValidator sUrlValidator = new UrlValidator(UrlValidator.ALLOW_2_SLASHES + UrlValidator.ALLOW_ALL_SCHEMES);
    private static final EmailValidator sEmailValidator = EmailValidator.getInstance(false);

    public static LinkFragment newInstance(String linkText, String url) {
        LinkFragment fragment = new LinkFragment();
        Bundle args = new Bundle();
        args.putString(LINK_TEXT, linkText);
        args.putString(LINK_ADDRESS, url);
        fragment.setArguments(args);
        return fragment;
    }

    public LinkFragment() {
    }

    @SuppressLint("InflateParams")
    @Override
    public final Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();
        LayoutInflater li = LayoutInflater.from(context);
        View view = li.inflate(R.layout.link_dialog, null);
        linkUrlLayout = view.findViewById(R.id.linkUrlLayout);
        cancel = view.findViewById(R.id.linkCancel);
        save = view.findViewById(R.id.linksave);
        remove = view.findViewById(R.id.linkRemove);

        Bundle args = getArguments();

        // link address
        String tmp = "http://";
        final String address = args.getString(LINK_ADDRESS);
        if (address != null && ! address.isEmpty()) {
            try {
                Uri uri = Uri.parse( Helper.decodeQuery(address) );
                // if we have an email address remove the mailto: part for editing purposes
                tmp = startsWithMailto(address) ? uri.getSchemeSpecificPart() : uri.toString();
            } catch (Exception ignore) {}
        }
        final String url = tmp;
         linkUrl = view.findViewById(R.id.linkURL);
        if (url != null) {
            linkUrl.setText(url);
        }

        // link text
        String mlinkText = args.getString(LINK_TEXT);
        linkText = view.findViewById(R.id.linkText);
        if (linkText != null) {
            linkText.setText(mlinkText);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                //.setTitle("Add a new Link")
                .setView(view)
                .setCancelable(false);

       dialog = builder.create();

        if (address != null) {
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventBus.getDefault().post(new LinkEvent(LinkFragment.this, null, false));
                    try { dialog.dismiss(); } catch (Exception ignore) {}
                }
            });
        }


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(dialog, linkUrl, linkText);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EventBus.getDefault().post(new LinkEvent(LinkFragment.this, new Link(null, url), true));
                try { dialog.dismiss(); } catch (Exception ignore) {}
            }
        });

        return dialog;
    }

    private void validate(DialogInterface dialog, TextInputEditText addressView, TextInputEditText textView) {
        // retrieve link address and do some cleanup
        final String address = addressView.getText().toString().trim();

        boolean isEmail = sEmailValidator.isValid(address);
        boolean isUrl = sUrlValidator.isValid(address);
        if (requiredFieldValid(addressView) && (isUrl || isEmail)) {
            // valid url or email address

            // encode address
            String newAddress = Helper.encodeUrl(address);

            // add mailto: for email addresses
            if (isEmail && !startsWithMailto(newAddress)) {
                newAddress = "mailto:" + newAddress;
            }

            // use the original address text as link text if the user didn't enter anything
            String linkText = textView.getText().toString();
            if (linkText.length() == 0) {
                linkText = address;
            }

            EventBus.getDefault().post(new LinkEvent(LinkFragment.this, new Link(linkText, newAddress), false));
            try { dialog.dismiss(); } catch (Exception ignore) {}
        } else {
            // invalid address (neither a url nor an email address
           // String errorMessage = getString("Error in Link", address);
            linkUrlLayout.setError("");
        }
    }

    private boolean startsWithMailto(String address) {
        return address != null && address.toLowerCase(Locale.getDefault()).startsWith("mailto:");
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        EventBus.getDefault().post(new LinkEvent(LinkFragment.this, null, true));
    }

    private boolean requiredFieldValid(TextInputEditText view) {
        return view.getText() != null && view.getText().length() > 0;
    }
}