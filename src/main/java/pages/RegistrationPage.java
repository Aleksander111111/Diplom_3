package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import io.qameta.allure.Step;

public class RegistrationPage extends BasePage {

    private By nameInput = By.xpath("//label[text()='Имя']/following-sibling::input");
    private By emailInput = By.xpath("//label[text()='Email']/following-sibling::input");
    private By passwordInput = By.xpath("//label[text()='Пароль']/following-sibling::input");
    private By registerButton = By.xpath("//button[text()='Зарегистрироваться']");
    private By loginLink = By.cssSelector("a[href*='login']");
    private By errorMessage = By.cssSelector(".input__error");

    public RegistrationPage(WebDriver driver) {
        super(driver);
    }

    @Step("Ввести имя: {name}")
    public void inputName(String name) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(nameInput));
        element.click();
        element.clear();
        element.sendKeys(name);
    }

    @Step("Ввести email: {email}")
    public void inputEmail(String email) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(emailInput));
        element.click();
        element.clear();
        element.sendKeys(email);
    }

    @Step("Ввести пароль")
    public void inputPassword(String password) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(passwordInput));
        element.click();
        element.clear();
        element.sendKeys(password);
    }

    @Step("Нажать кнопку 'Зарегистрироваться'")
    public void clickRegisterButton() {
        clickElement(registerButton);
    }

    @Step("Нажать ссылку 'Войти'")
    public void clickLoginLink() {
        clickElement(loginLink);
    }

    @Step("Получить текст ошибки")
    public String getErrorMessage() {
        if (isElementVisible(errorMessage)) {
            return getText(errorMessage);
        }
        return "";
    }

    @Step("Проверить отображение сообщения об ошибке")
    public boolean isErrorMessageDisplayed() {
        return isElementVisible(errorMessage);
    }

    @Step("Зарегистрировать пользователя с именем: {name}, email: {email}")
    public void register(String name, String email, String password) {
        inputName(name);
        inputEmail(email);
        inputPassword(password);
        clickRegisterButton();
    }

    @Step("Проверить доступность всех полей формы")
    public boolean areAllFieldsAvailable() {
        try {
            return isElementVisible(nameInput) &&
                    isElementVisible(emailInput) &&
                    isElementVisible(passwordInput) &&
                    isElementVisible(registerButton);
        } catch (Exception e) {
            return false;
        }
    }
}