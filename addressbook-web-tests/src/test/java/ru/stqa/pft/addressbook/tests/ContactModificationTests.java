package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {
    @Test
    public void testContactModification() {
        app.getNavigationHelper().gotoHomePage();
        if (!app.getContactHelper().isThereAContact()) {
            app.getNavigationHelper().gotoNewContactPage();
            app.getContactHelper().createContact(new ContactData("Yulia2", "Revina2", "9876543210", "test2@mail.ru"));
        }
        app.getContactHelper().selectContact();
        app.getContactHelper().initContactModification();
        app.getContactHelper().fillContactForm(new ContactData("Yulia3", "Revina3", "0123456789", "test3@gmail.com"));
        app.getContactHelper().updateContactModification();
        app.getContactHelper().returnToContactPage();
    }
}




