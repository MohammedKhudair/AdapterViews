package com.mohammed.adapterviews.data.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.mohammed.adapterviews.data.entity.ItemViewCheckBox;
import com.mohammed.adapterviews.data.entity.ItemViewPhoto;

import java.util.List;

@Dao
public interface NotePhotoDao {

    @Query("SELECT * FROM item_view_photo")
    LiveData<List<ItemViewPhoto>> getAllPhotoNotes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addNote(ItemViewPhoto itemViewPhoto);

    @Delete
    void deleteNote(ItemViewPhoto itemViewPhoto);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateNote(ItemViewPhoto itemViewPhoto);

    @Query("DELETE FROM item_view_photo")
    void deleteAllNotes();
}
