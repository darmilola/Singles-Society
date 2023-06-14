package com.aure.fragments

import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aure.R
import com.aure.UiAdapters.HomeMainAdapter
import com.aure.UiModels.*
import kotlinx.android.synthetic.main.activity_met_match_page.*
import kotlinx.android.synthetic.main.emptyfilter_layout.*
import kotlinx.android.synthetic.main.error_page.*
import kotlinx.android.synthetic.main.fragment_home.societyRecycler
import kotlinx.android.synthetic.main.fragment_home.loaderView
import nl.dionsegijn.konfetti.core.Angle
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.Rotation
import nl.dionsegijn.konfetti.core.emitter.Emitter
import nl.dionsegijn.konfetti.core.models.Shape
import nl.dionsegijn.konfetti.core.models.Size
import java.util.concurrent.TimeUnit
import kotlin.random.Random


class SpacesMainFragment(private var visitProfileListener: Function0<Unit>? = null) : Fragment(){

    var homeMainAdapter: HomeMainAdapter? = null
    var societyModelArrayList = java.util.ArrayList<SocietyModel>()
    var mainActivityModel: MainActivityModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_spaces_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        konfettiView.start(mParty)


        error_page_retry.setOnClickListener(View.OnClickListener {
            empty_layout_root.setVisibility(View.GONE)
            loaderView.setVisibility(View.VISIBLE)
            societyRecycler.setVisibility(View.GONE)
            mainActivityModel?.GetUserInfo()
        })

        mainActivityModel = MainActivityModel(requireContext())
        mainActivityModel?.GetUserInfo()
        mainActivityModel?.setInfoReadyListener(object : MainActivityModel.InfoReadyListener {
            override fun onReady(previewProfileModel: PreviewProfileModel,  likeIds: ArrayList<String>) {
                val preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
                preferences.edit().putString("firstname", previewProfileModel.firstname).apply()
                preferences.edit().putString("lastname", previewProfileModel.lastname).apply()
                preferences.edit().putString("imageUrl", previewProfileModel.imageUrl).apply()
                preferences.edit().putString("userId", previewProfileModel.userId).apply()
                preferences.edit().putString("phonenumber", previewProfileModel.phonenumber).apply()
                parseUserResponse()
            }

            override fun onError(message: String) {
                loaderView.setVisibility(View.GONE)
                societyRecycler.setVisibility(View.VISIBLE)
                error_layout_root.setVisibility(View.VISIBLE)
            }
        })
    }

    private fun parseUserResponse() {
        val mainActivityModel2 = MainActivityModel(requireContext())
        mainActivityModel2.GetShowUserInfo()
        mainActivityModel2.setShowcaseInfoReadyListener(object :
            MainActivityModel.ShowcaseInfoReadyListener {
            override fun onReady(previewProfileModels: ArrayList<PreviewProfileModel>, likeIds: ArrayList<String>) {
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

                val societyModelShowcase =  SocietyModel(previewProfileModels,likeIds,0)

                societyModelArrayList.add(societyModelShowcase)
                societyModelArrayList.shuffle(Random(50))

                homeMainAdapter =
                    HomeMainAdapter(
                        requireContext(),
                        societyModelArrayList
                    )

                homeMainAdapter?.setVisitProfileListener {
                    visitProfileListener?.invoke()
                }

                loaderView.setVisibility(View.GONE)
                societyRecycler.setVisibility(View.VISIBLE)
                met_match_root.visibility = View.GONE
            }

            override fun onError(message: String) {
                loaderView.setVisibility(View.GONE)
                societyRecycler.setVisibility(View.VISIBLE)
                met_match_root.setVisibility(View.GONE)
                error_layout_root.setVisibility(View.VISIBLE)
            }

            override fun onEmptyResponse() {

            }


        })
    }
}