package com.mohammed.adapterviews.data.database;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.mohammed.adapterviews.AddNewNoteActivity;
import com.mohammed.adapterviews.R;
import com.mohammed.adapterviews.data.converter.NotePhotoConverter;
import com.mohammed.adapterviews.data.entity.ItemViewCheckBox;
import com.mohammed.adapterviews.data.entity.ItemViewNote;
import com.mohammed.adapterviews.data.entity.ItemViewPhoto;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {ItemViewNote.class, ItemViewPhoto.class, ItemViewCheckBox.class}, version = 1, exportSchema = false)
@TypeConverters(NotePhotoConverter.class)
public abstract class NoteRoomDatabase extends RoomDatabase {

    public abstract NoteDao noteDao();

    public abstract NotePhotoDao notePhotoDao();

    public abstract NoteCheckBoxDao noteCheckBoxDao();

    private static NoteRoomDatabase INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    public static NoteRoomDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (NoteRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NoteRoomDatabase.class, "note_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }


    /**
     * Initialise database with one check box note.
     */
    private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        NoteCheckBoxDao noteCheckBoxDao;

        public PopulateDbAsync(NoteRoomDatabase db) {
            this.noteCheckBoxDao = db.noteCheckBoxDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ItemViewCheckBox itemViewCheckBox = new ItemViewCheckBox("تعلم كورس جديد على منصة برمج, والقاء نضره على التحديثات الجديدة", Color.argb(100,200,245,255), AddNewNoteActivity.ACTIVITY_NOTE_TYPE_CHECK_NOTE, true);
           if (noteCheckBoxDao.getAnyNote().length < 1){
               noteCheckBoxDao.addNote(itemViewCheckBox);
           }
            return null;
        }
    }

}
