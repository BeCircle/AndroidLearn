package cn.codeyourlife.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class DatabaseProvider extends ContentProvider {
    private static final String TAG = "DatabaseProvider";
    public DatabaseProvider() {
    }

    public static final int BOOK_DIR = 0;
    public static final int BOOK_ITEM = 1;
    public static final int CATEGORY_DIR = 2;
    public static final int CATEGORY_ITEM = 3;
    public static final String AUTHORITY = "cn.codeyourlife.database.provider";
    private static UriMatcher uriMatcher;
    private DBHelper dbHelper;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "book", BOOK_DIR);
        uriMatcher.addURI(AUTHORITY, "book/#", BOOK_ITEM);
        uriMatcher.addURI(AUTHORITY, "category", CATEGORY_DIR);
        uriMatcher.addURI(AUTHORITY, "category/#", CATEGORY_ITEM);
    }



    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int deletedRow = 0;
        switch (uriMatcher.match(uri)){
            case BOOK_DIR:
                deletedRow = db.delete(DBHelper.TB_Book, selection, selectionArgs);
                break;
            case BOOK_ITEM:
                String bookId = uri.getPathSegments().get(1);
                deletedRow = db.delete(DBHelper.TB_Book, "id=?", new String[]{bookId});
                break;
            case CATEGORY_DIR:
                deletedRow = db.delete(DBHelper.TB_Category, selection, selectionArgs);
                break;
            case CATEGORY_ITEM:
                String categoryId = uri.getPathSegments().get(1);
                deletedRow = db.delete(DBHelper.TB_Category, "id=?", new String[]{categoryId});
                break;
            default:
                break;
        }
        return deletedRow;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            case BOOK_DIR:
                return "vnd.android.cursor.dir/vnd/cn.codeyourlife.database.provider.book";
            case BOOK_ITEM:
                return "vnd.android.cursor.item/vnd/cn.codeyourlife.database.provider.book";
            case CATEGORY_DIR:
                return "vnd.android.cursor.dir/vnd/cn.codeyourlife.database.provider.category";
            case CATEGORY_ITEM:
                return "vnd.android.cursor.item/vnd/cn.codeyourlife.database.provider.category";
        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Uri uriReturn = null;
        switch (uriMatcher.match(uri)){
            case BOOK_DIR:
            case BOOK_ITEM:
                long newBooId = db.insert(DBHelper.TB_Book, null, values);
                uriReturn = Uri.parse("content://"+AUTHORITY+"/book/"+newBooId);
                break;
            case CATEGORY_DIR:
            case CATEGORY_ITEM:
                long newCategoryId = db.insert(DBHelper.TB_Category, null, values);
                uriReturn = Uri.parse("content://"+AUTHORITY+"/category/"+newCategoryId);
                break;
            default:
                break;
        }
        return uriReturn;
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext(), DBHelper.DB_BOOK, null, 2);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        switch (uriMatcher.match(uri)){
            case BOOK_DIR:
                cursor = db.query(DBHelper.TB_Book, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case BOOK_ITEM:
                String bookId = uri.getPathSegments().get(1);
                cursor = db.query(DBHelper.TB_Book, projection, "id=?", new String[]{bookId}, null, null, sortOrder);
                break;
            case CATEGORY_DIR:
                cursor = db.query(DBHelper.TB_Category, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case CATEGORY_ITEM:
                String categoryId = uri.getPathSegments().get(1);
                cursor = db.query(DBHelper.TB_Category, projection, "id=?", new String[]{categoryId}, null, null, sortOrder);
                break;
            default:
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int updateRows = 0;
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
                updateRows = db.update(DBHelper.TB_Book, values, selection, selectionArgs);
                break;
            case BOOK_ITEM:
                String bookId = uri.getPathSegments().get(1);
                Log.d(TAG, "update: "+bookId);
                updateRows = db.update(DBHelper.TB_Book, values, "id=?", new String[]{bookId});
                break;
            case CATEGORY_DIR:
                updateRows = db.update(DBHelper.TB_Category, values, selection, selectionArgs);
                break;
            case CATEGORY_ITEM:
                String categoryId = uri.getPathSegments().get(1);
                updateRows = db.update(DBHelper.TB_Category, values, "id=?", new String[]{categoryId});
            default:
                break;
        }
        return updateRows;
    }
}
