# PhotoPickerCameraGalery

|![](https://github.com/gzeinnumer/PhotoPickerCameraGalery/blob/master/preview/preview1.jpg)|![](https://github.com/gzeinnumer/PhotoPickerCameraGalery/blob/master/preview/preview2.jpg)|![](https://github.com/gzeinnumer/PhotoPickerCameraGalery/blob/master/preview/preview3.jpg)|![](https://github.com/gzeinnumer/PhotoPickerCameraGalery/blob/master/preview/preview4.jpg)|![](https://github.com/gzeinnumer/PhotoPickerCameraGalery/blob/master/preview/preview5.jpg)|
|---|---|---|---|---|

```
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
```

```
<?xml version="1.0" encoding="utf-8"?>
<androidx.appcompat.widget.LinearLayoutCompat xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context=".ui.main.MainActivity">

    <LinearLayout
        android:id="@+id/ln"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center_vertical"
        android:gravity="center"
        android:orientation="horizontal" />

</androidx.appcompat.widget.LinearLayoutCompat>
```

- `item_photo_no_rv.xml`
```
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_gravity="center"
    android:orientation="vertical">

    <androidx.cardview.widget.CardView
        android:id="@+id/cv_image"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginLeft="@dimen/space_half"
        android:layout_marginTop="@dimen/space"
        android:layout_marginRight="@dimen/space_half"
        android:layout_marginBottom="@dimen/space"
        app:cardBackgroundColor="@color/white_low_v2"
        app:cardCornerRadius="@dimen/radius"
        app:cardElevation="0dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent">

        <ImageView
            android:id="@+id/img"
            android:layout_width="50dp"
            android:layout_height="50dp"
            android:adjustViewBounds="true"
            android:scaleType="centerCrop"
            android:src="@drawable/pp_ic_no_image" />

    </androidx.cardview.widget.CardView>

    <TextView
        android:id="@+id/tv_path"
        style="@style/MyTextContent"
        android:visibility="gone"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintTop_toBottomOf="@id/cv_image"
        app:layout_constraintTop_toTopOf="parent"
        tools:text="path"
        tools:visibility="visible" />

    <TextView
        android:id="@+id/tv_more_detail"
        style="@style/MyTextContent"
        android:visibility="visible"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintTop_toBottomOf="@id/cv_image"
        app:layout_constraintTop_toTopOf="parent"
        tools:text="path" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

---

```
Copyright 2021 M. Fadli Zein
```
