package com.example.aula9;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class DepartamentoActivity extends AppCompatActivity {

    private Button btCadastrarDept;
    private Button btEditarDept;
    private Button btDeletarDept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departamento);

        btCadastrarDept = findViewById(R.id.btCadastrarDept);
        btEditarDept = findViewById(R.id.btEditarDept);
        btDeletarDept = findViewById(R.id.btDeletarDept);

    }
}