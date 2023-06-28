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

package com.SinglesSociety.SocialText.RichTextController.api.format;


/**
 * The RTText is the base class for all classes representing a rich text.
 * <p>
 * A rich text is defined by its format (RTFormat) and its content
 * (CharSequence). Subclasses extend it to implement representations of concrete
 * rich text types like Spanned rich text (used in the actual editor) or html
 * rich text (usually used to store the rich content).
 */
public abstract class RTText {

    final private RTFormat mRTFormat;
    final private CharSequence mRTText;

    /**
     * Use this constructor if the class supports exactly one rich text format
     * and that format is immutable meaning sub classes won't be able to change
     * its format (e.g. RTPlainText).
     *
     * @param rtFormat The rich text format
     */
    public RTText(RTFormat rtFormat, CharSequence text) {
        mRTFormat = rtFormat;
        mRTText = text;
    }

    /**
     * Return the format of this rich text.
     */
    public RTFormat getFormat() {
        return mRTFormat;
    }

    /**
     * Return the content of this rich text.
     */
    public CharSequence getText() {
        return mRTText;
    }


    public RTText convertTo(RTFormat destFormat) {
        if (destFormat == mRTFormat) {
            return this;
        }

        throw new UnsupportedOperationException("Can't convert from "
                + mRTFormat.getClass().getSimpleName() + " to "
                + destFormat.getClass().getSimpleName());
    }

}