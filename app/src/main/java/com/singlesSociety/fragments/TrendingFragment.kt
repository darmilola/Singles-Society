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
import androidx.recyclerview.widget.RecyclerView
import com.pchmn.materialchips.R2.attr.maxHeight
import com.pchmn.materialchips.model.Chip
import com.singlesSociety.R
import com.singlesSociety.uiAdapters.ExploreCommunityAdapter
import com.singlesSociety.uiAdapters.ExploreLiveAdapter
import com.singlesSociety.uiAdapters.HomeMainAdapter
import com.singlesSociety.uiAdapters.TrendingHeaderAdapter
import com.singlesSociety.UiModels.CommunityPostModel
import com.singlesSociety.UiModels.ExploreItem
import com.singlesSociety.UiModels.SocietyModel
import kotlinx.android.synthetic.main.fragment_trending.*


class TrendingFragment(private var enterSpaceListener: Function0<Unit>? = null, private var createSpaceListener: Function0<Unit>? = null, private var visitProfileListener: Function0<Unit>? = null) : Fragment(), CommentBottomSheet.CommentActionListener {

    private var itemList: ArrayList<ExploreItem>  = arrayListOf()
    private lateinit var itemView: View
    private lateinit var itemAdapter: ExploreCommunityAdapter

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

        createNewSpaceCta.setOnClickListener {
            createSpaceListener?.invoke()
        }

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
    }

    private fun populateView(){
        var itemList = ArrayList<ExploreItem>()
        populateForYouPosts()
        populateFeaturedEvents()
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
        exploreItemRecyclerview.adapter = itemAdapter
        mySpacesRecyclerview.adapter = itemAdapter
        exploreUpcomingLiveRecyclerview.adapter = ExploreLiveAdapter(itemList)

        exploreHeader.setAdapter(headerAdapter)
        exploreHeader.setItems(images)
       // populateFeaturedPosts()



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

        setHeightToXPercentage(requireActivity(),forYouRoot, 0.07)
        var societyModelArrayList = ArrayList<SocietyModel>()
            val communityPostModel = CommunityPostModel()
            val societyModel1 =
                SocietyModel(communityPostModel, 1)
            societyModelArrayList.add(societyModel1);


      val homeMainAdapter =
            HomeMainAdapter(
                requireContext(),
                societyModelArrayList
            )

        exploreForYouRecyclerView.layoutManager = LinearLayoutManager(requireContext(),
            RecyclerView.VERTICAL,false)
        exploreForYouRecyclerView.adapter = homeMainAdapter
        exploreForYouRecyclerView.requestLayout()


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


    private fun populateFeaturedPosts(){

        var societyModelArrayList = ArrayList<SocietyModel>()
        for (i in 0..4) {
            val communityPostModel = CommunityPostModel()
            val societyModel1 =
                SocietyModel(communityPostModel, 1)
            societyModelArrayList.add(societyModel1);
        }

        for (i in 0..4) {
            val societyModel1 = SocietyModel(2)
            societyModelArrayList.add(societyModel1);
        }

        val homeMainAdapter =
            HomeMainAdapter(
                requireContext(),
                societyModelArrayList
            )
        featuredRecyclerView.visibility = View.VISIBLE
        featuredRecyclerView.layoutManager = LinearLayoutManager(requireContext(),
            RecyclerView.VERTICAL,false)
        featuredRecyclerView.adapter = homeMainAdapter

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
        var itemList = ArrayList<ExploreItem>()
        for (i in 0..5) {
            itemList.add(ExploreItem())
        }
        itemAdapter = ExploreCommunityAdapter(
            itemList,
            requireContext()
        )
        exploreForYouRecyclerView.visibility = View.GONE
        exploreForYouRecyclerView.adapter = ExploreLiveAdapter(itemList)
        exploreForYouRecyclerView.layoutManager = GridLayoutManager(requireContext(),2)
        exploreForYouRecyclerView.visibility = View.VISIBLE
    }

    private fun populateFeaturedEvents(){
        var itemList = ArrayList<ExploreItem>()
        for (i in 0..3) {
            itemList.add(ExploreItem())
        }
        itemAdapter = ExploreCommunityAdapter(
            itemList,
            requireContext()
        )
        featuredRecyclerView.visibility = View.GONE
        featuredRecyclerView.adapter = ExploreLiveAdapter(itemList)
        featuredRecyclerView.layoutManager = GridLayoutManager(requireContext(),2)
        featuredRecyclerView.visibility = View.VISIBLE
    }


    private fun populateForYouCommunity(){
        var itemList = ArrayList<ExploreItem>()
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

        exploreForYouRecyclerView.visibility = View.GONE
        exploreForYouRecyclerView.adapter = ExploreCommunityAdapter(itemList,context)
        exploreForYouRecyclerView.layoutManager = GridLayoutManager(requireContext(),2)
        exploreForYouRecyclerView.visibility = View.VISIBLE
    }

    private fun populateFeaturedCommunity(){
        var itemList = ArrayList<ExploreItem>()
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

        featuredRecyclerView.visibility = View.GONE
        featuredRecyclerView.adapter = ExploreCommunityAdapter(itemList,context)
        featuredRecyclerView.layoutManager = GridLayoutManager(requireContext(),2)
        featuredRecyclerView.visibility = View.VISIBLE
    }

    override fun onProfileVisit() {
        visitProfileListener?.invoke()
    }

}