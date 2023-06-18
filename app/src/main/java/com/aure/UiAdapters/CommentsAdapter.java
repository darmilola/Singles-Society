package com.aure.UiAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.aure.R;
import com.aure.UiModels.CommentModel;
import com.aure.UiModels.CommentReplyModel;
import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.ParentViewHolder;

import java.util.ArrayList;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;

public class CommentsAdapter extends ExpandableRecyclerAdapter<CommentModel, CommentReplyModel, CommentsAdapter.CommentViewHolder, CommentsAdapter.CommentReplyViewHolder> {

    private LayoutInflater mInflater;
    private Context context;
    private ArrayList<CommentModel> commentModelList;
    private Function0<Unit> commentProfileVisitListener;

      public CommentsAdapter(Context context, ArrayList<CommentModel> commentModelList){
                super(commentModelList);
                this.context = context;
                this.commentModelList = commentModelList;
                mInflater = LayoutInflater.from(context);
      }

    public void setCommentProfileVisitListener(Function0<Unit> commentProfileVisitListener) {
        this.commentProfileVisitListener = commentProfileVisitListener;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        View commentView = mInflater.inflate(R.layout.post_comment_item, parentViewGroup, false);
        return new CommentViewHolder(commentView);
    }

    @NonNull
    @Override
    public CommentReplyViewHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        View replyView = mInflater.inflate(R.layout.post_comment_reply, childViewGroup, false);
        return new CommentReplyViewHolder(replyView);
    }

    @Override
    public void onBindParentViewHolder(@NonNull CommentViewHolder parentViewHolder, int parentPosition, @NonNull CommentModel parent) {

    }

    @Override
    public void onBindChildViewHolder(@NonNull CommentReplyViewHolder childViewHolder, int parentPosition, int childPosition, @NonNull CommentReplyModel child) {

    }

    public class CommentViewHolder extends ParentViewHolder {

        private TextView replyText;
        private ImageView replyArrow;
        private ImageView profileImage;
        private TextView username;
        public CommentViewHolder(View itemView) {
            super(itemView);
            replyText = itemView.findViewById(R.id.commentReplyCount);
            replyArrow = itemView.findViewById(R.id.commentReplyArrow);
            profileImage = itemView.findViewById(R.id.userProfileImage);
            username = itemView.findViewById(R.id.username);

            replyArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isExpanded()) {
                        collapseView();
                    } else {
                        expandView();
                    }
                }
            });

            profileImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    commentProfileVisitListener.invoke();
                }
            });

            username.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    commentProfileVisitListener.invoke();
                }
            });

         replyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpanded()) {
                    collapseView();
                } else {
                    expandView();
                }
            }
        });
    }
        @Override
        public boolean shouldItemViewClickToggleExpansion() {
            return false;
        }

    }


    public class CommentReplyViewHolder extends ChildViewHolder {

        private ImageView profileImage;
        private TextView username;
        public CommentReplyViewHolder(View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.userProfileImage);
            username = itemView.findViewById(R.id.username);



            profileImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    commentProfileVisitListener.invoke();
                }
            });

            username.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    commentProfileVisitListener.invoke();
                }
            });
        }

    }
}