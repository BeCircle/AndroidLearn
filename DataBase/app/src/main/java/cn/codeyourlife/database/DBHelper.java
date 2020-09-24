package cn.codeyourlife.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_BOOK = "BookStore.db";

    public static final String TB_Book = "Book";
    public static final String TB_Category = "Category";

    public static final String CREATE_BOOK = "create table "+TB_Book+" (" +
            "id integer primary key autoincrement, " +
            "author text, " +
            "price real, " +
            "pages integer, " +
            "name text)";

    public static final String CREATE_CATEGORY = "create table "+TB_Category+" (" +
            "id integer primary key autoincrement, " +
            "category_name text, " +
            "category_code integer)";

    private Context context;

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_BOOK);
        sqLiteDatabase.execSQL(CREATE_CATEGORY);
//        Toast.makeText(context, "Create succeeded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists "+TB_Book);
        sqLiteDatabase.execSQL("drop table if exists "+TB_Category);
        onCreate(sqLiteDatabase);
    }
}
