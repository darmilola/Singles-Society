package com.singlesSociety.fragments

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.singlesSociety.UiModels.dip
import com.singlesSociety.databinding.FragmentSpacesMainBinding


class SpacesMainFragment(private var visitProfileListener: Function0<Unit>? = null, private var exitSpaceListener: Function0<Unit>? = null) : Fragment(){

    private lateinit var viewBinding: FragmentSpacesMainBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewBinding = FragmentSpacesMainBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }


    private fun initView() {
        val viewPager = viewBinding.viewPager
        val params: CoordinatorLayout.LayoutParams = viewPager.layoutParams as CoordinatorLayout.LayoutParams
        params.height = getScreenHeight() - dip(requireContext(),50)
        viewPager.layoutParams = params
        viewPager.adapter = PageAdapter(parentFragmentManager)
        val tabLayout = viewBinding.tabLayout
        tabLayout.setupWithViewPager(viewPager)
        setupTabIcons()
    }



    private fun setupTabIcons() {
        viewBinding.tabLayout.getTabAt(0)?.text = "All"
        viewBinding.tabLayout.getTabAt(1)?.text = "Media"
        viewBinding.tabLayout.getTabAt(2)?.text = "Events"
        viewBinding.tabLayout.getTabAt(3)?.text = "Members"
    }


    fun getScreenWidth(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

    fun getScreenHeight(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }

    class PageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getCount(): Int {
            return 4
        }

        override fun getItem(position: Int): Fragment {
            return when(position) {
                0 -> {
                      SocietyHomeFragment()
                }

                1 -> {
                      SocietyHomeFragment()
                }

                2 -> {
                      SocietyEventFragment()
                }
                3 -> {
                      SocietyMembersListFragment()
                }

                else -> {
                      SocietyHomeFragment()
                }
            }
        }
    }

}