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
import com.singlesSociety.databinding.FragmentTrendingBinding
import com.singlesSociety.databinding.TrendingHashtagsBinding
import com.singlesSociety.uiAdapters.*


class TrendingFragment(private var enterSpaceListener: Function0<Unit>? = null, private var createSpaceListener: Function0<Unit>? = null, private var visitProfileListener: Function0<Unit>? = null, private var visitEventListener: Function0<Unit>? = null) : Fragment(), CommentBottomSheet.CommentActionListener {

    private var itemList: ArrayList<ExploreItem>  = arrayListOf()
    private var hashtagList: ArrayList<PopularHashtagModel>  = arrayListOf()
    private var hashtagAdapter: HashtagAdapter? = null
    private lateinit var itemView: View
    private lateinit var itemAdapter: ExploreCommunityAdapter
    private var snapHelper: SnapHelper? = null
    private var discoverSnapHelper: SnapHelper? = null
    private lateinit var viewBinding: FragmentTrendingBinding

    private val images = ArrayList<Int>().apply {
        add(R.drawable.asian_lady)
        add(R.drawable.asian_lady)
        add(R.drawable.asian_lady)
    }

    private val headerAdapter by lazy { TrendingHeaderAdapter() }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        viewBinding = FragmentTrendingBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateView()

       /* exploreSearchIcon.setOnClickListener {
            context?.startActivity(Intent(requireActivity(),ExploreSearch::class.java))
        }*/



        viewBinding.exploreDiscoverChipGroup.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.followed){
                populateDiscoverPosts()
            }
            else{
                populateDiscoverEvents()
            }
        }
    }

 /*   private fun populatePopularHashtags(){
        val popularHashtagModel1 = PopularHashtagModel("",true)
        val popularHashtagModel2 = PopularHashtagModel("",true)
        val popularHashtagModel3 = PopularHashtagModel("",false)
        val popularHashtagModel4 = PopularHashtagModel("",true)
        val popularHashtagModel5 = PopularHashtagModel("",false)
        val popularHashtagModel6 = PopularHashtagModel("",false)
        hashtagList.add(popularHashtagModel1)
        hashtagList.add(popularHashtagModel2)
        hashtagList.add(popularHashtagModel3)
        hashtagList.add(popularHashtagModel4)
        hashtagList.add(popularHashtagModel5)
        hashtagList.add(popularHashtagModel6)

        hashtagAdapter = HashtagAdapter(hashtagList)
    }*/

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

        viewBinding.exploreHeader.setAdapter(headerAdapter)
        viewBinding.exploreHeader.setItems(images)

    }


    private fun populateForYouPosts(){
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
        var societyModelArrayList = ArrayList<SocietyModel>()
        val communityPostModel = CommunityPostModel()
        val societyModel1 =
            SocietyModel(communityPostModel, 4)
        societyModelArrayList.add(societyModel1);
        societyModelArrayList.add(SocietyModel(communityPostModel, 4));
        societyModelArrayList.add(SocietyModel(communityPostModel, 4));
        societyModelArrayList.add(SocietyModel(communityPostModel, 4));


        val homeMainAdapter =
            HomeMainAdapter(
                requireContext(),
                societyModelArrayList
            )

        viewBinding.exploreDiscoverRecyclerView.layoutManager = LinearLayoutManager(requireContext(),
            RecyclerView.VERTICAL,false)
        viewBinding.exploreDiscoverRecyclerView.adapter = homeMainAdapter


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



    private fun populateDiscoverEvents(){
        var itemList = ArrayList<ExploreItem>()
        for (i in 0..5) {
            itemList.add(ExploreItem())
        }
        itemAdapter = ExploreCommunityAdapter(
            itemList,
            requireContext()
        )
        viewBinding.exploreDiscoverRecyclerView.adapter = ExploreLiveAdapter(itemList, visitEventListener = {
            visitEventListener?.invoke()
        })
        viewBinding.exploreDiscoverRecyclerView.layoutManager = GridLayoutManager(requireContext(),2)
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
    }



    override fun onProfileVisit() {
        visitProfileListener?.invoke()
    }

}