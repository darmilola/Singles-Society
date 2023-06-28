package com.singlesSociety.fragments

import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.singlesSociety.R
import com.singlesSociety.uiAdapters.MatchesAdapter
import com.singlesSociety.uiAdapters.MessagesAdapter
import com.singlesSociety.UiModels.MatchesModel
import com.singlesSociety.UiModels.MessageConnectionModel
import kotlinx.android.synthetic.main.activity_matches.matches_no_messages
import kotlinx.android.synthetic.main.activity_matches.matches_progressbar
import kotlinx.android.synthetic.main.activity_matches.matches_recyclerview
import kotlinx.android.synthetic.main.activity_matches.matches_root
import kotlinx.android.synthetic.main.activity_matches.messages_recyclerview
import kotlinx.android.synthetic.main.error_page.*
import kotlinx.android.synthetic.main.fragment_chat.*

class ChatFragment : Fragment() {

    var matchesList = ArrayList<MatchesModel>()
    var messagesList = ArrayList<MessageConnectionModel>()
    var messagesAdapter: MessagesAdapter? = null
    var matchesAdapter: MatchesAdapter? = null
    var messageConnectionModel: MessageConnectionModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false)
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
                messages_recyclerview.adapter = messagesAdapter
                matches_recyclerview.adapter = matchesAdapter
                val matchesManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                val messagesManger = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                messages_recyclerview!!.layoutManager = messagesManger
                matches_recyclerview.layoutManager = matchesManager
                matches_progressbar.visibility = View.GONE
                matchesListView.visibility = View.VISIBLE
                matches_root.visibility = View.VISIBLE
                matches_recyclerview.visibility = View.VISIBLE
                messages_recyclerview.visibility = View.VISIBLE
                matches_no_messages.visibility = View.GONE
                if (matchesModelArrayList.size < 1) {
                    matches_progressbar.visibility = View.VISIBLE
                    matches_progressbar.visibility = View.GONE
                    matches_no_messages.visibility = View.VISIBLE
                    matches_recyclerview.visibility = View.GONE
                    messages_recyclerview.visibility = View.VISIBLE
                    matches_no_messages.text = "No Matches"
                } else if (messageConnectionModels.size < 1) {
                    matches_root.visibility = View.VISIBLE
                    matches_progressbar.visibility = View.GONE
                    matches_no_messages.visibility = View.VISIBLE
                    messages_recyclerview!!.visibility = View.GONE
                    matches_recyclerview!!.visibility = View.VISIBLE
                }
            }

            override fun onConnectionEmpty(message: String) {
                matches_root.visibility = View.VISIBLE
                matches_progressbar.visibility = View.GONE
                matches_no_messages!!.visibility = View.VISIBLE
                messages_recyclerview!!.visibility = View.GONE
                matches_recyclerview!!.visibility = View.GONE
                matches_no_messages.text = "No Matches"
                matchesListView.visibility = View.GONE
            }

            override fun onError() {
                matches_root.visibility = View.GONE
                matches_progressbar.visibility = View.GONE
                matches_no_messages.visibility = View.GONE
                messages_recyclerview!!.visibility = View.GONE
                matches_recyclerview.visibility = View.GONE
                error_layout_root.visibility = View.VISIBLE
                matchesListView.visibility = View.GONE
            }
        })
        error_page_retry!!.setOnClickListener {
            matches_root!!.visibility = View.GONE
            matches_progressbar.visibility = View.VISIBLE
            matches_no_messages.visibility = View.GONE
            messages_recyclerview.visibility = View.GONE
            matches_recyclerview.visibility = View.GONE
            error_layout_root.visibility = View.GONE
            matchesListView.visibility = View.GONE
            messageConnectionModel!!.getConnection()
        }
        //goBack!!.setOnClickListener { finish() }
    }



}