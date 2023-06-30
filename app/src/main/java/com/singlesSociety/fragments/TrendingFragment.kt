package com.singlesSociety.fragments

import android.app.ActionBar.LayoutParams
import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.singlesSociety.R
import com.singlesSociety.UiModels.*
import com.singlesSociety.uiAdapters.ExploreCommunityAdapter
import com.singlesSociety.uiAdapters.ExploreLiveAdapter
import com.singlesSociety.uiAdapters.HomeMainAdapter
import com.singlesSociety.uiAdapters.TrendingHeaderAdapter
import kotlinx.android.synthetic.main.fragment_trending.*


class TrendingFragment(private var enterSpaceListener: Function0<Unit>? = null, private var createSpaceListener: Function0<Unit>? = null, private var visitProfileListener: Function0<Unit>? = null, private var visitEventListener: Function0<Unit>? = null) : Fragment(), CommentBottomSheet.CommentActionListener {

    private var itemList: ArrayList<ExploreItem>  = arrayListOf()
    private lateinit var itemView: View
    private lateinit var itemAdapter: ExploreCommunityAdapter
    private var snapHelper: SnapHelper? = null
    private var discoverSnapHelper: SnapHelper? = null

    private val images = ArrayList<Int>().apply {
        add(R.drawable.asian_lady)
        add(R.drawable.asian_lady)
        add(R.drawable.asian_lady)
    }

    private val headerAdapter by lazy { TrendingHeaderAdapter() }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        itemView = inflater.inflate(R.layout.fragment_trending, container, false)
        return itemView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateView()

       /* exploreSearchIcon.setOnClickListener {
            context?.startActivity(Intent(requireActivity(),ExploreSearch::class.java))
        }*/



