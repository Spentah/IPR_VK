package api;

import api.vk.*;
import io.qameta.allure.Step;

import java.util.ArrayList;
import java.util.List;

public class VkApi {

    private VkProfile profile = new VkProfile();
    private VkDocument document = new VkDocument();
    private VkNewsFeed newsFeed = new VkNewsFeed();
    private VkGroup group = new VkGroup();
    private VkPhoto photo = new VkPhoto();
    private List<Integer> albumIds = new ArrayList<>();

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
        document.getDocumentType();
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

    @Step("Ставим лайк на пост под номер '{number}' в рекомендациях")
    public VkApi likePost(int number) {
        newsFeed.like(number);
        return this;
    }

    @Step("Баним аккаунт под номером '{number}'")
    public VkApi banAccount(int number) {
        newsFeed.ban(number);
        return this;
    }

    @Step("Добавляем запись под номер '{number}' в закладки")
    public VkApi addToFav(int number) {
        newsFeed.addToFavorite(number);
        return this;
    }

    @Step("Создаем группу с названием '{title}'")
    public VkApi createGroup(String title) {
        group.createGroup(title);
        return this;
    }

    @Step("Создаем обсуждение с названием '{title}' и первым сообщением '{text}'")
    public VkApi createTopic(String title, String text) {
        group.createTopic(title, text);
        return this;
    }

    public VkApi leaveGroup() {
        group.leaveGroup();
        return this;
    }

    @Step("Закрепляем топик")
    public VkApi fixTopic() {
        group.fixTopic();
        return this;
    }

    @Step("Создаем коммент с текстом '{text}'")
    public VkApi createComment(String text) {
        group.createComment(text);
        return this;
    }

    @Step("Редактируем комментарий номер '{commentNum}', новый текст коммента - '{newText}'")
    public VkApi editComment(int commentNum, String newText) {
        group.editComment(commentNum, newText);
        return this;
    }

    @Step("Удаляем коммент под номером '{commentNum}'")
    public VkApi deleteComment(int commentNum) {
        group.deleteComment(commentNum);
        return this;
    }

    @Step("Создаем альбом с названием '{title}' и приватностью '{privacy}'")
    public VkApi createAlbum(String title, String privacy) {
        albumIds.add(photo.createAlbum(title, privacy));
        return this;
    }

    @Step("Загружаем в альбом фото '{filePath}'")
    public VkApi uploadPhoto(String filePath) {
        photo.saveUploadedPhoto(filePath, albumIds.get(0));
        return this;
    }

    @Step("Делаем загруженную фотографию обложкой альбома")
    public VkApi makeCover() {
        photo.makeCover(albumIds.get(0));
        return this;
    }

    @Step("Добавляем к фото комментарий '{text}'")
    public VkApi createCommentOnPhoto(String text) {
        photo.createComment(text);
        return this;
    }

    @Step("Отмечаем на фото юзера с айди '{userId}'")
    public VkApi putTag(int userId) {
        photo.putTag(userId);
        return this;
    }

    @Step("Переносим фотографию в новый альбом")
    public VkApi movePhoto() {
        photo.movePhoto(albumIds.get(1));
        return this;
    }

    @Step("Удаляем альбом под номером '{number}'")
    public VkApi deleteAlbum(int number) {
        photo.deleteAlbum(albumIds.get(number - 1));
        return this;
    }

    public VkApi deletePhoto() {
        photo.deletePhoto();
        return this;
    }


}
