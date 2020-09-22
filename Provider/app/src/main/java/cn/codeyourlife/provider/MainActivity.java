package cn.codeyourlife.provider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView showTable;
    private String newId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // db helper
        this.showTable = (TextView) findViewById(R.id.show_table);


        // button insert
        Button btnInsert = (Button) findViewById(R.id.btn_insert);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("content://cn.codeyourlife.database.provider/book");
                ContentValues values = new ContentValues();
                values.put("name", "The Da Vinci Code");
                values.put("author", "Dan Brown");
                values.put("pages", 454);
                values.put("price", 16.96);
                Uri newUri = getContentResolver().insert(uri, values);
                newId = newUri.getPathSegments().get(1);

                Toast.makeText(MainActivity.this, "插入成功", Toast.LENGTH_SHORT).show();
            }
        });

        // button query
        Button btnQuery = (Button) findViewById(R.id.btn_query);
        btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("content://cn.codeyourlife.database.provider/book");
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                StringBuilder record= new StringBuilder();
                if (cursor !=null){
                    while (cursor.moveToNext()){
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        record.append("<").append(name).append(">\t").append(author).append("\n")
                                .append("Page: ").append(pages).append("\t$").append(price);
                        record.append("\n");
                    }
                }
                cursor.close();
                // show data
                showTable.setText(record.toString());
            }
        });

        // btn update
        Button btnUpdate = (Button) findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("content://cn.codeyourlife.database.provider/book/"+newId);
                ContentValues values = new ContentValues();
                values.put("name", "success ");
                values.put("author", "biquanwang");
                values.put("pages", 454);
                values.put("price", 16.96);
                getContentResolver().update(uri, values, null, null);
                Toast.makeText(MainActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
            }
        });

        // btn delete
        Button btnDelete = (Button) findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("content://cn.codeyourlife.database.provider/book/"+newId);
                getContentResolver().delete(uri, null, null);
                Toast.makeText(MainActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
            }
        });
    }
}