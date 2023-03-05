package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactDeletionTests extends TestBase {
    @Test
    public void testContactDeletion() {
        app.getNavigationHelper().gotoHomePage();
        if (!app.getContactHelper().isThereAContact()) {
            app.getNavigationHelper().gotoNewContactPage();
            app.getContactHelper().createContact(new ContactData("Yulia2", "Revina2", "9876543210", "test2@mail.ru"));
        }
        app.getContactHelper().selectContact();
        app.getContactHelper().deleteContact();
        app.getHelperBase().acceptAlert();
    }

}