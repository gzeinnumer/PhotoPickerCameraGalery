package com.gzeinnumer.photopickercameragalery.ui.main;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.gzeinnumer.photopickercameragalery.adapter.PhotoAdapterV4;
import com.gzeinnumer.photopickercameragalery.databinding.ActivityMainBinding;
import com.gzeinnumer.photopickercameragalery.ui.pickPhoto.dialog.pickImage.PickImageDialog;
import com.gzeinnumer.photopickercameragalery.ui.pickPhoto.camera.CameraActivity;
import com.gzeinnumer.photopickercameragalery.ui.pickPhoto.galery.GaleryActivity;
import com.gzeinnumer.photopickercameragalery.ui.pickPhoto.withDesc.WithDescActivity;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setTitle("Main");

        initView();
    }

    private void initView() {
        initImageAdapter();
        binding.btnPath.setOnClickListener(v -> {
            StringBuilder path = new StringBuilder();
            for (int i = 0; i < adapterPhotoV4.getList().size(); i++) {
                path.append(i).append(". ").append(adapterPhotoV4.getList().get(i)).append("\n");
            }
            Toast.makeText(getApplicationContext(), path.toString(), Toast.LENGTH_SHORT).show();
        });

        binding.btnWithDesc.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), WithDescActivity.class);
            startActivity(intent);
        });
    }

    private final ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // There are no request codes
                    Intent data = result.getData();
                    setPath(data);
                }
            });

    private void setPath(Intent data) {
        String message = data.getStringExtra(CameraActivity.KEY_PATH);
        int position = data.getIntExtra(CameraActivity.KEY_POSITION, 0);
        if (position == -1)
            adapterPhotoV4.add(message);
        else
            adapterPhotoV4.updateList(position, message);
    }

    private PhotoAdapterV4 adapterPhotoV4;

    private void initImageAdapter() {
        adapterPhotoV4 = new PhotoAdapterV4(getSupportFragmentManager(), 4);
        binding.pp.rv.setAdapter(adapterPhotoV4);
        adapterPhotoV4.setCallbackVisibilty(visibility -> {
            binding.pp.cvAddItem.setVisibility(visibility);
        });
        binding.pp.cvAddItem.setVisibility(View.VISIBLE);

        adapterPhotoV4.setCallbackImage(new PhotoAdapterV4.CallbackImage() {
            @Override
            public void imageEdit(int adapterPosition) {
//                pickFile(PickImageDialog.FileFrom.CAMERA, adapterPosition);
                openDialog(adapterPosition);
            }

            @Override
            public void imageDelete(int adapterPosition) {
                adapterPhotoV4.removeList(adapterPosition);
            }
        });

        binding.pp.rv.hasFixedSize();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.pp.rv.setLayoutManager(layoutManager);

        binding.pp.cvAddItem.setOnClickListener(view -> {
//            pickFile(PickImageDialog.FileFrom.CAMERA, -1);
            openDialog(-1);
        });
    }

    private void openDialog(int adapterPosition) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment previous = getSupportFragmentManager().findFragmentByTag(PickImageDialog.TAG);
        if (previous != null) {
            transaction.remove(previous);
        }
        PickImageDialog dialog = PickImageDialog.newInstance(type -> {
            pickFile(type, adapterPosition);
        });
        dialog.show(transaction, PickImageDialog.TAG);
    }

    private void pickFile(PickImageDialog.FileFrom type, int adapterPosition) {
        Intent intent;
        if (type == PickImageDialog.FileFrom.CAMERA) {
            intent = new Intent(getApplicationContext(), CameraActivity.class);
            intent.putExtra(CameraActivity.KEY_TITLE, "Foto Camera");
        } else {
            intent = new Intent(getApplicationContext(), GaleryActivity.class);
            intent.putExtra(GaleryActivity.KEY_TITLE, "Foto Galery");
        }
        intent.putExtra(CameraActivity.KEY_POSITION, adapterPosition);
        someActivityResultLauncher.launch(intent);
    }
}