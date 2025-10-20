import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pages.MainPage;
import pages.LoginPage;
import pages.RegistrationPage;
import utils.TestData;
import utils.UserService;
import static org.junit.Assert.assertTrue;

@DisplayName("Тесты регистрации")
public class RegistrationTest {
    private WebDriver driver;
    private MainPage mainPage;
    private LoginPage loginPage;
    private RegistrationPage registrationPage;
    private String userEmail;
    private String userName;
    private String accessToken;
    private UserService userService;

    @Before
    public void setUp() {
        driver = DriverFactory.createDriver();
        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        registrationPage = new RegistrationPage(driver);
        userService = new UserService();
        userEmail = TestData.generateEmail();
        userName = TestData.generateName();
    }

    @Test
    @DisplayName("Успешная регистрация")
    public void testSuccessfulRegistration() throws Exception {
        mainPage.open();
        mainPage.clickLoginButton();
        loginPage.clickRegisterLink();

        boolean fieldsAvailable = registrationPage.areAllFieldsAvailable();
        assertTrue("Все поля формы регистрации должны быть доступны", fieldsAvailable);

        registrationPage.register(userName, userEmail, TestData.VALID_PASSWORD);

        registrationPage.waitForUrlContains("login");
        accessToken = userService.loginUser(userEmail, TestData.VALID_PASSWORD);
        assertTrue("Пользователь должен быть успешно зарегистрирован", accessToken != null);
    }

    @Test
    @DisplayName("Ошибка при некорректном пароле")
    public void testRegistrationWithInvalidPassword() {
        mainPage.open();
        mainPage.clickLoginButton();
        loginPage.clickRegisterLink();

        boolean fieldsAvailable = registrationPage.areAllFieldsAvailable();
        assertTrue("Все поля формы регистрации должны быть доступны", fieldsAvailable);

        registrationPage.register(userName, userEmail, TestData.INVALID_PASSWORD);

        boolean errorDisplayed = registrationPage.isErrorMessageDisplayed();
        assertTrue("Должно отображаться сообщение об ошибке пароля", errorDisplayed);
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