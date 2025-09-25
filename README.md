# GymGen - Aplicativo Android

## Descrição

Aplicativo Android nativo para geração personalizada de planos de treino. Interface frontend para o sistema GymGen que se comunica com backend Python/Flask via API REST.

## Arquitetura

O aplicativo segue os princípios MVVM (Model-View-ViewModel) com:
- **View**: Telas em Jetpack Compose
- **ViewModel**: Gerenciamento de estado com StateFlow
- **Repository**: Abstração da fonte de dados
- **Network**: Camada de comunicação com API

## Tecnologias Utilizadas

- **Linguagem**: Kotlin
- **UI Toolkit**: Jetpack Compose (declarativo)
- **Arquitetura**: MVVM
- **Navegação**: Jetpack Navigation Compose
- **Networking**: Retrofit + Gson
- **Coroutines**: Programação assíncrona
- **State Management**: StateFlow

## Estrutura do Projeto

```
com.gymgen/
├── MainActivity.kt                 # Activity principal
├── models/                         # Data classes
│   ├── WorkoutPlan.kt             # Modelo do plano de treino
│   └── WorkoutRequest.kt          # Modelo da requisição
├── network/                        # Camada de rede
│   ├── ApiService.kt              # Interface da API
│   └── RetrofitClient.kt          # Configuração do Retrofit
├── data/                          # Repository
│   └── WorkoutRepository.kt       # Gerenciamento de dados
├── viewmodel/                     # ViewModels
│   └── WorkoutViewModel.kt        # Lógica de negócio
├── ui/                            # Interface do usuário
│   ├── screens/                   # Telas principais
│   │   ├── InputScreen.kt         # Tela de entrada de dados
│   │   ├── LoadingScreen.kt       # Tela de carregamento
│   │   └── WorkoutPlanScreen.kt   # Tela de exibição do plano
│   ├── components/                # Componentes reutilizáveis
│   │   ├── WorkoutDayCard.kt      # Card de dia de treino
│   │   └── ExerciseItem.kt        # Item de exercício
│   └── theme/                     # Tema e estilos
│       ├── Theme.kt               # Configuração do tema
│       ├── Color.kt               # Paleta de cores
│       └── Type.kt                # Tipografia
└── navigation/                    # Navegação
    └── NavGraph.kt               # Configuração de rotas
```

## Fluxo de Funcionamento

1. **InputScreen**: Usuário seleciona objetivo e nível de experiência
2. **LoadingScreen**: Exibe durante processamento da API
3. **WorkoutPlanScreen**: Mostra o plano gerado ou mensagem de erro

## Configuração da API

**IMPORTANTE**: Antes de compilar, configure o IP do backend no arquivo:
`network/RetrofitClient.kt`

```kotlin
private const val BASE_URL = "http://[SEU_IP_LOCAL]:5000/"
```

### Contrato da API

**Endpoint**: `POST /api/generate-workout`

**Request**:
```json
{
  "objetivo": "hipertrofia" | "perder_peso",
  "nivel_experiencia": "Iniciante" | "Intermediário" | "Avançado"
}
```

**Response**:
```json
{
  "plano_semanal": [
    {
      "dia": "string",
      "exercicios": [
        {
          "nome": "string",
          "series": "string",
          "repeticoes": "string",
          "descanso": "string",
          "dicas_seguranca": ["string"]
        }
      ]
    }
  ]
}
```

## Instalação e Execução

### Pré-requisitos
- Android Studio Arctic Fox ou superior
- SDK Android 24+ (minSdk)
- Backend GymGen rodando

### Passos
1. Clone o repositório
2. Abra no Android Studio
3. Configure o IP do backend em `network/RetrofitClient.kt`
4. Sincronize as dependências (Gradle)
5. Execute em um emulador ou dispositivo físico

## Características Implementadas

✅ **Arquitetura MVVM** - Separação clara de responsabilidades  
✅ **UI Declarativa** - Jetpack Compose sem XML  
✅ **Estado Reativo** - StateFlow para atualizações automáticas  
✅ **Componentização** - Componentes reutilizáveis e desacoplados  
✅ **Navegação** - Transição fluida entre telas  
✅ **Tratamento de Erros** - Feedback claro ao usuário  
✅ **Design Responsivo** - Adapta-se a diferentes tamanhos de tela  
✅ **Comentários** - Código bem documentado  

## Próximos Passos Sugeridos

- Implementar cache local com Room Database
- Adicionar animações entre transições
- Implementar tema escuro/claro
- Adicionar testes unitários e de integração
- Implementar injeção de dependência com Hilt
- Adicionar métricas e analytics

## Licença

Este projeto está licenciado sob a licença MIT.