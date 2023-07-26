package com.singlesSociety.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.singlesSociety.R
import com.singlesSociety.UiModels.CommunityPostModel
import com.singlesSociety.UiModels.ExploreEvent
import com.singlesSociety.UiModels.ExploreItem
import com.singlesSociety.UiModels.PopularHashtagModel
import com.singlesSociety.UiModels.SocietyHeaderModel
import com.singlesSociety.UiModels.SocietyModel
import com.singlesSociety.databinding.FragmentTrendingBinding
import com.singlesSociety.uiAdapters.ExploreCommunityAdapter
import com.singlesSociety.uiAdapters.ExploreLiveAdapter
import com.singlesSociety.uiAdapters.HashtagAdapter
import com.singlesSociety.uiAdapters.HomeMainAdapter
import com.singlesSociety.uiAdapters.TrendingHeaderAdapter
import com.ss.storiesview.StoriesProgressView


class TrendingFragment(private var enterSpaceListener: Function0<Unit>? = null, private var createSpaceListener: Function0<Unit>? = null, private var visitProfileListener: Function0<Unit>? = null, private var visitEventListener: Function0<Unit>? = null) : Fragment(), CommentBottomSheet.CommentActionListener, StoriesProgressView.StoriesListener {

    private var itemList: ArrayList<ExploreItem>  = arrayListOf()
    private lateinit var itemView: View
    private var snapHelper: SnapHelper? = null
    private var discoverSnapHelper: SnapHelper? = null
    private lateinit var viewBinding: FragmentTrendingBinding
    private lateinit var manager: LinearLayoutManager
   /* private val smoothScroller: RecyclerView.SmoothScroller = object : LinearSmoothScroller(context) {
        override fun getVerticalSnapPreference(): Int {
            return SNAP_TO_START
        }
    }*/

    private val images = ArrayList<SocietyHeaderModel>().apply {
        add(SocietyHeaderModel(1,R.drawable.woman_official))
        add(SocietyHeaderModel(0,R.drawable.asian_lady))
        add(SocietyHeaderModel(1,R.drawable.african_american_woman))
        add(SocietyHeaderModel(0,R.drawable.woman_official))
    }

    private val headerAdapter by lazy { TrendingHeaderAdapter(images) }


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
            when (checkedId) {
                R.id.followed -> {
                    populateDiscoverPosts()
                }
                R.id.hashtag -> {
                    Log.e("mTag", "onViewCreated: 1")
                    populateDiscoverEvents()
                }
                R.id.spaces -> {
                    Log.e("mTag", "onViewCreated: 1")
                    populateCommunity()
                }
                else -> {
                    Log.e("mTag", "onViewCreated: 2")
                    populateCommunity()
                }
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

    private fun populateCommunity(){
        val itemList = ArrayList<ExploreItem>()
        for (i in 0..9) {
            itemList.add(ExploreItem())
        }

        val itemAdapter = ExploreCommunityAdapter(
            itemList,
            requireContext()
        )

        viewBinding.exploreDiscoverRecyclerView.adapter = itemAdapter

        itemAdapter.setSpacesClickedListener {
            enterSpaceListener?.invoke()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun populateView(){
        populateDiscoverPosts()
        viewBinding.exploreHeader.adapter = headerAdapter
        PagerSnapHelper().attachToRecyclerView(viewBinding.exploreHeader)
        viewBinding.stories.setStoriesCount(images.size)
        viewBinding.stories.setStoryDuration(6000L)
        viewBinding.stories.setStoriesListener(this)
        viewBinding.stories.startStories()
        manager =  viewBinding.exploreHeader.layoutManager as LinearLayoutManager
        viewBinding.exploreHeader.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val position = manager.findFirstCompletelyVisibleItemPosition()
                    if (position >= 0)viewBinding.stories.restartStories(position)
                }
                else if(newState == RecyclerView.SCROLL_STATE_DRAGGING){
                    viewBinding.stories.pause()
                }
            }
        })

        viewBinding.exploreHeader.setOnTouchListener(OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                viewBinding.stories.resume()
            } else if (event.action == MotionEvent.ACTION_DOWN) {
                viewBinding.stories.pause()
            }
            false
        })

    }



    private fun populateDiscoverPosts(){
        val societyModelArrayList = ArrayList<SocietyModel>()
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


        homeMainAdapter.setVisitProfileListener {
            visitProfileListener?.invoke()
        }

        homeMainAdapter.setPostListener {

        }

        homeMainAdapter.setAddACommentClickListener {
            val commentingSection = CommentBottomSheet(this@TrendingFragment)
            commentingSection.show(parentFragmentManager, "commentingSection")
        }

    }



    private fun populateDiscoverEvents(){
        var itemList = ArrayList<ExploreEvent>()
        itemList.add(ExploreEvent(1))
        for (i in 0..5) {
            itemList.add(ExploreEvent(0))
        }

        viewBinding.exploreDiscoverRecyclerView.adapter = ExploreLiveAdapter(requireContext(),itemList)
    }




    override fun onProfileVisit() {
        visitProfileListener?.invoke()
    }

    override fun onNext(position: Int) {
        manager.smoothScrollToPosition(viewBinding.exploreHeader,null,position)
    }

    override fun onPrev(position: Int) {
        manager.smoothScrollToPosition(viewBinding.exploreHeader,null,position)
    }

    override fun onComplete() {
        viewBinding.stories.restartStories()
        manager.smoothScrollToPosition(viewBinding.exploreHeader,null,0)
    }

}