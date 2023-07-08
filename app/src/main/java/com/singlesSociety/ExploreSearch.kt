package com.singlesSociety

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.singlesSociety.uiAdapters.ExploreSpacesAdapter
import com.singlesSociety.UiModels.ExploreItem
import com.singlesSociety.databinding.ActivityExploreSearchBinding

class ExploreSearch : AppCompatActivity() {


    private var itemList: ArrayList<ExploreItem>  = arrayListOf()
    private lateinit var itemAdapter: ExploreSpacesAdapter
    private lateinit var viewBinding: ActivityExploreSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityExploreSearchBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        populateView()

        viewBinding.exploreSearchBackButton.setOnClickListener {
            finish()
        }


    }

    private fun populateView(){
        for (i in 0..50) {
            itemList.add(ExploreItem())
        }
        itemAdapter = ExploreSpacesAdapter(itemList,this)
        viewBinding.exploreSearchRecyclerView.adapter = itemAdapter
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