package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() {
        app.getNavigationHelper().gotoNewContactPage();
        app.getContactHelper().createContact(new ContactData("Yulia", "Revina", "9876543210", "test@mail.ru"));
    }

}
