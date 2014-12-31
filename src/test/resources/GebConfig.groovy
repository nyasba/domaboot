import org.openqa.selenium.chrome.ChromeDriver


//// 環境にあったChromeDriverを以下からダウンロードして指定して下さい。
//// https://code.google.com/p/selenium/wiki/ChromeDriver
System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe")

driver = {
    return new ChromeDriver()
}
