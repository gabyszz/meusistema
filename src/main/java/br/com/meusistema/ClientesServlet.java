package br.com.meusistema;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/clientes")
public class ClientesServlet extends HttpServlet {

    private DataSource ds;

    // Executa uma vez quando o Tomcat carrega a aplicação.
    // Pega a conexão com o banco que foi configurada no arquivo meusistema.xml
    @Override
    public void init() throws ServletException {
        try {
            Context ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/meusistema");
        } catch (NamingException e) {
            throw new ServletException("Não encontrei o DataSource jdbc/meusistema", e);
        }
    }

    // Listar clientes (acesso pelo navegador)
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();

        out.println("<!DOCTYPE html><html lang='pt-BR'><head><meta charset='UTF-8'>");
        out.println("<title>Clientes</title>");
        out.println("<style>body{font-family:sans-serif;max-width:600px;margin:40px auto}"
                + "table{border-collapse:collapse;width:100%}"
                + "td,th{border:1px solid #ccc;padding:8px;text-align:left}"
                + "input{padding:6px;margin:4px 0}</style></head><body>");
        out.println("<h1>Clientes</h1>");

        out.println("<form method='post' action='clientes'>");
        out.println("<input name='nome' placeholder='Nome' required> ");
        out.println("<input name='email' type='email' placeholder='E-mail' required> ");
        out.println("<button type='submit'>Cadastrar</button></form><br>");

        out.println("<table><tr><th>ID</th><th>Nome</th><th>E-mail</th></tr>");

        String sql = "SELECT id, nome, email FROM clientes ORDER BY id";
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                out.println("<tr><td>" + rs.getInt("id") + "</td><td>"
                        + escapar(rs.getString("nome")) + "</td><td>"
                        + escapar(rs.getString("email")) + "</td></tr>");
            }
        } catch (SQLException e) {
            out.println("<tr><td colspan='3'>Erro no banco: " + escapar(e.getMessage()) + "</td></tr>");
        }

        out.println("</table></body></html>");
    }

    // Cadastrar cliente (envio do formulário)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String nome = req.getParameter("nome");
        String email = req.getParameter("email");

        String sql = "INSERT INTO clientes (nome, email) VALUES (?, ?)";
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nome);
            ps.setString(2, email);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ServletException("Erro ao inserir cliente", e);
        }

        resp.sendRedirect("clientes");
    }

    // Evita que texto digitado vire código HTML (proteção contra XSS)
    private String escapar(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;")
                .replace(">", "&gt;").replace("\"", "&quot;");
    }
}