package com.singlesSociety.UiModels.Utils

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction





fun Fragment.closeFragment(resultCode: Int? = null, returnArgs: Bundle? = null) {
    if (resultCode != null) {
        if (returnArgs != null) {
            activity?.setResult(
                resultCode,
                Intent().apply {
                    putExtras(returnArgs)
                }
            )
        } else {
            activity?.setResult(resultCode)
        }
    }
    activity?.finish()
}

enum class FragmentTarget{
       CHIP_SELECTION_FRAGMENT,
       LIST_SELECTION_FRAGMENT;
}


fun FragmentTransaction.withFadeAnimation(): FragmentTransaction {
    return setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
}



interface FragmentBackPressed {
    fun onBackPressed()
}
