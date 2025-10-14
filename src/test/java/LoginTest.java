import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import pages.MainPage;
import pages.RegistrationPage;
import utils.TestData;
import utils.UserAPI;
import com.google.gson.JsonObject;
import static org.junit.Assert.assertTrue;

@DisplayName("Тесты входа в систему")
public class LoginTest {
    private WebDriver driver;
    private MainPage mainPage;
    private LoginPage loginPage;
    private RegistrationPage registrationPage;
    private String userEmail;
    private String userName;
    private String accessToken;

    @Before
    public void setUp() throws Exception {
        driver = DriverFactory.createDriver("yandex");
        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        registrationPage = new RegistrationPage(driver);

        userEmail = TestData.generateEmail();
        userName = TestData.generateName();
        accessToken = UserAPI.registerUser(userEmail, TestData.VALID_PASSWORD, userName);
    }

    @Test
    @DisplayName("Вход по кнопке 'Войти в аккаунт' на главной")
    public void testLoginFromMainPage() throws Exception {
        mainPage.open();
        mainPage.clickLoginButton();

        loginPage.login(userEmail, TestData.VALID_PASSWORD);

        checkLoginThroughAPI();
    }

    @Test
    @DisplayName("Вход через кнопку 'Личный кабинет'")
    public void testLoginFromPersonalAccount() throws Exception {
        mainPage.open();
        mainPage.clickPersonalAccountButton();

        loginPage.login(userEmail, TestData.VALID_PASSWORD);

        checkLoginThroughAPI();
    }

    @Test
    @DisplayName("Вход через кнопку в форме регистрации")
    public void testLoginFromRegistrationForm() throws Exception {
        mainPage.open();
        mainPage.clickLoginButton();
        loginPage.clickRegisterLink();
        registrationPage.clickLoginLink();

        loginPage.login(userEmail, TestData.VALID_PASSWORD);

        checkLoginThroughAPI();
    }

    @Test
    @DisplayName("Вход через кнопку в форме восстановления пароля")
    public void testLoginFromForgotPasswordForm() throws Exception {
        mainPage.open();
        mainPage.clickLoginButton();
        loginPage.clickForgotPasswordLink();

        loginPage.clickLoginLink();

        loginPage.login(userEmail, TestData.VALID_PASSWORD);

        checkLoginThroughAPI();
    }

    private void checkLoginThroughAPI() throws Exception {
        String loginToken = UserAPI.loginUser(userEmail, TestData.VALID_PASSWORD);
        assertTrue("Должен быть получен токен при логине через API", loginToken != null);

        JsonObject userInfo = UserAPI.getUserInfo(loginToken);
        assertTrue("Запрос данных пользователя должен быть успешным",
                userInfo.get("success").getAsBoolean());

        JsonObject user = userInfo.getAsJsonObject("user");
        String actualEmail = user.get("email").getAsString();
        String actualName = user.get("name").getAsString();

        assertTrue("Email пользователя должен совпадать", userEmail.equals(actualEmail));
        assertTrue("Имя пользователя должно совпадать", userName.equals(actualName));
    }

    @After
    public void tearDown() throws Exception {
        if (accessToken != null) {
            UserAPI.deleteUser(accessToken);
        }

        if (driver != null) {
            driver.quit();
        }
    }
}