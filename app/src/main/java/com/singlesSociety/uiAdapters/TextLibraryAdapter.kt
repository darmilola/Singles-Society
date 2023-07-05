package com.singlesSociety.uiAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.singlesSociety.Arvi.widget.PlayableItemsRecyclerView
import com.singlesSociety.R
import com.singlesSociety.UiModels.CommunityPostTypeTextAttachmentModel
import com.singlesSociety.UiModels.NonMediaPostModel


class TextLibraryAdapter(private val postList: ArrayList<NonMediaPostModel>,private val mContext: Context, private var addACommentClickListener: Function0<Unit>? = null ): RecyclerView.Adapter<TextLibraryAdapter.TextPostItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextPostItemViewHolder {
        val view2: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.community_post_type_text, parent, false)
        return TextPostItemViewHolder(view2)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: TextPostItemViewHolder, position: Int) {
        holder.commentLayout.setOnClickListener { addACommentClickListener?.invoke() }
        holder.commentCount.setOnClickListener { addACommentClickListener?.invoke() }
    }



    class TextPostItemViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var attachmentView: PlayableItemsRecyclerView
        var commentLayout: LinearLayout
        var commentCount: TextView

        init {
            attachmentView = ItemView.findViewById(R.id.postAttachmentRecyclerView)
            val attachmentModel = CommunityPostTypeTextAttachmentModel(1)
            val attachmentModel2 = CommunityPostTypeTextAttachmentModel(2)
            val attachmentModel3 = CommunityPostTypeTextAttachmentModel(3)
            val attachmentModels = java.util.ArrayList<CommunityPostTypeTextAttachmentModel>()
            attachmentModels.add(attachmentModel)
            attachmentModels.add(attachmentModel2)
            attachmentModels.add(attachmentModel3)
            commentCount = ItemView.findViewById(R.id.commentCount)
            commentLayout = ItemView.findViewById(R.id.commentLayout)
            attachmentView.adapter =
                CommunityPostTypeTextAttachmentAdapter(attachmentModels, ItemView.context)
        }
    }

}