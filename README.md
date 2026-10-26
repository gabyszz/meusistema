# # Meu Sistema (SaaS)

Aplicação web em Java (Servlets + JDBC) com MySQL e Tomcat 10.

## Requisitos na VM
- Linux (Ubuntu/Debian)
- Java 11+, Maven, Git
- MySQL (`mysql.service`)
- Tomcat 10 em `/opt/tomcat10` (`tomcat10.service`)

## Arquivos
| Arquivo | Função |
|---|---|
| `script.sql` | Cria usuário, banco e tabelas |
| `config/meusistema.xml` | Configuração do Tomcat (conexão com o banco) |
| `docs/ADR-001-escolha-da-stack.md` | Registro de decisão de arquitetura |
| `deploy.sh` | Atualiza e reinicia a aplicação |

## Instalação (primeira vez)
```bash
cd /opt/tomcat10
sudo git clone https://github.com/SEU_USUARIO/meusistema.git
cd meusistema
sudo mysql < script.sql
sudo wget -P /opt/tomcat10/lib https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/9.1.0/mysql-connector-j-9.1.0.jar
sudo chmod +x deploy.sh
sudo ./deploy.sh
```

## Atualização
```bash
cd /opt/tomcat10/meusistema
sudo ./deploy.sh
```

## Acesso
http://IP_DA_VM:8080/meusistema