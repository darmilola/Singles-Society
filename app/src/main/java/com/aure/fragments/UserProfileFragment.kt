package com.aure.fragments

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aure.NewWelcome
import com.aure.R
import com.aure.UiAdapters.ExploreItemAdapter
import com.aure.UiModels.BottomNav
import com.aure.UiModels.ExploreItem
import com.aure.WelcomeActivity
import kotlinx.android.synthetic.main.fragment_trending.*
import kotlinx.android.synthetic.main.fragment_user_profile.*
import kotlinx.android.synthetic.main.profile_info_arena.*



private const val ID_IMAGE_LIBRARY = 9
private const val ID_VIDEO_LIBRARY = 6
private const val ID_BOOKMARKS = 7
private const val ID_DATING_PROFILE = 8
class UserProfileFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        logOutCta.setOnClickListener {
            logOut()
        }
    }

    private fun initView() {

        userProfileNavigation?.apply {

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
            add(
                BottomNav.Model(
                     ID_BOOKMARKS,
                    R.drawable.bookmark,
                    "Bookmarks"
                )
            )
            add(
                BottomNav.Model(
                    ID_DATING_PROFILE,
                    R.drawable.dating_smartphone_man_icon,
                    "datingProfile"
                )
            )
        }?.show(ID_IMAGE_LIBRARY)
        loadFragment(ImageLibraryFragment())

        userProfileNavigation.setOnClickMenuListener {
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
                 ID_DATING_PROFILE -> {
                    loadFragment(DatingProfileFragment())
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