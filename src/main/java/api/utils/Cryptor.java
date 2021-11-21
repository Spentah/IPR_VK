package api.utils;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class Cryptor {

    private static final String SALT = "password";

    private Cryptor() {

    }

    public static String encrypt(String password) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(SALT);
        return encryptor.encrypt(password);
    }

    public static String decryptValue(String encryptedPassword) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(SALT);
        return encryptor.decrypt(encryptedPassword);
    }

//    public static void main(String[] args) {
////        DatabaseExecutor.getConnect();
//        String token = "77707dcea93caae9e71df02d44134d00b0b6379cda150fff123b96acc12b6f5125fc3e4aab37c78a1bf58";
//        String encrypted = encrypt(token);
//        System.out.println(encrypted);
//        System.out.println(decryptValue(encrypted));
////        System.out.println(DatabaseExecutor.executeValue("login"));
//
////        DatabaseExecutor.closeConnect();
//    }

}
