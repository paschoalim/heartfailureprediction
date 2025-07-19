package com.example.heartfailureprediction;
import ai.onnxruntime.*;
import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class OnnxModel {

    private OrtEnvironment env;
    private OrtSession session;

    public OnnxModel(Context context) throws OrtException, IOException {
        // Inicializar o ambiente e a sessão do ONNX Runtime
        // Inicializar o ambiente ONNX Runtime diretamente
        env = OrtEnvironment.getEnvironment(); // Inicialize diretamente
        if (env == null) {
            throw new OrtException("Não foi possível inicializar o ambiente ONNX Runtime.");
        }


        if (env == null) {
            env = OrtEnvironment.getEnvironment(); // Isso deve retornar uma nova instância
        }




        // Acessar o arquivo ONNX a partir dos assets
        AssetManager assetManager = context.getAssets();
        InputStream modelStream = assetManager.open("random_forest_model.onnx");

        // Criar a sessão com o modelo ONNX
        session = env.createSession(String.valueOf(modelStream));
    }

    public float[] predict(float[] inputData) throws OrtException {
        // Preparar o tensor de entrada
        OnnxTensor inputTensor = OnnxTensor.createTensor(env, inputData);

        // Realizar a inferência
        Map<String, OnnxTensor> inputs = new HashMap<>();
        inputs.put("float_input", inputTensor);
        OrtSession.Result result = session.run(inputs);

        // Obter o resultado
        float[][] output = (float[][]) result.get(0).getValue();
        return output[0];
    }
}
