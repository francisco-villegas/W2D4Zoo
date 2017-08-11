package com.example.francisco.w2d4zoo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by FRANCISCO on 10/08/2017.
 */

public class SecondActivity extends AppCompatActivity {

    private static final String TAG = "SecondActivity";
    ArrayList<Animals> animalsList = new ArrayList<>();
    RecyclerView rvAnimalList;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.ItemAnimator itemAnimator;
    AnimalListAdapter animalListAdapter;
    ArrayList<Animals> animals;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animals);

        rvAnimalList = (RecyclerView) findViewById(R.id.rvAnimalList);
        layoutManager = new LinearLayoutManager(this);
        itemAnimator = new DefaultItemAnimator();
        rvAnimalList.setLayoutManager(layoutManager);
        rvAnimalList.setItemAnimator(itemAnimator);

        //initialize the adapter
        databaseHelper = new DatabaseHelper(this);
        getAnimalList();
        animalListAdapter = new AnimalListAdapter(animalsList);
        rvAnimalList.setAdapter(animalListAdapter);

        animalListAdapter.notifyDataSetChanged();

    }

    private void getAnimalList() {
        Intent intent = getIntent();
        Categories category = intent.getParcelableExtra(getString(R.string.ID));
        animalsList = databaseHelper.getAnimals(String.valueOf(category.getCategory_id()));
    }

}
