package com.singlesSociety

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.singlesSociety.databinding.VideoPostFullViewBinding

class VideoPostFullView(private var videoPostProfileVisitListener: Function0<Unit>? = null) : Fragment() {
    private lateinit var viewBinding: VideoPostFullViewBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = VideoPostFullViewBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       viewBinding.postEngagementOverlay.accountProfilePicture.setOnClickListener {
            videoPostProfileVisitListener?.invoke()
        }
    }

}