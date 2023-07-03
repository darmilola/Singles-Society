package com.singlesSociety.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.singlesSociety.R
import com.singlesSociety.UiModels.BottomNav
import com.singlesSociety.databinding.FragmentVisitProfileBinding


private const val ID_IMAGE_LIBRARY = 9
private const val ID_VIDEO_LIBRARY = 10
class VisitProfileFragment(private var visitProfileExitListener: Function0<Unit>? = null, private val backButtonVisible: Boolean = true) : Fragment() {


    private lateinit var viewBinding: FragmentVisitProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewBinding = FragmentVisitProfileBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {

        viewBinding.visitProfileNavigation?.apply {

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

        viewBinding.visitProfileNavigation.setOnClickMenuListener {
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
            viewBinding.exitUserProfileVisit.visibility = View.GONE
        }

        viewBinding.exitUserProfileVisit.setOnClickListener {
              visitProfileExitListener?.invoke()
        }

    }


    private fun loadFragment(fragment: Fragment){
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.visitProfileLibraryPage,fragment)
        transaction.commit()
    }


}