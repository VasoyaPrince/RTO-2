package anon.rtoinfo.rtovehical.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private Context mycontext;
    public SQLiteDatabase myDataBase;
    private static String DB_NAME = "rto_database.db";
    public static final String CONTACTS_TABLE_NAME = "rto_data";
    private String DB_PATH;

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    public DBHelper(Context context) {
        super(context, DB_NAME, (SQLiteDatabase.CursorFactory) null, 1);
        this.mycontext = context;

        DB_PATH = ("/data/data/" + this.mycontext.getApplicationContext().getPackageName() + "/databases/");

        if (checkdatabase()) {
            opendatabase();
            return;
        }
        System.out.println("Database doesn't exist");
        createdatabase();
    }

    public void createdatabase() {
        if (!checkdatabase()) {
            getReadableDatabase();
            try {
                copydatabase();
            } catch (IOException unused) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkdatabase() {
        try {
            return new File(this.DB_PATH + DB_NAME).exists();
        } catch (SQLiteException unused) {
            System.out.println("Database doesn't exist");
            return false;
        }
    }

    public ArrayList getAllCotacts() {
        ArrayList arrayList = new ArrayList();
        Cursor rawQuery = getReadableDatabase().rawQuery("select * from rto_data", null);
        rawQuery.moveToFirst();
        while (!rawQuery.isAfterLast()) {
            try {
                arrayList.add(rawQuery.getString(1));
                rawQuery.moveToNext();
            } catch (Exception unused) {
            }
        }
        return arrayList;
    }

    private void copydatabase() throws IOException {
        InputStream open = this.mycontext.getAssets().open(DB_NAME);
        FileOutputStream fileOutputStream = new FileOutputStream(this.DB_PATH + DB_NAME);
        byte[] bArr = new byte[1024];
        while (true) {
            int read = open.read(bArr);
            if (read > 0) {
                fileOutputStream.write(bArr, 0, read);
            } else {
                fileOutputStream.flush();
                fileOutputStream.close();
                open.close();
                return;
            }
        }
    }

    public void opendatabase() throws SQLException {
        this.myDataBase = SQLiteDatabase.openDatabase(this.DB_PATH + DB_NAME, null, 0);
    }

    @Override // java.lang.AutoCloseable
    public synchronized void close() {
        if (this.myDataBase != null) {
            this.myDataBase.close();
        }
        super.close();
    }

    public void onOpen(SQLiteDatabase sQLiteDatabase) {
        super.onOpen(sQLiteDatabase);
        if (Build.VERSION.SDK_INT >= 16) {
            sQLiteDatabase.disableWriteAheadLogging();
        }
    }

    public Cursor getData(String str) {
        SQLiteDatabase readableDatabase = getReadableDatabase();
        return readableDatabase.rawQuery("select * from rto_data where field2 like '" + str + "';", null);
    }
}
