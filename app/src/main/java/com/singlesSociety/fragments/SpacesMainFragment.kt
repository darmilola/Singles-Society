package com.singlesSociety.fragments

import android.content.Context
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.singlesSociety.R
import com.singlesSociety.uiAdapters.HomeMainAdapter
import com.singlesSociety.UiModels.*
import com.singlesSociety.databinding.FragmentSpacesMainBinding
import nl.dionsegijn.konfetti.core.Angle
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.Rotation
import nl.dionsegijn.konfetti.core.emitter.Emitter
import nl.dionsegijn.konfetti.core.models.Shape
import nl.dionsegijn.konfetti.core.models.Size
import java.util.concurrent.TimeUnit
import kotlin.random.Random


class SpacesMainFragment(private var visitProfileListener: Function0<Unit>? = null, private var exitSpaceListener: Function0<Unit>? = null) : Fragment(), CommentBottomSheet.CommentActionListener{

    var homeMainAdapter: HomeMainAdapter? = null
    var societyModelArrayList = java.util.ArrayList<SocietyModel>()
    var mainActivityModel: MainActivityModel? = null
    var lastPosition = RecyclerView.NO_POSITION
    val layoutManager = NoScrollLinearLayoutManager(context)
    private lateinit var viewBinding: FragmentSpacesMainBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewBinding = FragmentSpacesMainBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        viewBinding.exitSpaceButton.setOnClickListener {
            exitSpaceListener?.invoke()
        }
    }

    private fun initView() {
        activity?.window?.statusBarColor = resources.getColor(R.color.special_activity_background)
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


         parseUserResponse()

        /*mainActivityModel = MainActivityModel(requireContext())
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
                Toast.makeText(context,"date", Toast.LENGTH_SHORT).show()
                viewBinding.loaderView.setVisibility(View.GONE)
                viewBinding.societyRecycler.setVisibility(View.VISIBLE)
            }
        })*/
    }

    private fun parseUserResponse() {
      /*  val mainActivityModel2 = MainActivityModel(requireContext())
        mainActivityModel2.GetShowUserInfo()
        mainActivityModel2.setShowcaseInfoReadyListener(object :
            MainActivityModel.ShowcaseInfoReadyListener {
            override fun onReady(previewProfileModels: ArrayList<PreviewProfileModel>, likeIds: ArrayList<String>) {
*/
                societyModelArrayList.add(SocietyModel(3));
                for (i in 0..2) {
                    val societyModel1 =
                        SocietyModel(4)
                    societyModelArrayList.add(societyModel1);
                }
                val moreProfiles: ArrayList<PreviewProfileModel> = ArrayList()

              /*  for(profile in previewProfileModels){
                    moreProfiles.add(profile)
                    moreProfiles.add(profile)
                    moreProfiles.add(profile)
                    moreProfiles.add(profile)
                }*/

                /*val societyModelShowcase =  SocietyModel(moreProfiles,likeIds,0)

                societyModelArrayList.add(societyModelShowcase)*/


                homeMainAdapter =
                    HomeMainAdapter(
                        requireContext(),
                        societyModelArrayList
                    )

                homeMainAdapter?.setVisitProfileListener {
                    visitProfileListener?.invoke()
                }

                viewBinding.loaderView.setVisibility(View.GONE)
                viewBinding.societyRecycler.setVisibility(View.VISIBLE)
                viewBinding.societyRecycler.layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)

                viewBinding.societyRecycler.adapter = homeMainAdapter


                homeMainAdapter!!.setDatingProfileListener {
                    Toast.makeText(context,"date", Toast.LENGTH_SHORT).show()
                    //societyRecycler.layoutManager = scrollableLayoutManager

                }
                homeMainAdapter!!.setProfileEmptyListener {
                    societyModelArrayList.removeAt(lastPosition)
                    viewBinding.societyRecycler.layoutManager = scrollableLayoutManager
                    viewBinding.societyRecycler.scrollToPosition(lastPosition)
                }


                homeMainAdapter!!.setAddACommentClickListener {
                    val commentingSection = CommentBottomSheet(this@SpacesMainFragment)
                    commentingSection.show(parentFragmentManager, "commentingSection")
                }

                homeMainAdapter!!.setPostListener {
                    Toast.makeText(context,"post", Toast.LENGTH_SHORT).show()
                    // societyRecycler.layoutManager = scrollableLayoutManager

                }
                homeMainAdapter!!.setProfileMatchedListener{
                    viewBinding.loaderView.setVisibility(View.GONE)
                    viewBinding.societyRecycler.setVisibility(View.GONE)

                }

                viewBinding.societyRecycler.adapter = homeMainAdapter


                viewBinding.societyRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        lastPosition = (viewBinding.societyRecycler.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                        if (lastPosition != RecyclerView.NO_POSITION){
                            val viewType =  societyModelArrayList.get(lastPosition).itemViewType
                            if (viewType == 0){
                                //   societyRecycler.layoutManager = unScrollableLayoutManager
                                // societyRecycler.scrollToPosition(lastPosition)
                            }
                        }
                    }
                })

                /*
                                viewBinding.societyRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                                        lastPosition = (viewBinding.societyRecycler.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                                        if (lastPosition != RecyclerView.NO_POSITION){
                                            val viewType =  societyModelArrayList[lastPosition].itemViewType
                                            if (viewType == 0){
                                                (viewBinding.societyRecycler.layoutManager as NoScrollLinearLayoutManager).disableScrolling()
                                            }
                                            else if(viewType == 3){
                                                viewBinding.spacesMainToolbar.visibility = View.GONE
                                            }
                                            else{
                                                viewBinding.spacesMainToolbar.visibility = View.VISIBLE
                                                (viewBinding.societyRecycler.layoutManager as NoScrollLinearLayoutManager).enableScrolling()
                                            }
                                        }
                                    }
                                })*/
                Toast.makeText(context,"date3", Toast.LENGTH_SHORT).show()




       // })
    }

    var unScrollableLayoutManager: LinearLayoutManager = object : LinearLayoutManager(context) {
        override fun canScrollVertically(): Boolean {
            return false
        }
    }

    var scrollableLayoutManager: LinearLayoutManager = object : LinearLayoutManager(context) {
        override fun canScrollVertically(): Boolean {
            return true
        }
    }

    class NoScrollLinearLayoutManager(context: Context?) : LinearLayoutManager(context) {
        private var scrollable = true

        fun enableScrolling() {
            scrollable = true
        }

        fun disableScrolling() {
            scrollable = false
        }

        override fun canScrollVertically() =
            super.canScrollVertically() && scrollable
    }

    override fun onProfileVisit() {
        visitProfileListener?.invoke()
    }

}