package com.gzeinnumer.photopickercameragalery.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gzeinnumer.photopickercameragalery.databinding.ItemPhotoBinding;
import com.gzeinnumer.photopickercameragalery.helper.dpi.DialogPreviewImage;

import java.util.ArrayList;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.MyHolder> {

    private final ArrayList<String> list;
    private final FragmentManager fragmentManager;
    private int max;
    private int type = 1; //1->input 2->display

    public PhotoAdapter(FragmentManager fragmentManager, int max) {
        this.fragmentManager = fragmentManager;
        this.max = max;
        this.list = new ArrayList<>();
    }
    public PhotoAdapter(FragmentManager fragmentManager, int max, int type) {
        this.fragmentManager = fragmentManager;
        this.max = max;
        this.type = type;
        this.list = new ArrayList<>();
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public void add(String message) {
        this.list.add(message);
        triggerCallback();
        notifyItemInserted(list.size() - 1);
    }

    public void updateList(int position, String path){
        this.list.set(position, path);
        triggerCallback();
        notifyItemChanged(position);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void removeList(int position){
        this.list.remove(position);
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
        return new MyHolder(ItemPhotoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.bindData(list.get(position), callbackVisibilty, callbackImage, type, fragmentManager);
    }

    private CallbackVisibilty callbackVisibilty;

    public interface CallbackVisibilty {
        void visibilityAddButton(int visibility);
    }

    private CallbackImage callbackImage;

    public interface CallbackImage {
        void imageEdit(String adapterPosition);
        void imageDelete(String adapterPosition);
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

        public ItemPhotoBinding itemBinding;

        public MyHolder(@NonNull ItemPhotoBinding itemView) {
            super(itemView.getRoot());
            itemBinding = itemView;
        }

        public void bindData(String data, CallbackVisibilty callbackVisibilty, CallbackImage callbackImage, int type, FragmentManager fragmentManager) {
            itemBinding.tvPath.setText(data);
            if (data.length()>0){
                itemBinding.imgDelete.setVisibility(View.VISIBLE);
            } else {
                itemBinding.imgDelete.setVisibility(View.INVISIBLE);
            }
            Glide.with(itemBinding.img).load(data).into(itemBinding.img);

            if (type==1){
                itemBinding.imgDelete.setVisibility(View.VISIBLE);
            } else {
                itemBinding.imgDelete.setVisibility(View.GONE);
            }
            if (callbackVisibilty !=null){
                itemBinding.cvImage.setOnClickListener(v -> {
                    new DialogPreviewImage(fragmentManager).setImage(data).show();
                });
                itemBinding.imgDelete.setOnClickListener(v -> {
                    callbackImage.imageDelete(String.valueOf(getAdapterPosition()));
                });
                itemBinding.cvImage.setOnLongClickListener(v -> {
                    callbackImage.imageEdit(String.valueOf(getAdapterPosition()));
                    return false;
                });
            }
        }
    }
}
