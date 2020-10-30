package com.rs132studio.deepgrade;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Adaptor.RecyclerViewClickListner {

    //private static final int PERMISSION_CODE = 100;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    List<Integer> images;
    List<String> title;
    Adaptor adaptor;
    GridLayoutManager gridLayoutManager;

    private File storage;
    private String[] storagePaths;

    Uri image_uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        images = new ArrayList<>();
        title = new ArrayList<>();

        images.add(R.drawable.video);
        images.add(R.drawable.myvideo);
        images.add(R.drawable.quiztime);
        images.add(R.drawable.assignment);
        images.add(R.drawable.worksheet);
        images.add(R.drawable.documents);

        title.add("All Videos");
        title.add("My Videos");
        title.add("Quiz Time");
        title.add("Worksheets");
        title.add("Assignments");
        title.add("Documents");

        adaptor = new Adaptor(this, images, title, this);
        gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adaptor);

    }

    @Override
    public void onItemClick(View v, int position){


        View viewItem = recyclerView.getLayoutManager().findViewByPosition(position);
        TextView videoTitleView = viewItem.findViewById(R.id.myText);

        String titleText = videoTitleView.getText().toString();

       if (titleText.equals("All Videos")) {
           storagePaths = StorageUtil.getStorageDirectories(this);

           for (String path : storagePaths) {
               storage = new File(path);
               Method.load_Directory_Files(storage);
           }
           
           Intent adaptorIntent = new Intent(getApplicationContext(), VideoActitivity.class);
           getApplicationContext().startActivity(adaptorIntent);
       } else if (titleText.equals("Documents")){
           cameraAction();
       } else {
           Toast.makeText(getApplicationContext(), titleText +" pressed", Toast.LENGTH_SHORT).show();
       }
    }

    private void cameraAction() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.CAMERA
            }, 100);
        }
        insidecamera();
    }

    private void insidecamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, 100);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                insidecamera();
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            Bitmap captureImage = (Bitmap) data.getExtras().get("data");

            Intent i = new Intent(this, CameraImage.class);
            i.putExtra("images", captureImage);
            startActivity(i);

        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.bell) {
            Toast.makeText(getApplicationContext(), "Bell icon pressed", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, 100);
    }
}