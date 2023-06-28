package com.singlesSociety.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.singlesSociety.R
import com.singlesSociety.uiAdapters.ShowCaseAdapter
import com.singlesSociety.UiModels.*
import kotlinx.android.synthetic.main.fragment_dating_profile.*
import kotlinx.android.synthetic.main.fragment_dating_profile.loaderView
import kotlinx.android.synthetic.main.fragment_dating_profile.rootView


class DatingProfileFragment : Fragment(){

    private var showCaseAdapter: ShowCaseAdapter? = null
    private var mainActivityModel: MainActivityModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dating_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }


    private fun initView(){
        mainActivityModel = MainActivityModel(requireContext())
        mainActivityModel?.GetUserInfo()
        mainActivityModel?.setInfoReadyListener(object : MainActivityModel.InfoReadyListener {
            override fun onReady(
                previewProfileModel: PreviewProfileModel,
                likeIds: ArrayList<String>
            ) {
                loaderView.visibility = View.GONE
                rootView.visibility = View.VISIBLE
                val showCaseModelArrayList = ArrayList<ShowCaseModel>()
                val mainStrings = ArrayList<String>()
                val quoteStrings = ArrayList<String>()
                val aboutStrings = ArrayList<String>()
                val careerStrings = ArrayList<String>()
                val imageStrings = ArrayList<String>()
                val goalStrings = ArrayList<String>()
                mainStrings.add(previewProfileModel.firstname)
                mainStrings.add(previewProfileModel.age.toString())
                mainStrings.add(previewProfileModel.city)
                mainStrings.add(previewProfileModel.occupation)
                mainStrings.add(previewProfileModel.image1Url)
                mainStrings.add(previewProfileModel.userId)
                val showCaseModel =
                    ShowCaseModel(mainStrings, 1, likeIds, previewProfileModel.userId)
                showCaseModelArrayList.add(showCaseModel)
                quoteStrings.add(previewProfileModel.quote)
                val showCaseModel1 =
                    ShowCaseModel(quoteStrings, 2, likeIds, previewProfileModel.userId)
                showCaseModelArrayList.add(showCaseModel1)
                val showCaseModel9 =
                    ShowCaseModel(goalStrings, 9, likeIds, previewProfileModel.userId)
                showCaseModelArrayList.add(showCaseModel9)
                aboutStrings.add(previewProfileModel.status)
                aboutStrings.add(previewProfileModel.smoking)
                aboutStrings.add(previewProfileModel.drinking)
                aboutStrings.add(previewProfileModel.language)
                aboutStrings.add(previewProfileModel.religion)
                aboutStrings.add(previewProfileModel.marriageGoals)
                val showCaseModel2 =
                    ShowCaseModel(aboutStrings, 3, likeIds, previewProfileModel.userId)
                showCaseModelArrayList.add(showCaseModel2)
                careerStrings.add(previewProfileModel.educationLevel)
                careerStrings.add(previewProfileModel.occupation)
                careerStrings.add(previewProfileModel.workplace)
                careerStrings.add(previewProfileModel.image2Url)
                val showCaseModel3 =
                    ShowCaseModel(careerStrings, 4, likeIds, previewProfileModel.userId)
                showCaseModelArrayList.add(showCaseModel3)

                imageStrings.add(previewProfileModel.image3Url)
                val showCaseModel5 =
                    ShowCaseModel(imageStrings, 6, likeIds, previewProfileModel.userId)
                showCaseModelArrayList.add(showCaseModel5)

                showCaseAdapter = ShowCaseAdapter(requireContext(), showCaseModelArrayList)
                showCaseAdapter!!.setUiNeedsAdjustment(true)
                val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                userDatingProfileRecyclerview.setLayoutManager(layoutManager)
                userDatingProfileRecyclerview.setAdapter(showCaseAdapter)

            }

            override fun onError(message: String?) {

            }

        })


    }


}