package api;

import api.utils.VkUtils;

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
        vkDocument.getDocumentType(VkUtils.getCurrentOwnerId());
        return this;
    }

    public VkApi uploadDoc() {
        vkDocument.saveDocument("Омар Хайям");
        return this;
    }

    public VkApi renameDoc(String newName) {
        vkDocument.renameDocument(newName);
        return this;
    }

    public VkApi deleteDocument() {
        vkDocument.deleteDoc();
        return this;
    }
}
