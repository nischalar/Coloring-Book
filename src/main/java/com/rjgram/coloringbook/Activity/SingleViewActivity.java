package com.rjgram.coloringbook.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Color;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.flask.colorpicker.slider.LightnessSlider;
import com.rjgram.coloringbook.DrawingView;
import com.rjgram.coloringbook.R;
import com.rjgram.coloringbook.common;


public class SingleViewActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton btnColorSelector,btnSave,btnDelete,btnUndo;
    DrawingView drawingView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_view);


drawingView = findViewById(R.id.drawing);
        btnColorSelector = findViewById(R.id.img_select_color);
        btnSave = findViewById(R.id.bt_save);
        btnDelete = findViewById(R.id.bt_delete);
        btnUndo = findViewById(R.id.bt_undo);
        //drawView.setupBitmap();
    }






    @Override
    public void onClick(View view) {
        switch (view.getId()){
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
                saveBitmapToGallery();
                break;
                
        }

    }

    private void saveBitmapToGallery() {

    }

    private void undo() {
    }

    private void deleteAll() {
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
    
    
    
}

