package com.singlesSociety.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.singlesSociety.R
import com.singlesSociety.databinding.FragmentCommentBottomSheetBinding
import com.singlesSociety.databinding.FragmentEventBottomsheetBinding
import io.getstream.avatarview.coil.loadImage


class EventBottomsheet : BottomSheetDialogFragment() {

    private lateinit var viewBinding: FragmentEventBottomsheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentEventBottomsheetBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.accountProfilePictureLayout.userProfileImageViewWithIndicator.loadImage(context?.resources?.getDrawable(R.drawable.woman_official))
    }


}