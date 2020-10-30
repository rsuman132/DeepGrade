package com.rs132studio.deepgrade;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class CameraImage extends AppCompatActivity {

    private ImageView captureImage;
    private Button captureButton;
    private Bitmap operation;
    private Bitmap bmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_image);

        captureImage = (ImageView) findViewById(R.id.captureImage);
        captureButton = (Button) findViewById(R.id.captureButton);
        bmp  = getIntent().getExtras().getParcelable("images");
        captureImage.setImageBitmap(bmp);

        BitmapDrawable abmp = (BitmapDrawable) captureImage.getDrawable();
        bmp = abmp.getBitmap();
        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureButton.setEnabled(false);
                shadowEffect();
            }

            private void shadowEffect() {
                operation= Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(),bmp.getConfig());

                for(int i=0; i<bmp.getWidth(); i++){
                    for(int j=0; j<bmp.getHeight(); j++){
                        int p = bmp.getPixel(i, j);
                        int r = Color.red(p);
                        int g = Color.green(p);
                        int b = Color.blue(p);
                        int alpha = Color.alpha(p);

                        r = 100  +  r;
                        g = 100  + g;
                        b = 100  + b;
                        alpha = 100 + alpha;
                        operation.setPixel(i, j, Color.argb(alpha, r, g, b));
                    }
                }
                captureImage.setImageBitmap(operation);
            }
        });
    }
}