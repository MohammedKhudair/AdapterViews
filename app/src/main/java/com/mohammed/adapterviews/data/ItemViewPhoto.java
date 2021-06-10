package com.mohammed.adapterviews.data;

import android.net.Uri;

import com.mohammed.adapterviews.AddNewNoteActivity;
import com.mohammed.adapterviews.R;

import java.util.ArrayList;

public class ItemViewPhoto extends ItemViewNote {
    private Uri imageUri;

    public ItemViewPhoto(Uri imageUri, String noteText, int noteColor, String noteType) {
        super(noteText, noteColor, noteType);
        this.imageUri = imageUri;
    }

    public Uri getImageUri() {
        return imageUri;
    }

}
