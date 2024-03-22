package com.example.tugasday5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {
    EditText teks1, teks2, teks3;
    RadioGroup radioGroup;
    Button btnproses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        teks1 = findViewById(R.id.teks1);
        teks2 = findViewById(R.id.teks2);
        teks3 = findViewById(R.id.teks3);
        radioGroup = findViewById(R.id.radioGroup);
        btnproses = findViewById(R.id.btnproses);

        btnproses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processTransaction();
            }
        });

    }

    private void processTransaction() {
        String nama = teks1.getText().toString().trim();
        String kode = teks2.getText().toString().trim();
        int jumlah = Integer.parseInt(teks3.getText().toString().trim());
        String tipepelanggan = getTipepelanggan();
        Intent intent = new Intent(MainActivity.this, TotalHarga.class);
        intent.putExtra("nama", nama);
        intent.putExtra("Kode", kode);
        intent.putExtra("jumlah", jumlah);
        intent.putExtra("tipepelanggan", tipepelanggan);
        startActivity(intent);
    }
    private String getTipepelanggan() {
        int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
        return selectedRadioButton.getText().toString();
    }
}
