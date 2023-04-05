package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
import ru.stqa.pft.mantis.model.User;

public class ChangePasswordHelper extends HelperBase{
    public ChangePasswordHelper(ApplicationManager app) {
        super(app);
    }

  public void resetPassword(User user) {
     wd.get(String.format(app.getProperty("web.baseUrl") + "/manage_user_edit_page.php?user_id=%s", user.getId()));
     click(By.xpath("//*[@value='Reset Password']"));
 }

    public void resetPasswordLinkMail(String confirmationLink, String password) {
        wd.get(confirmationLink);
        type(By.name("password"), password);
        type(By.name("password_confirm"), password);
        click(By.cssSelector("input[value='Update User']"));
    }

    public void login(String administrator, String root) {
        type(By.name("username"), administrator);
        type(By.name("password"), root);
        click(By.xpath("//*[@value='Login']"));
    }

}
