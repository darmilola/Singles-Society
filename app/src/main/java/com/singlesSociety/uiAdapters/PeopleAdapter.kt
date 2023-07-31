package com.singlesSociety.uiAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.singlesSociety.R
import com.singlesSociety.UiModels.SocietyUserModel
import io.getstream.avatarview.AvatarView
import io.getstream.avatarview.coil.loadImage

class PeopleAdapter(private val usersList: ArrayList<SocietyUserModel>, private val mContext: Context, private var addACommentClickListener: Function0<Unit>? = null ): RecyclerView.Adapter<PeopleAdapter.PeopleItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleItemViewHolder {
        val view2: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_search_item, parent, false)
        return PeopleItemViewHolder(view2)
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    override fun onBindViewHolder(holder: PeopleItemViewHolder, position: Int) {}

    class PeopleItemViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        private lateinit var accountPicture: AvatarView
        init {
            accountPicture = ItemView.findViewById(R.id.userProfileImageViewWithIndicator)
            accountPicture.loadImage(itemView.context.resources.getDrawable(R.drawable.woman_official))
        }

    }

}