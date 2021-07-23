package com.mohammed.adapterviews.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.mohammed.adapterviews.BR;
import com.mohammed.adapterviews.R;
import com.mohammed.adapterviews.data.entity.ItemViewCheckBox;
import com.mohammed.adapterviews.data.entity.ItemViewNote;
import com.mohammed.adapterviews.data.entity.ItemViewPhoto;
import com.mohammed.adapterviews.databinding.ItemNoteBinding;
import com.mohammed.adapterviews.databinding.ItemNoteCheckBinding;
import com.mohammed.adapterviews.databinding.ItemNotePhotoBinding;
import com.mohammed.adapterviews.listener.CheckBoxListener;
import com.mohammed.adapterviews.listener.ItemClickListener;
import com.mohammed.adapterviews.listener.ItemLongClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM_VIEW_TYPE_PHOTO = 0;
    private static final int ITEM_VIEW_TYPE_CHECK_BOX = 1;
    private static final int ITEM_VIEW_TYPE_NOTE = 2;

    private Context mContext;
    private List<ItemViewNote> mItems;

    ItemClickListener itemClickListener;
    ItemLongClickListener itemLongClickListener;
    CheckBoxListener checkBoxListener;

    public NotesAdapter(Context context, List<ItemViewNote> mItems, ItemClickListener itemClickListener, ItemLongClickListener itemLongClickListener, CheckBoxListener checkBoxListener) {
        this.mContext = context;
        this.mItems = mItems;
        this.itemClickListener = itemClickListener;
        this.itemLongClickListener = itemLongClickListener;
        this.checkBoxListener = checkBoxListener;
    }

    // نقوم بعمل  inflate على حسب نوع العنصر
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_VIEW_TYPE_PHOTO) {
            ItemNotePhotoBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_note_photo, parent, false);
            return new PhotoViewHolder(binding, itemClickListener, itemLongClickListener);
        } else if (viewType == ITEM_VIEW_TYPE_CHECK_BOX) {
            ItemNoteCheckBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_note_check, parent, false);
            return new CheckBoxViewHolder(binding, itemClickListener, itemLongClickListener, checkBoxListener);
        } else {
            ItemNoteBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_note, parent, false);
            return new NoteViewHolder(binding, itemClickListener, itemLongClickListener);
        }
    }

    // نقوم بوضع البيانات على حسب نوع العنصر
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemViewNote itemViewNote = mItems.get(position);

        switch (holder.getItemViewType()) {
            case ITEM_VIEW_TYPE_PHOTO: {
                PhotoViewHolder photoViewHolder = (PhotoViewHolder) holder;
                ItemViewPhoto itemViewPhoto = (ItemViewPhoto) itemViewNote;
                photoViewHolder.bind(itemViewPhoto, position);
                break;
            }
            case ITEM_VIEW_TYPE_CHECK_BOX: {
                CheckBoxViewHolder checkBoxViewHolder = (CheckBoxViewHolder) holder;
                ItemViewCheckBox itemViewCheckBox = (ItemViewCheckBox) itemViewNote;
                checkBoxViewHolder.bind(itemViewCheckBox, position);
                break;
            }
            case ITEM_VIEW_TYPE_NOTE: {
                NoteViewHolder noteViewHolder = (NoteViewHolder) holder;
                noteViewHolder.bind(itemViewNote, position);
                break;
            }
        }

    }

    @Override
    public int getItemCount() {
        if (mItems == null) {
            return 0;
        } else
            return mItems.size();
    }

    //Note خاص بعنصر ال ViewHolder
    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        int position;
        ItemNoteBinding binding;

        public NoteViewHolder(@NonNull ItemNoteBinding binding, ItemClickListener itemClickListener, ItemLongClickListener itemLongClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            binding.LinearLayoutNote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onClickItem(position);
                }
            });
            binding.LinearLayoutNote.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    itemLongClickListener.onLongClickItem(position);
                    return true;
                }
            });
        }

        void bind(ItemViewNote itemViewNote, int position) {
            this.position = position;
            binding.setItemViewNote(itemViewNote);
        }
    }

    //CheckBox خاص بعنصر ال ViewHolder
    public static class CheckBoxViewHolder extends RecyclerView.ViewHolder {
        int position;
        ItemNoteCheckBinding binding;

        public CheckBoxViewHolder(@NonNull ItemNoteCheckBinding binding, ItemClickListener itemClickListener, ItemLongClickListener itemLongClickListener, CheckBoxListener checkBoxListener) {
            super(binding.getRoot());
            this.binding = binding;

            binding.LinearLayoutCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onClickItem(position);
                }
            });
            binding.LinearLayoutCheckBox.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    itemLongClickListener.onLongClickItem(position);
                    return true;
                }
            });
            binding.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkBoxListener.onCheckBox(position);
                }
            });
        }

        void bind(ItemViewCheckBox itemViewCheckBox, int position) {
            this.position = position;
            binding.setItemViewCheckBox(itemViewCheckBox);
        }
    }

    //Photo خاص بعنصر ال ViewHolder
    public static class PhotoViewHolder extends RecyclerView.ViewHolder {
        int position;
        ItemNotePhotoBinding binding;

        public PhotoViewHolder(@NonNull ItemNotePhotoBinding binding, ItemClickListener itemClickListener, ItemLongClickListener itemLongClickListener) {
            super(binding.getRoot());
            this.binding = binding;

            binding.LinearLayoutPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onClickItem(position);
                }
            });
            binding.LinearLayoutPhoto.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    itemLongClickListener.onLongClickItem(position);
                    return true;
                }
            });
        }

        void bind(ItemViewPhoto itemViewPhoto, int position) {
            this.position = position;
            binding.setItemViewPhototh(itemViewPhoto);
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

    // ارجاع ملاحضة في موضع معين في ال adapter
    public ItemViewNote getNoteAtPositions(int position) {
        return mItems.get(position);
    }


}