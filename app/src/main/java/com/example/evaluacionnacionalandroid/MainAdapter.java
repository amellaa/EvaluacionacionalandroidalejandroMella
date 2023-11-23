package com.example.evaluacionnacionalandroid;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapter extends FirebaseRecyclerAdapter<MainModel, MainAdapter.myViewHolder> {
    public MainAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull MainModel model) {
        holder.producto.setText(model.getProducto());
        holder.precio.setText(model.getPrecio());
        holder.codigo.setText(model.getCodigo());

        Glide.with(holder.img.getContext())
                .load(model.getImgURL())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.google.firebase.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.img);

        holder.editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                Log.d("MainAdapter", "Editar clic en posición: " + adapterPosition);

                if (adapterPosition != RecyclerView.NO_POSITION) {
                    final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                            .setContentHolder(new ViewHolder(R.layout.ventana_emergente))
                            .create();

                    View contentView = dialogPlus.getHolderView();

                    if (contentView != null) {
                        EditText producto = contentView.findViewById(R.id.productotxt);
                        EditText precio = contentView.findViewById(R.id.preciotxt);
                        EditText codigo = contentView.findViewById(R.id.codigotxt);
                        EditText imgURL = contentView.findViewById(R.id.img1);

                        producto.setText("");
                        precio.setText("");
                        codigo.setText("");
                        imgURL.setText("");

                        Button actualizar = contentView.findViewById(R.id.btn_actualizar);

                        dialogPlus.show();

                        actualizar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Map<String, Object> map = new HashMap<>();
                                map.put("Producto", producto.getText().toString());
                                map.put("Codigo", codigo.getText().toString());
                                map.put("Precio", precio.getText().toString());
                                map.put("imgURL", imgURL.getText().toString());

                                FirebaseDatabase.getInstance().getReference().child("Programacion Android")
                                        .child(getRef(adapterPosition).getKey()).updateChildren(map)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(holder.producto.getContext(), "Actualizacion Correcta", Toast.LENGTH_SHORT).show();
                                                dialogPlus.dismiss();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(holder.producto.getContext(), "Error de la Actualizacion", Toast.LENGTH_SHORT).show();
                                                dialogPlus.dismiss();
                                            }
                                        });
                            }
                        });
                    }

                    Log.d("MainAdapter", "Mostrando ventana emergente para posición: " + adapterPosition);
                }
            }
        });

        holder.eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.producto.getContext());
                builder.setTitle("¿Estás seguro de ELIMINARLO?");
                builder.setMessage("ELIMINADO");

                builder.setPositiveButton("ELIMINAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("Programacion Android")
                                .child(getRef(position).getKey()).removeValue()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.producto.getContext(), "Eliminación exitosa", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.producto.getContext(), "Error en la eliminación", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });

                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.producto.getContext(), "Cancelar", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.show();
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item, parent, false);
        return new myViewHolder(view);
    }

    static class myViewHolder extends RecyclerView.ViewHolder {
        CircleImageView img;
        TextView producto, precio, codigo;
        Button editar, eliminar;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img1);
            producto = itemView.findViewById(R.id.productotxt);
            precio = itemView.findViewById(R.id.preciotxt);
            codigo = itemView.findViewById(R.id.codigotxt);

            editar = itemView.findViewById(R.id.btn_editar);
            eliminar = itemView.findViewById(R.id.btn_eliminar);
        }
    }
}