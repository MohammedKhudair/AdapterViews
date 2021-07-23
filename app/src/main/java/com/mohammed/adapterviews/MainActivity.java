package com.mohammed.adapterviews;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.mohammed.adapterviews.NotesDetails.NoteCheckDetailsActivity;
import com.mohammed.adapterviews.NotesDetails.NoteDetailsActivity;
import com.mohammed.adapterviews.NotesDetails.NotePhotoDetailsActivity;
import com.mohammed.adapterviews.adapters.NotesAdapter;
import com.mohammed.adapterviews.data.entity.ItemViewCheckBox;
import com.mohammed.adapterviews.data.entity.ItemViewNote;
import com.mohammed.adapterviews.data.entity.ItemViewPhoto;
import com.mohammed.adapterviews.databinding.ActivityMainBinding;
import com.mohammed.adapterviews.listener.CheckBoxListener;
import com.mohammed.adapterviews.listener.ItemClickListener;
import com.mohammed.adapterviews.listener.ItemLongClickListener;
import com.mohammed.adapterviews.viewModel.NoteViewModel;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private static final int ADD_NOTE_REQUEST = 150;
    private static final int NOTE_DETAILS_ACTIVITY_REQUEST = 160;

    public static final String EXTRA_PHOTO_URI = "EXTRA_PHOTO_URI";
    public static final String EXTRA_NOTE_TEXT = "EXTRA_NOTE_TEXT";
    public static final String EXTRA_COLOR = "EXTRA_COLOR";
    public static final String EXTRA_CHECK_BOX = "EXTRA_CHECK_BOX";
    public static final String EXTRA_NOTE_ID = "EXTRA_NOTE_ID";

    List<ItemViewNote> mAllItems = new ArrayList<>();

    List<ItemViewNote> mNoteItems = new ArrayList<>();
    List<ItemViewPhoto> mPhotoNoteItems = new ArrayList<>();
    List<ItemViewCheckBox> mCheckBoxNoteItems = new ArrayList<>();

    NotesAdapter mAdapter;
    public NoteViewModel mNoteViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Initialise ViewModel object
        mNoteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);


        mAdapter = new NotesAdapter(this, mAllItems, new ItemClickListener() {
            @Override
            public void onClickItem(int position) {
                ItemViewNote itemViewNote = mAdapter.getNoteAtPositions(position);
                openDetails(itemViewNote);
            }
        }, new ItemLongClickListener() {
            @Override
            public void onLongClickItem(int position) {
                ItemViewNote itemViewNote = mAdapter.getNoteAtPositions(position);
                deleteNote(itemViewNote);
            }
        }, new CheckBoxListener() {
            @Override
            public void onCheckBox(int position) {
                ItemViewCheckBox itemViewCheckBox = (ItemViewCheckBox) mAdapter.getNoteAtPositions(position);
                itemViewCheckBox.setChecked(!itemViewCheckBox.isChecked());
                mNoteViewModel.updateCheckBoxNote(itemViewCheckBox);
            }
        });
        binding.recyclerViewPhotos.setAdapter(mAdapter);
        binding.recyclerViewPhotos.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        //---------------------------------------------------------------
        // Observers. To observe all changes on the database*************
        //---------------------------------------------------------------
        mNoteViewModel.getAllNotes().observe(this, itemViewNotes -> {
            mNoteItems.clear();
            mNoteItems.addAll(itemViewNotes);
            updateNoteItems();
            mAdapter.notifyDataSetChanged();
        });
        mNoteViewModel.getAllPhotoNotes().observe(this, itemViewPhotos -> {
            mPhotoNoteItems.clear();
            mPhotoNoteItems.addAll(itemViewPhotos);
            updateNoteItems();
            mAdapter.notifyDataSetChanged();
        });
        mNoteViewModel.getAllCheckNotes().observe(this, itemViewCheckBoxes -> {
            mCheckBoxNoteItems.clear();
            mCheckBoxNoteItems.addAll(itemViewCheckBoxes);
            updateNoteItems();
            mAdapter.notifyDataSetChanged();
        });


        // اضافة ملاحضه جديدة
        binding.floatingButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddNewNoteActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });

    }

    // نجمع كل العناصر من باقي الانواع في قائمه واحده
    private void updateNoteItems() {
        mAllItems.clear();
        mAllItems.addAll(mNoteItems);
        mAllItems.addAll(mPhotoNoteItems);
        mAllItems.addAll(mCheckBoxNoteItems);
    }

    // استلام النتائج من الانشطه الاخرى
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_NOTE_REQUEST) {
            if (resultCode == Activity.RESULT_OK && data != null) {

                Uri photoUri = data.getParcelableExtra(Constants.EXTRA_PHOTO_URI);
                String noteText = data.getStringExtra(Constants.NOTE_TEXT);
                int noteColor = data.getIntExtra(Constants.NOTE_COLOR, 0);
                String noteType = data.getStringExtra(Constants.NOTE_TYPE);
                boolean checkBox = data.getBooleanExtra(Constants.NOTE_CHECK_BOX, false);
                // وضع نتائج البيانات المعادة من النشاط
                putDataResult(photoUri, noteText, noteColor, checkBox, noteType);
            } else Toast.makeText(this, R.string.no_data_selected, Toast.LENGTH_SHORT).show();

            //هنا نستلم البيانات من النشاط المراد تعديله
        } else if (requestCode == NOTE_DETAILS_ACTIVITY_REQUEST) {
            if (resultCode == RESULT_OK && data != null) {

                Uri photoUri = data.getParcelableExtra(Constants.EXTRA_PHOTO_URI);
                String noteText = data.getStringExtra(Constants.NOTE_TEXT);
                int noteColor = data.getIntExtra(Constants.NOTE_COLOR, 0);
                boolean checkBox = data.getBooleanExtra(Constants.NOTE_CHECK_BOX, false);
                String noteType = data.getStringExtra(Constants.NOTE_TYPE);
                int noteId = data.getIntExtra(Constants.NOTE_ID, -1);
                // تعديل الملاحضة التي تم الضغظ عليها
                editNote(photoUri, noteText, noteColor, checkBox, noteType, noteId);
            }
        }
    }

    // تعديل الملاحضة التي تم الضغظ عليها
    private void editNote(Uri photoUri, String noteText, int noteColor, boolean checkBox, String noteType, int noteId) {
        switch (noteType) {
            case AddNewNoteActivity.ACTIVITY_NOTE_TYPE_PHOTO_NOTE: {
                ItemViewPhoto itemViewPhoto = new ItemViewPhoto(photoUri, noteText, noteColor, noteType);
                itemViewPhoto.setId(noteId);
                if (noteId != -1) {
                    mNoteViewModel.updatePhotoNote(itemViewPhoto);
                } else
                    Toast.makeText(this, "Unable to update", Toast.LENGTH_LONG).show();
                break;
            }
            case AddNewNoteActivity.ACTIVITY_NOTE_TYPE_CHECK_NOTE: {
                ItemViewCheckBox itemViewCheckBox = new ItemViewCheckBox(noteText, noteColor, noteType, checkBox);
                itemViewCheckBox.setId(noteId);
                if (noteId != -1) {
                    mNoteViewModel.updateCheckBoxNote(itemViewCheckBox);
                } else
                    Toast.makeText(this, "Unable to update", Toast.LENGTH_LONG).show();
                break;
            }
            case AddNewNoteActivity.ACTIVITY_NOTE_TYPE_NOTE: {
                ItemViewNote itemViewNote = new ItemViewNote(noteText, noteColor, noteType);
                itemViewNote.setId(noteId);
                if (noteId != -1) {
                    mNoteViewModel.updateNote(itemViewNote);
                } else
                    Toast.makeText(this, "Unable to update", Toast.LENGTH_LONG).show();

                break;
            }
        }
    }

    // وضع نتائج البيانات المعادة من النشاط
    private void putDataResult(Uri photoUri, String noteText, int noteColor, boolean checkBox, String noteType) {
        //هنا نضيف البيانات على حسب نوع النشاط
        switch (noteType) {
            case AddNewNoteActivity.ACTIVITY_NOTE_TYPE_PHOTO_NOTE:
                mNoteViewModel.addPhotoNote(new ItemViewPhoto(photoUri, noteText, noteColor, noteType));
                break;
            case AddNewNoteActivity.ACTIVITY_NOTE_TYPE_CHECK_NOTE:
                mNoteViewModel.addCheckBoxNote(new ItemViewCheckBox(noteText, noteColor, noteType, checkBox));
                break;
            case AddNewNoteActivity.ACTIVITY_NOTE_TYPE_NOTE:
                mNoteViewModel.addNote(new ItemViewNote(noteText, noteColor, noteType));
                break;
        }

    }

    // مسح الملاحضة
    private void deleteNote(ItemViewNote itemViewNote) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setMessage(R.string.delete_confirmation)
                .setTitle(R.string.delete)
                .setIcon(R.drawable.ic_delete)
                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        switch (itemViewNote.getNoteType()) {
                            case AddNewNoteActivity.ACTIVITY_NOTE_TYPE_PHOTO_NOTE:
                                ItemViewPhoto itemViewPhoto = (ItemViewPhoto) itemViewNote;
                                mNoteViewModel.deletePhotoNote(itemViewPhoto);
                                break;
                            case AddNewNoteActivity.ACTIVITY_NOTE_TYPE_CHECK_NOTE:
                                ItemViewCheckBox itemViewCheckBox = (ItemViewCheckBox) itemViewNote;
                                mNoteViewModel.deleteCheckBoxNote(itemViewCheckBox);
                                break;
                            case AddNewNoteActivity.ACTIVITY_NOTE_TYPE_NOTE:
                                mNoteViewModel.deleteNote(itemViewNote);
                                break;
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create();
        alertDialog.show();
    }

    // فتح تفاصيل الملاحضة
    private void openDetails(ItemViewNote itemViewNote) {
        Intent intent;
        switch (itemViewNote.getNoteType()) {
            case AddNewNoteActivity.ACTIVITY_NOTE_TYPE_PHOTO_NOTE:
                ItemViewPhoto itemViewPhoto = ((ItemViewPhoto) itemViewNote);

                intent = new Intent(MainActivity.this, NotePhotoDetailsActivity.class);
                intent.putExtra(EXTRA_PHOTO_URI, itemViewPhoto.getImageUri());
                intent.putExtra(EXTRA_NOTE_TEXT, itemViewPhoto.getNoteText());
                intent.putExtra(EXTRA_COLOR, itemViewPhoto.getNoteColor());
                intent.putExtra(EXTRA_NOTE_ID, itemViewPhoto.getId());
                startActivityForResult(intent, NOTE_DETAILS_ACTIVITY_REQUEST);

                break;
            case AddNewNoteActivity.ACTIVITY_NOTE_TYPE_CHECK_NOTE:
                ItemViewCheckBox itemViewCheckBox = ((ItemViewCheckBox) itemViewNote);

                intent = new Intent(MainActivity.this, NoteCheckDetailsActivity.class);
                intent.putExtra(EXTRA_CHECK_BOX, itemViewCheckBox.isChecked());
                intent.putExtra(EXTRA_NOTE_TEXT, itemViewCheckBox.getNoteText());
                intent.putExtra(EXTRA_COLOR, itemViewCheckBox.getNoteColor());
                intent.putExtra(EXTRA_NOTE_ID, itemViewCheckBox.getId());
                startActivityForResult(intent, NOTE_DETAILS_ACTIVITY_REQUEST);

                break;
            case AddNewNoteActivity.ACTIVITY_NOTE_TYPE_NOTE:
                intent = new Intent(MainActivity.this, NoteDetailsActivity.class);
                intent.putExtra(EXTRA_NOTE_TEXT, itemViewNote.getNoteText());
                intent.putExtra(EXTRA_COLOR, itemViewNote.getNoteColor());
                intent.putExtra(EXTRA_NOTE_ID, itemViewNote.getId());
                startActivityForResult(intent, NOTE_DETAILS_ACTIVITY_REQUEST);
                break;
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_view_clear,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_ClearAll){
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setMessage(R.string.delete_all)
                    .setTitle(R.string.delete)
                    .setIcon(R.drawable.ic_delete)
                    .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // Delete all Notes
                            mNoteViewModel.deleteAllNotes();
                            mNoteViewModel.deleteAllPhotoNotes();
                            mNoteViewModel.deleteAllCheckBoxNotes();
                            Toast.makeText(MainActivity.this, R.string.deleted_done, Toast.LENGTH_LONG).show();
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).create();
            alertDialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}