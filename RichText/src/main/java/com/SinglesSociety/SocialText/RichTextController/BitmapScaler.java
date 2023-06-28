package com.SinglesSociety.SocialText.RichTextController;

import android.graphics.Bitmap;

public class BitmapScaler
{

    public  Bitmap scaleBitmap(Bitmap b,float factor)
    {
        int height = b.getHeight();
        int width = b.getWidth();

        float widthToHeightRatio = width/height;
        if(widthToHeightRatio <= 0.5 && widthToHeightRatio > 0.4){
            factor = 0.270f;
        }
        else if(widthToHeightRatio <= 0.4 && widthToHeightRatio > 0.3){

            factor = 0.4f;
        }
        else if(widthToHeightRatio <= 0.3 && widthToHeightRatio > 0.2){

            factor = 0.5f;
        }
        else{
            factor = 1f;
        }

        return Bitmap.createScaledBitmap(b, (int) (b.getWidth() * factor), (int) (b.getHeight() * factor), true);
    }


    // Scale and maintain aspect ratio given a desired height
    // BitmapScaler.scaleToFitHeight(bitmap, 100);
    public static Bitmap scaleToFitHeight(Bitmap b, int height)
    {
        float factor = height / (float) b.getHeight();
        return Bitmap.createScaledBitmap(b, (int) (b.getWidth() * factor), height, true);
    }

}