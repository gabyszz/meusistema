-- 1) Criar o banco de dados do sistema
CREATE DATABASE IF NOT EXISTS meusistema
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

-- 2) Criar o usuário do sistema (TROQUE a senha!)
CREATE USER IF NOT EXISTS 'sistema'@'localhost' IDENTIFIED BY 'Troque_Esta_Senha_123';

-- 3) Dar permissão só neste banco
GRANT ALL PRIVILEGES ON meusistema.* TO 'sistema'@'localhost';
FLUSH PRIVILEGES;

-- 4) Criar as tabelas
USE meusistema;

CREATE TABLE IF NOT EXISTS clientes (
  id        INT AUTO_INCREMENT PRIMARY KEY,
  nome      VARCHAR(100) NOT NULL,
  email     VARCHAR(150) NOT NULL UNIQUE,
  criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 5) Dados de exemplo (INSERT IGNORE evita erro se rodar duas vezes)
INSERT IGNORE INTO clientes (nome, email) VALUES
  ('Maria Silva', 'maria@exemplo.com'),
  ('João Souza',  'joao@exemplo.com');