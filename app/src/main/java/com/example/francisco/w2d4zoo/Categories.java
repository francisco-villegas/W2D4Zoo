package com.example.francisco.w2d4zoo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by FRANCISCO on 07/08/2017.
 */

public class Categories implements Parcelable{
    int category_id;
    String category_name, category_description;
    byte[] img;

    public Categories(int category_id, String category_name, String category_description, byte[] img) {
        this.category_id = category_id;
        this.category_name = category_name;
        this.category_description = category_description;
        this.img = img;
    }

    protected Categories(Parcel in) {
        category_id = in.readInt();
        category_name = in.readString();
        category_description = in.readString();
        img = in.createByteArray();
    }

    public static final Creator<Categories> CREATOR = new Creator<Categories>() {
        @Override
        public Categories createFromParcel(Parcel in) {
            return new Categories(in);
        }

        @Override
        public Categories[] newArray(int size) {
            return new Categories[size];
        }
    };

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getCategory_description() {
        return category_description;
    }

    public void setCategory_description(String category_description) {
        this.category_description = category_description;
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
        dest.writeInt(category_id);
        dest.writeString(category_name);
        dest.writeString(category_description);
        dest.writeByteArray(img);
    }
}
