package com.mohammed.adapterviews.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.mohammed.adapterviews.AddNewNoteActivity;
import com.mohammed.adapterviews.R;

import java.util.ArrayList;

@Entity(tableName = "item_view_note")
public class ItemViewNote {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "note_text")
    private String noteText;
    @ColumnInfo(name = "note_color")
    private int noteColor;
    @ColumnInfo(name = "note_type")
    private String noteType;

    public ItemViewNote(String noteText, int noteColor, String noteType) {
        this.noteText = noteText;
        this.noteColor = noteColor;
        this.noteType = noteType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoteText() {
        return noteText;
    }

    public int getNoteColor() {
        return noteColor;
    }

    public String getNoteType() {
        return noteType;
    }

}
