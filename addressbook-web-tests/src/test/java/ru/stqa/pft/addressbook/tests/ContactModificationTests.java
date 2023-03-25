package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTests extends TestBase {
    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().homePage();
        if (app.db().contacts().size() == 0) {
            app.goTo().newContactPage();
            app.contact().create(new ContactData().withFirstname("Yulia0").withLastname("Revina0").
                    withAddress("000111222").withHomePhone("111").withMobilePhone("222").withWorkPhone("333")
                    .withEmail("test@mail.ru").withEmail2("test2@mail.ru").withEmail3("test3@mail.ru"));
        }
    }


    @Test
    public void testContactModification() {
        Contacts before = app.db().contacts();
        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData().
                withId(modifiedContact.getId()).withFirstname("Yulia2").withLastname("Revina2")
                .withAddress("111222333").withHomePhone("111").withMobilePhone("222").withWorkPhone("333")
                .withEmail("test@mail.ru").withEmail2("test2@mail.ru").withEmail3("test3@mail.ru");
        app.contact().modify(contact);
        assertThat(app.group().count(), equalTo(before.size()));
        Contacts after = app.db().contacts();
        assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
        verifyContactListInUI();
    }

}




