package com.SinglesSociety.SocialText.RichTextController.spans;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ForumPostModel {
    private ArrayList<User> recentReplyingUsersList;
    private ArrayList<ForumPostAttachmentsModel> postAttachmentList;
    private String forumPostText;
    private String forumPostSenderName;
    private String forumPostSenderId;
    private String forumPostSenderPhotoUrl;
    private int type;
    private String postId;
    private String postDate;
    private String postGroupDate;
    private String refText;

    public ForumPostModel(ArrayList<User> recentReplyingUsersList, ArrayList<ForumPostAttachmentsModel> postAttachmentList, int type){

        this.postAttachmentList = postAttachmentList;
        this.recentReplyingUsersList = recentReplyingUsersList;
        this.type = type;
    }

    public ArrayList<ForumPostAttachmentsModel> getPostAttachmentList() {
        return postAttachmentList;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public void setRefText(String refText) {
        this.refText = refText;
    }

    public String getPostId() {
        return postId;
    }

    public String getReferencedPostObjectJson(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",getPostId());
            jsonObject.put("refText",getRefText());
        } catch (JSONException e) {
            e.printStackTrace();
        }
      return jsonObject.toString();
    }
    public String getRefText() {
        return refText;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getPostDate() {
        return postDate;
    }

    public String getPostGroupDate() {
        return postGroupDate;
    }

    public void setPostGroupDate(String postGroupDate) {
        this.postGroupDate = postGroupDate;
    }

    public ArrayList<User> getRecentReplyingUsersList() {
        return recentReplyingUsersList;
    }

    public void setPostAttachmentList(ArrayList<ForumPostAttachmentsModel> postAttachmentList) {
        this.postAttachmentList = postAttachmentList;
    }

    public void setRecentReplyingUsersList(ArrayList<User> recentReplyingUsersList) {
        this.recentReplyingUsersList = recentReplyingUsersList;
    }
}
