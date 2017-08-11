package com.example.francisco.w2d4zoo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by FRANCISCO on 07/08/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "ZOO";
    public static final String TABLE_CATEGORIES ="CATEGORIES";

    public static final String CATEGORY_ID ="category_id";
    public static final String CATEGORY_NAME ="category_name";
    public static final String CATEGORY_DESCRIPTION ="category_description";
    public static final String CATEGORY_IMG ="img";

    public static final String TABLE_ANIMALS ="animals";
    public static final String ANIMAL_ID ="animal_id";

    public static final String ANIMAL_NAME ="animal_name";
    public static final String ANIMAL_DESCRIPTION = "description";
    public static final String ANIMAL_HABITAT ="habitat";

    public static final String ANIMAL_SOUND ="sound";
    public static final String ANIMAL_IMG ="img";

    private static final String TAG = "MyDBTag";

    Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_CATEGORIES = "CREATE TABLE " + TABLE_CATEGORIES + " ( " +
                CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CATEGORY_NAME + " TEXT, " +
                CATEGORY_DESCRIPTION + " TEXT, " +
                CATEGORY_IMG + " BLOB " +
                ")";
        Log.d(TAG, "onCreate: "+CREATE_TABLE_CATEGORIES);

        String CREATE_TABLE_ANIMALS = "CREATE TABLE " + TABLE_ANIMALS + " ( " +
                ANIMAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CATEGORY_ID + " INTEGER, " +
                ANIMAL_NAME + " TEXT, " +
                ANIMAL_DESCRIPTION + " TEXT, " +
                ANIMAL_HABITAT + " TEXT, " +
                ANIMAL_SOUND + " BLOB, " +
                ANIMAL_IMG + " BLOB " +
                ")";

        Log.d(TAG, "onCreate: "+CREATE_TABLE_ANIMALS);

        sqLiteDatabase.execSQL(CREATE_TABLE_CATEGORIES);
        sqLiteDatabase.execSQL(CREATE_TABLE_ANIMALS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.d(TAG, "onUpgrade: ");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_CATEGORIES);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_ANIMALS);
        onCreate(sqLiteDatabase);
    }

    public void saveNewCategory(Categories category){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CATEGORY_NAME, category.getCategory_name());
        contentValues.put(CATEGORY_DESCRIPTION, category.getCategory_description());
        contentValues.put(CATEGORY_IMG, category.getImg());

        Log.d(TAG, "saveNewCategory: "+category.getCategory_name());

        database.insert(TABLE_CATEGORIES,null,contentValues);
    }

    public void saveNewAnimal(Animals animal){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CATEGORY_ID, animal.getCategory_id());
        contentValues.put(ANIMAL_NAME, animal.getAnimal_name());
        contentValues.put(ANIMAL_DESCRIPTION, animal.getDescription());
        contentValues.put(ANIMAL_HABITAT, animal.getHabitat());
        contentValues.put(ANIMAL_SOUND, animal.getSound());
        contentValues.put(ANIMAL_IMG, animal.getImg());

        Log.d(TAG, "saveNewAnimal: "+animal.getAnimal_name());

        database.insert(TABLE_ANIMALS,null,contentValues);
    }

    public void uploadNewCategory(Categories category, String id){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CATEGORY_NAME, category.getCategory_name());
        contentValues.put(CATEGORY_DESCRIPTION, category.getCategory_description());
        contentValues.put(CATEGORY_IMG, category.getImg());

        Log.d(TAG, "uploadNewCategory: "+category.getCategory_name());

        database.update(TABLE_CATEGORIES,contentValues, CATEGORY_ID+" = ? ",new String[]{id});
    }

    public void uploadNewAnimal(Animals animal, String id){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CATEGORY_ID, animal.getCategory_id());
        contentValues.put(ANIMAL_NAME, animal.getAnimal_name());
        contentValues.put(ANIMAL_DESCRIPTION, animal.getDescription());
        contentValues.put(ANIMAL_HABITAT, animal.getHabitat());
        contentValues.put(ANIMAL_SOUND, animal.getSound());
        contentValues.put(ANIMAL_IMG, animal.getImg());

        Log.d(TAG, "uploadNewAnimal: "+animal.getAnimal_name());
        database.update(TABLE_ANIMALS,contentValues, ANIMAL_ID+" = ? ",new String[]{id});
    }

    public void DeleteCategory(String id){
        SQLiteDatabase database = this.getWritableDatabase();
        String[] args = new String[]{id};

        String DELETE = "DELETE FROM " + TABLE_CATEGORIES + " WHERE " + CATEGORY_ID + " = ? ";
        Log.d(TAG, "DeleteCategory: "+DELETE);

        database.execSQL(DELETE,args);
    }

    public void DeleteAnimal(String id){
        SQLiteDatabase database = this.getWritableDatabase();
        String[] args = new String[]{id};

        String DELETE = "DELETE FROM " + TABLE_ANIMALS + " WHERE " + ANIMAL_ID + " = ? ";
        Log.d(TAG, "DeleteAnimal: "+DELETE);

        database.execSQL(DELETE,args);
    }


    public ArrayList<Categories> getCategories(String id){
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "";
        Cursor cursor;
        if(id.equals("-1")){
            query = "SELECT * FROM " + TABLE_CATEGORIES;
            Log.d(TAG, "getCategories: "+query + " " + id);
            cursor = database.rawQuery(query, null);
        }
        else {
            query = "SELECT * FROM " + TABLE_CATEGORIES + " WHERE " + CATEGORY_ID + " = ?";
            Log.d(TAG, "getCategories: "+query + " " + id);
            String parameters[] = {id};
            cursor = database.rawQuery(query, parameters);
        }
        //Cursor cursor = database.rawQuery(query, null);
        ArrayList<Categories> categories = new ArrayList();
        if(cursor.moveToFirst()){
            do{
                Log.d(TAG, "getCategories: Name:" + cursor.getString(1) );
                categories.add(new Categories(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getBlob(3)));
            }while(cursor.moveToNext());
        }
        else{
            Log.d(TAG, "getCategories: empty");
        }
        return categories;
    }

    public ArrayList<Animals> getAnimals(String id){
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "";
        Cursor cursor;
        if(id.equals("-1")){
            query = "SELECT * FROM " + TABLE_ANIMALS;
            Log.d(TAG, "getAnimals: "+query + " " + id);
            cursor = database.rawQuery(query, null);
        }
        else {
            query = "SELECT * FROM " + TABLE_ANIMALS + " WHERE " + ANIMAL_ID + " = ?";
            Log.d(TAG, "getAnimals: "+query + " " + id);
            String parameters[] = {id};
            cursor = database.rawQuery(query, parameters);
        }
        //Cursor cursor = database.rawQuery(query, null);
        ArrayList<Animals> animals = new ArrayList();
        if(cursor.moveToFirst()){
            do{
                Log.d(TAG, "getAnimals: Name:" + cursor.getString(1) );
                animals.add(new Animals(cursor.getInt(0),cursor.getInt(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getBlob(5),cursor.getBlob(6)));
            }while(cursor.moveToNext());
        }
        else{
            Log.d(TAG, "getAnimals: empty");
        }
        return animals;
    }

}
