package com.aure.fragments

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aure.NewWelcome
import com.aure.R
import com.aure.UiAdapters.ExploreItemAdapter
import com.aure.UiModels.ExploreItem
import com.aure.WelcomeActivity
import kotlinx.android.synthetic.main.fragment_trending.*
import kotlinx.android.synthetic.main.fragment_user_profile.*
import kotlinx.android.synthetic.main.profile_info_arena.*


class UserProfileFragment : Fragment() {

    private var itemList: ArrayList<ExploreItem>  = arrayListOf()
    private lateinit var itemView: View
    private lateinit var itemAdapter: ExploreItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateView()

        logOutCta.setOnClickListener {
            logOut()
        }
    }

    private fun populateView(){
        for (i in 0..30) {
            itemList.add(ExploreItem())
        }
        itemAdapter = ExploreItemAdapter(itemList,requireContext())
        recyclerview.adapter = itemAdapter

    }

    private fun logOut() {
        val preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        preferences.edit().remove("userEmail").apply()
        startActivity(Intent(requireContext(), NewWelcome::class.java))
        activity?.finish()
    }
}