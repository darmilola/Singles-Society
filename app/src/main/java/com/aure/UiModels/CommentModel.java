package com.aure.UiModels;

import com.bignerdranch.expandablerecyclerview.model.Parent;

import java.util.ArrayList;
import java.util.List;

public class CommentModel implements Parent<CommentReplyModel> {

    private ArrayList<CommentReplyModel> commentReplyModelList;
    private int replyCount;

    public CommentModel(ArrayList<CommentReplyModel> commentReplyModelList, int replyCount){
        this.commentReplyModelList = commentReplyModelList;
        this.replyCount = replyCount;
        }
      @Override
      public ArrayList<CommentReplyModel> getChildList() {
        return commentReplyModelList;
        }
    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}


