package com.singlesSociety.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.singlesSociety.CompleteProfile
import com.singlesSociety.R
import com.singlesSociety.uiAdapters.HomeMainAdapter
import com.singlesSociety.UiModels.*
import com.singlesSociety.UserListingDetails
import com.singlesSociety.databinding.FragmentSpacesMainBinding
import nl.dionsegijn.konfetti.core.Angle
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.Rotation
import nl.dionsegijn.konfetti.core.emitter.Emitter
import nl.dionsegijn.konfetti.core.models.Shape
import nl.dionsegijn.konfetti.core.models.Size
import java.util.concurrent.TimeUnit
import kotlin.random.Random


class SpacesMainFragment(private var visitProfileListener: Function0<Unit>? = null, private var exitSpaceListener: Function0<Unit>? = null) : Fragment(){

    var homeMainAdapter: HomeMainAdapter? = null
    var societyModelArrayList = java.util.ArrayList<SocietyModel>()
    var mainActivityModel: MainActivityModel? = null
    var lastPosition = RecyclerView.NO_POSITION
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
        viewPager.adapter = PageAdapter(parentFragmentManager)

        val tabLayout = viewBinding.tabLayout
        tabLayout.setupWithViewPager(viewPager)
        setupTabIcons()

    }



    private fun setupTabIcons() {
        viewBinding.tabLayout.getTabAt(0)?.text = "All"
        viewBinding.tabLayout.getTabAt(1)?.text = "Media"
        viewBinding.tabLayout.getTabAt(2)?.text = "Events"
        viewBinding.tabLayout.getTabAt(3)?.text = "Requests"
    }

    class PageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getCount(): Int {
            return 4
        }

        override fun getItem(position: Int): Fragment {
            return when(position) {
                0 -> {
                    HomeFragment()
                }

                1 -> {
                    HomeFragment()
                }

                2 -> {
                     HomeFragment()
                }
                3 -> {
                    TextLibraryFragment()
                }

                else -> {
                    TextLibraryFragment()
                }
            }
        }
    }

}