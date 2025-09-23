# Jogo da Velha - Android

## Descrição
Aplicativo Android do clássico jogo da velha (Tic Tac Toe) desenvolvido em Kotlin. O jogo permite que dois jogadores alternem jogadas em um tabuleiro 3x3, com sistema de pontuação e interface intuitiva.

## Demo

<p align="center">
  <img src="https://github.com/user-attachments/assets/56913dd8-63f5-4952-876d-bd5c31d320f6" alt="Imagem 1" width="30%">
  <img src="https://github.com/user-attachments/assets/98bea3e2-ad84-442f-98f9-1361a62cdea8" alt="Imagem 2" width="30%">
  <img src="https://github.com/user-attachments/assets/259cbc60-50db-4fd7-8b08-ef7bb2443506" alt="Imagem 3" width="30%">
</p>


## Funcionalidades
- Tabuleiro 3x3 interativo
- Sistema de alternância entre jogadores (X e O)
- Detecção automática de vitória
- Placar persistente durante a sessão
- Botão para reiniciar o jogo
- Interface responsiva com animações

## Tecnologias Utilizadas
- **Linguagem**: Kotlin
- **IDE**: Android Studio
- **API Level**: 24 (Android 7.0) - 36 (Android 14)
- **Build System**: Gradle com Kotlin DSL

## Estrutura do Projeto
```
app/
├── src/main/
│   ├── java/com/example/tictactoe/
│   │   └── MainActivity.kt          # Lógica principal do jogo
│   ├── res/
│   │   ├── layout/
│   │   │   └── activity_main.xml    # Layout da interface
│   │   ├── drawable/                # Recursos visuais
│   │   └── values/                  # Cores, strings e temas
│   └── AndroidManifest.xml
└── build.gradle.kts
```

## Como Executar
1. Clone o repositório
2. Abra o projeto no Android Studio
3. Aguarde a sincronização do Gradle
4. Execute o projeto em um dispositivo Android ou emulador

## Regras do Jogo
1. O jogador X sempre inicia
2. Os jogadores alternam turnos
3. Vence quem conseguir três símbolos em linha (horizontal, vertical ou diagonal)
4. O jogo termina em empate se todas as casas forem preenchidas sem vencedor

## Requisitos do Sistema
- Android 7.0 (API 24) ou superior
- 20 MB de espaço livre

## Arquitetura
O aplicativo utiliza uma arquitetura simples com uma Activity principal que gerencia:
- Estado do tabuleiro através de matriz bidimensional
- Lógica de verificação de vitória
- Interface do usuário e interações
- Sistema de pontuação

## Autor
Desenvolvido como projeto acadêmico para a disciplina de Desenvolvimento Mobile.
