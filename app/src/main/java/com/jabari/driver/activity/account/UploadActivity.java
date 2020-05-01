package com.jabari.driver.activity.account;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.jabari.driver.R;
import com.jabari.driver.controller.RegisterController;
import com.jabari.driver.global.GlobalVariables;
import com.jabari.driver.network.config.ApiInterface;
import com.jabari.driver.network.model.Document;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import es.dmoral.toasty.Toasty;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class UploadActivity extends AppCompatActivity {
    private String photo_path;
    private int GALLERY = 1, CAMERA = 2;
    private static final String IMAGE_DIRECTORY = "/demonuts";
    private Uri imageUri;
    private View selectView;
    private int selectedView;
    private boolean meli = false, Id = false, military = false, greenPaper = false, waterBill = false, electricBill = false, licence = false;
    private Document document = new Document();
    ;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_photo);

        requestMultiplePermissions();
    }

    public void onClick(View view) {

        GlobalVariables.isClicked = true;
        selectedView = view.getId();
        showPictureDialog(view);
        this.selectView = view;

    }

    public void showPictureDialog(final View view) {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle(getResources().getString(R.string.select_action));
        String[] pictureDialogItems = {
                getResources().getString(R.string.select_photo),
                getResources().getString(R.string.capture_photo)};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallery();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallery() {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    public void takePhotoFromCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
        imageUri = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d("data", data.toString());
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            final Uri contentURI = data.getData();

            try {


                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle(getResources().getString(R.string.save_photo));
                dialog.setPositiveButton(getResources().getString(R.string.send), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        photo_path = getRealPathFromURI(contentURI);
                        uploadFile(photo_path);

                    }
                });

                dialog.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                dialog.show();


            } catch (Exception e) {
                e.printStackTrace();
                Toasty.error(UploadActivity.this, "انتخاب عکس با خطا مواجه شد", Toast.LENGTH_SHORT, true).show();
            }
        } else if (requestCode == CAMERA) {

            try {
                final Bitmap thumbnail;
                thumbnail = MediaStore.Images.Media.getBitmap(
                        getContentResolver(), imageUri);

                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle(getResources().getString(R.string.save_photo));
                dialog.setPositiveButton(getResources().getString(R.string.send), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        photo_path = saveImage(thumbnail);
                        uploadFile(photo_path);


                    }
                });

                dialog.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                dialog.show();

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }


    public void onClickRegister(View view) {
        if (meli & Id & licence & military & greenPaper & electricBill & waterBill) {
            Intent intent = new Intent(UploadActivity.this, RegisterActivity.class);
            intent.putExtra("document", (Parcelable) document);
            startActivity(intent);
        } else
            Toasty.error(UploadActivity.this, "مدارک ناقص است!", Toast.LENGTH_LONG, true).show();
    }

    public void uploadFile(String s) {

        ApiInterface.UploadFileCallback uploadFileCallback = new ApiInterface.UploadFileCallback() {
            @Override
            public void onResponse(String url) {

                selectView.setBackgroundDrawable(getResources().getDrawable(R.drawable.back_ten_radius_gray));
                Toasty.success(UploadActivity.this, "ارسال شد!", Toast.LENGTH_LONG, true).show();
                setDocument(url);

            }

            @Override
            public void onFailure(String error) {

                Toasty.error(UploadActivity.this, "ارسال با خطا مواجه شد!", Toast.LENGTH_LONG, true).show();

            }
        };
        RegisterController uploadController = new RegisterController(uploadFileCallback, UploadActivity.this);
        uploadController.upload(s);
    }

    public void setDocument(String url) {
        switch (selectedView) {
            case R.id.btn_send_meli:
                document.setDocumentMeli(url);
                meli = true;
                break;
            case R.id.btn_send_identity:
                document.setDocumentId(url);
                Id = true;
                break;
            case R.id.btn_send_license:
                document.setDocumentLicense(url);
                licence = true;
                break;
            case R.id.btn_send_military:
                document.setDocumentMilitary(url);
                military = true;
                break;
            case R.id.btn_send_green_paper:
                document.setDocumentGreenPaper(url);
                greenPaper = true;
                break;
            case R.id.btn_send_water_bill:
                document.setDocumentWaterBill(url);
                waterBill = true;
                break;
            case R.id.btn_send_electricity_bill:
                document.setDocumentElectricalBill(url);
                electricBill = true;
                break;
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(contentUri,
                proj, // Which columns to return
                null,       // WHERE clause; which rows to return (all rows)
                null,       // WHERE clause selection arguments (none)
                null); // Order-by clause (ascending by name)
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        return cursor.getString(column_index);
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::---&gt;" + f.getAbsolutePath());


            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    public void requestMultiplePermissions() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {

                        if (report.areAllPermissionsGranted()) {
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {

                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                        token.continuePermissionRequest();

                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

}
