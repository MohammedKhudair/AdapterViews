package com.mohammed.adapterviews.data.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.mohammed.adapterviews.data.entity.ItemViewNote;

import java.util.List;

@Dao
public interface NoteDao {

   @Query("SELECT * FROM item_view_note")
   LiveData<List<ItemViewNote>> getAllNotes();

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   void addNote(ItemViewNote itemViewNote);

   @Delete
   void deleteNote(ItemViewNote itemViewNote);

   @Update(onConflict = OnConflictStrategy.REPLACE)
   void updateNote(ItemViewNote itemViewNote);

   @Query("DELETE FROM item_view_note")
   void deleteAllNotes();
}
