package com.example.aula9;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    private CursoService requestCurso;
    private CursoResponse cursoatualizado;
    private List<String> listaNomeCursos;
    private ArrayAdapter<String>adapter;

    private EditText enviarMensagem = null;
    private EditText etIdCurso;
    private Button btEnviar;
    private Button btEditar;
    private Button btDelete;
    private ListView lvListaCurso;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaNomeCursos = new ArrayList<>();

        //CursoPost cursoPost = new CursoPost();
        //cursoPost.setName("Nome do Curso - Lorena 26/05");

        requestCurso = new RetrofitConfig()
                .criarService();

        enviarMensagem = findViewById(R.id.etMensagem);
        btEnviar = findViewById(R.id.btEnviar);
        btEditar = findViewById(R.id.btEditar);
        btDelete = findViewById(R.id.btDelete);
        etIdCurso = findViewById(R.id.etIdCurso);
        lvListaCurso = findViewById(R.id.lvListaCursos);

        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, listaNomeCursos);
        lvListaCurso.setAdapter(adapter);

        alterarCurso();
        deletarCurso();
        btEnviar.setOnClickListener(view -> {
            String conteudo = enviarMensagem.getText().toString();

            CursoPost novoCurso = new CursoPost();
            novoCurso.setName(conteudo);

            requestCurso.createRequestPost(novoCurso).enqueue(new Callback<CursoResponse>() {

                // requestCurso.enqueue(new Callback<Curso>() {
                @Override
                public void onResponse(Call<CursoResponse> call, Response<CursoResponse> response) {
                    cursoatualizado = response.body();
                    Toast.makeText(getApplicationContext(), "Sucesso", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<CursoResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), " Falha na request", Toast.LENGTH_LONG).show();

                }
            });
        });
    }
    private void alterarCurso() {

        btEditar.setOnClickListener(view -> {
            String editarCurso = enviarMensagem.getText().toString();
            String idDigitadoPeloUsuario = etIdCurso.getText().toString();
            CursoPost alterarNome = new CursoPost();
            alterarNome.setName(editarCurso);

            int idCurso = Integer.parseInt(idDigitadoPeloUsuario);

            requestCurso.createRequestPut(alterarNome, idCurso).enqueue(new Callback<CursoResponse>() {
                @Override
                public void onResponse(Call<CursoResponse> call, Response<CursoResponse> response) {
                    Toast.makeText(getApplicationContext(), "Sucesso", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<CursoResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Falha de comunicação", Toast.LENGTH_LONG).show();
                }

            });
        });
    }
    public void executarRequestGetAll(View view){
        requestCurso.createRequestGetAll().enqueue(new Callback<List<CursoResponse>>() {
            @Override
            public void onResponse(Call<List<CursoResponse>> call, Response<List<CursoResponse>>response) {
                Toast.makeText(getApplicationContext(), "Sucesso", Toast.LENGTH_LONG).show();

               List<CursoResponse> cursoLista = response.body();
               for (CursoResponse curso : cursoLista) {
                   Log.i(">>> Resultado", curso.getId() + " " + curso.getName());

                    listaNomeCursos.add(curso.getName());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<CursoResponse>>call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Falhou querida!", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void deletarCurso(){

        btDelete.setOnClickListener(view -> {;
        String deletarPorid = etIdCurso.getText().toString();
        int idRecebido = Integer.parseInt(deletarPorid);

        requestCurso.deleteRequest(idRecebido).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call <Object> call, Response<Object> response) {
                Toast.makeText(getApplicationContext(), "Curso Deletado", Toast.LENGTH_LONG).show();

                }

            @Override
            public void onFailure(Call <Object> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Falha na request", Toast.LENGTH_LONG).show();
            }
        });
    });}
    }

