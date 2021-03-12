package com.example.week8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity{
    private EditText fileNameEdit;
    private Context context;
    private TextView txtOut;
    private TextView txtCoins;
    private SeekBar seekBar;
    private Spinner spinner;
    private BottleDispenser bd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fileNameEdit = (EditText) findViewById(R.id.editTextReceiptName);
        context = MainActivity.this;
        txtOut = (TextView) findViewById(R.id.textOutput);
        txtCoins = (TextView) findViewById(R.id.textCoins);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txtCoins.setText(""+progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.Sodas, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        bd = BottleDispenser.getInstance();
    }


    public void handleReceiptEvent(View v){
        try {
            OutputStreamWriter osw = new OutputStreamWriter(context.openFileOutput(fileNameEdit.toString(), Context.MODE_PRIVATE));
            osw.write("Kuitti:\n\n");
            osw.write(bd.printReceipt());
            osw.close();
        } catch (FileNotFoundException e){
            Log.e("FileNotFound", "Ei tiedostoa");
        } catch (IOException e) {
            Log.e("IOException", "Virhe syötteessä");
            e.printStackTrace();
        } finally {
            System.out.println("Tallennettu\n");
            System.out.println(getFilesDir());
        }
    }
    public void handleBuyEvent(View v){
        int selectedItem = spinner.getSelectedItemPosition();
        txtOut.setText(bd.buyBottle(selectedItem));
    }
    public void handleReturnEvent(View v){
        txtOut.setText(bd.returnMoney());
    }
    public void handleAddEvent(View v){
        int amountAdded = seekBar.getProgress();
        txtOut.setText(bd.addMoney(amountAdded));
    }
}