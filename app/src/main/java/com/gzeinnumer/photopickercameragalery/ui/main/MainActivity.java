package com.gzeinnumer.photopickercameragalery.ui.main;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.gzeinnumer.photopickercameragalery.R;
import com.gzeinnumer.photopickercameragalery.databinding.ActivityMainBinding;

import java.io.File;
import java.util.ArrayList;

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
        ArrayList<String> data = new ArrayList<>();
        data.add("storage/emulated/0/DistributorAdvisor/Foto/20221202131022_6788790416421463523.jpg");
        data.add("storage/emulated/0/DistributorAdvisor/Foto/20221202131022_6788790416421463523.jpg");
        data.add("storage/emulated/0/DistributorAdvisor/Foto/20221202131022_6788790416421463523.jpg");

        final View[] views = new View[data.size()];
        for (int i = 0; i < data.size(); i++) {
            views[i] = LayoutInflater.from(this).inflate(R.layout.item_photo_no_rv, null);
            ImageView img = views[i].findViewById(R.id.img);
            TextView tvPath = views[i].findViewById(R.id.tv_path);
            TextView tvMoreDetail = views[i].findViewById(R.id.tv_more_detail);

            File imgFile = new File(data.get(i));
            img.setImageURI(Uri.fromFile(imgFile));
            tvMoreDetail.setText(i+"");

            tvPath.setText(data.get(i));

            ///storage/emulated/0/DistributorAdvisor/Foto/20221202131022_6788790416421463523a.jpg

            binding.ln.addView(views[i]);
        }
    }
}