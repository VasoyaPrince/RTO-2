package anon.rtoinfo.rtovehical.datamodels;

import java.io.Serializable;

public class VehicleDetailsDatabaseModel implements Serializable {
    private String data;
    private int id;
    private String registrationNo;

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

    public String getData() {
        return this.data;
    }

    public void setData(String str) {
        this.data = str;
    }

    public String toString() {
        return "VehicleDetailsDatabaseModel{id=" + this.id + ", registrationNo='" + this.registrationNo + '\'' + ", data='" + this.data + '\'' + '}';
    }
}
