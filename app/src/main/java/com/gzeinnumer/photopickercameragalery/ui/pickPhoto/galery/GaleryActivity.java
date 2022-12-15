package com.gzeinnumer.photopickercameragalery.ui.pickPhoto.galery;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.gzeinnumer.bu.utils.MBUtilsString;
import com.gzeinnumer.eeda.helper.FGFile;
import com.gzeinnumer.eeda.helper.FGPermission;
import com.gzeinnumer.eeda.helper.imagePicker.FileCompressor;
import com.gzeinnumer.eeda.helper.model.PermissionsResult;
import com.gzeinnumer.photopickercameragalery.R;
import com.gzeinnumer.photopickercameragalery.databinding.ActivityGaleryBinding;

import java.io.File;
import java.io.IOException;
import java.util.List;

import rebus.permissionutils.PermissionEnum;
import rebus.permissionutils.PermissionManager;

public class GaleryActivity extends AppCompatActivity {

    private static final String TAG = "GaleryActivityZein";
    private static final int REQUEST_TAKE_PHOTO = 3;
    public static String KEY_PATH = "path";
    public static String KEY_TITLE = "title";
    public static String KEY_POSITION = "position";
    private static File mPhotoFile;
    private ActivityGaleryBinding binding;
    private FileCompressor mCompressor;
    private String title = "";
    private int position = 0;

    private PermissionEnum[] permissions = new PermissionEnum[]{
            PermissionEnum.WRITE_EXTERNAL_STORAGE,
            PermissionEnum.READ_EXTERNAL_STORAGE,
            PermissionEnum.CAMERA
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGaleryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FGPermission.checkPermissions(this, permissions);

        checkPermissions();
    }


    private void checkPermissions() {
        boolean isAllGranted = FGPermission.getPermissionResult(this, permissions);

        if (isAllGranted) {
            onSuccessCheckPermitions();
        } else {
            Toast.makeText(this, "Permission Requared", Toast.LENGTH_SHORT).show();
        }

        FGPermission.getPermissionResult(this, permissions, new FGPermission.CallBackPermission() {
            @Override
            public void result(boolean isAllGranted, List<PermissionsResult> listPermissions) {
                Log.d(TAG, "result: " + isAllGranted);

                for (int i = 0; i < listPermissions.size(); i++) {
                    Log.d(TAG, "result: " + listPermissions.toString());
                }
                if (isAllGranted) {
                    onSuccessCheckPermitions();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Requared", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void onSuccessCheckPermitions() {
        initView();
        initTextWatcher();
        initObserver();
        initOnClick();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handleResult(this, requestCode, permissions, grantResults);

        checkPermissions();
    }

    private void initView() {
        if (getIntent().getStringExtra(KEY_TITLE) != null) {
            title = getIntent().getStringExtra(KEY_TITLE);
            binding.tvToolbar.setText(title);
        }
        position = getIntent().getIntExtra(KEY_POSITION, 0);
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
                    String filePath = MBUtilsString.getPath(getApplicationContext(), selectedImage);

                    if (validateFileSize(filePath)){
                        mPhotoFile = mCompressor.compressToFile(new File(FGFile.getRealPathFromUri(getApplicationContext(), selectedImage)));
                        binding.tvPath.setText(mPhotoFile.toString());
                        Glide.with(GaleryActivity.this)
                                .load(mPhotoFile)
                                .error(R.drawable.pp_ic_no_image)
                                .placeholder(R.drawable.pp_ic_no_image)
                                .into(binding.imgFoto);

                        if (mPhotoFile.length() > 0) {
                            binding.btnSimpan.setText("Simpan");
                        }
                    }  else {
                        Toast.makeText(getApplicationContext(), "Ukuran file tidak boleh melebihi 10MB.", Toast.LENGTH_SHORT).show();
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

    //return false if file size more than 10 mB
    public boolean validateFileSize(String path) {
        File file = new File(path);

        long fileSizeInBytes = file.length();
        long fileSizeInKB = fileSizeInBytes / 1024;
        long fileSizeInMB = fileSizeInKB / 1024;

        return fileSizeInMB <= 10;
    }
}