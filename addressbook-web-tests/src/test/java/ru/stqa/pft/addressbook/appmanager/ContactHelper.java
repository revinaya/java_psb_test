package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void submitContactCreation() {
        click(By.name("submit"));
      }

    public void fillContactForm(ContactData contactData) {
        type(By.name("firstname"), contactData.firstname());
        type(By.name("lastname"), contactData.lastname());
        type(By.name("mobile"), contactData.mobile());
        type(By.name("email"), contactData.email());
      }
    public void returnToContactPage() {
        click(By.linkText("home page"));
    }
    public void selectContact() { // пока выбираем первый
        click(By.name("selected[]"));
    }

    public void deleteContact() {
        click(By.xpath("//input[@value='Delete']"));
    }

    public void initContactModification() {
        click(By.xpath("//img[@alt='Edit']"));
    }

    public void updateContactModification() {
        click(By.name("update"));
    }

    public void createContact(ContactData contact) {
      fillContactForm(contact);
      submitContactCreation();
      returnToContactPage();
    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }
}
