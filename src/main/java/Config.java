public class Config {
    public static String getBrowser() {
        String browser = System.getProperty("browser");
        if (browser == null || browser.isEmpty()) {
            browser = "chrome";
        }
        return browser.toLowerCase();
    }
}