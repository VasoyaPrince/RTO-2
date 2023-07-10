package anon.rtoinfo.rtovehical.Adapter;

import java.util.Calendar;

public class AgeCalculation {
    private Calendar end;
    private int endDay;
    private int endMonth;
    private int endYear;
    private int resDay;
    private int resMonth;
    private int resYear;
    private Calendar start;
    private int startDay;
    private int startMonth;
    private int startYear;

    public String getCurrentDate() {
        this.end = Calendar.getInstance();
        this.endYear = this.end.get(1);
        this.endMonth = this.end.get(2);
        this.endMonth++;
        this.endDay = this.end.get(5);
        return this.endDay + ":" + this.endMonth + ":" + this.endYear;
    }

    public void setDateOfBirth(int i, int i2, int i3) {
        this.startYear = i;
        this.startMonth = i2;
        this.startDay = i3;
    }

    public int calcualteYear() {
        this.resYear = this.endYear - this.startYear;
        if (this.endMonth < this.startMonth) {
            this.resYear--;
        }
        return this.resYear;
    }

    public int calcualteMonth() {
        int i = this.endMonth;
        int i2 = this.startMonth;
        if (i >= i2) {
            this.resMonth = i - i2;
        } else {
            this.resMonth = i - i2;
            this.resMonth += 12;
            this.resYear--;
        }
        return this.resMonth;
    }

    public int calcualteDay() {
        int i = this.endDay;
        int i2 = this.startDay;
        if (i >= i2) {
            this.resDay = i - i2;
        } else {
            this.resDay = i - i2;
            this.resDay += 30;
            int i3 = this.resMonth;
            if (i3 == 0) {
                this.resMonth = 11;
                this.resYear--;
            } else {
                this.resMonth = i3 - 1;
            }
        }
        return this.resDay;
    }

    public String getResult() {
        return this.resDay + ":" + this.resMonth + ":" + this.resYear;
    }

    public String dob(int i, int i2, int i3) {
        this.startYear = i;
        this.startMonth = i2;
        this.startDay = i3;
        return this.startDay + "/" + this.startMonth + "/" + this.startYear;
    }
}
