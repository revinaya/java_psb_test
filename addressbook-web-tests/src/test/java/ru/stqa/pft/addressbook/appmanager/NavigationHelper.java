package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavigationHelper extends HelperBase {

    public NavigationHelper(WebDriver wd) {
        super(wd);
    }

    public void groupPage() {
        click(By.linkText("groups"));
      }

 /*   public void returnToContactPage() {
        click(By.linkText("home page"));
      } */

    public void newContactPage() {
        click(By.linkText("add new"));
      }

    public void homePage() {
        click(By.linkText("home"));
    }
}
