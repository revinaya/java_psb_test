package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


public class ApplicationManager {
    private final Properties propertiese;
    WebDriver wd;
    private ContactHelper contactHelper;
    private SessionHelper sessionHelper;
    private NavigationHelper navigationHelper;
    private GroupHelper groupHelper;
    private HelperBase helperBase;
    private String browser;
    private DbHelper dbHelper;


    public ApplicationManager(String browser) {
        this.browser = browser;
        propertiese = new Properties();
    }

    public void init() throws IOException {
        String target = System.getProperty("target", "local");
        propertiese.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));

        dbHelper = new DbHelper();

        if("".equals(propertiese.getProperty("selenium.server"))) {
        if (browser.equals(BrowserType.FIREFOX)) {
            System.setProperty("webdriver.gecko.driver", "c:\\geckodriver\\geckodriver.exe");
            wd = new FirefoxDriver();
        } else if (browser.equals(BrowserType.CHROME)) {
            System.setProperty("webdriver.chrome.driver", "c:\\geckodriver\\chromedriver.exe");
            wd = new ChromeDriver();
        }
        } else {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setBrowserName(browser); // любой браузер
            capabilities.setPlatform(Platform.fromString(System.getProperty("platform", "win7"))); // пробрасываем какую хотим операционку
            wd = new RemoteWebDriver(new URL(propertiese.getProperty("selenium.server")), capabilities);
        }

        wd.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        wd.get(propertiese.getProperty("web.baseUrl"));
        groupHelper = new GroupHelper(wd);
        contactHelper = new ContactHelper(wd);
        navigationHelper = new NavigationHelper(wd);
        sessionHelper = new SessionHelper(wd);
        helperBase = new HelperBase(wd);
        sessionHelper.login(propertiese.getProperty("web.adminLogin"), (propertiese.getProperty("web.adminPassword")));
    }

    public void stop() {
        wd.quit();
    }

    public boolean isElementPresent(By by) {
        try {
            wd.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public GroupHelper group() {
        return groupHelper;
    }

    public NavigationHelper goTo() {
        return navigationHelper;
    }

    public ContactHelper contact() {
        return contactHelper;
    }

    public HelperBase getHelperBase() {
        return helperBase;
    }

    public DbHelper db() {
        return dbHelper;
    }

}
