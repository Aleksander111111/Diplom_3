import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pages.MainPage;
import static org.junit.Assert.assertTrue;

@DisplayName("Тесты раздела 'Конструктор'")
public class ConstructorTest {
    private WebDriver driver;
    private MainPage mainPage;

    @Before
    public void setUp() {
        driver = DriverFactory.createDriver("yandex");
        mainPage = new MainPage(driver);
    }

    @Test
    @DisplayName("Переход к разделу 'Булки'")
    public void testNavigateToBunsSection() {
        mainPage.open();

        mainPage.clickBunsSection();

        boolean isBunsActive = mainPage.waitForBunsSectionActive();
        assertTrue("Раздел 'Булки' должен быть активным (иметь класс tab_tab_type_current__2BEPc)", isBunsActive);
    }

    @Test
    @DisplayName("Переход к разделу 'Соусы'")
    public void testNavigateToSaucesSection() {
        mainPage.open();

        mainPage.clickSaucesSection();

        boolean isSaucesActive = mainPage.waitForSaucesSectionActive();
        assertTrue("Раздел 'Соусы' должен быть активным (иметь класс tab_tab_type_current__2BEPc)", isSaucesActive);
    }

    @Test
    @DisplayName("Переход к разделу 'Начинки'")
    public void testNavigateToFillingsSection() {
        mainPage.open();

        mainPage.clickFillingsSection();

        boolean isFillingsActive = mainPage.waitForFillingsSectionActive();
        assertTrue("Раздел 'Начинки' должен быть активным (иметь класс tab_tab_type_current__2BEPc)", isFillingsActive);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}