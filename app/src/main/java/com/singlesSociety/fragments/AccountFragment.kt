package com.singlesSociety.fragments

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.singlesSociety.R
import com.singlesSociety.WelcomeActivity
import com.singlesSociety.databinding.ActivityMyAccountBinding
import com.singlesSociety.databinding.FragmentAccountBinding

class AccountFragment : Fragment() {


    private lateinit var viewBinding: FragmentAccountBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        viewBinding = FragmentAccountBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun logOut() {
        val preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        preferences.edit().remove("userEmail").apply()
        startActivity(Intent(requireContext(), WelcomeActivity::class.java))
        activity?.finish()
    }
}