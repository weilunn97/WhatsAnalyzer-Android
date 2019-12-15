package com.jed.whatsapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import static com.jed.whatsapp.FileProcessing.*;
import static com.jed.whatsapp.FileProcessing.setUploadedFile;

// TODO : Add onClick to button in main_activity.xml
public class MainActivity extends AppCompatActivity {

    // ATTRIBUTES
    private static final int UPLOAD_REQUEST_CODE = 1;
    private static final int ANALYZE_REQUEST_CODE = 2;
    private static final int GRAPH_REQUEST_CODE = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // EMAIL BUTTON
        FloatingActionButton fab = findViewById(R.id.emailButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // UPLOAD BUTTON
        Button uploadButton = findViewById(R.id.uploadButton);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("text/plain");
                startActivityForResult(intent, UPLOAD_REQUEST_CODE);
            }
        });

        // ANALYZE BUTTON
        Button analyzeButton = findViewById(R.id.analyzeButton);
        analyzeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MessageStatisticsActivity.class);
                startActivityForResult(intent, 100);
            }
        });

        // GRAPH BUTTON
        Button graphButton = findViewById(R.id.graphButton);
        graphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO : IMPLEMENT GRAPHING ONCLICK FUNCTION
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case UPLOAD_REQUEST_CODE:
                    String selectedFilePath = data.getData().getPath();
                    File selectedFile = new File(selectedFilePath);
                    setUploadedFile(selectedFile);

                    try {
                        readFile(data.getData(), getApplicationContext());
                        FileProcessing.retrieveConversationHistory();
                    } catch (IOException e) {
                        Toast.makeText(getApplicationContext(), "Mhm... are you sure that was a " +
                                "WhatsApp conversation text file?", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case ANALYZE_REQUEST_CODE:
                    break;

                case GRAPH_REQUEST_CODE:
                    break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
