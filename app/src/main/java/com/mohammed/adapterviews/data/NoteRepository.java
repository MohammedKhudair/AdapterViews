package com.mohammed.adapterviews.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.mohammed.adapterviews.data.database.NoteCheckBoxDao;
import com.mohammed.adapterviews.data.database.NoteDao;
import com.mohammed.adapterviews.data.database.NotePhotoDao;
import com.mohammed.adapterviews.data.database.NoteRoomDatabase;
import com.mohammed.adapterviews.data.entity.ItemViewCheckBox;
import com.mohammed.adapterviews.data.entity.ItemViewNote;
import com.mohammed.adapterviews.data.entity.ItemViewPhoto;

import java.util.List;

public class NoteRepository {

    private NoteDao noteDao;
    private NotePhotoDao notePhotoDao;
    private NoteCheckBoxDao noteCheckBoxDao;

    private LiveData<List<ItemViewNote>> mAllNotes;
    private LiveData<List<ItemViewPhoto>> mAllPhotoNotes;
    private LiveData<List<ItemViewCheckBox>> mAllCheckNotes;

    public NoteRepository(Application application) {
        NoteRoomDatabase db = NoteRoomDatabase.getDatabase(application);
        noteDao = db.noteDao();
        notePhotoDao = db.notePhotoDao();
        noteCheckBoxDao = db.noteCheckBoxDao();
        mAllNotes = noteDao.getAllNotes();
        mAllPhotoNotes = notePhotoDao.getAllPhotoNotes();
        mAllCheckNotes = noteCheckBoxDao.getAllCheckNotes();
    }

    //-----------------------------------------------------------------
    // Get all notes from database ************************************
    //-----------------------------------------------------------------

    public LiveData<List<ItemViewNote>> getAllNotes() {
        return mAllNotes;
    }

    public LiveData<List<ItemViewPhoto>> getAllPhotoNotes() {
        return mAllPhotoNotes;
    }

    public LiveData<List<ItemViewCheckBox>> getAllCheckNotes() {
        return mAllCheckNotes;
    }

    //-----------------------------------------------------------------
    // Add notes to database ******************************************
    //-----------------------------------------------------------------

    public void addNote(ItemViewNote itemViewNote) {
        NoteRoomDatabase.databaseWriteExecutor.execute(() -> noteDao.addNote(itemViewNote));
    }

    public void addPhotoNote(ItemViewPhoto itemViewPhoto) {
        NoteRoomDatabase.databaseWriteExecutor.execute(() -> notePhotoDao.addNote(itemViewPhoto));
    }

    public void addCheckBoxNote(ItemViewCheckBox itemViewCheckBox) {
        NoteRoomDatabase.databaseWriteExecutor.execute(() -> noteCheckBoxDao.addNote(itemViewCheckBox));
    }
    //----------------------------------------------------------------
    // Delete notes from database ************************************
    //----------------------------------------------------------------

    public void deleteNote(ItemViewNote itemViewNote) {
        NoteRoomDatabase.databaseWriteExecutor.execute(() -> noteDao.deleteNote(itemViewNote));
    }

    public void deletePhotoNote(ItemViewPhoto itemViewPhoto) {
        NoteRoomDatabase.databaseWriteExecutor.execute(() -> notePhotoDao.deleteNote(itemViewPhoto));
    }

    public void deleteCheckBoxNote(ItemViewCheckBox itemViewCheckBox) {
        NoteRoomDatabase.databaseWriteExecutor.execute(() -> noteCheckBoxDao.deleteNote(itemViewCheckBox));
    }
    //----------------------------------------------------------------
    // Update notes from database ************************************
    //----------------------------------------------------------------

    public void updateNote(ItemViewNote itemViewNote) {
        NoteRoomDatabase.databaseWriteExecutor.execute(() -> noteDao.updateNote(itemViewNote));
    }

    public void updatePhotoNote(ItemViewPhoto itemViewPhoto) {
        NoteRoomDatabase.databaseWriteExecutor.execute(() -> notePhotoDao.updateNote(itemViewPhoto));
    }

    public void updateCheckBoxNote(ItemViewCheckBox itemViewCheckBox) {
        NoteRoomDatabase.databaseWriteExecutor.execute(() -> noteCheckBoxDao.updateNote(itemViewCheckBox));
    }

    //----------------------------------------------------------------
    // Delete all notes from database ********************************
    //----------------------------------------------------------------
    public void deleteAllNotes() {
        NoteRoomDatabase.databaseWriteExecutor.execute(() -> noteDao.deleteAllNotes());
    }

    public void deleteAllPhotoNotes() {
        NoteRoomDatabase.databaseWriteExecutor.execute(() -> notePhotoDao.deleteAllNotes());
    }

    public void deleteAllCheckBoxNotes() {
        NoteRoomDatabase.databaseWriteExecutor.execute(() -> noteCheckBoxDao.deleteAllNotes());
    }


}
