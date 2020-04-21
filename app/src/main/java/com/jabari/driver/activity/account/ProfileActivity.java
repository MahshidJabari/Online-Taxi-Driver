package com.jabari.driver.activity.account;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jabari.driver.R;
import com.jabari.driver.activity.AccountActivity;
import com.jabari.driver.activity.DebugActivity;
import com.jabari.driver.activity.FirstActivity;
import com.jabari.driver.activity.MainActivity;
import com.jabari.driver.controller.UserController;
import com.jabari.driver.global.GlobalVariables;
import com.jabari.driver.global.PrefManager;
import com.jabari.driver.network.config.ApiInterface;
import com.jabari.driver.network.model.User;
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


public class ProfileActivity extends AppCompatActivity {

    private static final String IMAGE_DIRECTORY = "/demonuts";
    private FloatingActionButton btn_addImg;
    private int GALLERY = 1, CAMERA = 2;
    private ImageView iv_pro;
    private Uri imageUri;
    private Bitmap crupBitmap;
    private TextView tv_email, tv_phone, tv_name, tv_credit, tv_vehicle;
    private Button btn_sheba, btn_support, btn_debug, btn_telegram, btn_tutorial;
    private User user;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        setView();
        requestMultiplePermissions();
        addProfileImage();
        getCurrentUser();
        setBtnOnClickListener();
    }

    private void setView() {
        btn_addImg = findViewById(R.id.btn_image);
        iv_pro = findViewById(R.id.iv_pro);
        tv_email = findViewById(R.id.tv_email);
        tv_phone = findViewById(R.id.tv_phone);
        btn_sheba = findViewById(R.id.btn_numSheba);
        tv_credit = findViewById(R.id.tv_creditcard);
        btn_debug = findViewById(R.id.btn_debugnum);
        btn_support = findViewById(R.id.btn_support);
        btn_telegram = findViewById(R.id.btn_telegramchannel);
        btn_tutorial = findViewById(R.id.btn_tutorial);
        tv_name = findViewById(R.id.tv_name);
        tv_vehicle = findViewById(R.id.tv_vehicle_type);

        tv_email.setText(user.getEmail());
        tv_phone.setText(user.getMobileNum());
        tv_name.setText(user.getName());

    }

    private void setBtnOnClickListener() {
        btn_sheba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, AccountActivity.class));
            }
        });
        btn_support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cullSupport();
            }
        });
        btn_debug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, DebugActivity.class));
            }
        });
        btn_telegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tg://resolve?domain=partsilicon"));
                startActivity(intent);
            }
        });
        btn_tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tg://resolve?domain=partsilicon"));
                startActivity(intent);
            }
        });
    }

    private void cullSupport() {
        ApiInterface.callSupportCallback callSupportCallback = new ApiInterface.callSupportCallback() {
            @Override
            public void onResponse(String phone) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                startActivity(intent);
            }

            @Override
            public void onFailure(String error) {
                if (error.equals("connection"))
                    Toasty.error(ProfileActivity.this, "خطا در برقراری ارتباط", Toasty.LENGTH_LONG).show();
                if (error.equals("null"))
                    Toasty.error(ProfileActivity.this, "درخواست با خطا مواجه شد", Toasty.LENGTH_LONG).show();
            }
        };
        UserController userController = new UserController(callSupportCallback);
        userController.call();
    }

    private void addProfileImage() {
        btn_addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPictureDialog();
            }
        });

    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("عکس پروفایل را انتخاب کنید!");
        String[] pictureDialogItems = {
                "انتخاب عکس از گالری",
                "گرفتن عکس با دوربین"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    private void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
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

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {

                final Uri contentURI = data.getData();
                try {

                    final Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);

                    AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                    dialog.setTitle("ذخیره شود؟");
                    dialog.setPositiveButton("بله", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            crupBitmap = Bitmap.createScaledBitmap(bitmap, 120, 120, false);
                            iv_pro.setImageBitmap(crupBitmap);

                        }
                    });

                    dialog.setNegativeButton("خیر", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    dialog.show();

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(ProfileActivity.this, "Failed!", Toast.LENGTH_SHORT).show();

                }
            }
        } else if (requestCode == CAMERA) {


            try {
                final Bitmap thumbnail;
                thumbnail = MediaStore.Images.Media.getBitmap(
                        getContentResolver(), imageUri);
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("ذخیره شود؟");
                dialog.setPositiveButton("بله", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        saveImage(thumbnail);
                        crupBitmap = Bitmap.createScaledBitmap(thumbnail, 120, 120, false);
                        iv_pro.setImageBitmap(crupBitmap);

                    }
                });

                dialog.setNegativeButton("خیر", new DialogInterface.OnClickListener() {
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

    private void requestMultiplePermissions() {
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

    private String saveImage(Bitmap myBitmap) {
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
        return "";
    }

    public void backOnclick(View view) {

        startActivity(new Intent(ProfileActivity.this, MainActivity.class));

    }

    public void logout(View view) {
        removePreferences();
        startActivity(new Intent(ProfileActivity.this, FirstActivity.class));
    }

    private void removePreferences() {

        PrefManager prefManager = new PrefManager(this);
        prefManager.removeToken();
        prefManager.removeUser();
        GlobalVariables.tok = "";

    }

    private void getCurrentUser() {
        if (getIntent().getExtras() != null) {
            user = getIntent().getExtras().getParcelable("user");
        } else
            Toasty.error(ProfileActivity.this, "خطا در برقراری ارتباط!", Toasty.LENGTH_LONG).show();
    }
}
