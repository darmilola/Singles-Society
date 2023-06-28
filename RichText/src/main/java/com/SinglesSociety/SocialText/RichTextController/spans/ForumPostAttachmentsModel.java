package com.SinglesSociety.SocialText.RichTextController.spans;

public class ForumPostAttachmentsModel {

    private int attachmentTypeCode;
    private String attachmentImageUrl;
    private String attachmentsVideoUrl;
    private String attachmentVideoThumbnailUrl;

    public ForumPostAttachmentsModel(int attachmentTypeCode){
        this.attachmentTypeCode = attachmentTypeCode;
    }
    public ForumPostAttachmentsModel(int attachmentTypeCode, String attachmentImageUrl){
        this.attachmentTypeCode = attachmentTypeCode;
        this.attachmentImageUrl = attachmentImageUrl;
    }
    public ForumPostAttachmentsModel(int attachmentTypeCode, String attachmentsVideoUrl, String attachmentVideoThumbnailUrl){

        this.attachmentTypeCode = attachmentTypeCode;
        this.attachmentsVideoUrl = attachmentsVideoUrl;
        this.attachmentVideoThumbnailUrl = attachmentVideoThumbnailUrl;
    }

    public String getAttachmentsVideoUrl() {
        return attachmentsVideoUrl;
    }

    public String getAttachmentVideoThumbnailUrl() {
        return attachmentVideoThumbnailUrl;
    }

    public void setAttachmentsVideoUrl(String attachmentsVideoUrl) {
        this.attachmentsVideoUrl = attachmentsVideoUrl;
    }

    public void setAttachmentVideoThumbnailUrl(String attachmentVideoThumbnailUrl) {
        this.attachmentVideoThumbnailUrl = attachmentVideoThumbnailUrl;
    }

    public int getAttachmentTypeCode() {
        return attachmentTypeCode;
    }

    public String getAttachmentImageUrl() {
        return attachmentImageUrl;
    }

    public void setAttachmentImageUrl(String attachmentImageUrl) {
        this.attachmentImageUrl = attachmentImageUrl;
    }

    public void setAttachmentTypeCode(int attachmentTypeCode) {
        this.attachmentTypeCode = attachmentTypeCode;
    }

}
