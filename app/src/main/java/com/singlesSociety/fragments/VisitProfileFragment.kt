package com.singlesSociety.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.singlesSociety.R
import com.singlesSociety.UiModels.BottomNav
import kotlinx.android.synthetic.main.fragment_visit_profile.*


private const val ID_IMAGE_LIBRARY = 9
private const val ID_VIDEO_LIBRARY = 10
class VisitProfileFragment(private var visitProfileExitListener: Function0<Unit>? = null, private val backButtonVisible: Boolean = true) : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_visit_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {

        visitProfileNavigation?.apply {

            add(
                BottomNav.Model(
                    ID_IMAGE_LIBRARY,
                    R.drawable.pictures,
                    "ImageLibrary"
                )
            )
            add(
                BottomNav.Model(
                    ID_VIDEO_LIBRARY,
                    R.drawable.play_circle_icon,
                    "videoLibrary"
                )
            )
        }?.show(ID_IMAGE_LIBRARY)

        loadFragment(ImageLibraryFragment())

        visitProfileNavigation.setOnClickMenuListener {
            when (it.id) {
                ID_IMAGE_LIBRARY -> {
                    loadFragment(ImageLibraryFragment())
                }
                ID_VIDEO_LIBRARY -> {
                    loadFragment(VideoLibraryFragment())
                }
            }
        }

        if(!backButtonVisible){
            exitUserProfileVisit.visibility = View.GONE
        }

        exitUserProfileVisit.setOnClickListener {
              visitProfileExitListener?.invoke()
        }

    }


    private fun loadFragment(fragment: Fragment){
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.visitProfileLibraryPage,fragment)
        transaction.commit()
    }


}