# Heart Failure Prediction Android App

## Descrição

Este aplicativo Android utiliza um modelo de Random Forest pré-treinado (formato ONNX) para prever o risco de insuficiência cardíaca com base em parâmetros clínicos do paciente. A inferência é feita localmente no dispositivo usando o ONNX Runtime para Android, garantindo baixo tempo de resposta e independência de conexão de rede.

## Funcionalidades

- Carregamento automático do modelo `random_forest_model.onnx` a partir da pasta de `assets`.
- Execução de inferência ONNX para classificação binária:
  - **0**: Tudo Certo! (sem risco aparente)
  - **1**: Procure um Médico! (risco de insuficiência cardíaca)
- Exibição do resultado na tela e mudança de cor de fundo:
  - Verde para classe 0
  - Vermelho para classe 1

## Pré-requisitos

- Android Studio (versão 2021.1.1 "Arctic Fox" ou superior)
- JDK 11 ou superior
- Android SDK (API Level 34)
- Dispositivo físico ou emulador com Android 5.0 (API Level 21) ou superior

## Estrutura do Projeto

```
heartfailureprediction/          # Diretório raiz do projeto
├─ .gitignore
├─ build.gradle                   # Configurações de build do projeto
├─ gradle.properties
├─ gradlew
├─ gradlew.bat
├─ settings.gradle
├─ Heart_Failure_Prediction.ipynb  # Notebook de treinamento do modelo
└─ app/                           # Módulo de aplicação Android
   ├─ build.gradle               # Dependências e configuração do módulo
   ├─ src/
   │  ├─ main/
   │  │  ├─ java/com/example/heartfailureprediction/
   │  │  │   ├─ MainActivity.java  # Lógica de carregamento do modelo e inferência
   │  │  │   └─ OnnxModel.java     # Wrapper para ONNX Runtime
   │  │  ├─ res/
   │  │  │   ├─ layout/activity_main.xml
   │  │  │   ├─ values/strings.xml
   │  │  │   └─ ...
   │  │  └─ AndroidManifest.xml
   │  └─ androidTest/...
   └─ assets/
       └─ random_forest_model.onnx  # Modelo ONNX treinado
```

## Instalação e Execução

1. **Clonar o repositório**
   ```bash
   git clone https://github.com/paschoalim/heartfailureprediction.git
   cd heartfailureprediction
   ```
2. **Abrir no Android Studio**
   - Executar `File > Open...` e selecionar a pasta do projeto.
3. **Sincronizar e construir**
   - Aguarde o Gradle sincronizar as dependências.
   - Executar `Build > Make Project`.
4. **Rodar no Dispositivo/Emulador**
   - Conecte um dispositivo ou inicie um emulador.
   - Clique em `Run > Run 'app'`.

## Uso

Por padrão, o array de entrada (`inputData`) está definido em `MainActivity.java` com valores de exemplo para as 11 features:

```java
float[] inputData = new float[]{
                    40.0f,   // Age (exemplo)
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
```

- Para testar outros casos, edite esses valores e recompile o app.

## Dataset de Treinamento

O dataset utilizado para treinar o modelo está disponível no Kaggle em:

[https://www.kaggle.com/datasets/fedesoriano/heart-failure-prediction](https://www.kaggle.com/datasets/fedesoriano/heart-failure-prediction)

Este conjunto de dados contém 918 registros de pacientes com 11 variáveis clínicas (como idade, sexo, tipo de dor no peito, pressão arterial em repouso, etc.) e uma variável alvo indicando o óbito por insuficiência cardíaca dentro de um ano. No notebook `Heart_Failure_Prediction.ipynb`, realizamos:

1. Download e carregamento dos dados
2. Análise exploratória e tratamento de valores ausentes
3. Engenharia de features e normalização
4. Treinamento do Random Forest
5. Avaliação do modelo com métricas de acurácia, ROC AUC e matriz de confusão
6. Exportação para ONNX

## Notebook de Treinamento

O arquivo `Heart_Failure_Prediction.ipynb` na raiz do projeto contém o pipeline completo de treinamento do modelo, incluindo carregamento de dados, pré-processamento, treinamento e exportação para ONNX.

## Licença

Este projeto está licenciado sob a **MIT License**. Veja o arquivo `LICENSE` para mais detalhes.

## Referência do Artigo

Este trabalho é baseado no artigo:

> Cristiano Paschoalim de Almeida, Heder Soares Bernardino, Jairo Francisco de Souza e Luciana Conceição Dias Campos. *Real-Time Heart Failure Prediction: An Approach for Ambient Assisted Living*. Anais do 21º Simpósio Brasileiro de Sistemas de Informação (SBSI), Recife/PE, 2025, pp. 57–66. DOI: 10.5753/sbsi.2025.245972.

## Agradecimentos

**Autores:**

- Cristiano Paschoalim de Almeida (UFJF)
- Heder Soares Bernardino (UFJF)
- Jairo Francisco de Souza (UFJF)
- Luciana Conceição Dias Campos (UFJF)

Os autores expressam sua gratidão ao CNPq, CAPES, FAPEMIG e UFJF pelo suporte financeiro e incentivo à pesquisa científica, fundamentais para a realização deste estudo.

