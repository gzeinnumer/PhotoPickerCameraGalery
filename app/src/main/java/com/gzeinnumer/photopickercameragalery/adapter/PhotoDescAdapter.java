package com.gzeinnumer.photopickercameragalery.adapter;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gzeinnumer.photopickercameragalery.databinding.ItemPhotoDescBinding;
import com.gzeinnumer.photopickercameragalery.helper.dpi.DialogPreviewImage;

import java.util.ArrayList;
import java.util.List;

public class PhotoDescAdapter extends RecyclerView.Adapter<PhotoDescAdapter.MyHolder> {

    private final ArrayList<ModelDesc> list;
    private ArrayList<ItemPhotoDescBinding> holders;
    private final FragmentManager fragmentManager;
    private int max;
    private int type = 1; //1->input 2->display

    public PhotoDescAdapter(FragmentManager fragmentManager, int max) {
        this.fragmentManager = fragmentManager;
        this.max = max;
        this.list = new ArrayList<>();
        this.holders = new ArrayList<>(list.size());
        initHolders();
    }

    private void initHolders() {
        for (int i = 0; i < list.size(); i++) {
            holders.add(null);
        }
    }

    public List<ItemPhotoDescBinding> getHolders() {
        return holders;
    }

    public PhotoDescAdapter(FragmentManager fragmentManager, int max, int type) {
        this.fragmentManager = fragmentManager;
        this.max = max;
        this.type = type;
        this.list = new ArrayList<>();
        this.holders = new ArrayList<>(list.size());
        initHolders();
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ArrayList<ModelDesc> getList() {
        return list;
    }

    public void add(ModelDesc data) {
        this.list.add(data);
        this.holders.add(null);
        triggerCallback();
        notifyItemInserted(list.size() - 1);
    }

    public void updateList(int position, ModelDesc path){
        this.list.set(position, path);
        triggerCallback();
        notifyItemChanged(position);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void removeList(int position){
        this.list.remove(position);
        this.holders.remove(position);
        triggerCallback();
        notifyDataSetChanged();
    }

    private void triggerCallback() {
        if (this.list.size()==max){
            callbackVisibilty.visibilityAddButton(View.GONE);
        } else {
            callbackVisibilty.visibilityAddButton(View.VISIBLE);
        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(ItemPhotoDescBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, @SuppressLint("RecyclerView") int position) {
        holders.set(position, ItemPhotoDescBinding.bind(holder.itemBinding.getRoot()));
        holder.bindData(list.get(position), callbackVisibilty, callbackImage, type, fragmentManager);
    }

    private CallbackVisibilty callbackVisibilty;

    public interface CallbackVisibilty {
        void visibilityAddButton(int visibility);
    }

    private CallbackImage callbackImage;

    public interface CallbackImage {
        void imageEdit(int adapterPosition);
        void imageDelete(int adapterPosition);
    }

    public void setCallbackVisibilty(CallbackVisibilty callbackVisibilty) {
        this.callbackVisibilty = callbackVisibilty;
    }

    public void setCallbackImage(CallbackImage callbackImage) {
        this.callbackImage = callbackImage;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {

        public ItemPhotoDescBinding itemBinding;

        public MyHolder(@NonNull ItemPhotoDescBinding itemView) {
            super(itemView.getRoot());
            itemBinding = itemView;
        }

        public void bindData(ModelDesc data, CallbackVisibilty callbackVisibilty, CallbackImage callbackImage, int type, FragmentManager fragmentManager) {
            itemBinding.tvPath.setText(data.getPath());
            if (data.getPath().length()>0){
                itemBinding.imgDelete.setVisibility(View.VISIBLE);
            } else {
                itemBinding.imgDelete.setVisibility(View.INVISIBLE);
            }
            Glide.with(itemBinding.img).load(data.getPath()).into(itemBinding.img);

            if (type==1){
                itemBinding.imgDelete.setVisibility(View.VISIBLE);
            } else {
                itemBinding.imgDelete.setVisibility(View.GONE);
            }
            if (callbackVisibilty !=null){
                itemBinding.cvImage.setOnClickListener(v -> {
                    new DialogPreviewImage(fragmentManager).setImage(data.getPath()).show();
                });
                if (type==1){
                    itemBinding.imgDelete.setOnClickListener(v -> {
                        callbackImage.imageDelete(getAdapterPosition());
                    });
                    itemBinding.cvImage.setOnLongClickListener(v -> {
                        callbackImage.imageEdit(getAdapterPosition());
                        return false;
                    });
                }
            }
            itemBinding.edContent.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    data.setContent(s.toString());
                }
            });
        }
    }

    public static class ModelDesc {
        private String path;
        private String content;

        public ModelDesc(String path, String content) {
            this.path = path;
            this.content = content;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        @Override
        public String toString() {
            return content + " - " +path;
        }
    }
}
