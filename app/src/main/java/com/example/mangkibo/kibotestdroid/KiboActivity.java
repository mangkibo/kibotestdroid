package com.example.mangkibo.kibotestdroid;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class KiboActivity extends AppCompatActivity {

    public Button button;
    public Button open_btn;
    public String attachment;
    public String privateFilesDirectory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kibo);

        button = (Button) findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.w("activity_kibo","yes bray");
                // Perform action on click
            }
        });

        open_btn = (Button) findViewById(R.id.openGmail);

        open_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Uri path = null;
                Writer output = null;
                String fileName = new SimpleDateFormat("yyyyMMddHHmm'.json'").format(new Date());
                String jsonContent = " { 'Planet' : 'Namec', 'FileName' : '" + fileName + "'  } ";

                privateFilesDirectory = getApplicationContext().getFilesDir().getAbsolutePath();

                if (getApplicationContext().getExternalFilesDir(null) != null) {
                    attachment = getApplicationContext().getExternalFilesDir("/")
                            .getAbsolutePath() + "/" + fileName;
                } else {
                    attachment = privateFilesDirectory + "/" + fileName;
                }

                Log.w("Storage Location", attachment);

                File file = new File(attachment);

                try {
                    if(!file.exists()) {
                        file.createNewFile();

                        output = new BufferedWriter(new FileWriter(file));
                        output.write(jsonContent);
                        output.close();

                        path = Uri.fromFile(file);
                        Log.w("File Status", "Create New File :" + path.toString());
                    } else {
                        Log.w("File Status", "File Exist");
                    }

                    String[] address = {
                            "mangkibo.boo@gmail.com"
                    };

                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_EMAIL, address);
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                    intent.putExtra(Intent.EXTRA_TEXT, "I'm email body.");
                    intent.putExtra(Intent.EXTRA_STREAM, path);

                    startActivity(intent.createChooser(intent, "Send Email"));
                } catch (IOException e) {
                    //Log.w("Button Attach File", attachment);
                    e.printStackTrace();
                }
            }
        });
    }
}
