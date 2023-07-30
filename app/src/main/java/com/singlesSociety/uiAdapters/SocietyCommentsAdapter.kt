package com.singlesSociety.uiAdapters

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.SinglesSociety.SocialText.RichTextController.RTextView
import com.SinglesSociety.SocialText.RichTextController.RTextView.HashTagClickedListener
import com.SinglesSociety.SocialText.RichTextController.RTextView.MentionClickedListener
import com.SinglesSociety.SocialText.RichTextController.api.format.RTPlainText
import com.bignerdranch.expandablerecyclerview.ChildViewHolder
import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter
import com.bignerdranch.expandablerecyclerview.ParentViewHolder
import com.singlesSociety.R
import com.singlesSociety.UiModels.CommentModel
import com.singlesSociety.UiModels.CommentReplyModel
import io.getstream.avatarview.AvatarView
import io.getstream.avatarview.coil.loadImage

@Suppress("UPPER_BOUND_VIOLATED")
class SocietyCommentsAdapter(
    private val context: Context,
    private val commentModelList: ArrayList<CommentModel>,
    private val isTypePost: Boolean
) :
    ExpandableRecyclerAdapter<CommentModel, CommentReplyModel, SocietyCommentsAdapter.CommentViewHolder?, SocietyCommentsAdapter.CommentReplyViewHolder>(
        commentModelList
    ){
    private val mInflater: LayoutInflater
    private var commentProfileVisitListener: Function0<Unit>? = null

    init {
        mInflater = LayoutInflater.from(context)
    }

    fun setCommentProfileVisitListener(commentProfileVisitListener: Function0<Unit>?) {
        this.commentProfileVisitListener = commentProfileVisitListener
    }

    override fun onCreateParentViewHolder(
        parentViewGroup: ViewGroup,
        viewType: Int
    ): CommentViewHolder {
        val commentView = mInflater.inflate(R.layout.post_comment_item, parentViewGroup, false)
        return CommentViewHolder(commentView)
    }

    override fun onCreateChildViewHolder(
        childViewGroup: ViewGroup,
        viewType: Int
    ): CommentReplyViewHolder {
        val replyView = mInflater.inflate(R.layout.post_comment_reply, childViewGroup, false)
        return CommentReplyViewHolder(replyView)
    }

    override fun onBindParentViewHolder(
        parentViewHolder: CommentViewHolder,
        parentPosition: Int,
        parent: CommentModel
    ) {
    }

    override fun onBindChildViewHolder(
        childViewHolder: CommentReplyViewHolder,
        parentPosition: Int,
        childPosition: Int,
        child: CommentReplyModel
    ) {
    }

    inner class CommentViewHolder(itemView: View) :
        ParentViewHolder<Any?, Any?>(itemView), HashTagClickedListener,
        MentionClickedListener {
        private val replyText: TextView
        private val replyArrow: ImageView
        private val profileImage: AvatarView
        private val username: TextView
        private val replyLayout: LinearLayout

        init {
            replyText = itemView.findViewById(R.id.commentReplyCount)
            replyArrow = itemView.findViewById(R.id.commentReplyArrow)
            profileImage = itemView.findViewById(R.id.userProfileImageViewWithIndicator)
            username = itemView.findViewById(R.id.username)
            replyLayout = itemView.findViewById(R.id.commentReplyLayout)

            profileImage.loadImage(context.resources.getDrawable(R.drawable.woman_official))
            if (isTypePost) {
                replyLayout.visibility = View.VISIBLE
            } else {
                replyLayout.visibility = View.GONE
            }

            replyArrow.setOnClickListener {
                if (isExpanded) {
                    collapseView()
                } else {
                    expandView()
                }
            }
            profileImage.setOnClickListener { commentProfileVisitListener!!.invoke() }
            username.setOnClickListener { commentProfileVisitListener!!.invoke() }
            replyText.setOnClickListener {
                if (isExpanded) {
                    collapseView()
                } else {
                    expandView()
                }
            }
        }

        override fun shouldItemViewClickToggleExpansion(): Boolean {
            return false
        }

        override fun onMentionClicked(mentionJson: String) {
            Toast.makeText(context, mentionJson, Toast.LENGTH_SHORT).show()
        }

        override fun onHashTagClicked(hashtagText: String) {
            Toast.makeText(context, hashtagText, Toast.LENGTH_SHORT).show()
        }
    }

    inner class CommentReplyViewHolder(itemView: View) :
        ChildViewHolder<Any?>(itemView) {
        private val profileImage: AvatarView
        private val username: TextView
        private val replyLayout: LinearLayout

        init {
            profileImage = itemView.findViewById(R.id.userProfileImageViewWithIndicator)
            username = itemView.findViewById(R.id.username)
            replyLayout = itemView.findViewById(R.id.commentReplyLayout)
            replyLayout.visibility = View.GONE

            profileImage.loadImage(context.resources.getDrawable(R.drawable.woman_official))
            profileImage.setOnClickListener { commentProfileVisitListener!!.invoke() }
            username.setOnClickListener { commentProfileVisitListener!!.invoke() }
        }
    }


}