package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.concurrent.TimeUnit;

public class ApplicationManager {
    private WebDriver wd;

    public void init() {
      wd = new FirefoxDriver();
      wd.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
      wd.get("http://localhost:8080/addressbook/");
      login("admin", "secret");
    }

    private void login(String username, String password) {
        wd.findElement(By.name("user")).click();
        wd.findElement(By.name("user")).clear();
        wd.findElement(By.name("user")).sendKeys(username);
        wd.findElement(By.name("pass")).click();
        wd.findElement(By.name("pass")).clear();
        wd.findElement(By.name("pass")).sendKeys(password);
        wd.findElement(By.xpath("//input[@value='Login']")).click();
      }

    public void returnToGroupPage() {
        wd.findElement(By.linkText("group page")).click();
      }

    public void submitGroupCreation() {
        wd.findElement(By.name("submit")).click();
      }

    public void fillGroupForm(GroupData groupData) {
        wd.findElement(By.name("group_name")).click();
        wd.findElement(By.name("group_name")).clear();
        wd.findElement(By.name("group_name")).sendKeys(groupData.name());
        wd.findElement(By.name("group_header")).click();
        wd.findElement(By.name("group_header")).clear();
        wd.findElement(By.name("group_header")).sendKeys(groupData.header());
        wd.findElement(By.name("group_footer")).click();
        wd.findElement(By.name("group_footer")).clear();
        wd.findElement(By.name("group_footer")).sendKeys(groupData.footer());
      }

    public void initGroupCreation() {
        wd.findElement(By.name("new")).click();
      }

    public void gotoGroupPage() {
        wd.findElement(By.linkText("groups")).click();
      }

    public void logout() {
      wd.findElement(By.linkText("Logout")).click();
    }

    public void stop() {
      wd.quit();
    }

    public boolean isAlertPresent() {
        try {
          wd.switchTo().alert();
          return true;
        } catch (NoAlertPresentException e) {
          return false;
        }
      }

    public void deleteSelectedGroups() {
        wd.findElement(By.name("delete")).click();
      }

    public void selectGroup() {
        wd.findElement(By.name("selected[]")).click();
      }

    public void returnToContactPage() {
        wd.findElement(By.linkText("home page")).click();
      }

    public void submitContactCreation() {
        wd.findElement(By.name("submit")).click();
      }

    public void fillContactForm(ContactData contactData) {
        wd.findElement(By.name("firstname")).click();
        wd.findElement(By.name("firstname")).clear();
        wd.findElement(By.name("firstname")).sendKeys(contactData.firstname());
        wd.findElement(By.name("lastname")).click();
        wd.findElement(By.name("lastname")).clear();
        wd.findElement(By.name("lastname")).sendKeys(contactData.lastname());
        wd.findElement(By.name("mobile")).click();
        wd.findElement(By.name("mobile")).clear();
        wd.findElement(By.name("mobile")).sendKeys(contactData.mobile());
        wd.findElement(By.name("email")).click();
        wd.findElement(By.name("email")).clear();
        wd.findElement(By.name("email")).sendKeys(contactData.email());
      }

    public void gotoNewContactPage() {
        wd.findElement(By.linkText("add new")).click();
      }
}
