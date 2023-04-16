package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.Iterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class AddAndDelContactToGroupTests extends TestBase {

    private ContactData contactForTest;
    private GroupData groupForTest;

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

    public void getContactForTest() {
        Contacts beforeContacts = app.db().contacts();
        Groups beforeGroups = app.db().groups();
        Iterator<ContactData> iteratorContacts = beforeContacts.iterator();
        Iterator<GroupData> iteratorGroups = null;

        //  find contact not in group
        ContactData whileContact;
        GroupData whileGroup;
        while (iteratorContacts.hasNext()) {
            whileContact = iteratorContacts.next();
            iteratorGroups = beforeGroups.iterator();
            while (iteratorGroups.hasNext()) {
                whileGroup = iteratorGroups.next();
                if (whileContact.getGroups().isEmpty() || !whileContact.getGroups().contains(whileGroup)) {
                    contactForTest = whileContact;
                    groupForTest = whileGroup;
                    System.out.println("find contact " + contactForTest);
                    break;
                }
            }
            if (contactForTest != null) {
                break;
            }
        }

        if (contactForTest == null) {

            contactForTest = new ContactData().withFirstname("Yulia1").withLastname("Revina1")
                    .withAddress("000111222").withHomePhone("111").withMobilePhone("222").withWorkPhone("333")
                    .withEmail("test@mail.ru").withEmail2("test2@mail.ru").withEmail3("test3@mail.ru").withGroupName("test111");
            groupForTest = new GroupData().withName("test222").withHeader("test333").withFooter("test444");

            app.goTo().groupPage();
            app.group().create(groupForTest);

            app.goTo().newContactPage();
            app.contact().create(contactForTest);
        }
    }


    @Test
    public void testContactAddToGroup() {
        Contacts beforeContacts = app.db().contacts();
        Groups beforeGroups = app.db().groups();
        getContactForTest();
        ContactData contact = contactForTest;
        GroupData group = groupForTest;

        Groups beforeLinkedGroup = app.db().contacts().stream().iterator().next().getGroups();
        System.out.println("beforeLinkedGroup: " + beforeLinkedGroup);

        app.contact().addToGroup(contact, group);
        Contacts afterContacts = app.db().contacts();
        Groups afterGroups = app.db().groups();
        Groups afterLinkedGroup = app.db().contacts().stream().iterator().next().getGroups();
        System.out.println("afterLinkedGroup: " + afterLinkedGroup);

        Assert.assertEquals(afterGroups.size(), beforeGroups.size());
        Assert.assertEquals(afterContacts.size(), beforeContacts.size());
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
        System.out.println("beforeLinkedGroup: " + beforeLinkedGroup);

        app.contact().removeGroup(contact, group);
        Contacts afterContact = app.db().contacts();
        Groups afterGroup = app.db().groups();
        Groups afterLinkedGroup = app.db().contacts().stream().iterator().next().getGroups();
        System.out.println("afterLinkedGroup: " + afterLinkedGroup);

        Assert.assertEquals(afterGroup.size(), beforeGroup.size());
        Assert.assertEquals(afterContact.size(), beforeContact.size());
        assertThat(afterLinkedGroup.withAdded(group), equalTo(beforeLinkedGroup));
    }
}
