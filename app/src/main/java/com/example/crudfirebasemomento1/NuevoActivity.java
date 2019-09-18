package com.example.crudfirebasemomento1;

import android.content.Intent;
import android.os.Bundle;

import com.example.crudfirebasemomento1.models.TareaModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NuevoActivity extends AppCompatActivity {

    private EditText et_nuevo_nino,et_nuevo_maretia,et_nuevo_tarea,et_nuevo_descricion,et_nuevo_docente;
    private Button fab_nuevo_guardar2;
    private FloatingActionButton fab_nuevo_guardar;
    private TareaModel tareaModelmodel;

    private final String text_reference = ("clientes");
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference(text_reference);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo);
        Toolbar toolbar = findViewById(R.id.toolbar_nuevo);
        setSupportActionBar(toolbar);

        et_nuevo_nino = findViewById(R.id.et_nuevo_nino);
        et_nuevo_maretia = findViewById(R.id.et_nuevo_maretia);
        et_nuevo_tarea = findViewById(R.id.et_nuevo_tarea);
        et_nuevo_descricion = findViewById(R.id.et_nuevo_descricion);
        et_nuevo_docente = findViewById(R.id.et_nuevo_docente);




        fab_nuevo_guardar = findViewById(R.id.fab_nuevo_guardar);
        fab_nuevo_guardar2 = findViewById(R.id.fab_nuevo_guardar2);


        fab_nuevo_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String nino = et_nuevo_nino.getText().toString();
                String materia = et_nuevo_maretia.getText().toString();
                String tarea = et_nuevo_tarea.getText().toString();
                String descrcion = et_nuevo_descricion.getText().toString();
                String cocente = et_nuevo_docente.getText().toString();

            if (!nino.equals("") && !materia.equals("") && !tarea.equals("") && !descrcion.equals("") && !cocente.equals("")){
                String id = reference.push().getKey();

                if (id != null && !id.equals("")) {
                    tareaModelmodel = new TareaModel(id, nino, materia, tarea, descrcion, cocente);


                    reference.child(id).setValue(tareaModelmodel)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    if (!tareaModelmodel.get_id().equals("") && tareaModelmodel.get_id() != null) {
                                        Intent detalle = new Intent(NuevoActivity.this, DetalleActivity.class);
                                        detalle.putExtra("id", tareaModelmodel.get_id());
                                        startActivity(detalle);
                                        finish();
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Snackbar.make(view, "No Sepudo Guardar los datos Revisalos", Snackbar.LENGTH_LONG).show();
                                }
                            });
                }else {
                    Snackbar.make(view, "Problemas Al Crear ID en  La Base De Datos", Snackbar.LENGTH_LONG).show();
                }
            }else {
                Toast.makeText(NuevoActivity.this, "Por Favor Ingresar Todos los Datos",Toast.LENGTH_SHORT).show();
            }
            }
        });
        fab_nuevo_guardar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String nino = et_nuevo_nino.getText().toString();
                String materia = et_nuevo_maretia.getText().toString();
                String tarea = et_nuevo_tarea.getText().toString();
                String descrcion = et_nuevo_descricion.getText().toString();
                String cocente = et_nuevo_docente.getText().toString();

                if (!nino.equals("") && !materia.equals("") && !tarea.equals("") && !descrcion.equals("") && !cocente.equals("")){
                String id = reference.push().getKey();

                if (id != null) {
                    tareaModelmodel = new TareaModel(id, nino, materia, tarea, descrcion, cocente);


                    reference.child(id).setValue(tareaModelmodel)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Intent lista = new Intent(NuevoActivity.this, MainActivity.class);
                                    startActivity(lista);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Snackbar.make(view, "No Sepudo Guardar los datos Revisalos", Snackbar.LENGTH_LONG).show();
                                }
                            });
                }else {
                    Snackbar.make(view, "Problemas Al Crear ID en  La Base De Datos", Snackbar.LENGTH_LONG).show();
                }
            }else {
                Toast.makeText(NuevoActivity.this, "Por Favor Ingresar Todos los Datos", Toast.LENGTH_SHORT).show();
            }
        }
        });
    }

}
