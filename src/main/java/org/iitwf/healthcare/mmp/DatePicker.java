package org.iitwf.healthcare.mmp;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DatePicker {

    private static final String dateFormat = "dd MMM yyyy";

    @FindBy(css = "a.ui-datepicker-prev")
    private WebElement prev;

    @FindBy(css = "a.ui-datepicker-next")
    private WebElement next;

    @FindBy(css = "div.ui-datepicker-title")
    private WebElement curDate;

    @FindBy(css = "a.ui-state-default")
    private List < WebElement > dates;

    public void setDate(String date) {

        long diff = this.getDateDifferenceInMonths(date);
        int day = this.getDay(date);

        WebElement arrow = diff >= 0 ? next : prev;
        diff = Math.abs(diff);

        //click the arrows
        for (int i = 0; i < diff; i++)
            arrow.click();

        //select the date
//        dates.stream()
//            .filter(ele - > Integer.parseInt(ele.getText()) == day)
//            .findFirst()
//            .ifPresent(ele - > ele.click());

    }

    private int getDay(String date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(dateFormat);
        LocalDate dpToDate = LocalDate.parse(date, dtf);
        return dpToDate.getDayOfMonth();
    }

    private long getDateDifferenceInMonths(String date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(dateFormat);
        LocalDate dpCurDate = LocalDate.parse("01 " + this.getCurrentMonthFromDatePicker(), dtf);
        LocalDate dpToDate = LocalDate.parse(date, dtf);
        return YearMonth.from(dpCurDate).until(dpToDate, ChronoUnit.MONTHS);
    }

    private String getCurrentMonthFromDatePicker() {
        return this.curDate.getText();
    }
}