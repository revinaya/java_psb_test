package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class AddAndDelContactToGroupTests extends TestBase{

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
        Contacts beforeContact = app.db().contacts();
        ContactData contact = beforeContact.iterator().next();
        Groups beforeGroup = app.db().groups();
        GroupData group = beforeGroup.iterator().next();
        if (!contact.getGroups().isEmpty() && contact.getGroups().contains(group)) {
            app.contact().removeGroup(contact, group);
        }
        Groups beforeLinkedGroup = app.db().contacts().stream().iterator().next().getGroups();
        System.out.println("Группы связанные ДО " + beforeLinkedGroup);

        app.contact().addToGroup(contact, group);
        Contacts afterContact = app.db().contacts();
        Groups afterGroup = app.db().groups();
        Groups afterLinkedGroup = app.db().contacts().stream().iterator().next().getGroups();
        System.out.println("Группы связанные ПОСЛЕ " + afterLinkedGroup);

        Assert.assertEquals(afterGroup.size(), beforeGroup.size());
        Assert.assertEquals(afterContact.size(), beforeContact.size());
        assertThat(afterLinkedGroup, equalTo(beforeLinkedGroup.withAdded(group)));
    }

    @Test
    public void testContactRemoveToGroup() {
        Contacts beforeContact = app.db().contacts();
        ContactData contact = beforeContact.iterator().next();
        Groups beforeGroup = app.db().groups();
        GroupData group = beforeGroup.iterator().next();
        if (contact.getGroups().isEmpty()) {
            app.contact().addToGroup(contact, group);
        }
        Groups beforeLinkedGroup = app.db().contacts().stream().iterator().next().getGroups();
        System.out.println("Группы связанные ДО " + beforeLinkedGroup);

        app.contact().removeGroup(contact, group);
        Contacts afterContact = app.db().contacts();
        Groups afterGroup = app.db().groups();
        Groups afterLinkedGroup = app.db().contacts().stream().iterator().next().getGroups();
        System.out.println("Группы связанные ПОСЛЕ " + afterLinkedGroup);

        Assert.assertEquals(afterGroup.size(), beforeGroup.size());
        Assert.assertEquals(afterContact.size(), beforeContact.size());
        assertThat(afterLinkedGroup.withAdded(group), equalTo(beforeLinkedGroup));
    }
}