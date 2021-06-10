package com.mohammed.adapterviews;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.mohammed.adapterviews.databinding.ActivityAddNewNoteBinding;

public class AddNewNoteActivity extends AppCompatActivity {
    private ActivityAddNewNoteBinding binding;

    private static final int RED_PHOTO_RequestCode = 130;
    private static final int PICK_IMAGE = 120;
    // ثوابت نوع الملاحضة
    public static final String ACTIVITY_NOTE_TYPE_NOTE = "NOTE";
    public static final String ACTIVITY_NOTE_TYPE_CHECK_NOTE = "CHECK_NOTE";
    public static final String ACTIVITY_NOTE_TYPE_PHOTO_NOTE = "PHOTO_NOTE";
    // متغير للأحتفاض بلون الملاحضه
    private int noteColor;
    // متغير للأحتفاض بنوع الملاحضه
    private String noteType;
    // حفض بيانات ال Uri الخاص بصورة
    Uri mSelectedPhotoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNewNoteBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // القيمة الافتراضية للون
        noteColor = getResources().getColor(R.color.blue);
        //القيمة الفتراضية لنوع الملاحضة
        noteType = ACTIVITY_NOTE_TYPE_PHOTO_NOTE;


        binding.photoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPhoto();
            }
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitData();
            }
        });

    }

    // تعين نوع الملاحضة
    public void setNoteType(View view) {
        if (view.getId() == R.id.radioButtonNote) {
            binding.cardViewNote.setVisibility(View.VISIBLE);
            binding.cardViewCheckNote.setVisibility(View.GONE);
            binding.cardViewPhoto.setVisibility(View.GONE);
            noteType = ACTIVITY_NOTE_TYPE_NOTE;


        } else if (view.getId() == R.id.radioButtonCheckNote) {
            binding.cardViewNote.setVisibility(View.GONE);
            binding.cardViewCheckNote.setVisibility(View.VISIBLE);
            binding.cardViewPhoto.setVisibility(View.GONE);
            noteType = ACTIVITY_NOTE_TYPE_CHECK_NOTE;

        } else if (view.getId() == R.id.radioButtonPhoto) {
            binding.cardViewNote.setVisibility(View.GONE);
            binding.cardViewCheckNote.setVisibility(View.GONE);
            binding.cardViewPhoto.setVisibility(View.VISIBLE);
            noteType = ACTIVITY_NOTE_TYPE_PHOTO_NOTE;
        }
    }

    // تعيين لون الملاحضة
    public void setNoteColorBackground(View view) {
        if (view.getId() == R.id.radioButtonBlue) {
            binding.relativeLayoutNote.setBackgroundColor(getResources().getColor(R.color.blue));
            binding.relativeLayoutCheckNote.setBackgroundColor(getResources().getColor(R.color.blue));
            binding.relativeLayoutPhoto.setBackgroundColor(getResources().getColor(R.color.blue));
            noteColor = getResources().getColor(R.color.blue);

        } else if (view.getId() == R.id.radioButtonYellow) {
            binding.relativeLayoutNote.setBackgroundColor(getResources().getColor(R.color.yellow));
            binding.relativeLayoutCheckNote.setBackgroundColor(getResources().getColor(R.color.yellow));
            binding.relativeLayoutPhoto.setBackgroundColor(getResources().getColor(R.color.yellow));
            noteColor = getResources().getColor(R.color.yellow);

        } else if (view.getId() == R.id.radioButtonRed) {
            binding.relativeLayoutNote.setBackgroundColor(getResources().getColor(R.color.red));
            binding.relativeLayoutCheckNote.setBackgroundColor(getResources().getColor(R.color.red));
            binding.relativeLayoutPhoto.setBackgroundColor(getResources().getColor(R.color.red));
            noteColor = getResources().getColor(R.color.red);

        }

    }

    // التاكد من طلب اذن الصلاحيه
    private void selectPhoto() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}
                    , RED_PHOTO_RequestCode);
        } else {
            pickPhotoIntent();
        }

    }

    // التاكد من نتائج اذن الطلب
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RED_PHOTO_RequestCode) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickPhotoIntent();
            } else {
                Toast.makeText(this, R.string.permission_needed, Toast.LENGTH_LONG).show();
            }
        }

    }

    // اطلاق النيه لاختيار صورة من الجهاز
    private void pickPhotoIntent() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.choosePhoto)), PICK_IMAGE);

    }

    // التاكد من البيانات المرجعه
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            if (resultCode == RESULT_OK && data != null && data.getData() != null) {
                binding.photoImageView.setImageURI(data.getData());
                mSelectedPhotoUri = data.getData();
                getContentResolver().takePersistableUriPermission(data.getData(), Intent.FLAG_GRANT_READ_URI_PERMISSION);
            } else
                Toast.makeText(this, R.string.failed_to_get_Image, Toast.LENGTH_SHORT).show();
        }
    }

    //  تاكيد وارسال البيانات
    private void submitData() {
        String noteEditText = binding.noteEditText.getText().toString();
        String checkEditText = binding.checkNoteEditText.getText().toString();
        String photoEditText = binding.photoNoteEditText.getText().toString();
        boolean checkBook = binding.checkNoteCheckBox.isChecked();
        Intent data = new Intent();

        if (binding.radioButtonNote.isChecked()) {
            if (!noteEditText.equals("")) {
                data.putExtra(Constants.NOTE_TEXT, noteEditText);
                data.putExtra(Constants.NOTE_TYPE, noteType);
                data.putExtra(Constants.NOTE_COLOR, noteColor);
                setResult(RESULT_OK, data);
                finish();
            } else
                Toast.makeText(this, "تاكد من كتابة ملاحضة", Toast.LENGTH_SHORT).show();

        } else if (binding.radioButtonCheckNote.isChecked()) {
            if (!checkEditText.equals("")) {
                data.putExtra(Constants.NOTE_TEXT, checkEditText);
                data.putExtra(Constants.NOTE_CHECK_BOX, checkBook);
                data.putExtra(Constants.NOTE_TYPE, noteType);
                data.putExtra(Constants.NOTE_COLOR, noteColor);
                setResult(RESULT_OK, data);
                finish();
            } else
                Toast.makeText(this, "تاكد من كتابة ملاحضة", Toast.LENGTH_SHORT).show();


        } else if (binding.radioButtonPhoto.isChecked()) {
            if (!photoEditText.equals("") && mSelectedPhotoUri != null) {
                data.putExtra(Constants.NOTE_TEXT, photoEditText);
                data.putExtra(Constants.NOTE_TYPE, noteType);
                data.putExtra(Constants.NOTE_COLOR, noteColor);
                data.putExtra(Constants.EXTRA_PHOTO_URI, mSelectedPhotoUri);
                setResult(RESULT_OK, data);
                finish();
            } else
                Toast.makeText(this, "تاكد من كتابة ملاحضة واستيراد صورة", Toast.LENGTH_SHORT).show();

        }

    }


}


