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
import android.widget.EditText;
import android.widget.Toast;

public class EditarActivity extends AppCompatActivity {
    private EditText et_editar_nino,et_editar_materia,et_editar_tarea,et_editar_descrcion,et_editar_cocente;
    private FloatingActionButton fab_editar_guardar;
    private TareaModel tareaModelmodel;

    private final String text_reference = ("clientes");
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference(text_reference);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        Toolbar toolbar = findViewById(R.id.toolbar_editar);
        setSupportActionBar(toolbar);

        et_editar_nino = findViewById(R.id.et_editar_nino);
        et_editar_materia = findViewById(R.id.et_editar_materia);
        et_editar_tarea = findViewById(R.id.et_editar_tarea);
        et_editar_descrcion = findViewById(R.id.et_editar_descrcion);
        et_editar_cocente = findViewById(R.id.et_editar_cocente);


        fab_editar_guardar = findViewById(R.id.fab_editar_guardar);
        tareaModelmodel = new TareaModel();

        String id = getIntent().getStringExtra("id");
        if (id != null && !id.equals("")){
            reference.child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    tareaModelmodel = dataSnapshot.getValue(TareaModel.class);
                    if (tareaModelmodel != null){
                        et_editar_nino.setText(tareaModelmodel.getNino());
                        et_editar_materia.setText(tareaModelmodel.getMateria());
                        et_editar_tarea.setText(tareaModelmodel.getTarea());
                        et_editar_descrcion.setText(tareaModelmodel.getDescrcion());
                        et_editar_cocente.setText(tareaModelmodel.getCocente());
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
                String nino = et_editar_nino.getText().toString();
                String materia = et_editar_materia.getText().toString();
                String tarea = et_editar_tarea.getText().toString();
                String descrcion = et_editar_descrcion.getText().toString();
                String cocente = et_editar_cocente.getText().toString();

                if (!nino.equals("") && !materia.equals("") && !tarea.equals("") && !descrcion.equals("") && !cocente.equals("")){
                    if (tareaModelmodel != null) {
                    String id = tareaModelmodel.get_id();

                    if (id != null && !id.equals("")) {
                        tareaModelmodel.setNino(nino);
                        tareaModelmodel.setMateria(materia);
                        tareaModelmodel.setTarea(tarea);
                        tareaModelmodel.setDescrcion(descrcion);
                        tareaModelmodel.setCocente(cocente);

                        reference.child(id).setValue(tareaModelmodel)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        if (!tareaModelmodel.get_id().equals("") && tareaModelmodel.get_id() != null) {
                                            Intent detalle = new Intent(EditarActivity.this, MainActivity.class);
                                            detalle.putExtra("id", tareaModelmodel.get_id());
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
