package com.mohammed.adapterviews.data;

import android.net.Uri;

import com.mohammed.adapterviews.AddNewNoteActivity;
import com.mohammed.adapterviews.R;

import java.util.ArrayList;

public class ItemViewNote {
    private String noteText;
    private int noteColor;
    private String noteType;

    public ItemViewNote(String noteText, int noteColor, String noteType) {
        this.noteText = noteText;
        this.noteColor = noteColor;
        this.noteType = noteType;
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

    // اضافة بيانات افتراضية الى القائمة
    public static ArrayList<ItemViewNote> getDefaultList() {
        ArrayList<ItemViewNote> list = new ArrayList<>();
        list.add(new ItemViewCheckBox("تعلم كورس جديد على منصة برمج, والقاء نضره على التحديثات الجديدة", R.color.blue, AddNewNoteActivity.ACTIVITY_NOTE_TYPE_CHECK_NOTE, true));
        return list;
    }
}
