package com.singlesSociety

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.singlesSociety.UiModels.SocietyUserModel
import com.singlesSociety.uiAdapters.PeopleAdapter

class SearchActivity : AppCompatActivity() {
    var adapter: PeopleAdapter? = null
    var peopleList: ArrayList<SocietyUserModel>? = null
    lateinit var recylerview: RecyclerView
    lateinit var backBtn: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initView()
    }

    private fun initView(){
        recylerview = findViewById(R.id.searchRecyclerView)
        backBtn = findViewById(R.id.searchBackBtn)
        peopleList = arrayListOf()
        for (i in 0..50) {
             peopleList?.add(SocietyUserModel())
        }
        adapter = PeopleAdapter(peopleList!!, this)
        recylerview.adapter = adapter
        recylerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)

        backBtn.setOnClickListener {
            finish()
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
}