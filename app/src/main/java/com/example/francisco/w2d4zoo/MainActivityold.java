package com.example.francisco.w2d4zoo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public class MainActivityold extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Main";
    ArrayList<Animals> animalsList = new ArrayList<>();
    RecyclerView rvFoodList;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.ItemAnimator itemAnimator;
    AnimalListAdapter animalListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        rvFoodList = (RecyclerView) findViewById(R.id.rvAnimalList);
        layoutManager = new LinearLayoutManager(this);
        itemAnimator = new DefaultItemAnimator();
        rvFoodList.setLayoutManager(layoutManager);
        rvFoodList.setItemAnimator(itemAnimator);

        //initialize the adapter
        animalListAdapter = new AnimalListAdapter(animalsList);
        rvFoodList.setAdapter(animalListAdapter);

        initFoodList();
        animalListAdapter.notifyDataSetChanged();

        rvFoodList.setOnClickListener(this);

    }

    private void initFoodList() {
//        animalsList.add(new Animals("Burger", 12, 300, 4.5));
//        animalsList.add(new Animals("Pizza", 34, 340, 4.9));
//        animalsList.add(new Animals("Soup", 14, 500, 4.1));
//        animalsList.add(new Animals("Fried rice", 15, 600, 2.5));
    }

    @Override
    public void onClick(View v) {

    }
}
