package com.mohammed.adapterviews.data.entity;

import android.net.Uri;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "item_view_photo")
public class ItemViewPhoto extends ItemViewNote {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "image_uri")
    private Uri imageUri;

    public ItemViewPhoto(Uri imageUri, String noteText, int noteColor, String noteType) {
        super(noteText, noteColor, noteType);
        this.imageUri = imageUri;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public Uri getImageUri() {
        return imageUri;
    }

}
