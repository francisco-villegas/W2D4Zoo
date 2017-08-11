package com.example.francisco.w2d4zoo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by FRANCISCO on 10/08/2017.
 */

public class AnimalListAdapter extends RecyclerView.Adapter<AnimalListAdapter.ViewHolder> {

    private static final String TAG = "AnimalListAdapter";
    ArrayList<Animals> animalsList = new ArrayList<>();
    Context context;
    Bitmap bitmap;

    public AnimalListAdapter(ArrayList<Animals> animalsList) {
        this.animalsList = animalsList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvAnimalName, tvAnimalDescriptin;
        ImageView img_animal;

        public ViewHolder(View itemView) {
            super(itemView);

            tvAnimalName = (TextView) itemView.findViewById(R.id.tvAnimalName);
            tvAnimalDescriptin = (TextView) itemView.findViewById(R.id.tvAnimalDescriptin);
            img_animal = (ImageView) itemView.findViewById(R.id.img_animal);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.animalslist_item, parent, false);
        return new ViewHolder(view);
    }

    private int lastPosition = -1;
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if(position > lastPosition){
            //Animation animation = AnimationUtils
        }

        Log.d(TAG, "onBindViewHolder: ");
        final Animals animal = animalsList.get(position);
        holder.tvAnimalName.setText(animal.getAnimal_name());
        holder.tvAnimalDescriptin.setText(String.valueOf(animal.getDescription()));

        //img
        byte[] b = animal.getImg();
        bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
        bitmap = getResizedBitmap(bitmap, 60, 60);
        holder.img_animal.setImageBitmap(bitmap);

        holder.itemView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(context, "itemview", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,ActivityModifyAnimals.class);
                intent.putExtra(context.getString(R.string.Key2), animal);
                context.startActivity(intent);
            }
        });

//        holder.tvAnimalName.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "animalname", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(context,ActivityContact.class);
//                intent.putExtra("value", animal);
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: "+ animalsList.size());
        return animalsList.size();
    }

    public Bitmap getResizedBitmap(Bitmap image, int bitmapWidth, int bitmapHeight) {
        return Bitmap.createScaledBitmap(image, bitmapWidth, bitmapHeight, true);
    }
}
