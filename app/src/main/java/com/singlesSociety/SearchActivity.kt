package com.singlesSociety

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.ChipGroup
import com.singlesSociety.UiModels.CommunityPostModel
import com.singlesSociety.UiModels.ExploreEvent
import com.singlesSociety.UiModels.ExploreItem
import com.singlesSociety.UiModels.PopularHashtagModel
import com.singlesSociety.UiModels.SocietyModel
import com.singlesSociety.UiModels.SocietyUserModel
import com.singlesSociety.fragments.CommentBottomSheet
import com.singlesSociety.uiAdapters.ExploreCommunityAdapter
import com.singlesSociety.uiAdapters.SocietyEventsAdapter
import com.singlesSociety.uiAdapters.HashtagAdapter
import com.singlesSociety.uiAdapters.HomeMainAdapter
import com.singlesSociety.uiAdapters.PeopleAdapter

class SearchActivity : AppCompatActivity(), CommentBottomSheet.CommentActionListener {
    var adapter: PeopleAdapter? = null
    var peopleList: ArrayList<SocietyUserModel>? = null
    private var hashtagList: ArrayList<PopularHashtagModel>  = arrayListOf()
    private var hashtagAdapter: HashtagAdapter? = null
    lateinit var recylerview: RecyclerView
    lateinit var backBtn: ImageView
    lateinit var searchGroup: ChipGroup
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initView()
    }

    private fun initHashtags(){
        for (i in 0..50) {
            hashtagList.add(PopularHashtagModel())
        }
        hashtagAdapter = HashtagAdapter(hashtagList)
        recylerview.adapter = hashtagAdapter
    }

    private fun initPeople(){
        peopleList = arrayListOf()
        for (i in 0..50) {
            peopleList?.add(SocietyUserModel())
        }
        adapter = PeopleAdapter(peopleList!!, this)
        recylerview.adapter = adapter
    }

    private fun populateEvents(){
        var itemList = ArrayList<ExploreEvent>()
        for (i in 0..5) {
            itemList.add(ExploreEvent(0))
        }

        recylerview.adapter = SocietyEventsAdapter(this,itemList)
    }

    private fun initPosts(){
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
                this,
                societyModelArrayList
            )
        recylerview.adapter = homeMainAdapter


        homeMainAdapter.setVisitProfileListener {

        }

        homeMainAdapter.setPostListener {

        }

        homeMainAdapter.setAddACommentClickListener {
            val commentingSection = CommentBottomSheet(this)
            commentingSection.show(supportFragmentManager, "commentingSection")
        }

    }

    private fun initCommunity(){
        val itemList = ArrayList<ExploreItem>()
        for (i in 0..9) {
            itemList.add(ExploreItem())
        }

        val itemAdapter = ExploreCommunityAdapter(
            itemList,
            this
        )
        recylerview.adapter = itemAdapter
    }



    private fun initView(){
        recylerview = findViewById(R.id.searchRecyclerView)
        backBtn = findViewById(R.id.searchBackBtn)
        searchGroup = findViewById(R.id.searchChipGroup)

        recylerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        backBtn.setOnClickListener {
            finish()
        }
        initPeople()
        searchGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.people -> {
                    initPeople()
                }
                R.id.posts -> {
                    Log.e("mTag", "onViewCreated: 1")
                    initPosts()
                }
                R.id.societies -> {
                    initCommunity()
                }
                R.id.hashtags -> {
                    initHashtags()
                }
                R.id.events -> {
                    populateEvents()
                }
                else -> {
                    initPeople()
                }
            }
        }
    }


    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            window.navigationBarColor = ContextCompat.getColor(this, R.color.special_activity_background)
            window.statusBarColor = ContextCompat.getColor(this, R.color.special_activity_background)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    override fun onProfileVisit() {

    }
}