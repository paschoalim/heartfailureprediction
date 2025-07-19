package com.example.heartfailureprediction;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ai.onnxruntime.*;

import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    private TextView status;
    private RelativeLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Remover a barra de status e fazer full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        status=(TextView)findViewById(R.id.resultTextView);

        layout = findViewById(R.id.main_layout);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        try {
            OrtEnvironment env = OrtEnvironment.getEnvironment();
            OrtSession.SessionOptions opts = new OrtSession.SessionOptions();

            // Carregar o modelo ONNX
            InputStream inputStream = getAssets().open("random_forest_model.onnx");
            byte[] modelBytes = convertInputStreamToByteArray(inputStream);

            OrtSession session = env.createSession(modelBytes, opts);

            // Preparar os dados de entrada
            float[] inputData = new float[]{
                    40.0f,   // ST_Slope: Up (exemplo)
                    1.0f, // Sex: Masculino (exemplo)
                    1.0f, // ChestPainType: TA (exemplo)
                    140.0f, // RestingBP
                    289.0f,   // Cholesterol
                    0.0f,   // FastingBS: maior que 120 mg/dl (exemplo)
                    0.0f,   // RestingECG: ST (exemplo)
                    172.0f, // MaxHR
                    0.0f,   // ExerciseAngina: Sim (exemplo)
                    0.0f,   // Oldpeak
                    0.0f,   // ST_Slope: Up (exemplo)

            };

            long[] shape = new long[]{1, inputData.length};
            OnnxTensor tensor = OnnxTensor.createTensor(env, FloatBuffer.wrap(inputData), shape);

            // Executar a inferência
            OrtSession.Result result = session.run(Collections.singletonMap("float_input", tensor));

            // Processar a saída
            Object output = result.get(0).getValue();

            // Verificar e processar a saída
            if (output instanceof long[]) {
                long[] outputArray = (long[]) output;
                System.out.println("Predição do modelo: " + outputArray[0]);
                if (outputArray[0] == 0) {
                    status.setText(String.valueOf("Tudo Certo!"));
                    layout.setBackgroundColor(Color.parseColor("GREEN")); // Cor laranja
                }else{
                    status.setText(String.valueOf("Procure um Médico!"));
                    layout.setBackgroundColor(Color.parseColor("RED")); // Cor laranja
                }
            } else if (output instanceof float[]) {
                float[] outputArray = (float[]) output;
                System.out.println("Predição do modelo: " + outputArray[0]);
            } else {
                System.out.println("Tipo de saída não suportado: " + output.getClass().getName());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Método para alterar a cor dinamicamente em tempo de execução
    }

    private byte[] convertInputStreamToByteArray(InputStream inputStream) throws Exception {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[16384];
        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        return buffer.toByteArray();
    }
}

