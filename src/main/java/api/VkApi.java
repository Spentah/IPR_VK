package api;

public class VkApi {

    private VkProfile vkProfile = new VkProfile();
    private VkDocument vkDocument = new VkDocument();

    public VkApi getProfileInfo() {
        vkProfile.getProfileInfo();
        return this;
    }

    public VkApi saveProfileInfo() {
        vkProfile.setProfileInfo(vkProfile.findEmptyProfileInfo(), "api test");
        return this;
    }

    public VkApi saveUploadPhoto() {
        vkProfile.saveUploadedPhoto();
        return this;
    }

    public VkApi checkAvaibleType() {
        vkDocument.getDocumentType(vkProfile.getVkResponse().getResponse().getId());
        return this;
    }

    public VkApi uploadDoc() {
        vkDocument.saveDocument("Омар Хайям");
        return this;
    }

    public VkApi renameAndDeleteDoc(String documentName) {
        vkDocument.getDocIdByName(documentName);
        return this;
    }
}
