package anon.rtoinfo.rtovehical.datamodels;

import java.io.Serializable;

public class VehicleInfo implements Serializable {
    private int brandId;
    private String brandName;
    private String exShowroomPrice;
    private int id;
    private String imageUrl;
    private String modelName;
    private String modelSlug;

    public int getId() {
        return this.id;
    }

    public String getBrandName() {
        return this.brandName;
    }

    public String getModelName() {
        return this.modelName;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public String toString() {
        return "VehicleInfo{id=" + this.id + ", brandId='" + this.brandId + '\'' + ", brandName='" + this.brandName + '\'' + ", modelName='" + this.modelName + '\'' + ", modelSlug='" + this.modelSlug + '\'' + ", exShowroomPrice='" + this.exShowroomPrice + '\'' + ", imageUrl='" + this.imageUrl + '\'' + '}';
    }
}