        exploreForYouChipGroup.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.followed){
                populateForYouPosts()
            }
            else{
                populateForYouEvents()
                setHeightToXPercentage(requireActivity(),forYouRoot, -0.08)
                Toast.makeText(context,group.checkedChipId.toString(),Toast.LENGTH_SHORT).show()
                exploreForYouRecyclerView.requestLayout()
            }

        }

        exploreDiscoverChipGroup.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.followed){
                populateDiscoverPosts()
            }
            else{
                populateDiscoverEvents()
                setHeightToXPercentage(requireActivity(),forYouDiscoverRoot, -0.08)
                Toast.makeText(context,group.checkedChipId.toString(),Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun populateView(){
        var itemList = ArrayList<ExploreItem>()
        populateForYouPosts()
        populateDiscoverPosts()
        populateEvents()
        for (i in 0..3) {
            itemList.add(ExploreItem())
        }
        itemAdapter = ExploreCommunityAdapter(
            itemList,
            requireContext()
        )
        itemAdapter.setSpacesClickedListener {
            enterSpaceListener?.invoke()
        }

        exploreHeader.setAdapter(headerAdapter)
        exploreHeader.setItems(images)

    }


    private fun setHeightToXPercentage(activity: Activity, view: View, fraction: Double) {
        val metrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(metrics)
        val maxHeight = metrics.heightPixels - (metrics.heightPixels * fraction).toInt()

        val layoutParams = view.layoutParams
        if(fraction < 0) {
             layoutParams.height = LayoutParams.WRAP_CONTENT
        }
        else{
             layoutParams.height = maxHeight
        }
        layoutParams.width = LayoutParams.MATCH_PARENT
        view.layoutParams = layoutParams
        view.requestLayout()
    }

    private fun populateForYouPosts(){
        forYouIndicator.visibility  = View.VISIBLE
        setHeightToXPercentage(requireActivity(),forYouRoot, 0.07)
        var societyModelArrayList = ArrayList<SocietyModel>()
            val communityPostModel = CommunityPostModel()
            val societyModel1 =
                SocietyModel(communityPostModel, 1)
            societyModelArrayList.add(societyModel1);
        societyModelArrayList.add(SocietyModel(communityPostModel, 2));
        societyModelArrayList.add(SocietyModel(communityPostModel, 1));
        societyModelArrayList.add(SocietyModel(communityPostModel, 1));


      val homeMainAdapter =
            HomeMainAdapter(
                requireContext(),
                societyModelArrayList
            )

        exploreForYouRecyclerView.layoutManager = LinearLayoutManager(requireContext(),
            RecyclerView.HORIZONTAL,false)
        exploreForYouRecyclerView.adapter = homeMainAdapter
        exploreForYouRecyclerView.requestLayout()
         if (snapHelper != null){

         }
        else{
             snapHelper =  PagerSnapHelper()
             (snapHelper as PagerSnapHelper).attachToRecyclerView(exploreForYouRecyclerView)
             forYouIndicator.attachToRecyclerView(exploreForYouRecyclerView,
                 snapHelper as PagerSnapHelper
             )
             homeMainAdapter.registerAdapterDataObserver(forYouIndicator.adapterDataObserver);
        }

        homeMainAdapter?.setVisitProfileListener {
            visitProfileListener?.invoke()
        }

        homeMainAdapter!!.setPostListener {

        }

        homeMainAdapter!!.setAddACommentClickListener {
            val commentingSection = CommentBottomSheet(this@TrendingFragment)
            commentingSection.show(parentFragmentManager, "commentingSection")
        }

    }



    private fun populateDiscoverPosts(){
        forYouDiscoverIndicator.visibility  = View.VISIBLE
        setHeightToXPercentage(requireActivity(),forYouDiscoverRoot, 0.07)
        var societyModelArrayList = ArrayList<SocietyModel>()
        val communityPostModel = CommunityPostModel()
        val societyModel1 =
            SocietyModel(communityPostModel, 1)
        societyModelArrayList.add(societyModel1);
        societyModelArrayList.add(SocietyModel(communityPostModel, 2));
        societyModelArrayList.add(SocietyModel(communityPostModel, 1));
        societyModelArrayList.add(SocietyModel(communityPostModel, 1));


        val homeMainAdapter =
            HomeMainAdapter(
                requireContext(),
                societyModelArrayList
            )

        exploreDiscoverRecyclerView.layoutManager = LinearLayoutManager(requireContext(),
            RecyclerView.HORIZONTAL,false)
        exploreDiscoverRecyclerView.adapter = homeMainAdapter
        if (discoverSnapHelper != null){

        }
        else{
            discoverSnapHelper =  PagerSnapHelper()
            (discoverSnapHelper as PagerSnapHelper).attachToRecyclerView(exploreDiscoverRecyclerView)
            forYouDiscoverIndicator.attachToRecyclerView(exploreDiscoverRecyclerView,
                discoverSnapHelper as PagerSnapHelper
            )
            homeMainAdapter.registerAdapterDataObserver(forYouDiscoverIndicator.adapterDataObserver);
        }

        homeMainAdapter?.setVisitProfileListener {
            visitProfileListener?.invoke()
        }

        homeMainAdapter!!.setPostListener {

        }

        homeMainAdapter!!.setAddACommentClickListener {
            val commentingSection = CommentBottomSheet(this@TrendingFragment)
            commentingSection.show(parentFragmentManager, "commentingSection")
        }

    }



    private fun populateForYouEvents(){
        forYouIndicator.visibility  = View.GONE
        var itemList = ArrayList<ExploreItem>()
        for (i in 0..5) {
            itemList.add(ExploreItem())
        }
        itemAdapter = ExploreCommunityAdapter(
            itemList,
            requireContext()
        )
        exploreForYouRecyclerView.visibility = View.GONE
        exploreForYouRecyclerView.adapter = ExploreLiveAdapter(itemList, visitEventListener = {
            visitEventListener?.invoke()
        })
        exploreForYouRecyclerView.layoutManager = GridLayoutManager(requireContext(),2)
        exploreForYouRecyclerView.visibility = View.VISIBLE
    }


    private fun populateDiscoverEvents(){
        forYouDiscoverIndicator.visibility  = View.GONE
        var itemList = ArrayList<ExploreItem>()
        for (i in 0..5) {
            itemList.add(ExploreItem())
        }
        itemAdapter = ExploreCommunityAdapter(
            itemList,
            requireContext()
        )
        exploreDiscoverRecyclerView.adapter = ExploreLiveAdapter(itemList, visitEventListener = {
            visitEventListener?.invoke()
        })
        exploreDiscoverRecyclerView.layoutManager = GridLayoutManager(requireContext(),2)
    }


    private fun populateEvents(){
        var itemList = ArrayList<ExploreItem>()
        for (i in 0..5) {
            itemList.add(ExploreItem())
        }
        itemAdapter = ExploreCommunityAdapter(
            itemList,
            requireContext()
        )
        exploreForYouEventsRecyclerView.visibility = View.GONE
        exploreForYouEventsRecyclerView.adapter = ExploreLiveAdapter(itemList)
        exploreForYouEventsRecyclerView.layoutManager = GridLayoutManager(requireContext(),2)
        exploreForYouEventsRecyclerView.visibility = View.VISIBLE
    }



    override fun onProfileVisit() {
        visitProfileListener?.invoke()
    }

}