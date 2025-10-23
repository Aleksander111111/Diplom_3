package utils;

import com.google.gson.JsonObject;
import io.qameta.allure.Step;
import java.io.IOException;

public class UserService {
    private final ApiClient apiClient;

    public UserService() {
        this.apiClient = new ApiClient();
    }

    @Step("Регистрация пользователя через API: {email}")
    public String registerUser(String email, String password, String name) throws IOException, InterruptedException {
        UserCredentials user = new UserCredentials(email, password, name);
        JsonObject response = apiClient.sendPostRequest("/auth/register", user);

        if (response.get("success").getAsBoolean()) {
            return response.get("accessToken").getAsString();
        }
        return null;
    }

    @Step("Логин пользователя через API: {email}")
    public String loginUser(String email, String password) throws IOException, InterruptedException {
        UserCredentials credentials = new UserCredentials(email, password);
        JsonObject response = apiClient.sendPostRequest("/auth/login", credentials);

        if (response.get("success").getAsBoolean()) {
            return response.get("accessToken").getAsString();
        }
        return null;
    }

    @Step("Получить информацию о пользователе через API")
    public JsonObject getUserInfo(String accessToken) throws IOException, InterruptedException {
        return apiClient.sendGetRequest("/auth/user", accessToken);
    }

    @Step("Удалить пользователя через API")
    public boolean deleteUser(String accessToken) throws IOException, InterruptedException {
        JsonObject response = apiClient.sendDeleteRequest("/auth/user", accessToken);
        return response.get("success").getAsBoolean();
    }
}