package api;

import api.utils.VkUtils;
import io.qameta.allure.Step;

public class VkApi {

    private VkProfile profile = new VkProfile();
    private VkDocument document = new VkDocument();
    private VkNewsFeed newsFeed = new VkNewsFeed();

    @Step("Получаем информацию о профиле")
    public VkApi getProfileInfo() {
        profile.getProfileInfo();
        return this;
    }

    @Step("Заполняем незаполненные поля в профиле")
    public VkApi saveProfileInfo() {
        profile.setProfileInfo(profile.findEmptyProfileInfo(), "api test");
        return this;
    }

    @Step("Загружаем фото профиля")
    public VkApi saveUploadPhoto() {
        profile.saveUploadedPhoto();
        return this;
    }

    @Step("Удостоверяемся, что можно загружать документы")
    public VkApi checkAvaibleType() {
        document.getDocumentType(VkUtils.getCurrentOwnerId());
        return this;
    }

    @Step("Загружаем текстовый документ с именем '{documentName}'")
    public VkApi uploadDoc(String documentName) {
        document.saveDocument(documentName);
        return this;
    }

    @Step("Переименовываем загруженный документ именем '{newName}'")
    public VkApi renameDoc(String newName) {
        document.renameDocument(newName);
        return this;
    }

    @Step("Удаляем документ")
    public VkApi deleteDocument() {
        document.deleteDoc();
        return this;
    }

    @Step("Переходим в рекомендации")
    public VkApi getNewsFeed() {
        newsFeed.getRecommendedFeed();
        return this;
    }

    public VkApi likePost() {
        newsFeed.like();
        return this;
    }



}
