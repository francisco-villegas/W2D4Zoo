package com.example.francisco.w2d4zoo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by FRANCISCO on 10/08/2017.
 */

public class DummyData {

    int immages;
    int did[] = {
            R.drawable.husky,
            R.drawable.agapornis,
            R.drawable.fish,
            R.drawable.reptile,
            R.drawable.frog,
            R.drawable.arana
                };

    int didanimal[] = {
            R.drawable.husky,
            R.drawable.agapornis,
            R.drawable.fish,
            R.drawable.reptile,
            R.drawable.frog,
            R.drawable.arana
    };

    int ridanimal[] = {
            R.raw.dogs,
            R.raw.birds,
            R.raw.dolphins,
            R.raw.reptile,
            R.raw.frogs,
            R.raw.arana
    };

    public DummyData() {

    }

    public ArrayList<Categories> DataCategories(Context context){
        ArrayList<Categories> categories = new ArrayList<>();

        categories.add(new Categories(-1,"Mammals","Mammals are vertebrate animals, as are amphibians, reptiles, birds and fish.",drawable_to_byte_array(context,did[0])));
        categories.add(new Categories(-1,"Birds","They have the body covered with feathers and, the current birds, a spike horny peak.",drawable_to_byte_array(context,did[1])));
        categories.add(new Categories(-1,"Fishes","The fish are aquatic vertebrate animals, mostly covered with scales and endowed with fins, which allow their movement in the aquatic environment, and gills, with which they capture dissolved oxygen in the water.",drawable_to_byte_array(context,did[2])));
        categories.add(new Categories(-1,"Reptiles","These groups are: lizards and snakes, crocodiles, turtles and tuátaras.",drawable_to_byte_array(context,did[3])));
        categories.add(new Categories(-1,"Amphibians","The amphibians are a group of vertebrates, with branchial respiration during the larval and pulmonary phase when reaching the adult state.",drawable_to_byte_array(context,did[4])));
        categories.add(new Categories(-1,"Invertebrates","With the name of invertebrates it is known to all the animals that do not have backbone, although they have a more or less rigid inner skeleton.",drawable_to_byte_array(context,did[5])));

        return categories;
    }

    public ArrayList<Animals> DataAnimals(Context context){
        ArrayList<Animals> animals = new ArrayList<>();

        animals.add(new Animals(-1,1,"Dog","Dogs are awesome.","Home",song_to_byte_array(context,ridanimal[0]),drawable_to_byte_array(context,did[0])));
        animals.add(new Animals(-1,2,"Agaporni","Agapornis are cute.","Home",song_to_byte_array(context,ridanimal[01]),drawable_to_byte_array(context,did[1])));
        animals.add(new Animals(-1,3,"Fish","Live in water.","Ocean",song_to_byte_array(context,ridanimal[2]),drawable_to_byte_array(context,did[2])));
        animals.add(new Animals(-1,4,"Iguana","Cold blood.","desert",song_to_byte_array(context,ridanimal[3]),drawable_to_byte_array(context,did[3])));
        animals.add(new Animals(-1,5,"Frog","Always wet.","water",song_to_byte_array(context,ridanimal[4]),drawable_to_byte_array(context,did[4])));
        animals.add(new Animals(-1,6,"Spider","Almost no one likes it.","Bed",song_to_byte_array(context,ridanimal[5]),drawable_to_byte_array(context,did[5])));

        return animals;
    }

    public byte[] drawable_to_byte_array(Context context, int did){
        Drawable d = context.getResources().getDrawable(did);
        Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bitmapdata = stream.toByteArray();
        return bitmapdata;
    }

    public byte[] song_to_byte_array(Context context, int sid){

        InputStream inStream = context.getResources().openRawResource(sid);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try {
            for (int readNum; (readNum = inStream.read(buf)) != -1;) {
                bos.write(buf, 0, readNum); //no doubt here is 0
                //Writes len bytes from the specified byte array starting at offset off to this byte array output stream.
                System.out.println("read " + readNum + " bytes,");
            }
        } catch (IOException ex) {
        }
        byte[] bytes = bos.toByteArray();

        return bytes;
    }
}
