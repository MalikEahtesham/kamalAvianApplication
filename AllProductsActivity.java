package com.example.kamalavianapp;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AllProductsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MainAdapter mainAdapter;
    Button viewCartBtn;
    FloatingActionButton floatingActionButton ;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_products);
       floatingActionButton = findViewById(R.id.floatingActionButton);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("Cart");
                if (LoginActivity.username == null) {

                    Toast.makeText(getApplicationContext(), "First Login and then Shop :)", Toast.LENGTH_SHORT).show();

                } else {
                    Query check_Email = databaseReference.child(LoginActivity.username);

                    check_Email.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {

                                Intent i = new Intent(AllProductsActivity.this, CartActivity.class);
                                startActivity(i);



                            } else {
                                Toast.makeText(getApplicationContext(), "Ooopss! Cart is empty...", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }


            }



        });

        floatingActionButton = findViewById(R.id.floatingActionButton);
        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<MainModel> options =
                new FirebaseRecyclerOptions.Builder<MainModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("AllProducts"), MainModel.class)
                        .build();


        mainAdapter = new MainAdapter(options, new MainAdapter.MyAdapterListener() {


            @Override
            public void addToCartOnClick(String n, String p,String img, String quantity, String p1) {
                firebaseDatabase= FirebaseDatabase.getInstance();
                databaseReference= firebaseDatabase.getReference("Cart");

                Query check_Cart = databaseReference.child(LoginActivity.username).child(n);

                check_Cart.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            Toast.makeText(getApplicationContext(),"Already Exists",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            OrderModel order = new OrderModel(n,p,img,quantity,p);
                            databaseReference.child(LoginActivity.username).child(n).setValue(order);
                            Toast.makeText(getApplicationContext(),"Added to Cart",Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                mainAdapter.notifyDataSetChanged();

            }

        });
        recyclerView.setAdapter(mainAdapter);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("Cart");
                if (LoginActivity.username == null) {

                    Toast.makeText(getApplicationContext(), "First Login and then Shop :)", Toast.LENGTH_SHORT).show();
                }
                else {
                    Query check_Email = databaseReference.child(LoginActivity.username);

                    check_Email.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {

                                Intent i = new Intent(AllProductsActivity.this, CartActivity.class);
                                startActivity(i);


                            } else {
                                Toast.makeText(getApplicationContext(), "Ooopss! Cart is empty...", Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }

            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {

                if(dy>0)
                {
                    floatingActionButton.hide();

                }
                else
                {
                    floatingActionButton.show();
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }
   //     mainAdapter.notifyDataSetChanged();


//        bottomNavigationView = findViewById(R.id.bottom_nav);
//
//        bottomNavigationView.setSelectedItemId(R.id.nav_home1);
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//                switch (item.getItemId()){
//                    case R.id.nav_home1:
//                        startActivity(new Intent(getApplicationContext(),HomePageActivity.class));
//                        overridePendingTransition(0,0);
//
//                        return  true;
//                    case R.id.nav_info:
//                        startActivity(new Intent(getApplicationContext(),AboutUsActivity.class));
//                        overridePendingTransition(0,0);
//                        return  true;
//                    case R.id.nav_cart:
//
//                        return  true;
//                }
//
//                return false;
//            }
//        });
//
//    }
//


    @Override
    protected void onStart() {
        super.onStart();
        mainAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainAdapter.stopListening();
    }

  //  @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.searchmenu,menu);
//        MenuItem item=menu.findItem(R.id.search);
//        SearchView searchView=(SearchView) item.getActionView();
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                processsearch(s);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                processsearch(s);
//                return false;
//            }
//        });
//
//        return super.onCreateOptionsMenu(menu);
//    }
//    private  void  processsearch(String s)
//    {
//        FirebaseRecyclerOptions<MainModel> options =
//                new FirebaseRecyclerOptions.Builder<MainModel>()
//                        .setQuery(FirebaseDatabase.getInstance().getReference().child("AllProducts").orderByChild("name").startAt(s).endAt(s+"\uf8ff") ,MainModel.class)
//                        .build();
//
//        mainAdapter = new MainAdapter(options);
//        mainAdapter.startListening();
//        recyclerView.setAdapter(mainAdapter);
//    }

}