package com.gzeinnumer.photopickercameragalery.ui.galery;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.gzeinnumer.eeda.helper.FGFile;
import com.gzeinnumer.eeda.helper.imagePicker.FileCompressor;
import com.gzeinnumer.photopickercameragalery.R;
import com.gzeinnumer.photopickercameragalery.databinding.ActivityGaleryBinding;

import java.io.File;
import java.io.IOException;

public class GaleryActivity extends AppCompatActivity {

    private static final int REQUEST_TAKE_PHOTO = 3;
    public static String KEY_DATA = "data";
    public static String KEY_TITLE = "title";
    public static String KEY_POSITION = "position";
    private static File mPhotoFile;
    private ActivityGaleryBinding binding;
    private FileCompressor mCompressor;
    private String title = "";
    private String data = "";
    private String position = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGaleryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();
        initTextWatcher();
        initObserver();
        initOnClick();
    }

    private void initView() {
        if (getIntent().getStringExtra(KEY_TITLE) != null) {
            title = getIntent().getStringExtra(KEY_TITLE);
            binding.tvToolbar.setText(title);
        }
        if (getIntent().getStringExtra(KEY_DATA) != null) {
            data = getIntent().getStringExtra(KEY_DATA);
        }
        if (getIntent().getStringExtra(KEY_POSITION) != null) {
            position = getIntent().getStringExtra(KEY_POSITION);
        }
    }

    private void initTextWatcher() {

    }

    private void initObserver() {

    }

    private void initOnClick() {
        binding.imgFoto.setOnClickListener(view -> {
            dispatchTakePictureIntent();
        });
        binding.btnSimpan.setOnClickListener(view -> {
            if (binding.tvPath.getText().length() > 0) {
                String data = binding.tvPath.getText().toString();
                Intent intent = new Intent();
                intent.putExtra(KEY_TITLE, title);
                intent.putExtra(KEY_DATA, data);
                intent.putExtra(KEY_POSITION, position);
                setResult(Activity.RESULT_OK, intent);
                finish();//finishing activity
            } else {
                dispatchTakePictureIntent();
            }
        });
        binding.imgFoto.performClick();
    }

    private void dispatchTakePictureIntent() {
        mCompressor = new FileCompressor(this, 50);
        mCompressor.setDestinationDirectoryPath("/Foto");

        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(pickPhoto, REQUEST_TAKE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO) {
                Uri selectedImage = data.getData();
                try {
                    mPhotoFile = mCompressor.compressToFile(new File(FGFile.getRealPathFromUri(getApplicationContext(),selectedImage)));
                    binding.tvPath.setText(mPhotoFile.toString());
                    if (mPhotoFile.length() > 0) {
                        binding.btnSimpan.setText("Simpan");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Glide.with(GaleryActivity.this)
                        .load(mPhotoFile)
                        .error(R.drawable.pp_ic_no_image)
                        .placeholder(R.drawable.pp_ic_no_image)
                        .into(binding.imgFoto);
            }
        }
    }
}