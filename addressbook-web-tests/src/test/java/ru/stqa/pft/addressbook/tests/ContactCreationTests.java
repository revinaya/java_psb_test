package ru.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;

public class ContactCreationTests extends TestBase {

    @DataProvider
    public Iterator<Object[]> validContactsFromJson() throws JsonIOException, IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.json")))) {
            String json = "";
            String line = reader.readLine();
            while (line != null) {
                json += line;
                line = reader.readLine();
            }
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>() {}.getType()); // List<ContactData.class> то же самое
            return contacts.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
        }
    }
    @Test (dataProvider = "validContactsFromJson")
    public void testContactCreation(ContactData contact)  {
        File photo = new File ("src/test/resources/tomcat.png");
        Contacts before = app.db().contacts();
        app.goTo().newContactPage();
        app.contact().create(contact);
        Contacts after = app.db().contacts();
        assertThat(after.size(), equalTo(before.size() + 1));
        assertThat(after, equalTo(
          before.withAdded(contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
        verifyContactListInUI();
       }

    @Test
    public void testContactCreation() {
        Groups groups = app.db().groups();
        File photo = new File ("src/test/resources/tomcat.png");
        ContactData contact = new ContactData().withFirstname("Firstname").withLastname("Lastname").withPhoto(photo)
                .inGroup(groups.iterator().next());
        app.goTo().newContactPage();
        Contacts before = app.db().contacts();
        app.contact().create(contact);
        Contacts after = app.db().contacts();
        assertEquals(after.size(), before.size() + 1);
        assertThat(after, equalTo(before.withAdded(contact.withId(after.stream().mapToInt(c -> c.getId()).max().getAsInt()))));
        verifyContactListInUI();
    }
}

/*    @Test
    public void testCurrentDir() {
      File currentDir = new File (".");
      System.out.println(currentDir.getAbsolutePath());
      File photo = new File ("src/test/resources/tomcat.png");
      System.out.println(photo.getAbsolutePath());
      System.out.println(photo.exists());
}*/

