package com.example.francisco.w2d4zoo;

/**
 * Created by FRANCISCO on 07/08/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class ActivityCategories extends AppCompatActivity implements OnItemClickListener, View.OnClickListener {

    private static final String TAG = "MA";
    ListView list;

    Button btnAdd;
    DatabaseHelper databaseHelper;
    ArrayList<Categories> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        databaseHelper = new DatabaseHelper(this);

        getCategoriesList();

        Button btnFill = (Button) findViewById(R.id.btnFill);

        btnFill.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        DummyData dummy = new DummyData();
        ArrayList<Categories> categoriesal = dummy.DataCategories(this);
        if(categoriesal.size()<categories.size() || categories.size()==0) {
        ArrayList<Animals> animalsal = dummy.DataAnimals(this);
            for (int i = 0; i < categoriesal.size(); i++) {
                databaseHelper.saveNewCategory(categoriesal.get(i));
            }
            for (int i = 0; i < animalsal.size(); i++) {
                databaseHelper.saveNewAnimal(animalsal.get(i));
            }

            getCategoriesList();
        }
        else
            Toast.makeText(this, "The db was filled, you cannot perform this operation twice!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d(TAG, "onItemClick: ");
        //Toast.makeText(getApplicationContext(), itemname[i] + " " + id[i], Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra(getString(R.string.ID),categories.get(i));
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        getCategoriesList();
    }

    public void getCategoriesList(){
        try {
            categories = databaseHelper.getCategories("-1");
            String [] cname = new String[categories.size()];
            for(int i=0;i<categories.size();i++) {
                cname[i] = categories.get(i).getCategory_name();
                Log.d(TAG, "Refresh: " + categories.get(i).getCategory_name());
            }

            CategoriesListViewAdapter adapter = new CategoriesListViewAdapter(this, cname, categories);
            list = (ListView) findViewById(R.id.list);
            list.setAdapter(adapter);

            list.setOnItemClickListener(this);

        }catch(Exception ex){}
    }
}