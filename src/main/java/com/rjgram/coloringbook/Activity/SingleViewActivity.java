package com.rjgram.coloringbook.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.DialogInterface;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.rjgram.coloringbook.DrawingView;
import com.rjgram.coloringbook.R;
import com.rjgram.coloringbook.common;


import java.util.LinkedList;
import java.util.Queue;

import in.myinnos.savebitmapandsharelib.SaveAndShare;


public class SingleViewActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int PERMISSION_REQUEST_CODE = 100;
    DrawingView drawingView;
    Bitmap bitmap;
    static ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_view);

        if (savedInstanceState != null) {
            savedInstanceState = null;
        }
        drawingView = findViewById(R.id.drawing);
        progressBar = findViewById(R.id.progressBar);
        ImageButton btnColorSelector = findViewById(R.id.img_select_color);
        ImageButton btnSave = findViewById(R.id.bt_save);
        ImageButton btnDelete = findViewById(R.id.bt_delete);
        ImageButton btnUndo = findViewById(R.id.bt_undo);
if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.JELLY_BEAN_MR1){

}else{

}

        btnColorSelector.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnUndo.setOnClickListener(this);
        btnUndo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                drawingView.newPage();
                return true;
            }
        });


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_select_color:
                showDialog();
                break;
            case R.id.bt_delete:
                deleteAll();
                break;
            case R.id.bt_undo:
                undo();
                break;
            case R.id.bt_save:

                getPermissionAndSave();
//               saveBitmapToGallery();
                break;

        }

    }


    private void getPermissionAndSave() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkPermission()) {
                saveBitmapToGallery();
            } else {
                requestPermission();
            }
        } else {
            saveBitmapToGallery();

        }
    }


    private void saveBitmapToGallery() {

        bitmap = drawingView.getBitmap();
        boolean checkForPermission = SaveAndShare.checkPermissionForExternalStorage(this);
        Log.d("TAG", "saveBitmapToGallery" + checkForPermission);
       // if (checkForPermission)

            SaveAndShare.save(this, bitmap, null, null, null);
    }


    private void undo() {
        Log.d("TAG", "undo: ");
        drawingView.undoLastAction();
    }

    private void deleteAll() {
        drawingView.DeleteAll();
    }


    private void showDialog() {
        ColorPickerDialogBuilder
                .with(this)
                .initialColor(common.SELECTED_COLOR)
                .setTitle("Choose color")
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {
                        // Toast.makeText(getApplicationContext(), "onColorSelected: 0x" + Integer.toHexString(selectedColor), Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton("ok", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {

                        common.SELECTED_COLOR = selectedColor;

                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })

                .build()
                .show();
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(SingleViewActivity.this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(SingleViewActivity.this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(SingleViewActivity.this,
                    "Write External Storage permission allows us to do store images." +
                            " Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(SingleViewActivity.this,
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.e("value", "Permission Granted, Now you can use ");
                saveBitmapToGallery();
            } else {
                Log.e("value", "Permission Denied, You cannot use");
            }
        }
    }

    public static class myTask extends AsyncTask<Void, Integer, Void> {
        Bitmap bitmap;
        Context context;
        Point point;
        int replacementColor;
        int targetColor;

        public myTask(Bitmap bitmap, Context context,
                      Point point, int replacementColor, int targetColor) {
            this.bitmap = bitmap;
            this.context = context;
            this.point = point;
            this.replacementColor = replacementColor;
            this.targetColor = targetColor;

        }

        @Override
        protected Void doInBackground(Void... voids) {

            floodFill(bitmap, point, targetColor, replacementColor);

            return null;

        }


        public void floodFill(Bitmap bitmap, Point point, int targetColor, int newColor) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            if (targetColor != newColor) {
                Queue<Point> queue = new LinkedList<>();
                do {
                    int x = point.x;
                    int y = point.y;

                    while (x > 0 && bitmap.getPixel(x - 1, y) == targetColor) {
                        x--;
                    }
                    boolean spanUp = false;
                    boolean spanDown = false;


                    while (x < width && bitmap.getPixel(x, y) == targetColor) {
                        bitmap.setPixel(x, y, newColor);

                        if (!spanUp && y > 0 && bitmap.getPixel(x, y - 1) == targetColor) {
                            queue.add(new Point(x, y - 1));
                            spanUp = true;

                        } else if (spanUp && y > 0 && bitmap.getPixel(x, y - 1) == targetColor) {
                            spanUp = false;

                        }


                        if (!spanDown && y < height - 1 && bitmap.getPixel(x, y + 1) == targetColor) {
                            queue.add(new Point(x, y + 1));
                            spanDown = true;

                        } else if (spanDown && y < height - 1 && bitmap.getPixel(x, y + 1) == targetColor) {
                            spanDown = false;

                        }

                        x++;
                    }
                } while ((point = queue.poll()) != null);
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            progressBar.setMax(100);
            progressBar.setVisibility(View.GONE);


        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(75);


        }
    }

}