package ru.stqa.pft.addressbook;

import org.testng.annotations.Test;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() {
    gotoNewContactPage();
    fillContactForm(new ContactData("Yulia", "Revina", "9876543210", "test@mail.ru"));
    submitContactCreation();
    returnToContactPage();
  }

}
