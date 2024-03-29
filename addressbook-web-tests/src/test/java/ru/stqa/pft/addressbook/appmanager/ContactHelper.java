package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static ru.stqa.pft.addressbook.tests.TestBase.app;

public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void submitContactCreation() {
        click(By.name("submit"));
      }

    public void fillContactForm(ContactData contactData, boolean creation) {
        type(By.name("firstname"), contactData.firstname());
        type(By.name("lastname"), contactData.lastname());
        type(By.name("address"), contactData.address());
        type(By.name("home"), contactData.homePhone());
        type(By.name("mobile"), contactData.mobilePhone());
        type(By.name("work"), contactData.workPhone());
        type(By.name("email"), contactData.email());
        type(By.name("email2"), contactData.email2());
        type(By.name("email3"), contactData.email3());
        attach(By.name("photo"), contactData.getPhoto());
        groupInAddContact(contactData, creation);
        if (creation) {
            if(contactData.getGroups().size() > 0) {
            Assert.assertTrue(contactData.getGroups().size() == 1);
            new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroups().iterator().next().getName());
            }
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }

    public void returnToContactPage() {
        click(By.linkText("home page"));
    }
    public void selectContact(int index) {
        wd.findElements(By.name("selected[]")).get(index).click();
    }
    public void selectContactById(int id) {
        wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
    }

    public void deleteContact() {
        click(By.xpath("//input[@value='Delete']"));
    }

    public void initContactModification(int index) {
        wd.findElements(By.xpath("//img[@alt='Edit']")).get(index).click();;
    }
    public void initContactModificationById(int id) {
        wd.findElement(By.xpath("//*[contains(@href,'id=" + id + "')]//img[@alt='Edit']")).click();
    }
    public void initContactModificationById2(int id) {
        wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']", id))).click();
    }

    public void updateContactModification() {
        click(By.name("update"));
    }

    public ContactData create(ContactData contact) {
      fillContactForm(contact, true);
      submitContactCreation();
      ContactCache = null;
      returnToContactPage();
      return contact;
    }
    public void modify(ContactData contact) {
        selectContactById(contact.getId());
        initContactModificationById(contact.getId());
        fillContactForm(contact, true);
        updateContactModification();
        ContactCache = null;
        returnToContactPage();
    }

    public void delete(ContactData contact) {
        selectContactById(contact.getId());
        deleteContact();
        ContactCache = null;
        app.getHelperBase().acceptAlert();
        app.goTo().homePage();
    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }

    public int getContactCount() {
        return wd.findElements(By.name("selected[]")).size();
    }

    private Contacts ContactCache = null;

    public Contacts all() {
        if (ContactCache != null) {
            return new Contacts(ContactCache);
        }
        ContactCache = new Contacts();
        List<WebElement> elements = wd.findElements(By.name("entry"));
        for (WebElement element : elements) {
            String lastName = element.findElement(By.xpath(".//td[2]")).getText();
            String firstName = element.findElement(By.xpath(".//td[3]")).getText();
            String allPhones = element.findElement(By.xpath(".//td[6]")).getText();
            String allEmails = element.findElement(By.xpath(".//td[5]")).getText();
            String address = element.findElement(By.xpath(".//td[4]")).getText();
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            ContactCache.add(new ContactData().withId(id).withFirstname(firstName).withLastname(lastName).withAddress(address)
                    .withAllPhones(allPhones).withAllEmails(allEmails));
        }
        return new Contacts(ContactCache);
    }
    public ContactData infoFromEditForm(ContactData contact) {
        initContactModificationById2(contact.getId());
        String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
        String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
        String address = wd.findElement(By.name("address")).getAttribute("value");
        String homePhone = wd.findElement(By.name("home")).getAttribute("value");
        String mobilePhone = wd.findElement(By.name("mobile")).getAttribute("value");
        String workPhone = wd.findElement(By.name("work")).getAttribute("value");
        String email = wd.findElement(By.name("email")).getAttribute("value");
        String email2 = wd.findElement(By.name("email2")).getAttribute("value");
        String email3 = wd.findElement(By.name("email3")).getAttribute("value");
        wd.navigate().back();
        return new ContactData().withId(contact.getId()).withFirstname(firstname).withLastname(lastname).withAddress(address)
                .withHomePhone(homePhone).withMobilePhone(mobilePhone).withWorkPhone(workPhone)
                .withEmail(email).withEmail2(email2).withEmail3(email3);
    }

    private void groupInAddContact(ContactData contactData, boolean creation) {
        if (contactData.getGroups().size() > 0) {
            Assert.assertTrue(contactData.getGroups().size() == 1);
            if (creation) {
                new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroups().iterator().next().getName());
            } else {
                Assert.assertFalse(isElementPresent(By.name("new_group")));
            }
        }
    }
    public void addToGroup(ContactData contact, GroupData group){
        selectContactById(contact.getId());
        new Select(wd.findElement(By.name("to_group"))).selectByVisibleText(group.getName());
        wd.findElement(By.name("add")).click();
        wd.get("http://localhost:8080/addressbook/");
    }

    public void removeGroup(ContactData contact, GroupData group){
        new Select(wd.findElement(By.name("group"))).selectByVisibleText(group.getName());
        selectContactById(contact.getId());
        wd.findElement(By.name("remove")).click();
        wd.get("http://localhost:8080/addressbook/");
    }


}
