package anon.rtoinfo.rtovehical.helpers;

import com.google.gson.Gson;
import anon.rtoinfo.rtovehical.datamodels.ExternalVehicleDetailsResponse;
import anon.rtoinfo.rtovehical.utils.GlobalReferenceEngine;
import anon.rtoinfo.rtovehical.utils.Utils;

public class ResponseJuicer {
    public static ExternalVehicleDetailsResponse responseJuice(String str) {
        if (Utils.isNullOrEmpty(str)) {
            return null;
        }
        try {
            String decrypt = EncryptionHandler.decrypt(str, GlobalReferenceEngine.dataAccessKey);
            if (Utils.isNullOrEmpty(decrypt)) {
                return null;
            }
            return (ExternalVehicleDetailsResponse) new Gson().fromJson(decrypt, ExternalVehicleDetailsResponse.class);
        } catch (Exception e) {
            return null;
        }
    }
}
