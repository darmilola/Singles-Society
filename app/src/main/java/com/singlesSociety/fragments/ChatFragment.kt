package com.singlesSociety.fragments

import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.pchmn.materialchips.R2.attr.layoutManager
import com.singlesSociety.R
import com.singlesSociety.uiAdapters.MatchesAdapter
import com.singlesSociety.uiAdapters.MessagesAdapter
import com.singlesSociety.UiModels.MatchesModel
import com.singlesSociety.UiModels.MessageConnectionModel
import com.singlesSociety.databinding.FragmentChatBinding

class ChatFragment : Fragment() {

    var matchesList = ArrayList<MatchesModel>()
    var messagesList = ArrayList<MessageConnectionModel>()
    var messagesAdapter: MessagesAdapter? = null
    var matchesAdapter: MatchesAdapter? = null
    var messageConnectionModel: MessageConnectionModel? = null
    private lateinit var viewBinding: FragmentChatBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        viewBinding = FragmentChatBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {

        val preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val userEmail = preferences.getString("userEmail", "")
        messageConnectionModel = MessageConnectionModel(userEmail, requireContext())
        messageConnectionModel?.getConnection()
        messageConnectionModel?.setConnectionListener(object : MessageConnectionModel.ConnectionListener {
            override fun onConnectionReady(messageConnectionModels: java.util.ArrayList<MessageConnectionModel>, matchesModelArrayList: java.util.ArrayList<MatchesModel>) {
                messagesAdapter = MessagesAdapter(requireContext(), messageConnectionModels)
                matchesAdapter = MatchesAdapter(requireContext(), matchesModelArrayList)
                viewBinding.messagesRecyclerview.adapter = messagesAdapter
                viewBinding.matchesRecyclerview.adapter = matchesAdapter
                viewBinding.matchesProgressbar.visibility = View.GONE
                viewBinding.matchesListView.visibility = View.VISIBLE
                viewBinding.matchesRoot.visibility = View.VISIBLE
                viewBinding.matchesRecyclerview.visibility = View.VISIBLE
                viewBinding.messagesRecyclerview.visibility = View.VISIBLE
                viewBinding.matchesNoMessages.visibility = View.GONE
                if (matchesModelArrayList.size < 1) {

                } else if (messageConnectionModels.size < 1) {

                }
            }

            override fun onConnectionEmpty(message: String) {

            }

            override fun onError() {

            }
        })

        //goBack!!.setOnClickListener { finish() }
    }



}