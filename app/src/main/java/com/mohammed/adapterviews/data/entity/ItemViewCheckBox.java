package com.mohammed.adapterviews.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "item_view_CheckBox")
public class ItemViewCheckBox extends ItemViewNote {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "checked")
    boolean checked;

    public ItemViewCheckBox(String noteText, int noteColor, String noteType, boolean checked) {
        super(noteText, noteColor, noteType);
        this.checked = checked;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}