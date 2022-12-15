# PhotoPickerCameraGalery

|![](https://github.com/gzeinnumer/PhotoPickerCameraGalery/blob/master/preview/preview1.jpg)|![](https://github.com/gzeinnumer/PhotoPickerCameraGalery/blob/master/preview/preview2.jpg)|![](https://github.com/gzeinnumer/PhotoPickerCameraGalery/blob/master/preview/preview3.jpg)|![](https://github.com/gzeinnumer/PhotoPickerCameraGalery/blob/master/preview/preview4.jpg)|![](https://github.com/gzeinnumer/PhotoPickerCameraGalery/blob/master/preview/preview5.jpg)|
|---|---|---|---|---|

```xml
android:configChanges="orientation|screenSize|keyboardHidden"
```

- [AndroidManifest.xml](https://github.com/gzeinnumer/PhotoPickerCameraGalery/blob/master/app/src/main/AndroidManifest.xml)
- app
  - adapter
    - [PhotoAdapter.java](https://github.com/gzeinnumer/PhotoPickerCameraGalery/blob/master/app/src/main/java/com/gzeinnumer/photopickercameragalery/adapter/PhotoAdapter.java)
    - [PhotoDescAdapter.java](https://github.com/gzeinnumer/PhotoPickerCameraGalery/blob/master/app/src/main/java/com/gzeinnumer/photopickercameragalery/adapter/PhotoDescAdapter.java)
  - helper
    - dpi
      - [DialogPreviewImage.java](https://github.com/gzeinnumer/PhotoPickerCameraGalery/blob/master/app/src/main/java/com/gzeinnumer/photopickercameragalery/helper/dpi/DialogPreviewImage.java)
      - [DialogPreviewImageSetting.java](https://github.com/gzeinnumer/PhotoPickerCameraGalery/blob/master/app/src/main/java/com/gzeinnumer/photopickercameragalery/helper/dpi/DialogPreviewImageSetting.java)
  - ui
    - main
      - [MainActivity.java](https://github.com/gzeinnumer/PhotoPickerCameraGalery/blob/master/app/src/main/java/com/gzeinnumer/photopickercameragalery/ui/main/MainActivity.java)
    - pickPhoto
      - camera
        - [CameraActivity.java](https://github.com/gzeinnumer/PhotoPickerCameraGalery/blob/master/app/src/main/java/com/gzeinnumer/photopickercameragalery/ui/pickPhoto/camera/CameraActivity.java)
      - withDecs
        - [WithDescActivity.java](https://github.com/gzeinnumer/PhotoPickerCameraGalery/blob/master/app/src/main/java/com/gzeinnumer/photopickercameragalery/ui/pickPhoto/withDesc/WithDescActivity.java)
      - galery
        - [GaleryActivity.java](https://github.com/gzeinnumer/PhotoPickerCameraGalery/blob/master/app/src/main/java/com/gzeinnumer/photopickercameragalery/ui/pickPhoto/galery/GaleryActivity.java)
      - dialog
        - pickImage
          - [PickImageDialog.java](https://github.com/gzeinnumer/PhotoPickerCameraGalery/blob/master/app/src/main/java/com/gzeinnumer/photopickercameragalery/ui/pickPhoto/dialog/pickImage/PickImageDialog.java)
    - splash
      - [SplashScreenActivity.java](https://github.com/gzeinnumer/PhotoPickerCameraGalery/blob/master/app/src/main/java/com/gzeinnumer/photopickercameragalery/ui/splash/SplashScreenActivity.java)
  - [MyApp.java](https://github.com/gzeinnumer/PhotoPickerCameraGalery/blob/master/app/src/main/java/com/gzeinnumer/photopickercameragalery/MyApp.java)
- res
  - layout
    - [activity_camera.xml](https://github.com/gzeinnumer/PhotoPickerCameraGalery/blob/master/app/src/main/res/layout/activity_camera.xml)
    - [activity_galery.xml](https://github.com/gzeinnumer/PhotoPickerCameraGalery/blob/master/app/src/main/res/layout/activity_galery.xml)
    - [activity_main.xml](https://github.com/gzeinnumer/PhotoPickerCameraGalery/blob/master/app/src/main/res/layout/activity_main.xml)
    - [activity_splash_screen.xml](https://github.com/gzeinnumer/PhotoPickerCameraGalery/blob/master/app/src/main/res/layout/activity_splash_screen.xml)
    - [activity_with_desc.xml](https://github.com/gzeinnumer/PhotoPickerCameraGalery/blob/master/app/src/main/res/layout/activity_splash_screen.xml)
    - [dialog_pick_image.xml](https://github.com/gzeinnumer/PhotoPickerCameraGalery/blob/master/app/src/main/res/layout/dialog_pick_image.xml)
    - [dialog_preview_image.xml](https://github.com/gzeinnumer/PhotoPickerCameraGalery/blob/master/app/src/main/res/layout/dialog_preview_image.xml)
    - [item_photo.xml](https://github.com/gzeinnumer/PhotoPickerCameraGalery/blob/master/app/src/main/res/layout/item_photo.xml)
    - [widget_photo_picker.xml](https://github.com/gzeinnumer/PhotoPickerCameraGalery/blob/master/app/src/main/res/layout/widget_photo_picker.xml)
    - [widget_photo_picker_desc.xml](https://github.com/gzeinnumer/PhotoPickerCameraGalery/blob/master/app/src/main/res/layout/widget_photo_picker.xml)
  - values
    - [themes.xml](https://github.com/gzeinnumer/PhotoPickerCameraGalery/blob/master/app/src/main/res/values/themes.xml)
- gradle
  - [build.gradle](https://github.com/gzeinnumer/PhotoPickerCameraGalery/blob/master/build.gradle)
  - [build.gradle(app)](https://github.com/gzeinnumer/PhotoPickerCameraGalery/blob/master/app/build.gradle)

---

```
Copyright 2021 M. Fadli Zein
```