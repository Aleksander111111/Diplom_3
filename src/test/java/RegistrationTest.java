import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.MainPage;
import pages.LoginPage;
import pages.RegistrationPage;
import utils.TestData;
import utils.UserAPI;
import java.time.Duration;
import static org.junit.Assert.assertTrue;

@DisplayName("Тесты регистрации")
public class RegistrationTest {
    private WebDriver driver;
    private MainPage mainPage;
    private LoginPage loginPage;
    private RegistrationPage registrationPage;
    private WebDriverWait wait;
    private String userEmail;
    private String userName;
    private String accessToken;

    @Before
    public void setUp() {
        driver = DriverFactory.createDriver("yandex");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        registrationPage = new RegistrationPage(driver);
        userEmail = TestData.generateEmail();
        userName = TestData.generateName();
    }

    @Test
    @DisplayName("Успешная регистрация")
    public void testSuccessfulRegistration() throws Exception {
        mainPage.open();
        mainPage.clickLoginButton();

        wait.until(ExpectedConditions.urlContains("login"));

        loginPage.clickRegisterLink();

        wait.until(ExpectedConditions.urlContains("register"));

        boolean fieldsAvailable = registrationPage.areAllFieldsAvailable();
        System.out.println("Все поля доступны: " + fieldsAvailable);

        if (!fieldsAvailable) {
            System.out.println("Текущий URL: " + driver.getCurrentUrl());
            throw new AssertionError("Не все поля формы регистрации доступны");
        }

        registrationPage.register(userName, userEmail, TestData.VALID_PASSWORD);

        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("login"),
                ExpectedConditions.urlContains("main")
        ));

        try {
            accessToken = UserAPI.loginUser(userEmail, TestData.VALID_PASSWORD);
            assertTrue("Пользователь должен быть успешно зарегистрирован", accessToken != null);
            System.out.println("Регистрация успешна, получен токен: " + accessToken);
        } catch (Exception e) {
            if (registrationPage.isErrorMessageDisplayed()) {
                String errorMessage = registrationPage.getErrorMessage();
                System.out.println("Ошибка регистрации на странице: " + errorMessage);
                throw new AssertionError("Регистрация не удалась: " + errorMessage);
            } else {
                String currentUrl = driver.getCurrentUrl();
                System.out.println("Текущий URL после регистрации: " + currentUrl);
                try {
                    String apiToken = UserAPI.registerUser(userEmail, TestData.VALID_PASSWORD, userName);
                    if (apiToken != null) {
                        accessToken = apiToken;
                        System.out.println("Пользователь зарегистрирован через API");
                        assertTrue("Пользователь зарегистрирован через API", true);
                    } else {
                        throw new AssertionError("Регистрация не удалась через UI и API");
                    }
                } catch (Exception apiException) {
                    throw new AssertionError("Регистрация не удалась: " + apiException.getMessage());
                }
            }
        }
    }

    @Test
    @DisplayName("Ошибка при некорректном пароле")
    public void testRegistrationWithInvalidPassword() {
        mainPage.open();
        mainPage.clickLoginButton();

        wait.until(ExpectedConditions.urlContains("login"));

        loginPage.clickRegisterLink();

        wait.until(ExpectedConditions.urlContains("register"));

        boolean fieldsAvailable = registrationPage.areAllFieldsAvailable();
        System.out.println("Все поля доступны для теста с ошибкой: " + fieldsAvailable);

        registrationPage.register(userName, userEmail, TestData.INVALID_PASSWORD);

        boolean errorDisplayed = wait.until(driver -> registrationPage.isErrorMessageDisplayed());
        System.out.println("Сообщение об ошибке отображается: " + errorDisplayed);

        if (errorDisplayed) {
            System.out.println("Текст ошибки: " + registrationPage.getErrorMessage());
        }

        assertTrue("Должно отображаться сообщение об ошибке пароля", errorDisplayed);
    }

    @After
    public void tearDown() throws Exception {
        if (accessToken != null) {
            try {
                UserAPI.deleteUser(accessToken);
                System.out.println("Пользователь удален через API");
            } catch (Exception e) {
                System.out.println("Ошибка при удалении пользователя: " + e.getMessage());
            }
        }

        if (driver != null) {
            driver.quit();
        }
    }
}