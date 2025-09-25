# Instruções de Compilação - GymGen Android

## Visão Geral
Este documento fornece instruções detalhadas para compilar e executar o aplicativo GymGen no Android Studio.

## Estrutura dos Arquivos Entregues

```
/mnt/okcomputer/output/
├── MainActivity.kt
├── AndroidManifest.xml
├── build.gradle.kts
├── proguard-rules.pro
├── INSTRUCOES_COMPILACAO.md
├── README.md
├── models/
│   ├── WorkoutPlan.kt
│   └── WorkoutRequest.kt
├── network/
│   ├── ApiService.kt
│   └── RetrofitClient.kt
├── data/
│   └── WorkoutRepository.kt
├── viewmodel/
│   └── WorkoutViewModel.kt
├── ui/
│   ├── screens/
│   │   ├── InputScreen.kt
│   │   ├── LoadingScreen.kt
│   │   └── WorkoutPlanScreen.kt
│   ├── components/
│   │   ├── WorkoutDayCard.kt
│   │   └── ExerciseItem.kt
│   └── theme/
│       ├── Theme.kt
│       ├── Color.kt
│       └── Type.kt
└── navigation/
    └── NavGraph.kt
```

## Passos para Compilação

### 1. Configuração Inicial

1. **Abrir o Android Studio**
   - Versão recomendada: Arctic Fox ou superior
   - Certifique-se de ter o SDK Android 34 instalado

2. **Criar Novo Projeto**
   - Selecione "Empty Activity"
   - Nome do pacote: `com.gymgen`
   - Linguagem: Kotlin
   - Minimum SDK: API 24

### 2. Substituição de Arquivos

1. **Substituir arquivos principais**:
   - Copie `MainActivity.kt` para `app/src/main/java/com/gymgen/`
   - Copie `AndroidManifest.xml` para `app/src/main/`
   - Copie `build.gradle.kts` para `app/`
   - Copie `proguard-rules.pro` para `app/`

2. **Criar estrutura de diretórios**:
   ```bash
   mkdir -p app/src/main/java/com/gymgen/{models,network,data,viewmodel,ui/{screens,components,theme},navigation}
   ```

3. **Copiar todos os arquivos de código**:
   - Copie todos os arquivos `.kt` para seus respectivos diretórios
   - Mantenha a estrutura de pacotes conforme especificado nos arquivos

### 3. Configuração Crítica

**⚠️ IMPORTANTE: Configure o IP do backend**

Antes de compilar, edite o arquivo:
`app/src/main/java/com/gymgen/network/RetrofitClient.kt`

```kotlin
// ALTERE ESTA LINHA:
private const val BASE_URL = "http://192.168.1.100:5000/"

// PARA O IP DA MÁQUINA QUE RODA O BACKEND FLASK
// Exemplo: "http://192.168.0.10:5000/"
```

### 4. Sincronização e Compilação

1. **Sincronizar Gradle**:
   - Clique em "Sync Now" quando o Android Studio solicitar
   - Ou use: `Tools` → `File` → `Sync Project with Gradle Files`

2. **Limpar e Reconstruir**:
   - `Build` → `Clean Project`
   - `Build` → `Rebuild Project`

3. **Executar o aplicativo**:
   - Conecte um dispositivo físico ou inicie um emulador
   - Clique em "Run" (Shift+F10)

## Solução de Problemas

### Erros Comuns

1. **"Unresolved reference"**:
   - Certifique-se de que todos os arquivos estão nos diretórios corretos
   - Verifique se os pacotes (package declarations) estão corretos

2. **Erros de Gradle**:
   - Clique em "Sync Now" novamente
   - `File` → `Invalidate Caches / Restart`

3. **Erro de conexão com API**:
   - Verifique se o backend Flask está rodando
   - Confirme se o IP está correto no RetrofitClient.kt
   - Verifique se há conexão de rede entre dispositivo e computador

4. **Erro de permissão de internet**:
   - O AndroidManifest.xml já inclui a permissão necessária
   - Para Android 9+, foi adicionado `android:usesCleartextTraffic="true"`

### Configuração de Rede

Para conectar dispositivo físico ao backend local:

1. **Dispositivo na mesma rede WiFi** que o computador
2. **Firewall desabilitado** ou porta 5000 liberada
3. **Backend Flask rodando** no IP configurado
4. **Teste a API** antes de executar o app:
   ```bash
   curl -X POST http://[SEU_IP]:5000/api/generate-workout \
     -H "Content-Type: application/json" \
     -d '{"objetivo":"hipertrofia","nivel_experiencia":"Iniciante"}'
   ```

## Verificação Final

Antes de executar, verifique:

- [ ] Todos os arquivos estão nos diretórios corretos
- [ ] IP do backend está configurado no RetrofitClient.kt
- [ ] Backend Flask está rodando e acessível
- [ ] Gradle sync foi executado sem erros
- [ ] Dispositivo/emulador está conectado

## Suporte

Se encontrar problemas durante a compilação:

1. Verifique o console de build para erros específicos
2. Confira que todas as dependências foram sincronizadas
3. Teste a conectividade com o backend separadamente
4. Consulte o README.md para mais informações sobre a arquitetura

## APK Gerado

Após compilação bem-sucedida, o APK pode ser encontrado em:
`app/build/outputs/apk/debug/app-debug.apk`