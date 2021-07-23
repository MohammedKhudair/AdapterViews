package com.mohammed.adapterviews.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mohammed.adapterviews.data.NoteRepository;
import com.mohammed.adapterviews.data.entity.ItemViewCheckBox;
import com.mohammed.adapterviews.data.entity.ItemViewNote;
import com.mohammed.adapterviews.data.entity.ItemViewPhoto;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private NoteRepository mRepository;

    private LiveData<List<ItemViewNote>> mAllNotes;
    private LiveData<List<ItemViewPhoto>> mAllPhotoNotes;
    private LiveData<List<ItemViewCheckBox>> mAllCheckNotes;


    public NoteViewModel(@NonNull Application application) {
        super(application);
        mRepository = new NoteRepository(application);
        mAllNotes = mRepository.getAllNotes();
        mAllPhotoNotes = mRepository.getAllPhotoNotes();
        mAllCheckNotes = mRepository.getAllCheckNotes();
    }
    //-----------------------------------------------------------
    // Get all notes from database ******************************
    //-----------------------------------------------------------

    public LiveData<List<ItemViewNote>> getAllNotes() {
        return mAllNotes;
    }

    public LiveData<List<ItemViewPhoto>> getAllPhotoNotes() {
        return mAllPhotoNotes;
    }

    public LiveData<List<ItemViewCheckBox>> getAllCheckNotes() {
        return mAllCheckNotes;
    }

    //-----------------------------------------------------------
    // Add notes to database ************************************
    //-----------------------------------------------------------

    public void addNote(ItemViewNote itemViewNote) {
        mRepository.addNote(itemViewNote);
    }

    public void addPhotoNote(ItemViewPhoto itemViewPhoto) {
        mRepository.addPhotoNote(itemViewPhoto);
    }

    public void addCheckBoxNote(ItemViewCheckBox itemViewCheckBox) {
        mRepository.addCheckBoxNote(itemViewCheckBox);
    }
    //-----------------------------------------------------------
    // Delete notes from database *******************************
    //-----------------------------------------------------------

    public void deleteNote(ItemViewNote itemViewNote) {
        mRepository.deleteNote(itemViewNote);
    }

    public void deletePhotoNote(ItemViewPhoto itemViewPhoto) {
        mRepository.deletePhotoNote(itemViewPhoto);
    }

    public void deleteCheckBoxNote(ItemViewCheckBox itemViewCheckBox) {
        mRepository.deleteCheckBoxNote(itemViewCheckBox);
    }
    //-----------------------------------------------------------
    // Update notes from database *******************************
    //-----------------------------------------------------------

    public void updateNote(ItemViewNote itemViewNote) {
        mRepository.updateNote(itemViewNote);
    }

    public void updatePhotoNote(ItemViewPhoto itemViewPhoto) {
        mRepository.updatePhotoNote(itemViewPhoto);
    }

    public void updateCheckBoxNote(ItemViewCheckBox itemViewCheckBox) {
        mRepository.updateCheckBoxNote(itemViewCheckBox);
    }

    //----------------------------------------------------------------
    // Delete all notes from database ************************************
    //----------------------------------------------------------------
    public void deleteAllNotes() {
        mRepository.deleteAllNotes();
    }

    public void deleteAllPhotoNotes() {
        mRepository.deleteAllPhotoNotes();
    }

    public void deleteAllCheckBoxNotes() {
        mRepository.deleteAllCheckBoxNotes();
    }

}
