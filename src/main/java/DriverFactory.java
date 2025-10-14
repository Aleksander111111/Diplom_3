import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {
    public static WebDriver createDriver(String browser) {
        WebDriver driver;

        switch (browser.toLowerCase()) {
            case "yandex":
                driver = createYandexDriver();
                break;
            case "chrome":
            default:
                driver = createChromeDriver();
                break;
        }

        driver.manage().window().maximize();
        return driver;
    }

    private static WebDriver createChromeDriver() {
        return new ChromeDriver();
    }

    private static WebDriver createYandexDriver() {
        System.setProperty("webdriver.chrome.driver", "C:\\tools\\chromedriver.exe");

        ChromeOptions yandexOptions = new ChromeOptions();
        yandexOptions.setBinary("C:\\Users\\User\\AppData\\Local\\Yandex\\YandexBrowser\\Application\\browser.exe");

        return new ChromeDriver(yandexOptions);
    }
}