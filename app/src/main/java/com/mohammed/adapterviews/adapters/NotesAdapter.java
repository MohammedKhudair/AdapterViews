package com.mohammed.adapterviews.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mohammed.adapterviews.R;
import com.mohammed.adapterviews.data.ItemViewCheckBox;
import com.mohammed.adapterviews.data.ItemViewNote;
import com.mohammed.adapterviews.data.ItemViewPhoto;
import com.mohammed.adapterviews.listener.ItemClickListener;
import com.mohammed.adapterviews.listener.ItemLongClickListener;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM_VIEW_TYPE_PHOTO = 0;
    private static final int ITEM_VIEW_TYPE_CHECK_BOX = 1;
    private static final int ITEM_VIEW_TYPE_NOTE = 2;

    ArrayList<ItemViewNote> mItems;

    ItemClickListener itemClickListener;
    ItemLongClickListener itemLongClickListener;

    public NotesAdapter(ArrayList<ItemViewNote> mItems, ItemClickListener itemClickListener, ItemLongClickListener itemLongClickListener) {
        this.mItems = mItems;
        this.itemClickListener = itemClickListener;
        this.itemLongClickListener = itemLongClickListener;
    }
  // نقوم بعمل  inflate على حسب نوع العنصر
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == ITEM_VIEW_TYPE_PHOTO) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note_photo, parent, false);
            return new PhotoViewHolder(view, itemClickListener, itemLongClickListener);
        } else if (viewType == ITEM_VIEW_TYPE_CHECK_BOX) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note_check, parent, false);
            return new CheckBoxViewHolder(view, itemClickListener, itemLongClickListener);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
            return new NoteViewHolder(view, itemClickListener, itemLongClickListener);
        }
    }

    // نقوم بوضع البيانات على حسب نوع العنصر
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemViewNote itemViewNote = mItems.get(position);

        switch (holder.getItemViewType()) {
            case ITEM_VIEW_TYPE_PHOTO: {
                PhotoViewHolder photoViewHolder = (PhotoViewHolder) holder;
                photoViewHolder.photoImageView.setImageURI(((ItemViewPhoto) itemViewNote).getImageUri());
                photoViewHolder.textPhoto.setText(itemViewNote.getNoteText());
                photoViewHolder.linearLayoutPhoto.setBackgroundColor(itemViewNote.getNoteColor());
                photoViewHolder.position = position;
                break;
            }
            case ITEM_VIEW_TYPE_CHECK_BOX: {
                CheckBoxViewHolder checkBoxViewHolder = (CheckBoxViewHolder) holder;
                checkBoxViewHolder.checkBox.setChecked(((ItemViewCheckBox) itemViewNote).isChecked());
                checkBoxViewHolder.textCheck.setText(itemViewNote.getNoteText());
                checkBoxViewHolder.linearLayoutCheck.setBackgroundColor(itemViewNote.getNoteColor());
                checkBoxViewHolder.position = position;
                break;
            }
            case ITEM_VIEW_TYPE_NOTE: {
                NoteViewHolder noteViewHolder = (NoteViewHolder) holder;
                noteViewHolder.textNote.setText(itemViewNote.getNoteText());
                noteViewHolder.linearLayoutNote.setBackgroundColor(itemViewNote.getNoteColor());
                noteViewHolder.position = position;
                break;
            }
        }

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

   //Note خاص بعنصر ال ViewHolder
    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayoutNote;
        TextView textNote;
        int position;

        public NoteViewHolder(@NonNull View itemView, ItemClickListener itemClickListener, ItemLongClickListener itemLongClickListener) {
            super(itemView);
            linearLayoutNote = itemView.findViewById(R.id.LinearLayoutNote);
            textNote = itemView.findViewById(R.id.textView_Note);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onClickItem(position);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    itemLongClickListener.onLongClickItem(position);
                    return true;
                }
            });
        }
    }

    //CheckBox خاص بعنصر ال ViewHolder
    public static class CheckBoxViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayoutCheck;
        TextView textCheck;
        CheckBox checkBox;
        int position;

        public CheckBoxViewHolder(@NonNull View itemView, ItemClickListener itemClickListener, ItemLongClickListener itemLongClickListener) {
            super(itemView);
            linearLayoutCheck = itemView.findViewById(R.id.LinearLayoutCheckBox);
            textCheck = itemView.findViewById(R.id.textView_Checkbox);
            checkBox = itemView.findViewById(R.id.checkBox);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onClickItem(position);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    itemLongClickListener.onLongClickItem(position);
                    return true;
                }
            });
        }
    }

    //Photo خاص بعنصر ال ViewHolder
    public static class PhotoViewHolder extends RecyclerView.ViewHolder {
        ImageView photoImageView;
        LinearLayout linearLayoutPhoto;
        TextView textPhoto;
        int position;

        public PhotoViewHolder(@NonNull View itemView, ItemClickListener itemClickListener, ItemLongClickListener itemLongClickListener) {
            super(itemView);
            photoImageView = itemView.findViewById(R.id.item_photo_imageView);
            linearLayoutPhoto = itemView.findViewById(R.id.LinearLayoutPhoto);
            textPhoto = itemView.findViewById(R.id.textView_Photo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onClickItem(position);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    itemLongClickListener.onLongClickItem(position);
                    return true;
                }
            });
        }
    }

    //  الحصول على نوع العنصر
    @Override
    public int getItemViewType(int position) {
        if (mItems.get(position) instanceof ItemViewPhoto) {
            return ITEM_VIEW_TYPE_PHOTO;
        } else if (mItems.get(position) instanceof ItemViewCheckBox) {
            return ITEM_VIEW_TYPE_CHECK_BOX;
        } else
            return ITEM_VIEW_TYPE_NOTE;
    }
}
