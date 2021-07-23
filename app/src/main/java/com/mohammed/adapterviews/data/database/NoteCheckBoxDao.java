package com.mohammed.adapterviews.data.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.mohammed.adapterviews.data.entity.ItemViewCheckBox;
import com.mohammed.adapterviews.data.entity.ItemViewNote;

import java.util.List;

@Dao
public interface NoteCheckBoxDao {

    @Query("SELECT * FROM item_view_CheckBox")
    LiveData<List<ItemViewCheckBox>> getAllCheckNotes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addNote(ItemViewCheckBox itemViewCheckBox);

    @Delete
    void deleteNote(ItemViewCheckBox itemViewCheckBox);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateNote(ItemViewCheckBox itemViewCheckBox);


    @Query("SELECT * FROM item_view_CheckBox LIMIT 1")
    ItemViewCheckBox[] getAnyNote();

    @Query("DELETE FROM item_view_CheckBox")
    void deleteAllNotes();
}
