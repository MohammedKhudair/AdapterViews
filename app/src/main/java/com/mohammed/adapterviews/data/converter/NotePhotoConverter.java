package com.mohammed.adapterviews.data.converter;

import android.net.Uri;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class NotePhotoConverter {

    @TypeConverter
    public static Uri fromString(String value) {
        return Uri.parse(value);
    }

    @TypeConverter
    public static String fromUri(Uri uri) {
        return uri.toString();
    }

}
