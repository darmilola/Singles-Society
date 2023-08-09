package com.singlesSociety.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.singlesSociety.UiModels.CommunityPostModel
import com.singlesSociety.UiModels.MainActivityModel
import com.singlesSociety.UiModels.PreviewProfileModel
import com.singlesSociety.UiModels.ShowCaseModel
import com.singlesSociety.UiModels.SocietyModel
import com.singlesSociety.databinding.FragmentSocietyHomeBinding
import com.singlesSociety.uiAdapters.ShowCaseAdapter
import com.singlesSociety.uiAdapters.SocietyMainAdapter
import nl.dionsegijn.konfetti.core.Angle
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.Rotation
import nl.dionsegijn.konfetti.core.emitter.Emitter
import nl.dionsegijn.konfetti.core.models.Shape
import nl.dionsegijn.konfetti.core.models.Size
import java.util.concurrent.TimeUnit


class SocietyHomeFragment(private var visitProfileListener: Function0<Unit>? = null) : Fragment(), CommentBottomSheet.CommentActionListener{

    var societyMainAdapter: SocietyMainAdapter? = null
    var societyModelArrayList = ArrayList<SocietyModel>()
    var mainActivityModel: MainActivityModel? = null
    var lastPosition = RecyclerView.NO_POSITION
    private lateinit var viewBinding: FragmentSocietyHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        viewBinding = FragmentSocietyHomeBinding.inflate(layoutInflater)
        return viewBinding.root
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
        viewBinding.konfettiView.konfettiView.start(mParty)
        parseUserResponse()

/*
          mainActivityModel = MainActivityModel(requireContext())
          mainActivityModel?.GetUserInfo()
          mainActivityModel?.setInfoReadyListener(object : MainActivityModel.InfoReadyListener {
              override fun onReady(previewProfileModel: PreviewProfileModel,  likeIds: ArrayList<String>) {
                  parseUserResponse()
              }

              override fun onError(message: String) {

              }
          })*/
    }

    private fun parseUserResponse() {
          /* val mainActivityModel2 = MainActivityModel(requireContext())
           mainActivityModel2.GetShowUserInfo()
           mainActivityModel2.setShowcaseInfoReadyListener(object :
               MainActivityModel.ShowcaseInfoReadyListener {
               override fun onReady(
                   previewProfileModels: ArrayList<PreviewProfileModel>,
                   likeIds: ArrayList<String>
               ) {*/


        val moreProfiles: ArrayList<PreviewProfileModel> = ArrayList()

        mainActivityModel = MainActivityModel(requireContext())
        mainActivityModel?.GetUserInfo()
        mainActivityModel?.setInfoReadyListener(object : MainActivityModel.InfoReadyListener {
            override fun onReady(
                previewProfileModel: PreviewProfileModel,
                likeIds: ArrayList<String>
            ) {

                for (i in 0..2) {
                    val communityPostModel = CommunityPostModel()
                    val societyModel1 =
                        SocietyModel(communityPostModel, 1)
                    societyModelArrayList.add(societyModel1);
                }

                for (i in 0..2) {
                    val societyModel1 =
                        SocietyModel(4)
                    societyModelArrayList.add(societyModel1);
                    societyModelArrayList.add(SocietyModel(5))
                }

                moreProfiles.add(previewProfileModel)
                moreProfiles.add(previewProfileModel)
                moreProfiles.add(previewProfileModel)
                moreProfiles.add(previewProfileModel)
                moreProfiles.add(previewProfileModel)
                moreProfiles.add(previewProfileModel)

            }

            override fun onError(message: String?) {
                Toast.makeText(context,message,Toast.LENGTH_LONG).show()

                for (i in 0..2) {
                    val communityPostModel = CommunityPostModel()
                    val societyModel1 =
                        SocietyModel(communityPostModel, 1)
                    societyModelArrayList.add(societyModel1);
                }

                for (i in 0..2) {
                    val societyModel1 =
                        SocietyModel(4)
                    societyModelArrayList.add(societyModel1);
                    societyModelArrayList.add(SocietyModel(5))
                }

            }

        })

                   /*  for (i in 0..2) {
            val societyModel1 = SocietyModel(2)
            societyModelArrayList.add(societyModel1);
        }*/


                   /*   for (profile in previewProfileModels) {
               moreProfiles.add(profile)
               moreProfiles.add(profile)
               moreProfiles.add(profile)
               moreProfiles.add(profile)
           }*/
                  val societyModelShowcase = SocietyModel(moreProfiles, arrayListOf(), 0)

                   societyModelArrayList.add(societyModelShowcase)
                   societyModelArrayList.add(SocietyModel(6))


                   societyMainAdapter =
                       SocietyMainAdapter(
                           requireContext(),
                           societyModelArrayList
                       )

                   societyMainAdapter?.setVisitProfileListener {
                       visitProfileListener?.invoke()
                   }

                   viewBinding.loaderView.setVisibility(View.GONE)
                   viewBinding.societyRecycler.setVisibility(View.VISIBLE)
                   viewBinding.societyRecycler.layoutManager =
                       LinearLayoutManager(context, RecyclerView.VERTICAL, false)

                   viewBinding.societyRecycler.adapter = societyMainAdapter


                   societyMainAdapter!!.setDatingProfileListener {
                       Toast.makeText(context, "date", Toast.LENGTH_SHORT).show()
                       //societyRecycler.layoutManager = scrollableLayoutManager

                   }
                   societyMainAdapter!!.setProfileEmptyListener {
                       societyModelArrayList.removeAt(lastPosition)
                       viewBinding.societyRecycler.layoutManager = scrollableLayoutManager
                       viewBinding.societyRecycler.scrollToPosition(lastPosition)
                   }


                   societyMainAdapter!!.setAddACommentClickListener {
                       val commentingSection = CommentBottomSheet(this@SocietyHomeFragment)
                       commentingSection.show(parentFragmentManager, "commentingSection")
                   }

                   societyMainAdapter!!.setPostListener {
                       Toast.makeText(context, "post", Toast.LENGTH_SHORT).show()
                       // societyRecycler.layoutManager = scrollableLayoutManager

                   }
                   societyMainAdapter!!.setProfileMatchedListener {
                       viewBinding.loaderView.setVisibility(View.GONE)
                       viewBinding.societyRecycler.setVisibility(View.GONE)
                       viewBinding.konfettiView.metMatchRoot.setVisibility(View.VISIBLE)

                   }


          /*         viewBinding.societyRecycler.addOnScrollListener(object :
                       RecyclerView.OnScrollListener() {
                       override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                           lastPosition =
                               (viewBinding.societyRecycler.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                           if (lastPosition != RecyclerView.NO_POSITION) {
                               val viewType = societyModelArrayList.get(lastPosition).itemViewType
                               if (viewType == 0) {
                                   //   societyRecycler.layoutManager = unScrollableLayoutManager
                                   // societyRecycler.scrollToPosition(lastPosition)
                               }
                           }
                       }
                   })

               }
           })*/
    }


    /*     override fun onError(message: String) {
             Log.e("onError: ", message)
             for (i in 0..2) {
                 val communityPostModel = CommunityPostModel()
                 societyModelArrayList.add(SocietyModel(5))
             }


             societyModelArrayList.shuffle(Random(50))

             homeMainAdapter =
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
                 val commentingSection = CommentBottomSheet(this@HomeFragment)
                 commentingSection.show(parentFragmentManager, "commentingSection")
             }

             viewBinding.loaderView.setVisibility(View.GONE)
             viewBinding.societyRecycler.setVisibility(View.VISIBLE)
             viewBinding.konfettiView.metMatchRoot.visibility = View.GONE
             viewBinding.societyRecycler.layoutManager = unScrollableLayoutManager

             viewBinding.societyRecycler.adapter = homeMainAdapter

             homeMainAdapter!!.setDatingProfileListener {
                 viewBinding.societyRecycler.isNestedScrollingEnabled = true
             }
             homeMainAdapter!!.setPostListener {
                 viewBinding.societyRecycler.isNestedScrollingEnabled = false

             }
             homeMainAdapter!!.setDatingProfileListener {

             }
             homeMainAdapter!!.setProfileMatchedListener {
                 viewBinding.loaderView.setVisibility(View.GONE)
                 viewBinding.societyRecycler.setVisibility(View.GONE)
                 viewBinding.konfettiView.metMatchRoot.setVisibility(View.VISIBLE)
             }
             *//*  loaderView.setVisibility(View.GONE)
                    activity_main_main_view.setVisibility(View.VISIBLE)
                    met_match_root.setVisibility(View.GONE)
                    error_layout_root.setVisibility(View.VISIBLE*//*
                }

                override fun onEmptyResponse() {
                    for (i in 0..2) {
                        val communityPostModel = CommunityPostModel()
                        val societyModel1 =
                            SocietyModel(communityPostModel, 4)
                        societyModelArrayList.add(SocietyModel(5))
                        societyModelArrayList.add(societyModel1);
                    }


                    societyModelArrayList.shuffle(Random(50))

                    homeMainAdapter =
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
                        val commentingSection = CommentBottomSheet(this@HomeFragment)
                        commentingSection.show(parentFragmentManager, "commentingSection")
                    }

                    viewBinding.loaderView.setVisibility(View.GONE)
                    viewBinding.societyRecycler.setVisibility(View.VISIBLE)
                    viewBinding.konfettiView.metMatchRoot.visibility = View.GONE
                    viewBinding.societyRecycler.layoutManager = unScrollableLayoutManager

                    viewBinding.societyRecycler.adapter = homeMainAdapter

                    homeMainAdapter!!.setDatingProfileListener {
                        viewBinding.societyRecycler.isNestedScrollingEnabled = true
                    }
                    homeMainAdapter!!.setPostListener {
                        viewBinding.societyRecycler.isNestedScrollingEnabled = false

                    }
                    homeMainAdapter!!.setDatingProfileListener {

                    }
                    homeMainAdapter!!.setProfileMatchedListener {
                        viewBinding.loaderView.setVisibility(View.GONE)
                        viewBinding.societyRecycler.setVisibility(View.GONE)
                        viewBinding.konfettiView.metMatchRoot.setVisibility(View.VISIBLE)
                    }
                    *//*  loaderView.setVisibility(View.GONE)
                      activity_main_main_view.setVisibility(View.VISIBLE)
                      met_match_root.setVisibility(View.GONE)
                      error_layout_root.setVisibility(View.VISIBLE*//*

                }
            }
                )*/


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

    override fun onProfileVisit() {
        visitProfileListener?.invoke()
    }

}