package com.singlesSociety.fragments

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.singlesSociety.NewWelcome
import com.singlesSociety.R
import com.singlesSociety.UiModels.BottomNav
import com.singlesSociety.databinding.FragmentUserProfileBinding



private const val ID_IMAGE_LIBRARY = 9
private const val ID_VIDEO_LIBRARY = 6
private const val ID_BOOKMARKS = 7
private const val ID_TEXT_LIBRARY = 8
class UserProfileFragment : Fragment() {


    private lateinit var viewBinding: FragmentUserProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentUserProfileBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

    }

    private fun initView() {

        viewBinding.userProfileNavigation?.apply {

            add(
                BottomNav.Model(
                     ID_IMAGE_LIBRARY,
                    R.drawable.photos_icon,
                    "ImageLibrary"
                )
            )
            add(
                BottomNav.Model(
                     ID_VIDEO_LIBRARY,
                    R.drawable.play_right_icon,
                    "videoLibrary"
                )
            )
            add(
                BottomNav.Model(
                    ID_TEXT_LIBRARY,
                    R.drawable.write_icon,
                    "datingProfile"
                )
            )
        }?.show(ID_IMAGE_LIBRARY)
        loadFragment(ImageLibraryFragment())

        viewBinding.userProfileNavigation.setOnClickMenuListener {
            when (it.id) {
                ID_IMAGE_LIBRARY -> {
                    loadFragment(ImageLibraryFragment())
                }
                 ID_VIDEO_LIBRARY -> {
                    loadFragment(VideoLibraryFragment())
                }
                 ID_BOOKMARKS -> {
                    loadFragment(BookMarksFragment())
                }
                 ID_TEXT_LIBRARY -> {
                    loadFragment(TextLibraryFragment())
                }
            }
        }

    }



    private fun loadFragment(fragment: Fragment){
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.userProfileLibraryPage,fragment)
        transaction.commit()
    }




    private fun logOut() {
        val preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        preferences.edit().remove("userEmail").apply()
        startActivity(Intent(requireContext(), NewWelcome::class.java))
        activity?.finish()
    }
}