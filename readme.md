# ⚙️ Diário Online – Backend

Esta é a API do projeto Diário Online, responsável por autenticação, persistência de dados dos usuários, gerenciamento de mídias (jogos) e integração com APIs externas.

<div align="center">
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![MongoDB](https://img.shields.io/badge/MongoDB-%234ea94b.svg?style=for-the-badge&logo=mongodb&logoColor=white)
</div>

---

## 🏗️ O Projeto

O backend do Diário Online fornece uma API REST responsável por autenticação e autorização de usuários, persistência de dados no MongoDB, gerenciamento de usuários e mídias salvas, integração com APIs externas,  de jogos (IGDB) e emissão e validação de tokens JWT. Ele foi desenvolvido seguindo princípios de arquitetura em camadas, boas práticas REST e segurança.

### 🔗 Repositórios Relacionados

🌐 Frontend (Angular)

🏗️ Infraestrutura (Docker / Compose)

---

## 🛠️ Tecnologias e Decisões Técnicas

Java 17: LTS atual, garantindo performance e recursos modernos da linguagem

Spring Boot 4: Framework principal da aplicação

Spring Web / WebFlux: Criação de APIs REST e consumo reativo de APIs externas

Spring Security: Controle de autenticação e autorização

JWT (JSON Web Token): Autenticação stateless

MongoDB: Banco NoSQL para persistência de dados

Docker: Conteinerização do ambiente

Bean Validation: Validação de dados de entrada

Lombok: Redução de boilerplate

---

## 📊 Status das Funcionalidades

| Funcionalidade            | Status | Descrição                               |
|:--------------------------| :---: |:----------------------------------------|
| **Autenticação (JWT)**    | ✅ | Login e Cadastro de usuários funcional. |
| **Autorização por Roles** | ✅ | Controle de acesso por perfil.          |
| **CRUD com Mongodb**      | ✅ | CRUD de usuários e mídias.              |
| **Integração IGDB**  | ✅ | Busca de jogos via API externa.         |
| **Testes Automatizados**             | ⚠️ | Em desenvolvimento.                     |

---

## 🚀 Como rodar localmente

### 1. Com Docker (Recomendado)
```bash
# Clone o repositório
git clone https://github.com/guilhermevcoskype/diario-online-backend.git

# Acesse a pasta
cd diario-online-backend

# Build da imagem
docker build -t diario-backend .

# Execute o container
docker run -p 8080:8080 diario-backend

```


A API ficará disponível em:
👉 http://localhost:8080

---

### 2. Sem Docker

- **Java** 17 ou superior
- **Maven** 3.9 ou superior
- **MongoDB** Versão atualizada

1. **Clone o repositório**
```bash
git clone https://github.com/guilhermevcoskype/diario-online-backend.git
cd diario-online-backend
```

2. **Configure o MongoDB**
```bash
Certifique-se que o MongoDB esteja rodando em:

mongodb://localhost:27017/diario-online
```

3. **Execute a aplicação**
```bash
mvn spring-boot:run
```
---

## 📂 Estrutura do Projeto

```
diario-online-backend/
├── src/
│   ├── main/
│   │   ├── java/com/gui/
│   │   │   ├── controller/     # Controllers REST
│   │   │   ├── domain/         # Entidades e DTOs
│   │   │   ├── repository/     # Repositórios MongoDB
│   │   │   ├── service/        # Regras de negócio
│   │   │   ├── security/       # Configuração de segurança
│   │   │   └── config/         # Configurações gerais
│   │   └── resources/
│   │       ├── application.properties
│   │       └── application-prod.properties
│   │
│   └── test/                   # Testes automatizados
│
├── dockerfile                  # Dockerfile da aplicação
├── pom.xml                     # Dependências Maven
└── README.md                   # Documentação
```

---

## 🔒 Segurança

Autenticação baseada em JWT

Autorização com roles

Endpoints protegidos via Spring Security

Validação de payloads com Bean Validation

Arquitetura stateless

---

## 🎯 Padrões de Código

- Arquitetura em camadas

- DTOs para comunicação externa

- Services isolando regras de negócio

- Controllers finos

- Tratamento centralizado de erros

- Configuração por perfil (dev/prod)

---

### 👨‍💻 Autor

**Guilherme Oliveira**

- GitHub: [guilhermevcoskype](https://github.com/guilhermevcoskype)
- LinkedIn: [guilherme-vale-oliveira-dev](https://www.linkedin.com/in/guilherme-vale-oliveira-dev/)
- Email: [guilhermevcoskype@gmail](guilhermevcoskype@gmail.com)

---

<div align="center">

Desenvolvido com ❤️ usando Java

</div>
