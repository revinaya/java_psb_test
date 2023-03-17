package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.File;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() {
        Contacts before = app.contact().all();
        app.goTo().newContactPage();
        File photo = new File ("src/test/resources/tomcat.png");
        app.contact().fillContactForm(
                 new ContactData().withFirstname("Yulia1").withLastname("Revina1").withPhoto(photo)
                .withAddress("000111222").withHomePhone("111").withMobilePhone("222").withWorkPhone("333")
                .withEmail("test@mail.ru").withEmail2("test2@mail.ru").withEmail3("test3@mail.ru").withPhoto(photo));
     //   app.contact().create(contact);
        app.contact().submitContactCreation();
        app.contact().returnToContactPage();  // переделать
      /*  Contacts after = app.contact().all();
        assertThat(after.size(), equalTo(before.size() + 1));
        assertThat(after, equalTo(
                before.withAdded(contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));*/
    }

/*    @Test
    public void testCurrentDir() {
      File currentDir = new File (".");
      System.out.println(currentDir.getAbsolutePath());
      File photo = new File ("src/test/resources/tomcat.png");
      System.out.println(photo.getAbsolutePath());
      System.out.println(photo.exists());
}*/
}
