package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertTrue;

public class AddAndDelContactToGroupTests extends TestBase {


    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test111").withHeader("test222").withFooter("test333"));
        }
        if (app.db().contacts().size() == 0) {
            app.goTo().newContactPage();
            app.contact().create(new ContactData().withFirstname("Yulia0").withLastname("Revina0")
                    .withAddress("000111222").withHomePhone("111").withMobilePhone("222").withWorkPhone("333")
                    .withEmail("test@mail.ru").withEmail2("test2@mail.ru").withEmail3("test3@mail.ru").withGroupName("test111"));
        }
    }
    @Test
    public void testContactAddToGroup() {

        ContactData contactForTest = app.db().contacts().stream().findAny().get();
        GroupData groupForTest = app.db().groups().stream().findAny().get();

        if(contactForTest.getGroups()
                .stream().anyMatch(group -> group.getId()==groupForTest.getId())) {
            app.goTo().newContactPage();
            contactForTest = app.contact().create(new ContactData().withFirstname("Yulia0").withLastname("Revina0")
                    .withAddress("000111222").withHomePhone("111").withMobilePhone("222").withWorkPhone("333")
                    .withEmail("test@mail.ru").withEmail2("test2@mail.ru").withEmail3("test3@mail.ru").withGroupName("test111"));
            app.goTo().homePage();
            int maxId = app.db().contacts().stream()
                    .mapToInt((value) -> {return value.getId();})
                    .summaryStatistics()
                    .getMax();
            contactForTest.withId(maxId);
        }
        app.contact().addToGroup(contactForTest, groupForTest);
        ContactData finalContactForIncluding = contactForTest;

        ContactData contactAfterTest = app.db().contacts().stream().filter(c -> c.getId()== finalContactForIncluding.getId()).findAny().get();
        assertTrue(contactAfterTest.getGroups()
                .stream().anyMatch(group -> group.getId()==groupForTest.getId()));
    }

    @Test
    public void testContactRemoveToGroup() {
        Contacts beforeContact = app.db().contacts();
        ContactData contactForTest = beforeContact.iterator().next();
        Groups beforeGroup = app.db().groups();
        GroupData groupForTest = beforeGroup.iterator().next();
        if(!contactForTest.getGroups()
                .stream().anyMatch(group -> group.getId()==groupForTest.getId())) {
            app.contact().addToGroup(contactForTest, groupForTest);
        }
        Groups beforeLinkedGroup = app.db().contacts().stream().iterator().next().getGroups();
        System.out.println("beforeLinkedGroup: " + beforeLinkedGroup);

        app.contact().removeGroup(contactForTest, groupForTest);
        Contacts afterContact = app.db().contacts();
        Groups afterGroup = app.db().groups();
        Groups afterLinkedGroup = app.db().contacts().stream().iterator().next().getGroups();
        System.out.println("afterLinkedGroup: " + afterLinkedGroup);

        Assert.assertEquals(afterGroup.size(), beforeGroup.size());
        Assert.assertEquals(afterContact.size(), beforeContact.size());
        assertThat(afterLinkedGroup.withAdded(groupForTest), equalTo(beforeLinkedGroup));
    }
}
