package anon.rtoinfo.rtovehical.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import anon.rtoinfo.rtovehical.datamodels.VehicleDetailsDatabaseModel;

public class VehicleDetailsTableAdapter {
    private TradetuDatabaseHelper databaseHelper;

    public VehicleDetailsTableAdapter(Context context) {
        this.databaseHelper = TradetuDatabaseHelper.getInstance(context);
    }

    public long saveVehicleDetails(String str, String str2) {
        SQLiteDatabase writableDatabase = this.databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("reg_no", str);
        contentValues.put("show_data", str2);
        long insert = writableDatabase.insert("VehicleDetailsHistory", (String) null, contentValues);
        writableDatabase.close();
        return insert;
    }

    public VehicleDetailsDatabaseModel readVehicleDetails(String str) {
        VehicleDetailsDatabaseModel vehicleDetailsDatabaseModel = new VehicleDetailsDatabaseModel();
        SQLiteDatabase writableDatabase = this.databaseHelper.getWritableDatabase();
        Cursor rawQuery = writableDatabase.rawQuery("SELECT * FROM VehicleDetailsHistory WHERE reg_no = '" + str + "' ORDER BY id DESC LIMIT 1", (String[]) null);
        while (rawQuery.moveToNext()) {
            try {
                vehicleDetailsDatabaseModel.setId(rawQuery.getInt(rawQuery.getColumnIndex("id")));
                vehicleDetailsDatabaseModel.setRegistrationNo(rawQuery.getString(rawQuery.getColumnIndex("reg_no")));
                vehicleDetailsDatabaseModel.setData(rawQuery.getString(rawQuery.getColumnIndex("show_data")));
            } catch (Throwable th) {
                writableDatabase.close();
                rawQuery.close();
                throw th;
            }
            writableDatabase.close();
            rawQuery.close();
        }
        writableDatabase.close();
        rawQuery.close();
        return vehicleDetailsDatabaseModel;
    }

    public void deleteHistoryByArgs(String str) {
        SQLiteDatabase writableDatabase = this.databaseHelper.getWritableDatabase();
        writableDatabase.execSQL("DELETE FROM VehicleDetailsHistory WHERE reg_no = '" + str + "'");
        writableDatabase.close();
    }
}
