package com.example.webf1movil1704;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;


public class activity3 extends AppCompatActivity {

    private ProductAdapter productAdapter;
    private List<Product> productList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        RecyclerView recyclerView = findViewById(R.id.itemproduc);
        Button procButton = this.findViewById(R.id.proc);


        Button reser = findViewById(R.id.reser);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productList = new ArrayList<>();
        productAdapter = new ProductAdapter(this, productList);
        recyclerView.setAdapter(productAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);

        recyclerView.addItemDecoration(dividerItemDecoration);



        loadProducts();

        procButton.setOnClickListener(v -> {

            Intent intent = new Intent(activity3.this, MainActivity.class);
            startActivity(intent);
        });


        reser.setOnClickListener(v -> {

            Intent intent = new Intent(activity3.this, reserva.class);
            startActivity(intent);
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadProducts() {


        productList.add(new Product("GRAN HOREB CONDOMINIO CAMPESTRE", R.drawable.img, getString(R.string.product_price1)));
        productList.add(new Product("FINCAS LA FLORIDA", R.drawable.img_1, getString(R.string.product_price2)));
        productList.add(new Product("OASIS DEL OLIMPO CONJUNTO PARCELACION", R.drawable.ima2, getString(R.string.product_price3)));
        productAdapter.notifyDataSetChanged();

    }
}

