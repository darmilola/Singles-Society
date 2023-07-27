package com.singlesSociety.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.singlesSociety.UiModels.SocietyUserModel
import com.singlesSociety.databinding.FragmentSocietyMembersListBinding
import com.singlesSociety.uiAdapters.PeopleAdapter


class SocietyMembersListFragment : Fragment() {

    var adapter: PeopleAdapter? = null
    private var peopleList: ArrayList<SocietyUserModel>? = null

    private lateinit var viewBinding: FragmentSocietyMembersListBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentSocietyMembersListBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPeople()
    }

    private fun initPeople(){
        peopleList = arrayListOf()
        for (i in 0..50) {
            peopleList?.add(SocietyUserModel())
        }
        adapter = PeopleAdapter(peopleList!!, requireContext())
        viewBinding.usersListRecyclerview.adapter = adapter
    }


}