package anon.rtoinfo.rtovehical.datamodels;

import androidx.core.app.NotificationCompat;

import com.google.gson.annotations.SerializedName;

public class ExternalVehicleDetailsResponse {
    private ExternalVehicleDetails result;
    @SerializedName(NotificationCompat.CATEGORY_STATUS)
    private int statusCode;
    @SerializedName("message")
    private String statusMessage;

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getStatusMessage() {
        return this.statusMessage;
    }

    public ExternalVehicleDetails getResult() {
        return this.result;
    }

    public String toString() {
        return "ExternalVehicleDetailsResponse{statusCode=" + this.statusCode + ", statusMessage='" + this.statusMessage + '\'' + ", result=" + this.result + '}';
    }
}
