package com.mohammed.adapterviews.NotesDetails;

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

import com.mohammed.adapterviews.Constants;
import com.mohammed.adapterviews.MainActivity;
import com.mohammed.adapterviews.R;
import com.mohammed.adapterviews.databinding.ActivityNotePhotoDetailsBinding;

public class NotePhotoDetailsActivity extends AppCompatActivity {
    ActivityNotePhotoDetailsBinding binding;
    private static final int RED_PHOTO_RequestCode = 130;
    private static final int PICK_IMAGE = 120;
    Uri uri;
    String noteText;
    int noteColor;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotePhotoDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // الحصول علي البيانات المرسلة من نشاط
        Intent intent = getIntent();
        if (intent != null) {
            uri = intent.getParcelableExtra(MainActivity.EXTRA_PHOTO_URI);
            binding.photoImageView.setImageURI(uri);
            noteText = intent.getStringExtra(MainActivity.EXTRA_NOTE_TEXT);
            binding.photoNoteEditText.setText(noteText);
            noteColor = intent.getIntExtra(MainActivity.EXTRA_COLOR, 0);
            binding.PhotoRelativeLayout.setBackgroundColor(noteColor);
            position = intent.getIntExtra(MainActivity.EXTRA_POSITION, 0);
        }

        binding.photoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPhoto();
            }
        });
        binding.noteEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editNote();
            }
        });

    }

    // تاكيد وارسال البيانات للنشاط
    private void editNote() {
        String noteText = binding.photoNoteEditText.getText().toString();
        if (!noteText.equals("") && uri != null) {
            Intent intent = new Intent();
            intent.putExtra(Constants.EXTRA_PHOTO_URI, uri);
            intent.putExtra(Constants.NOTE_TEXT, noteText);
            intent.putExtra(Constants.NOTE_COLOR, noteColor);
            intent.putExtra(Constants.ITEM_POSITION, position);
            setResult(RESULT_OK, intent);
            finish();
        } else
            Toast.makeText(this, "تأكد من ادخال البيانات", Toast.LENGTH_SHORT).show();

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
                uri = data.getData();
                getContentResolver().takePersistableUriPermission(data.getData(), Intent.FLAG_GRANT_READ_URI_PERMISSION);
            } else
                Toast.makeText(this, R.string.failed_to_get_Image, Toast.LENGTH_SHORT).show();
        }
    }
}