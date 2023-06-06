package com.aure.fragments

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aure.R
import com.aure.WelcomeActivity
import kotlinx.android.synthetic.main.fragment_account.*
import kotlinx.android.synthetic.main.profile_info_arena.*
import kotlin.math.log

class AccountFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logOutCta.setOnClickListener {
            logOut()
        }
    }

    private fun logOut() {
        val preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        preferences.edit().remove("userEmail").apply()
        startActivity(Intent(requireContext(), WelcomeActivity::class.java))
        activity?.finish()
    }
}