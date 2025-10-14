package utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.qameta.allure.Step;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class UserAPI {
    private static final String BASE_URL = "https://stellarburgers.education-services.ru/api";
    private static final HttpClient httpClient = HttpClient.newHttpClient();

    @Step("Регистрация пользователя через API: {email}")
    public static String registerUser(String email, String password, String name) throws IOException, InterruptedException {
        String url = BASE_URL + "/auth/register";

        JsonObject user = new JsonObject();
        user.addProperty("email", email);
        user.addProperty("password", password);
        user.addProperty("name", name);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(user.toString()))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();

        if (jsonResponse.get("success").getAsBoolean()) {
            return jsonResponse.get("accessToken").getAsString();
        }
        return null;
    }

    @Step("Получить информацию о пользователе через API")
    public static JsonObject getUserInfo(String accessToken) throws IOException, InterruptedException {
        String url = BASE_URL + "/auth/user";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", accessToken)
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return JsonParser.parseString(response.body()).getAsJsonObject();
    }

    @Step("Удалить пользователя через API")
    public static boolean deleteUser(String accessToken) throws IOException, InterruptedException {
        String url = BASE_URL + "/auth/user";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", accessToken)
                .DELETE()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();
        return jsonResponse.get("success").getAsBoolean();
    }

    @Step("Логин пользователя через API: {email}")
    public static String loginUser(String email, String password) throws IOException, InterruptedException {
        String url = BASE_URL + "/auth/login";

        JsonObject credentials = new JsonObject();
        credentials.addProperty("email", email);
        credentials.addProperty("password", password);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(credentials.toString()))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();

        if (jsonResponse.get("success").getAsBoolean()) {
            return jsonResponse.get("accessToken").getAsString();
        }
        return null;
    }
}