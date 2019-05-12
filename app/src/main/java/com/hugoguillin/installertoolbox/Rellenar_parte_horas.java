package com.hugoguillin.installertoolbox;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.hugoguillin.installertoolbox.Parte_horas.EXTRA_DATA_ACTUALIZAR_ENTRADA;
import static com.hugoguillin.installertoolbox.Parte_horas.EXTRA_DATA_ID;

public class Rellenar_parte_horas extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.hugoguillin.installertoolbox.rellemarpartehoras.REPLY";
    public static final String EXTRA_REPLY_ID = "com.hugoguillin.installertoolbox.rellenarpartehoras.REPLY_ID";

    private EditText fecha, hora_inicio, hora_fin, descripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rellenar_parte_horas);
        fecha = findViewById(R.id.texto_fecha);
        hora_inicio = findViewById(R.id.texto_hora_inicio);
        hora_fin = findViewById(R.id.texto_hora_fin);
        descripcion = findViewById(R.id.texto_descripcion);
        fecha.requestFocus();
        int id = -1;

        final Bundle extras = getIntent().getExtras();
        if (extras != null){
            String entrada = extras.getString(EXTRA_DATA_ACTUALIZAR_ENTRADA, "");
            if (!entrada.isEmpty()){
                String[] partes = entrada.split("\n");
                String inicio = partes[1].substring(partes[1].indexOf("e")+2, partes[1].indexOf("a"));
                String fin = partes[1].substring(partes[1].indexOf("a")+2);
                fecha.setText(partes[0]);
                hora_inicio.setText(inicio);
                hora_fin.setText(fin);
                descripcion.setText(partes[2]);
                fecha.requestFocus();
            }
        }

        final Button anadir = findViewById(R.id.boton_anadir_parte);

        anadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                boolean b = fecha.getText().toString().isEmpty()
                        && hora_inicio.getText().toString().isEmpty()
                        && hora_fin.getText().toString().isEmpty()
                        && descripcion.getText().toString().isEmpty();

                if(!b){
                    String entrada = fecha.getText().toString() + "\nDe "
                            +hora_inicio.getText().toString() + " a "
                            +hora_fin.getText().toString() + "\n"
                            +descripcion.getText().toString() + "\n";
                    intent.putExtra(EXTRA_REPLY, entrada);

                    if (extras != null && extras.containsKey(EXTRA_DATA_ID)){
                        int id = extras.getInt(EXTRA_DATA_ID, -1);
                        if(id != -1){
                            intent.putExtra(EXTRA_REPLY_ID, id);
                        }
                    }

                        setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Debes rellenar todos los campos",
                            Toast.LENGTH_LONG).show();
                    //setResult(RESULT_CANCELED, intent);
                }
            }
        });
    }

}
