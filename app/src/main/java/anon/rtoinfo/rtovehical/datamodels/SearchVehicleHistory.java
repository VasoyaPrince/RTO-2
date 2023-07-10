package anon.rtoinfo.rtovehical.datamodels;

import java.io.Serializable;

public class SearchVehicleHistory implements Serializable {
    private int id;
    private String name;
    private String registrationNo;
    private int searchOrder;

    public SearchVehicleHistory() {
    }

    public SearchVehicleHistory(int i, String str, String str2, int i2) {
        this.id = i;
        this.registrationNo = str;
        this.name = str2;
        this.searchOrder = i2;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public String getRegistrationNo() {
        return this.registrationNo;
    }

    public void setRegistrationNo(String str) {
        this.registrationNo = str;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public int getSearchOrder() {
        return this.searchOrder;
    }

    public void setSearchOrder(int i) {
        this.searchOrder = i;
    }

    public String toString() {
        return "SearchVehicleHistory{id=" + this.id + ", registrationNo='" + this.registrationNo + '\'' + ", name='" + this.name + '\'' + ", searchOrder=" + this.searchOrder + '}';
    }
}
