package com.singlesSociety.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.singlesSociety.R
import com.singlesSociety.uiAdapters.VideoLibraryAdapter
import com.singlesSociety.UiModels.VideoModel
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