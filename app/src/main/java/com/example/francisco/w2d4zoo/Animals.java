package com.example.francisco.w2d4zoo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by FRANCISCO on 10/08/2017.
 */

public class Animals implements Parcelable {

    int animal_id,category_id;
    String animal_name, description, habitat;
    byte[] sound,img;

    public Animals(int animal_id, int category_id, String animal_name, String description, String habitat, byte[] sound, byte[] img) {
        this.animal_id = animal_id;
        this.category_id = category_id;
        this.animal_name = animal_name;
        this.description = description;
        this.habitat = habitat;
        this.sound = sound;
        this.img = img;
    }

    protected Animals(Parcel in) {
        animal_id = in.readInt();
        category_id = in.readInt();
        animal_name = in.readString();
        description = in.readString();
        habitat = in.readString();
        sound = in.createByteArray();
        img = in.createByteArray();
    }

    public static final Creator<Animals> CREATOR = new Creator<Animals>() {
        @Override
        public Animals createFromParcel(Parcel in) {
            return new Animals(in);
        }

        @Override
        public Animals[] newArray(int size) {
            return new Animals[size];
        }
    };

    public int getAnimal_id() {
        return animal_id;
    }

    public void setAnimal_id(int animal_id) {
        this.animal_id = animal_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getAnimal_name() {
        return animal_name;
    }

    public void setAnimal_name(String animal_name) {
        this.animal_name = animal_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHabitat() {
        return habitat;
    }

    public void setHabitat(String habitat) {
        this.habitat = habitat;
    }

    public byte[] getSound() {
        return sound;
    }

    public void setSound(byte[] sound) {
        this.sound = sound;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(animal_id);
        dest.writeInt(category_id);
        dest.writeString(animal_name);
        dest.writeString(description);
        dest.writeString(habitat);
        dest.writeByteArray(sound);
        dest.writeByteArray(img);
    }
}
