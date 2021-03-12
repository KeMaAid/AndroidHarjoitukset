package com.example.week7;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.widget.EditText;
import android.text.TextWatcher;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    EditText fileNameEdit;
    EditText et;
    Context context;
    String fileName;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fileNameEdit = (EditText) findViewById(R.id.editTextTextPersonName2);
        context = MainActivity.this;
        txt = (TextView) findViewById(R.id.textView3);
        et = (EditText) findViewById(R.id.editTextTextPersonName);
        fileNameEdit.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable e){
                fileName = fileNameEdit.getText().toString();
                txt.setText(("Käsiteltävä tiedosto on: "+ fileName));
            }
        });
    }

    public void handleLoad(View v){
        try {
            InputStream ins = context.openFileInput(fileName);

            BufferedReader br = new BufferedReader(new InputStreamReader(ins));
            String buffer;
            et.setText("");
            while((buffer=br.readLine()) != null){
                et.append(buffer);
            }
            br.close();
            ins.close();
        } catch (FileNotFoundException e){
            Log.e("FileNotFound", "Ei tiedostoa");
        } catch (IOException e) {
            Log.e("IOException", "Virhe syötteessä");
            e.printStackTrace();
        } finally {
            System.out.println("Luettu");
        }
    }

    public void handleSave(View v){
        try {
            OutputStreamWriter osw = new OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE));

            osw.write(et.getText().toString());
            osw.close();
        } catch (FileNotFoundException e){
            Log.e("FileNotFound", "Ei tiedostoa");
        } catch (IOException e) {
            Log.e("IOException", "Virhe syötteessä");
            e.printStackTrace();
        } finally {
            System.out.println("Tallennettu");
        }

    }
}