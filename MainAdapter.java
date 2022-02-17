package com.example.kamalavianapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends FirebaseRecyclerAdapter<MainModel,MainAdapter.myViewHolder>  {


    ArrayList<MainModel> data;
    Context context;
    RecyclerView rv1;
    Button  ViewCartBtn;



    FirebaseDatabase firebaseDatabase;
    // ActivityMainBinding binding;
    DatabaseReference databaseReference;

    private static MyAdapterListener onClickListener;

    public MainAdapter(FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }

    public interface MyAdapterListener {

     //   void addToCartOnClick(View v, int position);

        void addToCartOnClick( String n, String p,String img, String quantity, String p1);
    }
    public void setOnItemClickListener(MyAdapterListener myClickListener) {
        MainAdapter.onClickListener = myClickListener;
    }

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MainAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options, MyAdapterListener listener) {

        super(options);
        this.context= context;
        onClickListener=listener;
    }


    private List<Model> uploads;

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull MainModel model) {


        //Model upload = uploads.get(position);
 //final MainModel temp = data.get(position);



        holder.name.setText(model.getName());
        holder.symptoms.setText(model.getSymptoms());
        holder.price.setText(model.getPrice());


         String  n = model.getName();
        String p = model.getPrice();
        String s= model.getSymptoms();
        String imgurl = model.getImageURl();
        String quantity = ""+1;
       // int q = Integer.parseInt(quantity);
        String total = p;

        Picasso.get()
                .load(model.getImageURl())
                .fit()
                .centerCrop()
                .into(holder.img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent i = new Intent(v.getContext(), ProductsDetailActivity.class);
                    i.putExtra("Name", n);
                i.putExtra("Price", p);
                i.putExtra("Symptoms", s);
                i.putExtra("ImageURL", imgurl);
                    v.getContext().startActivity(i);
                    notifyDataSetChanged();
                }

        });



        holder.addcartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(LoginActivity.username==null)
                {
                    Toast.makeText(v.getContext(), "Login First and then Shop!", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(v.getContext(), LoginActivity.class);
                    v.getContext().startActivity(i);
                }
                else {

                    onClickListener.addToCartOnClick(model.getName(), model.getPrice(), model.getImageURl(), quantity, model.getName());
                }

            }
        });

//        holder.addcartBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String userId=LoginActivity.username;
//
//                firebaseDatabase= FirebaseDatabase.getInstance();
//                databaseReference= firebaseDatabase.getReference("Cart");
//
//                Query check_Cart = databaseReference.child(userId).child(model.getName());
//
//                check_Cart.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        if (snapshot.exists()) {
//                            Toast.makeText(v.getContext(),"Already Exists",Toast.LENGTH_SHORT).show();
//                        }
//                        else
//                        {
//
//                            addToCart(userId,model.getName(),model.getPrice(),model.getImageURl(),quantity,model.getPrice());
//                            Toast.makeText(v.getContext(),"Added to Cart",Toast.LENGTH_SHORT).show();
//
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//           //     OrderModel orderModel=new OrderModel(model.getName(),model.getPrice(),model.getImageURl(),quantity,model.getPrice());
//
//
//
//                //notifyItemChanged(holder.getAdapterPosition,());
//          //      databaseReference.child(userId).child(model.getName()).setValue(orderModel);
//
//       //         holder.addcartBtn.setEnabled(false);
//            }
//        });



//        addcartBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////
////                firebaseDatabase = FirebaseDatabase.getInstance();
////                databaseReference = firebaseDatabase.getReference("Cart");
//                String userId=LoginActivity.username;
//                OrderModel orderModel=new OrderModel(n,p,imgurl,quantity,total);
//                databaseReference.child(userId).child(n).setValue(orderModel);
//
//
         //       Toast.makeText(v.getContext(),"Added to Cart",Toast.LENGTH_SHORT).show();
//
//             //   addcartBtn.setClickable(false);
//              //  addcartBtn.setEnabled(false);
//
//            }
//        });


    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item,parent,false);
        return new myViewHolder(view);
    }



    class  myViewHolder extends RecyclerView.ViewHolder  implements  View.OnClickListener {

        ImageView img;
        TextView name,symptoms,price;
        RecyclerView rv1;
        Button addcartBtn;
        private WeakReference listenerRef;



        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            listenerRef = new WeakReference(listenerRef);
            img = itemView.findViewById(R.id.img1);
            name = itemView.findViewById(R.id.nametxt);
            symptoms = itemView.findViewById(R.id.symptomstxt);
            price = itemView.findViewById(R.id.pricetxt);
            addcartBtn = itemView.findViewById(R.id.AddtoCartBtn1);
            addcartBtn.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

            Toast.makeText(v.getContext(), "Clickedd"+ getAdapterPosition(), Toast.LENGTH_SHORT).show();

        }
    }
}
