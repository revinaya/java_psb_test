package ru.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class ContactCreationTests extends TestBase {

    @DataProvider
    public Iterator<Object[]> validContactsFromJson() throws JsonIOException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.json")));
        String json = "";
        String line = reader.readLine();
        while (line != null) {
            json += line;
            line = reader.readLine();
        }
        Gson gson = new Gson();
        List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>(){}.getType()); // List<ContactData.class> то же самое
        return contacts.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
    }

    @Test (dataProvider = "validContactsFromJson")
    public void testContactCreation()  {
        app.goTo().newContactPage();


    }
}
      /*  File photo = new File ("src/test/resources/tomcat.png");
        Contacts before = app.contact().all();
        app.goTo().newContactPage();
        app.contact().fillContactForm(
                 new ContactData().withFirstname("Yulia1").withLastname("Revina1")
                .withAddress("000111222"));
            /*    .withHomePhone("111").withMobilePhone("222").withWorkPhone("333")
                .withEmail("test@mail.ru").withEmail2("test2@mail.ru").withEmail3("test3@mail.ru").withPhoto(photo)); */
     //   app.contact().create(contact);
  /*      app.contact().submitContactCreation();
        app.contact().returnToContactPage();  // переделать
        Contacts after = app.contact().all();
     //   assertThat(after.size(), equalTo(before.size() + 1));
     //   assertThat(after, equalTo(
     //           before.withAdded(contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
    }

/*    @Test
    public void testCurrentDir() {
      File currentDir = new File (".");
      System.out.println(currentDir.getAbsolutePath());
      File photo = new File ("src/test/resources/tomcat.png");
      System.out.println(photo.getAbsolutePath());
      System.out.println(photo.exists());
}*/

