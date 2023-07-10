package anon.rtoinfo.rtovehical.datamodels;

import com.google.gson.annotations.SerializedName;
import anon.rtoinfo.rtovehical.utils.Utils;

public class ExternalVehicleDetails {
    @SerializedName("rc_body_type_desc")
    private String bodyTypeDesc;
    @SerializedName("rc_chasi_no")
    private String chassisNo;
    @SerializedName("rc_eng_no")
    private String engineNo;
    @SerializedName("rc_financer")
    private String financierName;
    @SerializedName("rc_fit_upto")
    private String fitnessUpto;
    @SerializedName("rc_norms_desc")
    private String fuelNorms;
    @SerializedName("rc_fuel_desc")
    private String fuelType;
    @SerializedName("rc_insurance_comp")
    private String insuranceCompany;
    @SerializedName("rc_insurance_upto")
    private String insuranceUpto;
    @SerializedName("rc_maker_model")
    private String makerModel;
    @SerializedName("rc_manu_month_yr")
    private String manufactureMonthYear;
    @SerializedName("rc_owner_name")
    private String ownerName;
    @SerializedName("rc_owner_sr")
    private String ownership;
    private String ownershipDesc;
    @SerializedName("rc_pucc_upto")
    private String pucUpto;
    @SerializedName("rc_status")
    private String rcStatus;
    @SerializedName("rc_registered_at")
    private String registrationAuthority;
    @SerializedName("rc_regn_dt")
    private String registrationDate;
    @SerializedName("rc_regn_no")
    private String registrationNo;
    @SerializedName("rc_tax_upto")
    private String roadTaxPaidUpto;
    private int searchCount;
    @SerializedName("rc_seat_cap")
    private String seatCapacity;
    @SerializedName("rc_unld_wt")
    private String unloadWeight;
    @SerializedName("rc_vh_class_desc")
    private String vehicleClass;
    @SerializedName("rc_color")
    private String vehicleColor;
    private VehicleInfo vehicleInfo;
    private String vehicleType;

    public VehicleDetails convertInto() {
        String str = "";
        String encodePrivateString = !Utils.isNullOrEmpty(this.chassisNo) ? encodePrivateString(this.chassisNo) : str;
        if (!Utils.isNullOrEmpty(this.engineNo)) {
            str = encodePrivateString(this.engineNo);
        }
        String str2 = str;
        if (Utils.isNullOrEmpty(this.ownershipDesc) && !Utils.isNullOrEmpty(this.ownership)) {
            this.ownershipDesc = Utils.getOwnershipString(this.ownership);
        }
        return new VehicleDetails(this.registrationAuthority, this.registrationNo, this.registrationDate, encodePrivateString, str2, this.ownerName, this.vehicleClass, this.fuelType, this.makerModel, this.fitnessUpto, this.insuranceUpto, this.fuelNorms, this.roadTaxPaidUpto, this.pucUpto, this.vehicleColor, this.seatCapacity, this.ownership, this.ownershipDesc, this.financierName, this.insuranceCompany, this.unloadWeight, this.bodyTypeDesc, this.manufactureMonthYear, this.rcStatus);
    }

    private String encodePrivateString(String str) {
        if (Utils.isNullOrEmpty(str)) {
            return str;
        }
        int length = str.length();
        if (length < 3) {
            return str.substring(0, str.length() - 2) + "XX";
        } else if (length < 4) {
            return str.substring(0, str.length() - 3) + "XXX";
        } else if (length < 5) {
            return str.substring(0, str.length() - 4) + "XXXX";
        } else {
            return str.substring(0, str.length() - 5) + "XXXXX";
        }
    }

    public boolean isEmptyResponse() {
        return Utils.isNullOrEmpty(this.ownerName) || Utils.isNullOrEmpty(this.registrationNo);
    }

    public String getRegistrationAuthority() {
        return this.registrationAuthority;
    }

    public String getRegistrationNo() {
        return this.registrationNo;
    }

    public String getRegistrationDate() {
        return this.registrationDate;
    }

    public String getChassisNo() {
        return this.chassisNo;
    }

    public String getEngineNo() {
        return this.engineNo;
    }

    public String getOwnerName() {
        return this.ownerName;
    }

    public String getVehicleClass() {
        return this.vehicleClass;
    }

    public String getFuelType() {
        return this.fuelType;
    }

    public String getMakerModel() {
        return this.makerModel;
    }

    public String getFitnessUpto() {
        return this.fitnessUpto;
    }

    public String getInsuranceUpto() {
        return this.insuranceUpto;
    }

    public String getFuelNorms() {
        return this.fuelNorms;
    }

    public String getVehicleColor() {
        return this.vehicleColor;
    }

    public String getSeatCapacity() {
        return this.seatCapacity;
    }

    public String getOwnership() {
        return this.ownership;
    }

    public String getRoadTaxPaidUpto() {
        return this.roadTaxPaidUpto;
    }

    public String toString() {
        return "ExternalVehicleDetails{registrationAuthority='" + this.registrationAuthority + '\'' + ", registrationNo='" + this.registrationNo + '\'' + ", registrationDate='" + this.registrationDate + '\'' + ", chassisNo='" + this.chassisNo + '\'' + ", engineNo='" + this.engineNo + '\'' + ", ownerName='" + this.ownerName + '\'' + ", vehicleClass='" + this.vehicleClass + '\'' + ", fuelType='" + this.fuelType + '\'' + ", makerModel='" + this.makerModel + '\'' + ", fitnessUpto='" + this.fitnessUpto + '\'' + ", insuranceUpto='" + this.insuranceUpto + '\'' + ", fuelNorms='" + this.fuelNorms + '\'' + ", roadTaxPaidUpto='" + this.roadTaxPaidUpto + '\'' + ", pucUpto='" + this.pucUpto + '\'' + ", vehicleColor='" + this.vehicleColor + '\'' + ", seatCapacity='" + this.seatCapacity + '\'' + ", ownership='" + this.ownership + '\'' + ", ownershipDesc='" + this.ownershipDesc + '\'' + ", financierName='" + this.financierName + '\'' + ", insuranceCompany='" + this.insuranceCompany + '\'' + ", searchCount=" + this.searchCount + ", vehicleInfo=" + this.vehicleInfo + ", vehicleType='" + this.vehicleType + '\'' + ", unloadWeight='" + this.unloadWeight + '\'' + ", bodyTypeDesc='" + this.bodyTypeDesc + '\'' + ", manufactureMonthYear='" + this.manufactureMonthYear + '\'' + ", rcStatus='" + this.rcStatus + '\'' + '}';
    }
}
