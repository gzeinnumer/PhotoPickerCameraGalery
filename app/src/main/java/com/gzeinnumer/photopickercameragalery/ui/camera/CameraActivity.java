package com.gzeinnumer.photopickercameragalery.ui.camera;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.gzeinnumer.eeda.helper.FGFile;
import com.gzeinnumer.eeda.helper.imagePicker.FileCompressor;
import com.gzeinnumer.photopickercameragalery.BuildConfig;
import com.gzeinnumer.photopickercameragalery.R;
import com.gzeinnumer.photopickercameragalery.databinding.ActivityCameraBinding;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraActivity extends AppCompatActivity {

    private static final int REQUEST_TAKE_PHOTO = 2;
    public static String KEY_PATH = "path";
    public static String KEY_TITLE = "title";
    public static String KEY_POSITION = "position";
    private static File mPhotoFile;
    private ActivityCameraBinding binding;
    private FileCompressor mCompressor;
    private String title = "Take Foto";
    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCameraBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();
        initTextWatcher();
        initObserver();
        initOnClick();
    }

    private void initView() {
        if (getIntent().getStringExtra(KEY_TITLE) != null) {
            title = getIntent().getStringExtra(KEY_TITLE);
        }
        position = getIntent().getIntExtra(KEY_POSITION, 0);
        binding.tvToolbar.setText(title);
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
                intent.putExtra(KEY_PATH, data);
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

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                photoFile = FGFile.createImageFile(getApplicationContext(), fileName);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", photoFile);

                mPhotoFile = photoFile;
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO) {
                try {
                    //setelah foto diambil, dan tampil di preview maka akan lansung disimpan ke folder yang di sudah diset sebelumnya
                    mPhotoFile = mCompressor.compressToFile(mPhotoFile);
                    binding.tvPath.setText(mPhotoFile.toString());
                    Glide.with(CameraActivity.this)
                            .load(mPhotoFile)
                            .error(R.drawable.pp_ic_no_image)
                            .placeholder(R.drawable.pp_ic_no_image)
                            .into(binding.imgFoto);

                    if (mPhotoFile.length() > 0) {
                        binding.btnSimpan.setText("Simpan");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}