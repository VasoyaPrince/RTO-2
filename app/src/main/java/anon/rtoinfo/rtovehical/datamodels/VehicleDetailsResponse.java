package anon.rtoinfo.rtovehical.datamodels;

import java.io.Serializable;

public class VehicleDetailsResponse implements Serializable {
    private VehicleDetails details;
    private boolean extra;
    private int statusCode;
    private String statusMessage;

    public VehicleDetailsResponse(int i, String str, VehicleDetails vehicleDetails) {
        this.statusCode = i;
        this.statusMessage = str;
        this.details = vehicleDetails;
    }


    public int getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(int i) {
        this.statusCode = i;
    }

    public String getStatusMessage() {
        return this.statusMessage;
    }

    public VehicleDetails getDetails() {
        return this.details;
    }

    public boolean isExtra() {
        return this.extra;
    }

    public String toString() {
        return "VehicleDetails{statusCode=" + this.statusCode + ", statusMessage='" + this.statusMessage + '\'' + ", details=" + this.details + ", extra=" + this.extra + '}';
    }
}
