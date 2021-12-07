package com.gzeinnumer.photopickercameragalery.ui.dialog.pickImage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.gzeinnumer.photopickercameragalery.databinding.DialogPickImageBinding;


public class PickImageDialog extends DialogFragment {
    public static final String TAG = "PickImageDialog";
    private DialogPickImageBinding binding;
    private ButtonCallBack buttonCallBack;

    public PickImageDialog() {
    }

    public PickImageDialog(ButtonCallBack buttonCallBack) {
        this.buttonCallBack = buttonCallBack;
    }

    public static PickImageDialog newInstance(ButtonCallBack buttonCallBack) {
        return new PickImageDialog(buttonCallBack);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DialogPickImageBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        initTextWatcher();
        initObserver();
        initOnClick();
    }

    private void initView() {

    }

    private void initTextWatcher() {

    }

    private void initObserver() {

    }

    private void initOnClick() {
        binding.btnCamera.setOnClickListener(view -> {
            buttonCallBack.onButtonChoised(FileFrom.CAMERA);
            dismiss();
        });
        binding.btnGalery.setOnClickListener(view -> {
            buttonCallBack.onButtonChoised(FileFrom.GALERY);
            dismiss();
        });
        binding.btnTutup.setOnClickListener(view -> {
            getDialog().dismiss();
        });
    }

    public enum FileFrom {
        CAMERA, GALERY
    }

    public interface ButtonCallBack {
        void onButtonChoised(FileFrom type);
    }
}