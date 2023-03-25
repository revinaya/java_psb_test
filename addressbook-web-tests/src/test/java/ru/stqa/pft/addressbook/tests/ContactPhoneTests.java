package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactPhoneTests extends TestBase {
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
    public void testContactPhone() {
        app.goTo().homePage();
        ContactData contact = app.db().contacts().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

        assertThat(contact.address(), equalTo(mergeAddress(contactInfoFromEditForm)));
        assertThat(contact.allEmails(), equalTo(mergeEmails(contactInfoFromEditForm)));
        assertThat(contact.allPhones(), equalTo(mergePhones(contactInfoFromEditForm)));
 //     assertThat(contact.address(), equalTo(contactInfoFromEditForm.address()));
    }
    public static String cleaned (String phone){
        return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
    }

    private String mergePhones (ContactData contact){
        return Arrays.asList(contact.homePhone(), contact.mobilePhone(), contact.workPhone())
                .stream().filter((s) -> !s.equals(""))
                .map(ContactPhoneTests::cleaned)
                .collect(Collectors.joining("\n"));
    }
    private String mergeEmails (ContactData contact){
        return Arrays.asList(contact.email(), contact.email2(), contact.email3())
                .stream().filter((s) -> !s.equals(""))
                .collect(Collectors.joining("\n"));
    }

    private String mergeAddress (ContactData contact){
        return Arrays.asList(contact.address())
                .stream().filter((s) -> !s.equals(""))
                .collect(Collectors.joining("\n"));
    }

}


