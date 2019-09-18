package com.example.crudfirebasemomento1;

import android.content.Intent;
import android.os.Bundle;

import com.example.crudfirebasemomento1.models.TareaModel;
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
import android.widget.TextView;
import android.widget.Toast;

public class DetalleActivity extends AppCompatActivity {
    private TextView tv_detalle_nino,tv_detalle_materia,tv_detalle_tarea,tv_detalle_descrcion,tv_detalle_cocente;
    private FloatingActionButton fab_detalle_editar,fab_detalle_eliminar;
    private TareaModel tareaModelmodel;

    private final String text_reference = ("clientes");
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference(text_reference);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        Toolbar toolbar = findViewById(R.id.toolbar_detalle);
        setSupportActionBar(toolbar);

        fab_detalle_editar = findViewById(R.id.fab_detalle_editar);
        fab_detalle_eliminar = findViewById(R.id.fab_detalle_eliminar);
        tv_detalle_nino = findViewById(R.id.tv_detalle_nino);
        tv_detalle_materia = findViewById(R.id.tv_detalle_materia);
        tv_detalle_tarea = findViewById(R.id.tv_detalle_tarea);
        tv_detalle_descrcion = findViewById(R.id.tv_detalle_descrcion);
        tv_detalle_cocente = findViewById(R.id.tv_detalle_cocente);

        tareaModelmodel = new TareaModel();

        String id = getIntent().getStringExtra("id");
        if (id != null && !id.equals("")){
            reference.child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    tareaModelmodel = dataSnapshot.getValue(TareaModel.class);
                    if (tareaModelmodel != null){
                        tv_detalle_nino.setText(tareaModelmodel.getNino());
                        tv_detalle_materia.setText(tareaModelmodel.getMateria());
                        tv_detalle_tarea.setText(tareaModelmodel.getTarea());
                        tv_detalle_descrcion.setText(tareaModelmodel.getDescrcion());
                        tv_detalle_cocente.setText(tareaModelmodel.getCocente());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(DetalleActivity.this, "ERROR CON FIREBASE", Toast.LENGTH_SHORT).show();
                }
            });
        }


        fab_detalle_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tareaModelmodel != null) {
                    if (tareaModelmodel.get_id() != null && !tareaModelmodel.get_id().equals("")) {
                        Intent editar = new Intent(DetalleActivity.this, EditarActivity.class);
                        editar.putExtra("id", tareaModelmodel.get_id());
                        startActivity(editar);
                    }
                }
            }
        });

        fab_detalle_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackbar = Snackbar.make(view, "Seguro quieres Eliminarlo?",Snackbar.LENGTH_LONG);
                snackbar.setAction("Estoy Seguro!", new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        if (tareaModelmodel != null) {
                            reference.child(tareaModelmodel.get_id()).removeValue()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                                Intent inicio = new Intent(DetalleActivity.this, MainActivity.class);
                                                startActivity(inicio);
                                                finish();

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Snackbar.make(view, "No Sepudo Eliminar los datos Revisalos", Snackbar.LENGTH_LONG).show();
                                        }
                                    });
                        }
                    }
                });
                snackbar.show();
            }
        });
    }

}
