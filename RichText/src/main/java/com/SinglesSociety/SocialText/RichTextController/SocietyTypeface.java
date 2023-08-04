package com.SinglesSociety.SocialText.RichTextController;

import androidx.annotation.FontRes;

import com.SinglesSociety.SocialText.R;

public enum SocietyTypeface {
/*    OPEN_SANS_BOLD(C1183R.font.opensans_bold),
    OPEN_SANS_BOLD_ITALIC(C1183R.font.opensans_bold_italic),
    OPEN_SANS_EXTRA_BOLD(C1183R.font.opensans_extra_bold),
    OPEN_SANS_EXTRA_BOLD_ITALIC(C1183R.font.opensans_extra_bold_italic),
    OPEN_SANS_ITALIC(C1183R.font.opensans_italic),
    OPEN_SANS_LIGHT(C1183R.font.opensans_light),
    OPEN_SANS_LIGHT_ITALIC(C1183R.font.opensans_light_italic),*/
    OPEN_SANS_REGULAR(R.font.opensansreg),
    MANROPE_REGULAR(R.font.manropemedium);
   /* OPEN_SANS_SEMI_BOLD(C1183R.font.opensans_semi_bold),
    OPEN_SANS_SEMI_BOLD_ITALIC(C1183R.font.opensans_semi_bold_italic);*/

    @FontRes
    private final int resId;

    private SocietyTypeface(int i) {
        this.resId = i;
    }

    public static SocietyTypeface  fromResId(int i) {
        for (SocietyTypeface societyTypeface: values()) {
            if (societyTypeface.getResId() == i) {
                return societyTypeface;
            }
        }
        return MANROPE_REGULAR;
    }

    @FontRes
    public int getResId() {
        return this.resId;
    }
}


