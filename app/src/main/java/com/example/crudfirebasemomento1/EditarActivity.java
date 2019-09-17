package com.example.crudfirebasemomento1;

import android.content.Intent;
import android.os.Bundle;

import com.example.crudfirebasemomento1.models.ClienteModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditarActivity extends AppCompatActivity {
    private EditText et_editar_cedula,et_editar_nombre,et_editar_apellido;
    private FloatingActionButton fab_editar_guardar;
    private ClienteModel model;

    private final String text_reference = ("clientes");
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference(text_reference);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        Toolbar toolbar = findViewById(R.id.toolbar_editar);
        setSupportActionBar(toolbar);

        et_editar_cedula = findViewById(R.id.et_editar_cedula);
        et_editar_nombre = findViewById(R.id.et_editar_nombre);
        et_editar_apellido = findViewById(R.id.et_editar_apellido);
        fab_editar_guardar = findViewById(R.id.fab_editar_guardar);
        model = new ClienteModel();

        String id = getIntent().getStringExtra("id");
        if (id != null && !id.equals("")){
            reference.child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    model = dataSnapshot.getValue(ClienteModel.class);
                    if (model != null){
                        et_editar_cedula.setText(model.get_cedula());
                        et_editar_nombre.setText(model.get_nombre());
                        et_editar_apellido.setText(model.get_apellido());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(EditarActivity.this, "ERROR CON FIREBASE", Toast.LENGTH_SHORT).show();
                }
            });
        }

        fab_editar_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( final View view) {
                String cedula = et_editar_cedula.getText().toString();
                String nombre = et_editar_nombre.getText().toString();
                String apellido = et_editar_apellido.getText().toString();

                if (!cedula.equals("") && !nombre.equals("") && !apellido.equals("")) {
                    if (model != null) {
                    String id = model.get_id();

                    if (id != null && !id.equals("")) {
                        model.set_cedula(cedula);
                        model.set_nombre(nombre);
                        model.set_apellido(apellido);

                        reference.child(id).setValue(model)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        if (!model.get_id().equals("") && model.get_id() != null) {
                                            Intent detalle = new Intent(EditarActivity.this, MainActivity.class);
                                            detalle.putExtra("id", model.get_id());
                                            startActivity(detalle);
                                            finish();
                                        }
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Snackbar.make(view, "No Sepudo Actualizar los datos Revisalos", Snackbar.LENGTH_LONG).show();
                                    }
                                });
                    } else {
                        Snackbar.make(view, "Problemas Al Crear ID en  La Base De Datos", Snackbar.LENGTH_LONG).show();
                    }
                }
                }else {
                    Toast.makeText(EditarActivity.this, "Por Favor Ingresar Todos los Datos", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }

}
