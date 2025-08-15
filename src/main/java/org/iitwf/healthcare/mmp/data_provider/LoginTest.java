package org.iitwf.healthcare.mmp.data_provider;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginTest {

    @DataProvider(name = "loginData")
    public Object[][] getData() {
        return new Object[][]{
            {"user1", "pass1"},
            {"user2", "pass2"},
            {"admin", "admin123"}
        };
    }

    @Test(dataProvider = "loginData")
    public void loginTest(String username, String password) {
        System.out.println("Testing login with: " + username + " / " + password);
    }
}

