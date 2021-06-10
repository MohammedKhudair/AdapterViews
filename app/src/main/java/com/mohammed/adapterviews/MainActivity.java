package com.mohammed.adapterviews;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.mohammed.adapterviews.NotesDetails.NoteCheckDetailsActivity;
import com.mohammed.adapterviews.NotesDetails.NoteDetailsActivity;
import com.mohammed.adapterviews.NotesDetails.NotePhotoDetailsActivity;
import com.mohammed.adapterviews.adapters.NotesAdapter;
import com.mohammed.adapterviews.data.ItemViewCheckBox;
import com.mohammed.adapterviews.data.ItemViewNote;
import com.mohammed.adapterviews.data.ItemViewPhoto;
import com.mohammed.adapterviews.databinding.ActivityMainBinding;
import com.mohammed.adapterviews.listener.ItemClickListener;
import com.mohammed.adapterviews.listener.ItemLongClickListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private static final int ADD_NOTE_REQUEST = 150;
    private static final int NOTE_DETAILS_ACTIVITY_REQUEST = 160;

    public static final String EXTRA_PHOTO_URI = "EXTRA_PHOTO_URI";
    public static final String EXTRA_NOTE_TEXT = "EXTRA_NOTE_TEXT";
    public static final String EXTRA_COLOR = "EXTRA_COLOR";
    public static final String EXTRA_CHECK_BOX = "EXTRA_CHECK_BOX";
    public static final String EXTRA_POSITION = "EXTRA_POSITION";

    // تعيين نوع افترضي  للملاحضة
    private String noteType = AddNewNoteActivity.ACTIVITY_NOTE_TYPE_NOTE;

    ArrayList<ItemViewNote> mItems;
    NotesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // تهيئة البيانات
        mItems = ItemViewNote.getDefaultList();

        mAdapter = new NotesAdapter(mItems, new ItemClickListener() {
            @Override
            public void onClickItem(int position) {
                noteType = mItems.get(position).getNoteType();
                openDetails(position);
            }
        }, new ItemLongClickListener() {
            @Override
            public void onLongClickItem(int position) {
                deleteNote(position);
            }
        });
        binding.recyclerViewPhotos.setAdapter(mAdapter);
        binding.recyclerViewPhotos.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));


        binding.floatingButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewNote();
            }
        });
    }

    // اضافة ملاحضه جديدة
    private void addNewNote() {
        Intent intent = new Intent(MainActivity.this, AddNewNoteActivity.class);
        startActivityForResult(intent, ADD_NOTE_REQUEST);
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
            } else Toast.makeText(this, "لم يتم اختيار اي بيانات", Toast.LENGTH_SHORT).show();

            //هنا نستلم البيانات من النشاط المراد تعديله
        } else if (requestCode == NOTE_DETAILS_ACTIVITY_REQUEST) {
            if (resultCode == RESULT_OK && data != null) {

                Uri photoUri = data.getParcelableExtra(Constants.EXTRA_PHOTO_URI);
                String noteText = data.getStringExtra(Constants.NOTE_TEXT);
                int noteColor = data.getIntExtra(Constants.NOTE_COLOR, 0);
                int position = data.getIntExtra(Constants.ITEM_POSITION, 0);
                boolean checkBox = data.getBooleanExtra(Constants.NOTE_CHECK_BOX, false);
                // تعديل الملاحضة التي تم الضغظ عليها
                editNote(photoUri, noteText, noteColor, checkBox, position);
            }
        }
    }

    // تعديل الملاحضة التي تم الضغظ عليها
    private void editNote(Uri photoUri, String noteText, int noteColor, boolean checkBox, int position) {
        switch (noteType) {
            case AddNewNoteActivity.ACTIVITY_NOTE_TYPE_PHOTO_NOTE: {
                mItems.remove(position);
                mItems.add(position, new ItemViewPhoto(photoUri, noteText, noteColor, noteType));
                mAdapter.notifyItemChanged(position);
                break;
            }
            case AddNewNoteActivity.ACTIVITY_NOTE_TYPE_CHECK_NOTE: {
                mItems.remove(position);
                mItems.add(position, new ItemViewCheckBox(noteText, noteColor, noteType, checkBox));
                mAdapter.notifyItemChanged(position);
                break;
            }
            case AddNewNoteActivity.ACTIVITY_NOTE_TYPE_NOTE: {
                mItems.remove(position);
                mItems.add(position, new ItemViewNote(noteText, noteColor, noteType));
                mAdapter.notifyItemChanged(position);
                break;
            }
        }
    }

    // وضع نتائج البيانات المعادة من النشاط
    private void putDataResult(Uri photoUri, String noteText, int noteColor, boolean checkBox, String noteType) {
        this.noteType = noteType;
        //هنا نضيف البيانات على حسب نوع النشاط
        switch (noteType) {
            case AddNewNoteActivity.ACTIVITY_NOTE_TYPE_PHOTO_NOTE:
                mItems.add(new ItemViewPhoto(photoUri, noteText, noteColor, noteType));
                mAdapter.notifyItemInserted(mItems.size() - 1);
                break;
            case AddNewNoteActivity.ACTIVITY_NOTE_TYPE_CHECK_NOTE:
                mItems.add(new ItemViewCheckBox(noteText, noteColor, noteType, checkBox));
                mAdapter.notifyItemInserted(mItems.size() - 1);
                break;
            case AddNewNoteActivity.ACTIVITY_NOTE_TYPE_NOTE:
                mItems.add(new ItemViewNote(noteText, noteColor, noteType));
                mAdapter.notifyItemInserted(mItems.size() - 1);
                break;
        }

    }

    // مسح الملاحضة
    private void deleteNote(int position) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setMessage(R.string.delete_confirmation)
                .setTitle("حذف")
                .setIcon(R.drawable.ic_delete)
                .setPositiveButton("حذف", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mItems.remove(position);
                        mAdapter.notifyItemRemoved(position);
                    }
                })
                .setNegativeButton("الغاء", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create();
        alertDialog.show();
    }

    // فتح تفاصيل الملاحضة
    private void openDetails(int position) {
        Intent intent;
        switch (noteType) {
            case AddNewNoteActivity.ACTIVITY_NOTE_TYPE_PHOTO_NOTE:
                intent = new Intent(MainActivity.this, NotePhotoDetailsActivity.class);
                intent.putExtra(EXTRA_PHOTO_URI, ((ItemViewPhoto) mItems.get(position)).getImageUri());
                intent.putExtra(EXTRA_NOTE_TEXT, mItems.get(position).getNoteText());
                intent.putExtra(EXTRA_COLOR, mItems.get(position).getNoteColor());
                intent.putExtra(EXTRA_POSITION, position);
                startActivityForResult(intent, NOTE_DETAILS_ACTIVITY_REQUEST);

                break;
            case AddNewNoteActivity.ACTIVITY_NOTE_TYPE_CHECK_NOTE:
                intent = new Intent(MainActivity.this, NoteCheckDetailsActivity.class);
                intent.putExtra(EXTRA_CHECK_BOX, ((ItemViewCheckBox) mItems.get(position)).isChecked());
                intent.putExtra(EXTRA_NOTE_TEXT, mItems.get(position).getNoteText());
                intent.putExtra(EXTRA_COLOR, mItems.get(position).getNoteColor());
                intent.putExtra(EXTRA_POSITION, position);
                startActivityForResult(intent, NOTE_DETAILS_ACTIVITY_REQUEST);

                break;
            case AddNewNoteActivity.ACTIVITY_NOTE_TYPE_NOTE:
                intent = new Intent(MainActivity.this, NoteDetailsActivity.class);
                intent.putExtra(EXTRA_NOTE_TEXT, mItems.get(position).getNoteText());
                intent.putExtra(EXTRA_COLOR, mItems.get(position).getNoteColor());
                intent.putExtra(EXTRA_POSITION, position);
                startActivityForResult(intent, NOTE_DETAILS_ACTIVITY_REQUEST);
                break;
        }

    }

}