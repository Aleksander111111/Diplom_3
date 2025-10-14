package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import io.qameta.allure.Step;

public class LoginPage extends BasePage {

    private By emailInput = By.xpath("//label[text()='Email']/following-sibling::input");
    private By passwordInput = By.cssSelector("input[name='Пароль']");
    private By loginButton = By.xpath("//button[text()='Войти']");
    private By registerLink = By.cssSelector("a[href*='register']");
    private By forgotPasswordLink = By.cssSelector("a[href*='forgot-password']");
    private By loginLink = By.cssSelector("a[href*='login']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Ввести email: {email}")
    public void inputEmail(String email) {
        inputText(emailInput, email);
    }

    @Step("Ввести пароль")
    public void inputPassword(String password) {
        inputText(passwordInput, password);
    }

    @Step("Нажать кнопку 'Войти'")
    public void clickLoginButton() {
        clickElement(loginButton);
    }

    @Step("Нажать ссылку 'Зарегистрироваться'")
    public void clickRegisterLink() {
        clickElement(registerLink);
    }

    @Step("Нажать ссылку 'Восстановить пароль'")
    public void clickForgotPasswordLink() {
        clickElement(forgotPasswordLink);
    }

    @Step("Нажать ссылку 'Войти'")
    public void clickLoginLink() {
        clickElement(loginLink);
    }

    @Step("Выполнить вход с email: {email} и паролем")
    public void login(String email, String password) {
        inputEmail(email);
        inputPassword(password);
        clickLoginButton();
    }
}