package com.singlesSociety.fragments

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.singlesSociety.NewWelcome
import com.singlesSociety.R
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
        val viewPager = viewBinding.viewPager
        viewPager.adapter = PageAdapter(parentFragmentManager)

        val tabLayout = viewBinding.tabLayout
        tabLayout.setupWithViewPager(viewPager)
        setupTabIcons()

    /*    viewBinding.profileInfoArena.profileInfoImageArena.profileInfoEditProfile.setOnClickListener {
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
        }*/

    }

  /*  private fun loadFragment(fragment: Fragment){
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.userProfileLibraryPage,fragment)
        transaction.commit()
    }*/




    private fun logOut() {
        val preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        preferences.edit().remove("userEmail").apply()
        startActivity(Intent(requireContext(), NewWelcome::class.java))
        activity?.finish()
    }

    private fun setupTabIcons() {
        viewBinding.tabLayout.getTabAt(0)?.icon = resources.getDrawable(R.drawable.pen_draw_icon)
        viewBinding.tabLayout.getTabAt(1)?.icon = resources.getDrawable(R.drawable.star_favourite_icon)
        viewBinding.tabLayout.getTabAt(2)?.icon = resources.getDrawable(R.drawable.brand_tinder_icon)
    }

    class PageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getCount(): Int {
            return 3;
        }

        override fun getItem(position: Int): Fragment {
            return when(position) {
                0 -> {
                    TextLibraryFragment()
                }

                1 -> {
                    TextLibraryFragment()
                }

                2 -> {
                    DatingProfileFragment(isFromUser = false)
                }

                else -> {
                    TextLibraryFragment()
                }
            }
        }
    }
}