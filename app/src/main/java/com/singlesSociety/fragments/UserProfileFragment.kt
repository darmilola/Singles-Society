package com.singlesSociety.fragments

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.singlesSociety.CompleteProfile
import com.singlesSociety.NewWelcome
import com.singlesSociety.R
import com.singlesSociety.UiModels.BottomNav
import com.singlesSociety.databinding.FragmentUserProfileBinding




private const val ID_TEXT_LIBRARY = 8
private const val ID_STARRED_PROFILE = 9
private const val ID_PERSONAL_PROFILE = 10
class UserProfileFragment : Fragment() {


    private lateinit var viewBinding: FragmentUserProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentUserProfileBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

    }

    private fun initView() {

        viewBinding.profileInfoArena.profileInfoImageArena.profileInfoEditProfile.setOnClickListener {
            startActivity(Intent(requireContext(),CompleteProfile::class.java))
        }

        viewBinding.userProfileNavigation.apply {

            add(
                BottomNav.Model(
                    ID_TEXT_LIBRARY,
                    R.drawable.text_icon,
                    "TEXT"
                )
            )
            add(
                BottomNav.Model(
                    ID_STARRED_PROFILE,
                    R.drawable.star_favourite_icon,
                    "STARRED"
                )
            )
            add(
                BottomNav.Model(
                    ID_PERSONAL_PROFILE,
                    R.drawable.love,
                    "USER"
                )
            )
        }.show(ID_TEXT_LIBRARY)
        loadFragment(TextLibraryFragment())

        viewBinding.userProfileNavigation.setOnClickMenuListener {
            when (it.id) {
                ID_TEXT_LIBRARY -> {
                    loadFragment(TextLibraryFragment())
                }
                ID_PERSONAL_PROFILE -> {
                    loadFragment(DatingProfileFragment())
                }
                ID_STARRED_PROFILE -> {
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