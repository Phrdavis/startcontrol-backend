# 🚀 API do StartControl

A API do StartControl fornece endpoints RESTful para gerenciar startups, equipes, tarefas e métricas de desempenho. Ela permite integração com sistemas externos e facilita o desenvolvimento de aplicações clientes.

## 🌟 Principais Endpoints

- **Startups:** Cadastro, listagem, atualização e remoção de startups.
- **Equipes:** Gerenciamento de membros e funções dentro das startups.
- **Associação de Usuários:** Criação, atualização e acompanhamento de associações

## 🛠️ Tecnologias

- ⚙️ Springboot (backend)
- 🗄️ Hibernate (banco de dados)
- 🔐 JWT (autenticação)

## 🚦 Como Usar

1. **Pré-requisitos:**
    - Certifique-se de ter o [Java 17+](https://adoptium.net/) e o [Maven](https://maven.apache.org/) instalados.

2. **Clone o repositório:**
    ```bash
    git clone https://github.com/Phrdavis/startcontrol-backend
    cd startcontrol
    ```

3. **Execute a aplicação:**
    ```bash
    ./mvnw spring-boot:run
    ```
    Ou, se preferir, gere o arquivo `.jar`:
    ```bash
    ./mvnw clean package
    java -jar target/startcontrol-*.jar
    ```

4. **Documentação dos Endpoints:**
    - Acesse `/swagger-ui/index.html` após iniciar o servidor para visualizar a documentação Swagger. 📚

## 🤝 Contribuição

Sugestões e melhorias para a API são bem-vindas! Abra uma issue ou envie um pull request.

---

Feito com 💙 por StartControl Team 🚀
