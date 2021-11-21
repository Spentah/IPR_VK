package api;

public enum EndPoints {

    GET_PROFILE_INFO("account.getProfileInfo"),
    SAVE_PROFILE_INFO("account.saveProfileInfo"),
    SAVE_PHOTO_ON_SERVER("photos.saveOwnerPhoto"),
    GET_PHOTO_UPLOAD_SERVER("photos.getOwnerPhotoUploadServer"),
    GET_DOCUMENT_TYPES("docs.getTypes"),
    GET_DOCS_UPLOAD_SERVER("docs.getUploadServer"),
    SAVE_DOCUMENT("docs.save"),
    GET_DOC_ID("docs.get"),
    EDIT_DOCUMENT("docs.edit"),
    DELETE_DOC("docs.delete"),
    GET_NEWSFEED("newsfeed.getRecommended"),
    ADD_LIKE("likes.add"),
    BAN_ACCOUNT("account.ban"),
    ADD_TO_FAVORITE("fave.addPost"),
    CREATE_GROUP("groups.create"),
    CREATE_TOPIC("board.addTopic"),
    LEAVE_GROUP("groups.leave"),
    FIX_TOPIC("board.fixTopic"),
    CREATE_COMMENT("board.createComment"),
    EDIT_COMMENT("board.editComment"),
    DELETE_COMMENT("board.deleteComment"),
    CREATE_ALBUM("photos.createAlbum"),
    PHOTO_GET_UPLOAD_SERVER("photos.getUploadServer"),
    SAVE_PHOTO("photos.save"),
    MAKE_COVER_PHOTO("photos.makeCover"),
    CREATE_COMMENT_ON_PHOTO("photos.createComment"),
    PUT_TAG("photos.putTag"),
    MOVE_PHOTO("photos.move"),
    DELETE_ALBUM("photos.deleteAlbum"),
    DELETE_PHOTO("photos.delete");

    public final String endPoint;

    EndPoints(String endPoint) {
        this.endPoint = endPoint;
    }

}
