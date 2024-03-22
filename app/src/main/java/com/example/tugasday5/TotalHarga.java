package com.example.tugasday5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

public class TotalHarga extends AppCompatActivity {
    TextView tvnamapembeli, tvtipepelanggan, tvItemCode, tvItemName, tvItemPrice, tvTotalPrice,
            tvDiscount, tvMemberDiscount, tvTotalPayment, tvTerimakasih;

    // Data barang
    String[] itemCodes = {"OAS", "IPX", "AV4"};
    String[] itemNames = {"Oppo a5s", "Iphone X", "Asus Vivobook 14"};
    double[] itemPrices = {1989123, 5725300, 9150999};

    // Variabel diskon
    double discountBiasa = 0.02; // 2%
    double discountSilver = 0.05; // 5%
    double discountGold = 0.10; // 10%
    double discountThreshold = 10000000; // Batas diskon

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_harga);

        tvnamapembeli = findViewById(R.id.tvnamapembeli);
        tvtipepelanggan = findViewById(R.id.tvtipepelanggan);
        tvItemCode = findViewById(R.id.tvItemCode);
        tvItemName = findViewById(R.id.tvItemName);
        tvItemPrice = findViewById(R.id.tvItemPrice);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        tvDiscount = findViewById(R.id.tvDiscount);
        tvMemberDiscount = findViewById(R.id.tvMemberDiscount);
        tvTotalPayment = findViewById(R.id.tvTotalPayment);
        tvTerimakasih = findViewById(R.id.tvTerimakasih);

        // Mendapatkan data dari intent sebelumnya
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String namapembeli = extras.getString("nama");
            String tipepelanggan = extras.getString("tipepelanggan");
            String itemCode = extras.getString("Kode");
            int unit = extras.getInt("jumlah");

            // Menampilkan data customer dan membership type
            tvnamapembeli.setText(namapembeli);
            tvtipepelanggan.setText("Tipe Pelanggan: " + tipepelanggan);

            // Menghitung total harga dan diskon
            int selectedItemIndex = findItemIndex(itemCode);
            double itemPrice = itemPrices[selectedItemIndex] * unit;
            double discountAmount = calculateDiscount(itemPrice, tipepelanggan);
            double totalPrice = itemPrice - discountAmount;
            double totalPayment = totalPrice;

            // Menampilkan data barang dan transaksi
            tvItemCode.setText("Kode barang: " + itemCode);
            tvItemName.setText("Nama Barang: " + itemNames[selectedItemIndex]);
            tvItemPrice.setText("Harga: Rp " + formatPrice(itemPrice));
            tvTotalPrice.setText("Total Harga: Rp " + formatPrice(totalPrice));
            tvDiscount.setText("Discount harga: Rp " + formatPrice(discountAmount));
            tvMemberDiscount.setText("Discount member: Rp " + formatPrice(discountAmount));
            tvTotalPayment.setText("Jumlah Bayar: Rp " + formatPrice(totalPayment));
            tvTerimakasih.setText("♡⊹˚₊TERIMA KASIH SUDAH BERBELANJA DISINI ₊˚⊹♡");

            // Set onClickListener untuk tombol Share
            Button btnShare = findViewById(R.id.btnshare);
            btnShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    shareTransaction();
                }
            });
        }
    }

    // Fungsi untuk mencari indeks item berdasarkan kode
    private int findItemIndex(String itemCode) {
        for (int i = 0; i < itemCodes.length; i++) {
            if (itemCodes[i].equals(itemCode)) {
                return i;
            }
        }
        return -1;
    }

    // Fungsi untuk menghitung diskon
    private double calculateDiscount(double price, String membershipType) {
        double discountAmount = 0;

        // Menerapkan diskon berdasarkan tipe member
        if (membershipType.equals("Biasa")) {
            discountAmount = price * discountBiasa;
        } else if (membershipType.equals("Silver")) {
            discountAmount = price * discountSilver;
        } else if (membershipType.equals("Gold")) {
            discountAmount = price * discountGold;
        }

        // Menambahkan diskon tambahan jika total harga melebihi threshold
        if (price > discountThreshold) {
            discountAmount += 100000;
        }

        return discountAmount;
    }

    // Fungsi untuk memformat harga dengan format Rp XXX.XXX.XXX
    private String formatPrice(double price) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(price);
    }

    // Fungsi untuk berbagi transaksi ke media sosial
    private void shareTransaction() {
        String message = tvnamapembeli.getText().toString() + "\n" +
                tvtipepelanggan.getText().toString() + "\n" +
                tvItemCode.getText().toString() + "\n" +
                tvItemName.getText().toString() + "\n" +
                tvItemPrice.getText().toString() + "\n" +
                tvTotalPrice.getText().toString() + "\n" +
                tvDiscount.getText().toString() + "\n" +
                tvMemberDiscount.getText().toString() + "\n" +
                tvTotalPayment.getText().toString() + "\n" +
                tvTerimakasih.getText().toString();

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, message);
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }
}
