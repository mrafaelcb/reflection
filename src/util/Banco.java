package util;

import modelos.Pessoa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Banco {
    private String usuario;
    private String url;
    private String banco;
    private String senha;
    private Connection conexao;


    public Banco() {
        this.url = "jdbc:postgresql://localhost:5433/";
        this.banco = "loja";
        this.usuario = "postgres";
        this.senha = "master";
        try {
            this.conexao = DriverManager.getConnection(url+banco, usuario, senha);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void inserir(String classe,String sql){
        PreparedStatement stmt = null;
        //System.out.print("insert into "+classe +" values "+sql);
        try {
            stmt = getConexao().prepareStatement("insert into "+classe +" values "+sql);
            stmt.execute();
            stmt.close();
            System.out.println("Inserido!");
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Pessoa> pesquisarPessoa(){
        PreparedStatement stmt = null;
        List<Pessoa> lista = new ArrayList<>();
        try {
            stmt = getConexao().prepareStatement("SELECT * FROM Pessoa");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Pessoa aux = new Pessoa();
                aux.setId(rs.getInt("id"));
                aux.setNome(rs.getString("nome"));
                aux.setNascimento(rs.getDate("nascimento"));
                lista.add(aux);
            }
        } catch (SQLException e) {
            System.out.println("Erro: "+e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    public void deletarPessoa(Integer id){
        PreparedStatement stmt = null;
        try {
            stmt = getConexao().prepareStatement("DELETE FROM Pessoa WHERE id = ?");
            stmt.setInt(1,id);
            stmt.execute();
            stmt.close();
            System.out.println("Deletado!");
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Connection getConexao() {
        return conexao;
    }

    public void setConexao(Connection conexao) {
        this.conexao = conexao;
    }
}
