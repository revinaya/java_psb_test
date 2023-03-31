package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.BrowserType;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ApplicationManager {
    private final Properties propertiese;
    private WebDriver wd;
    private String browser;

    public ApplicationManager(String browser) {
        this.browser = browser;
        propertiese = new Properties();
    }

    public void init() throws IOException {
        String target = System.getProperty("target", "local");
        propertiese.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));

        if (browser.equals(BrowserType.FIREFOX)) {
            wd = new FirefoxDriver();
        } else if (browser.equals(BrowserType.CHROME)) {
            wd = new ChromeDriver();
        }
        wd.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        wd.get(propertiese.getProperty("web.baseUrl"));
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

    public HttpSession newSession() {
        return new HttpSession(this);
    }

    public String getProperty(String key) {
        return propertiese.getProperty(key);
    }
}
