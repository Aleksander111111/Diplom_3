package utils;

import io.qameta.allure.Step;

public class TestData {
    @Step("Сгенерировать email")
    public static String generateEmail() {
        return "testuser_" + System.currentTimeMillis() + "@example.com";
    }

    @Step("Сгенерировать имя")
    public static String generateName() {
        return "TestUser_" + System.currentTimeMillis();
    }

    public static final String VALID_PASSWORD = "123456";
    public static final String INVALID_PASSWORD = "12345";
}