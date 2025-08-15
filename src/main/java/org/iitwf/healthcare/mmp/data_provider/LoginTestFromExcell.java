package org.iitwf.healthcare.mmp.data_provider;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.io.IOException;

public class LoginTestFromExcell {

    @DataProvider(name = "excelData")
    public Object[][] getExcelData() throws IOException {
        return ExcelUtilsForDataProvider.readExcelData("src/test/resources/TestDataLogin.xlsx", "Sheet1");
    }

    @Test(dataProvider = "excelData")
    public void loginTest(String username, String password, String status) {
        System.out.println("Username: " + username + " | Password: " + password+ " | Status: " + status);
        // Add your Selenium/Playwright code here
    }
}
