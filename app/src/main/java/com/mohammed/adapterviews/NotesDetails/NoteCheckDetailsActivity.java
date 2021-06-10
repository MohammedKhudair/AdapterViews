package com.mohammed.adapterviews.NotesDetails;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.mohammed.adapterviews.Constants;
import com.mohammed.adapterviews.MainActivity;
import com.mohammed.adapterviews.R;
import com.mohammed.adapterviews.databinding.ActivityNoteCheckDetailsBinding;

public class NoteCheckDetailsActivity extends AppCompatActivity {
    ActivityNoteCheckDetailsBinding binding;
    boolean checkBox;
    String noteText;
    int noteColor;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNoteCheckDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

     // الحصول علي البيانات المرسلة من نشاط
        Intent intent = getIntent();
        if (intent != null) {
            noteText = intent.getStringExtra(MainActivity.EXTRA_NOTE_TEXT);
            binding.checkNoteEditText.setText(noteText);
            checkBox = intent.getBooleanExtra(MainActivity.EXTRA_CHECK_BOX, false);
            binding.checkNoteCheckBox.setChecked(checkBox);
            noteColor = intent.getIntExtra(MainActivity.EXTRA_COLOR, 0);
            binding.CheckBoxConstraintLayout.setBackgroundColor(noteColor);
            position = intent.getIntExtra(MainActivity.EXTRA_POSITION, 0);

        }
        binding.noteEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editNote();
            }
        });
    }

    // تاكيد وارسال البيانات للنشاط
    private void editNote() {
        String noteText = binding.checkNoteEditText.getText().toString();
        if (!noteText.equals("")) {
            Intent intent = new Intent();
            intent.putExtra(Constants.NOTE_TEXT, noteText);
            intent.putExtra(Constants.NOTE_CHECK_BOX, checkBox);
            intent.putExtra(Constants.NOTE_COLOR, noteColor);
            intent.putExtra(Constants.ITEM_POSITION, position);
            setResult(RESULT_OK, intent);
            finish();
        } else
            Toast.makeText(this, "تأكد من ادخال البيانات", Toast.LENGTH_SHORT).show();
    }
}