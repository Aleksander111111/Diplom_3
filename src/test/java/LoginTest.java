import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import pages.MainPage;
import pages.RegistrationPage;
import utils.TestData;
import utils.UserService;
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
    private UserService userService;

    @Before
    public void setUp() throws Exception {
        driver = DriverFactory.createDriver();
        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        registrationPage = new RegistrationPage(driver);
        userService = new UserService();

        userEmail = TestData.generateEmail();
        userName = TestData.generateName();
        accessToken = userService.registerUser(userEmail, TestData.VALID_PASSWORD, userName);
    }

    @Test
    @DisplayName("Вход по кнопке 'Войти в аккаунт' на главной")
    public void testLoginFromMainPage() {
        mainPage.open();
        mainPage.clickLoginButton();

        loginPage.login(userEmail, TestData.VALID_PASSWORD);

        assertTrue("Пользователь должен быть авторизован", mainPage.isUserLoggedIn());
    }

    @Test
    @DisplayName("Вход через кнопку 'Личный кабинет'")
    public void testLoginFromPersonalAccount() {
        mainPage.open();
        mainPage.clickPersonalAccountButton();

        loginPage.login(userEmail, TestData.VALID_PASSWORD);

        assertTrue("Пользователь должен быть авторизован", mainPage.isUserLoggedIn());
    }

    @Test
    @DisplayName("Вход через кнопку в форме регистрации")
    public void testLoginFromRegistrationForm() {
        mainPage.open();
        mainPage.clickLoginButton();
        loginPage.clickRegisterLink();
        registrationPage.clickLoginLink();

        loginPage.login(userEmail, TestData.VALID_PASSWORD);

        assertTrue("Пользователь должен быть авторизован", mainPage.isUserLoggedIn());
    }

    @Test
    @DisplayName("Вход через кнопку в форме восстановления пароля")
    public void testLoginFromForgotPasswordForm() {
        mainPage.open();
        mainPage.clickLoginButton();
        loginPage.clickForgotPasswordLink();
        loginPage.clickLoginLink();

        loginPage.login(userEmail, TestData.VALID_PASSWORD);

        assertTrue("Пользователь должен быть авторизован", mainPage.isUserLoggedIn());
    }

    @After
    public void tearDown() throws Exception {
        if (accessToken != null) {
            userService.deleteUser(accessToken);
        }

        if (driver != null) {
            driver.quit();
        }
    }
}