# language: pt

Funcionalidade: Login do usuário
  # Descreve a funcionalidade de login

  Cenário: : Usuário loga com sucesso usando credenciais válidas
    # Cenário que simula um login bem-sucedido
    Dado que o usuário possui credenciais válidas
    # "Given": pré-condição; aqui, o usuário tem dados válidos
    Quando ele envia uma requisição para o endpoint de login
    # "When": ação executada pelo usuário
    Então o sistema retorna um token JWT válido
    # "Then": resultado esperado após a ação
