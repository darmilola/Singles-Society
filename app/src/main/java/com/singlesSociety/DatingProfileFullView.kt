package com.singlesSociety

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.singlesSociety.Arvi.widget.CardStackLayoutManager
import com.singlesSociety.uiAdapters.HomeMainAdapter
import com.singlesSociety.uiAdapters.ViewProfileAdapter
import com.singlesSociety.UiModels.*
import com.singlesSociety.databinding.ActivityDatingProfileFullViewBinding
import nl.dionsegijn.konfetti.core.Angle
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.Rotation
import nl.dionsegijn.konfetti.core.emitter.Emitter
import nl.dionsegijn.konfetti.core.models.Shape
import nl.dionsegijn.konfetti.core.models.Size
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class DatingProfileFullView : AppCompatActivity(){

    var recyclerView: RecyclerView? = null
    var adapter: ViewProfileAdapter? = null
    var progressBar: ProgressBar? = null
    var search: TextView? = null
    var manager: CardStackLayoutManager? = null
    var homeMainAdapter: HomeMainAdapter? = null
    var societyModelArrayList = ArrayList<SocietyModel>()
    var mainActivityModel: MainActivityModel? = null
    private lateinit var viewBinding: ActivityDatingProfileFullViewBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityDatingProfileFullViewBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        initView()
    }


    private fun initView() {
        val size = ArrayList<Size>()
        size.add(Size.LARGE)
        size.add(Size.MEDIUM)
        size.add(Size.SMALL)
        val colors = ArrayList<Int>()
        colors.add(0xfce18a)
        colors.add(0xff726d)
        colors.add(0xf4306d)
        colors.add(0xb48def)
        val sh = ArrayList<Shape>()
        sh.add(Shape.Rectangle(0.4f))
        val mParty = Party(
            Angle.TOP,
            360,
            120f,
            0f,
            0.9f,
            size,
            colors,
            sh,
            3000,
            true,
            Position.Relative(0.0, 1.0),
            0,
            Rotation(),
            Emitter(5, TimeUnit.MINUTES).perSecond(500))



        viewBinding.explorePageBackButton.setOnClickListener {
            finish()
        }


        mainActivityModel = MainActivityModel(this)
        mainActivityModel?.GetUserInfo()
        mainActivityModel?.setInfoReadyListener(object : MainActivityModel.InfoReadyListener {
            override fun onReady(previewProfileModel: PreviewProfileModel,
                                 likeIds: ArrayList<String>) {

                parseUserResponse()
            }

            override fun onError(message: String?) {
                TODO("Not yet implemented")
            }

        })
    }


    private fun parseUserResponse() {
        val mainActivityModel2 = MainActivityModel(this)
        mainActivityModel2.GetShowUserInfo()
        mainActivityModel2.setShowcaseInfoReadyListener(object :
            MainActivityModel.ShowcaseInfoReadyListener {
            override fun onReady(previewProfileModels: ArrayList<PreviewProfileModel>, likeIds: ArrayList<String>) {

                val societyModelShowcase =  SocietyModel(previewProfileModels,likeIds,0)

                societyModelArrayList.add(societyModelShowcase)
                societyModelArrayList.shuffle(Random(50))

                homeMainAdapter =
                    HomeMainAdapter(
                        this@DatingProfileFullView,
                        societyModelArrayList
                    )

                viewBinding.loaderView.setVisibility(View.GONE)
                viewBinding.bookmarkedDatingProfiles.layoutManager = LinearLayoutManager(this@DatingProfileFullView,RecyclerView.VERTICAL,false)
                viewBinding.bookmarkedDatingProfiles.adapter = homeMainAdapter

                homeMainAdapter!!.setDatingProfileListener {

                }

                homeMainAdapter!!.setProfileMatchedListener{
                    viewBinding.loaderView.setVisibility(View.GONE)

                }
            }

            override fun onError(message: String?) {
                TODO("Not yet implemented")
            }


            override fun onEmptyResponse() {

            }


        })
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