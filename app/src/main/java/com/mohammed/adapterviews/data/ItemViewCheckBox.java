package com.mohammed.adapterviews.data;

import com.mohammed.adapterviews.AddNewNoteActivity;
import com.mohammed.adapterviews.R;

import java.util.ArrayList;

public class ItemViewCheckBox extends ItemViewNote {
    boolean checked;

    public ItemViewCheckBox(String noteText, int noteColor, String noteType, boolean checked) {
        super(noteText, noteColor, noteType);
        this.checked = checked;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
