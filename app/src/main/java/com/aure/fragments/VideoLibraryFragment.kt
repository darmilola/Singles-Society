package com.aure.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aure.R
import com.aure.UiAdapters.ImageLibraryAdapter
import com.aure.UiAdapters.VideoLibraryAdapter
import com.aure.UiModels.ImageModel
import com.aure.UiModels.VideoModel
import kotlinx.android.synthetic.main.fragment_image_library.*
import kotlinx.android.synthetic.main.fragment_video_library.*


class VideoLibraryFragment : Fragment() {

    var videoList = ArrayList<VideoModel>()
    var videoAdapter: VideoLibraryAdapter? = null
    var videoModel: VideoModel = VideoModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_video_library, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        for (i in 0..50) {
            videoList.add(videoModel)

        }
        videoAdapter = VideoLibraryAdapter(videoList,requireContext())
        videosRecyclerView.adapter = videoAdapter
    }

}