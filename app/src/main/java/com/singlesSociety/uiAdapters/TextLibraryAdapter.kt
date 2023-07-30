package com.singlesSociety.uiAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.SinglesSociety.SocialText.RichTextController.FontTextView
import com.SinglesSociety.SocialText.RichTextController.SocietyTypeface
import com.singlesSociety.Arvi.widget.PlayableItemsRecyclerView
import com.singlesSociety.R
import com.singlesSociety.UiModels.CommunityPostTypeTextAttachmentModel
import com.singlesSociety.UiModels.NonMediaPostModel


class TextLibraryAdapter(private val postList: ArrayList<NonMediaPostModel>,private val mContext: Context, private var addACommentClickListener: Function0<Unit>? = null ): RecyclerView.Adapter<TextLibraryAdapter.TextPostItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextPostItemViewHolder {
        val view2: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.community_post_type_text_v2, parent, false)
        return TextPostItemViewHolder(view2)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: TextPostItemViewHolder, position: Int) {
       /* holder.commentLayout.setOnClickListener { addACommentClickListener?.invoke() }
        holder.commentCount.setOnClickListener { addACommentClickListener?.invoke() }*/
    }



    class TextPostItemViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var attachmentView: PlayableItemsRecyclerView
       // private var postTextView: FontTextView


        init {
           // postTextView = ItemView.findViewById(R.id.postTextView)
            attachmentView = ItemView.findViewById(R.id.postAttachmentRecyclerView)
            val attachmentModel = CommunityPostTypeTextAttachmentModel(1)
            val attachmentModel2 = CommunityPostTypeTextAttachmentModel(2)
            val attachmentModel3 = CommunityPostTypeTextAttachmentModel(3)
            val attachmentModels = java.util.ArrayList<CommunityPostTypeTextAttachmentModel>()
            attachmentModels.add(attachmentModel)

            attachmentView.adapter =
                CommunityPostTypeTextAttachmentAdapter(attachmentModels, ItemView.context)
        }
    }

}