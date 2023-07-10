package anon.rtoinfo.rtovehical.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TradetuDatabaseHelper extends SQLiteOpenHelper {
    private static final String ALTER_TABLE_SEARCH_VEHICLE_HISTORY = "ALTER TABLE SearchVehicleHistory ADD COLUMN name TEXT";
    private static final String TABLE_CREATE_CAR_BIKE_COMPARISON_HISTORY = "CREATE TABLE IF NOT EXISTS CarBikeComparisonHistory (id INTEGER PRIMARY KEY AUTOINCREMENT, vehicle_type TEXT, car_model_1 TEXT, car_model_2 TEXT, car_variant_1 TEXT, car_variant_2 TEXT, bike_model_1 TEXT, bike_model_2 TEXT, bike_variant_1 TEXT, bike_variant_2 TEXT, car_model_1_name TEXT, car_model_2_name TEXT, car_variant_1_name TEXT, car_variant_2_name TEXT, bike_model_1_name, bike_model_2_name TEXT, bike_variant_1_name TEXT, bike_variant_2_name TEXT, search_order INTEGER)";
    private static final String TABLE_CREATE_FAVORITE_CAR_BIKE_MODEL = "CREATE TABLE IF NOT EXISTS FavouriteCarBikeModel (id INTEGER PRIMARY KEY AUTOINCREMENT, vehicle_type TEXT, model_id INTEGER, show_data TEXT)";
    private static final String TABLE_CREATE_LICENSE_DETAIL = "CREATE TABLE IF NOT EXISTS LicenseDetailsHistory (id INTEGER PRIMARY KEY AUTOINCREMENT, dl_no TEXT, dob TEXT, show_data TEXT)";
    private static final String TABLE_CREATE_SEARCH_CHALLAN_HISTORY = "CREATE TABLE IF NOT EXISTS SearchChallanHistory (_id INTEGER PRIMARY KEY AUTOINCREMENT, reg_no TEXT, searchType TEXT, search_order INTEGER)";
    private static final String TABLE_CREATE_SEARCH_LICENSE_HISTORY = "CREATE TABLE IF NOT EXISTS SearchLicenseHistory (_id INTEGER PRIMARY KEY AUTOINCREMENT, dl_no TEXT, dob TEXT, name TEXT, search_order INTEGER)";
    private static final String TABLE_CREATE_SEARCH_VEHICLE_HISTORY = "CREATE TABLE IF NOT EXISTS SearchVehicleHistory (_id INTEGER PRIMARY KEY AUTOINCREMENT, reg_no TEXT, name TEXT, search_order INTEGER)";
    private static final String TABLE_CREATE_VEHICLE_DETAIL = "CREATE TABLE IF NOT EXISTS VehicleDetailsHistory (id INTEGER PRIMARY KEY AUTOINCREMENT, reg_no TEXT, show_data TEXT)";
    private static final String TAG = "TradetuDatabaseHelper";
    private static TradetuDatabaseHelper mInstance;

    private TradetuDatabaseHelper(Context context) {
        super(context, "vehicle_details.sqlite", (SQLiteDatabase.CursorFactory) null, 9);
    }

    public static synchronized TradetuDatabaseHelper getInstance(Context context) {
        Class<TradetuDatabaseHelper> cls = TradetuDatabaseHelper.class;
        synchronized (cls) {
            synchronized (cls) {
                if (mInstance == null) {
                    mInstance = new TradetuDatabaseHelper(context.getApplicationContext());
                }
            }
        }
       TradetuDatabaseHelper tradetuDatabaseHelper = mInstance;
        return tradetuDatabaseHelper;
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        Log.i(TAG, "onCreate");
        sQLiteDatabase.execSQL(TABLE_CREATE_SEARCH_VEHICLE_HISTORY);
        sQLiteDatabase.execSQL(TABLE_CREATE_VEHICLE_DETAIL);
        sQLiteDatabase.execSQL(TABLE_CREATE_SEARCH_LICENSE_HISTORY);
        sQLiteDatabase.execSQL(TABLE_CREATE_LICENSE_DETAIL);
        sQLiteDatabase.execSQL(TABLE_CREATE_CAR_BIKE_COMPARISON_HISTORY);
        sQLiteDatabase.execSQL(TABLE_CREATE_SEARCH_CHALLAN_HISTORY);
        sQLiteDatabase.execSQL(TABLE_CREATE_FAVORITE_CAR_BIKE_MODEL);
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        sQLiteDatabase.execSQL(TABLE_CREATE_SEARCH_VEHICLE_HISTORY);
        sQLiteDatabase.execSQL(TABLE_CREATE_VEHICLE_DETAIL);
        sQLiteDatabase.execSQL(TABLE_CREATE_SEARCH_LICENSE_HISTORY);
        sQLiteDatabase.execSQL(TABLE_CREATE_LICENSE_DETAIL);
        sQLiteDatabase.execSQL(TABLE_CREATE_CAR_BIKE_COMPARISON_HISTORY);
        sQLiteDatabase.execSQL(TABLE_CREATE_SEARCH_CHALLAN_HISTORY);
        sQLiteDatabase.execSQL(TABLE_CREATE_FAVORITE_CAR_BIKE_MODEL);
        if (i >= i2) {
            String str = TAG;
            Log.i(str, "No changes in DB version; " + i);
            return;
        }
        try {
            sQLiteDatabase.execSQL(ALTER_TABLE_SEARCH_VEHICLE_HISTORY);
            String str2 = TAG;
            Log.i(str2, "New column added in Table: " + i2);
        } catch (Exception e) {
            String str3 = TAG;
            Log.e(str3, "Error updating table: " + e.getMessage());
        }
    }
}
