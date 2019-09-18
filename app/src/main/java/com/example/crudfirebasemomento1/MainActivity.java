package com.example.crudfirebasemomento1;

import android.content.Intent;
import android.os.Bundle;

import com.example.crudfirebasemomento1.adapters.TareaAdater;
import com.example.crudfirebasemomento1.models.TareaModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lv_main_contactos;
    private FloatingActionButton fab_main_nuevo;
    private ArrayList<TareaModel> list;
    private TareaModel tareaModelmodel;


    private final String text_reference = ("clientes");
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference(text_reference);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        fab_main_nuevo = findViewById(R.id.fab_main_nuevo);
        lv_main_contactos = findViewById(R.id.lv_main_contactos);
        list = new ArrayList<>();
        tareaModelmodel = new TareaModel();


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    tareaModelmodel = child.getValue(TareaModel.class);
                    if (tareaModelmodel != null) {
                        list.add(tareaModelmodel);
                    }
                }
                lv_main_contactos.setAdapter(new TareaAdater(MainActivity.this, list));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "ERROR CON FIREBASE", Toast.LENGTH_SHORT).show();
            }
        });

        lv_main_contactos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> AdapterView, View view, int i, long l) {
                tareaModelmodel = (TareaModel) AdapterView.getItemAtPosition(i);
                if (tareaModelmodel != null) {
                    if (!tareaModelmodel.get_id().equals("") && tareaModelmodel.get_id() != null) {
                        Intent detalle = new Intent(MainActivity.this, DetalleActivity.class);
                        detalle.putExtra("id", tareaModelmodel.get_id());
                        startActivity(detalle);
                    }
                }

            }
        });


        fab_main_nuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nuevo = new Intent(MainActivity.this, NuevoActivity.class);
                startActivity(nuevo);
            }
        });
    }

}
