package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import io.qameta.allure.Step;

public class MainPage extends BasePage {

    private By loginButton = By.xpath("//button[text()='Войти в аккаунт']");
    private By personalAccountButton = By.xpath("//p[text()='Личный Кабинет']");
    private By bunsSection = By.xpath("//span[text()='Булки']");
    private By saucesSection = By.xpath("//span[text()='Соусы']");
    private By fillingsSection = By.xpath("//span[text()='Начинки']");
    private By activeBunsSection = By.xpath("//span[text()='Булки']/parent::div[contains(@class, 'tab_tab_type_current__2BEPc')]");
    private By activeSaucesSection = By.xpath("//span[text()='Соусы']/parent::div[contains(@class, 'tab_tab_type_current__2BEPc')]");
    private By activeFillingsSection = By.xpath("//span[text()='Начинки']/parent::div[contains(@class, 'tab_tab_type_current__2BEPc')]");

    public MainPage(WebDriver driver) {
        super(driver);
    }

    @Step("Открыть главную страницу")
    public void open() {
        driver.get("https://stellarburgers.education-services.ru/");
        wait.until(ExpectedConditions.visibilityOfElementLocated(bunsSection));
    }

    @Step("Нажать кнопку 'Войти в аккаунт'")
    public void clickLoginButton() {
        clickElement(loginButton);
    }

    @Step("Нажать кнопку 'Личный кабинет'")
    public void clickPersonalAccountButton() {
        clickElement(personalAccountButton);
    }

    @Step("Нажать раздел 'Булки'")
    public void clickBunsSection() {
        waitForSectionClickable(bunsSection);
        clickElementWithJS(bunsSection);
    }

    @Step("Нажать раздел 'Соусы'")
    public void clickSaucesSection() {
        waitForSectionClickable(saucesSection);
        clickElementWithJS(saucesSection);
    }

    @Step("Нажать раздел 'Начинки'")
    public void clickFillingsSection() {
        waitForSectionClickable(fillingsSection);
        clickElementWithJS(fillingsSection);
    }

    @Step("Проверить активацию раздела 'Булки'")
    public boolean waitForBunsSectionActive() {
        return waitForSectionActive(activeBunsSection, "Булки");
    }

    @Step("Проверить активацию раздела 'Соусы'")
    public boolean waitForSaucesSectionActive() {
        return waitForSectionActive(activeSaucesSection, "Соусы");
    }

    @Step("Проверить активацию раздела 'Начинки'")
    public boolean waitForFillingsSectionActive() {
        return waitForSectionActive(activeFillingsSection, "Начинки");
    }

    private boolean waitForSectionActive(By activeSectionLocator, String sectionName) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(activeSectionLocator));
            System.out.println("Раздел '" + sectionName + "' активирован");
            return true;
        } catch (Exception e) {
            System.out.println("Раздел '" + sectionName + "' не активировался в течение времени ожидания");
            return false;
        }
    }

    private void waitForSectionClickable(By sectionLocator) {
        wait.until(ExpectedConditions.elementToBeClickable(sectionLocator));
    }

    private void clickElementWithJS(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }
}