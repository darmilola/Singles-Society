package com.singlesSociety

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.singlesSociety.uiAdapters.ExploreSearchAdapter
import com.singlesSociety.UiModels.ExploreItem
import kotlinx.android.synthetic.main.activity_explore_search.*

class ExploreSearch : AppCompatActivity() {


    private var itemList: ArrayList<ExploreItem>  = arrayListOf()
    private lateinit var itemAdapter: ExploreSearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explore_search)
        populateView()

        exploreSearchBackButton.setOnClickListener {
            finish()
        }


    }

    private fun populateView(){
        for (i in 0..50) {
            itemList.add(ExploreItem())
        }
        itemAdapter = ExploreSearchAdapter(itemList,this)
        exploreSearchRecyclerView.adapter = itemAdapter
    }

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            window.navigationBarColor = ContextCompat.getColor(this, R.color.special_activity_background)
            window.statusBarColor = ContextCompat.getColor(this, R.color.special_activity_background)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            // getWindow().setFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS,WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }
    }
}