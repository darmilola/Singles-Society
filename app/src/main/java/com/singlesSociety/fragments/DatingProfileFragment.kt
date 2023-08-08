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
import com.singlesSociety.databinding.FragmentDatingProfileBinding


class DatingProfileFragment(private val isFromUser: Boolean = false) : Fragment(){

    private var showCaseAdapter: ShowCaseAdapter? = null
    private var mainActivityModel: MainActivityModel? = null
    private lateinit var viewBinding: FragmentDatingProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentDatingProfileBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }


    private fun initView(){

        if(isFromUser){
            viewBinding.swipeLayout.root.visibility = View.VISIBLE
        }
        else{
            viewBinding.swipeLayout.root.visibility = View.GONE
        }

        mainActivityModel = MainActivityModel(requireContext())
        mainActivityModel?.GetUserInfo()
        mainActivityModel?.setInfoReadyListener(object : MainActivityModel.InfoReadyListener {
            override fun onReady(
                previewProfileModel: PreviewProfileModel,
                likeIds: ArrayList<String>
            ) {
                viewBinding.rootView.visibility = View.VISIBLE
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
                mainStrings.add("https://cdn.pixabay.com/photo/2020/03/11/17/32/engineer-4922781_1280.jpg")
                mainStrings.add(previewProfileModel.userId)
                val showCaseModel =
                    ShowCaseModel(mainStrings, 1, likeIds, previewProfileModel.userId)
                showCaseModelArrayList.add(showCaseModel)
                quoteStrings.add(previewProfileModel.quote)
                val showCaseModel1 =
                    ShowCaseModel(quoteStrings, 2, likeIds, previewProfileModel.userId)
                showCaseModelArrayList.add(showCaseModel1)

                imageStrings.add(previewProfileModel.image3Url)
                val showCaseModel5 =
                    ShowCaseModel(imageStrings, 6, likeIds, previewProfileModel.userId)
                showCaseModelArrayList.add(showCaseModel5)

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
                careerStrings.add("https://cdn.pixabay.com/photo/2020/03/11/17/32/engineer-4922781_1280.jpg")
                val showCaseModel3 =
                    ShowCaseModel(careerStrings, 4, likeIds, previewProfileModel.userId)
                showCaseModelArrayList.add(showCaseModel3)



                showCaseAdapter = ShowCaseAdapter(requireContext(), showCaseModelArrayList)
                showCaseAdapter!!.setUiNeedsAdjustment(true)
                val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                viewBinding.userDatingProfileRecyclerview.setLayoutManager(layoutManager)
                viewBinding.userDatingProfileRecyclerview.setAdapter(showCaseAdapter)

            }

            override fun onError(message: String?) {

            }

        })


    }


}