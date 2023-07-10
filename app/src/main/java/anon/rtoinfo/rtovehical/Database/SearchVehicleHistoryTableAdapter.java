package anon.rtoinfo.rtovehical.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import anon.rtoinfo.rtovehical.datamodels.SearchVehicleHistory;

import java.util.ArrayList;

public class SearchVehicleHistoryTableAdapter {
    private static final String QUERY_SELECT_HISTORY = "SELECT * FROM SearchVehicleHistory ORDER BY search_order DESC";
    private static final String QUERY_SELECT_HISTORY_BY_ID = "SELECT * FROM SearchVehicleHistory WHERE _id = ?";
    private static final String QUERY_SELECT_HISTORY_By_NAME = "SELECT * FROM SearchVehicleHistory WHERE reg_no = ?";
    private static final String TABLE_SEARCH_BUSES_HISTORY = "SearchVehicleHistory";
    private Context context;
    private SQLiteDatabase database;
    private TradetuDatabaseHelper dbHelper;

    public SearchVehicleHistoryTableAdapter(Context context2) {
        this.context = context2;
        open();
        close();
    }

    private void close() {
        this.dbHelper.close();
    }

    private void open() throws SQLException, NullPointerException {
        try {
            TradetuDatabaseHelper instance = TradetuDatabaseHelper.getInstance(this.context);
            this.dbHelper = instance;
            this.database = instance.getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SearchVehicleHistory getSearchVehicleHistoryByDetails(String str, boolean z) {
        SQLiteDatabase sQLiteDatabase = this.database;
        if (sQLiteDatabase == null || !sQLiteDatabase.isOpen()) {
            open();
        }
        SearchVehicleHistory searchVehicleHistory = null;
        if (str == null) {
            return null;
        }
        SQLiteDatabase sQLiteDatabase2 = this.database;
        if (sQLiteDatabase2 != null) {
            Cursor rawQuery = sQLiteDatabase2.rawQuery(QUERY_SELECT_HISTORY_By_NAME, new String[]{str});
            if (rawQuery != null && rawQuery.getCount() > 0) {
                rawQuery.moveToFirst();
                searchVehicleHistory = convertCursorToObject(rawQuery);
                rawQuery.close();
            }
            if (z) {
                sQLiteDatabase2.close();
            }
        }
        return searchVehicleHistory;
    }

    public ArrayList<SearchVehicleHistory> getSearchVehicleHistoryList(boolean z, int i) {
        Cursor cursor;
        SQLiteDatabase sQLiteDatabase = this.database;
        if (sQLiteDatabase == null || !sQLiteDatabase.isOpen()) {
            open();
        }
        SQLiteDatabase sQLiteDatabase2 = this.database;
        ArrayList<SearchVehicleHistory> arrayList = new ArrayList<>();
        if (sQLiteDatabase2 != null) {
            if (i > 0) {
                cursor = sQLiteDatabase2.rawQuery("SELECT * FROM SearchVehicleHistory ORDER BY search_order DESC LIMIT " + i, (String[]) null);
            } else {
                cursor = sQLiteDatabase2.rawQuery(QUERY_SELECT_HISTORY, (String[]) null);
            }
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        arrayList.add(convertCursorToObject(cursor));
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
            if (z) {
                sQLiteDatabase2.close();
            }
        }
        return arrayList;
    }

    private int getLastSearchOrder(boolean z) {
        SQLiteDatabase sQLiteDatabase = this.database;
        if (sQLiteDatabase == null || !sQLiteDatabase.isOpen()) {
            open();
        }
        int i = 0;
        SQLiteDatabase sQLiteDatabase2 = this.database;
        if (sQLiteDatabase2 != null) {
            Cursor rawQuery = sQLiteDatabase2.rawQuery(QUERY_SELECT_HISTORY, (String[]) null);
            if (rawQuery != null && rawQuery.getCount() > 0) {
                rawQuery.moveToFirst();
                i = rawQuery.getInt(rawQuery.getColumnIndex("search_order"));
                rawQuery.close();
            }
            if (z) {
                this.database.close();
            }
        }
        return i;
    }

    public void insertSearchVehicleHistory(SearchVehicleHistory searchVehicleHistory, boolean z) {
        SQLiteDatabase sQLiteDatabase;
        if (searchVehicleHistory != null) {
            SQLiteDatabase sQLiteDatabase2 = this.database;
            if (sQLiteDatabase2 == null || !sQLiteDatabase2.isOpen()) {
                open();
            }
            SearchVehicleHistory searchVehicleHistoryByDetails = getSearchVehicleHistoryByDetails(searchVehicleHistory.getRegistrationNo(), false);
            int lastSearchOrder = getLastSearchOrder(false);
            if (searchVehicleHistoryByDetails == null) {
                searchVehicleHistory.setSearchOrder(lastSearchOrder + 1);
                ContentValues createContentValues = createContentValues(searchVehicleHistory);
                SQLiteDatabase sQLiteDatabase3 = this.database;
                if ((sQLiteDatabase3 instanceof SQLiteDatabase) && createContentValues != null) {
                    sQLiteDatabase3.insert(TABLE_SEARCH_BUSES_HISTORY, (String) null, createContentValues);
                }
            } else {
                searchVehicleHistoryByDetails.setSearchOrder(lastSearchOrder + 1);
                if (searchVehicleHistory.getName() != null) {
                    searchVehicleHistoryByDetails.setName(searchVehicleHistory.getName());
                }
                ContentValues createContentValues2 = createContentValues(searchVehicleHistoryByDetails);
                SQLiteDatabase sQLiteDatabase4 = this.database;
                if ((sQLiteDatabase4 instanceof SQLiteDatabase) && createContentValues2 != null) {
                    sQLiteDatabase4.update(TABLE_SEARCH_BUSES_HISTORY, createContentValues2, "_id = ?", new String[]{String.valueOf(searchVehicleHistoryByDetails.getId())});
                }
            }
            if (z && (sQLiteDatabase = this.database) != null && sQLiteDatabase.isOpen()) {
                this.database.close();
            }
        }
    }

    public void deleteHistoryById(String str, boolean z) {
        SQLiteDatabase sQLiteDatabase = this.database;
        if (sQLiteDatabase == null || !sQLiteDatabase.isOpen()) {
            open();
        }
        SQLiteDatabase sQLiteDatabase2 = this.database;
        if (sQLiteDatabase2 != null) {
            sQLiteDatabase2.delete(TABLE_SEARCH_BUSES_HISTORY, "_id=?", new String[]{str});
            if (z) {
                this.database.close();
            }
        }
    }

    private SearchVehicleHistory convertCursorToObject(Cursor cursor) {
        SearchVehicleHistory searchVehicleHistory = new SearchVehicleHistory();
        searchVehicleHistory.setId(cursor.getInt(cursor.getColumnIndex("_id")));
        searchVehicleHistory.setRegistrationNo(cursor.getString(cursor.getColumnIndex("reg_no")));
        searchVehicleHistory.setName(cursor.getString(cursor.getColumnIndex("name")));
        searchVehicleHistory.setSearchOrder(cursor.getInt(cursor.getColumnIndex("search_order")));
        return searchVehicleHistory;
    }

    private ContentValues createContentValues(SearchVehicleHistory searchVehicleHistory) {
        if (searchVehicleHistory == null || searchVehicleHistory.getRegistrationNo() == null) {
            return null;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("reg_no", searchVehicleHistory.getRegistrationNo());
        contentValues.put("name", searchVehicleHistory.getName());
        contentValues.put("search_order", Integer.valueOf(searchVehicleHistory.getSearchOrder()));
        return contentValues;
    }
}
