package util;

import modelos.Pessoa;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessControlException;
import java.sql.*;
import java.util.*;
import java.util.Date;

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

    public void inserir(Object object) {//inserir com reflexão
        PreparedStatement stmt = null;
        try {
            String query = query(object);
            System.out.println(query);
            stmt = getConexao().prepareStatement(query);
            stmt.execute();
            stmt.close();
           } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static String query(Object object){
        Class classe = object.getClass();
        Boolean flag = false;
        Map<String,String> atributo = pegarAtributos(classe);
        String sql = "INSERT INTO "+classe.getSimpleName() + "(";//pega todos os atributos
        for (String key : atributo.keySet()) {
            if(flag) {
                sql += "," + key;
            }else{
                sql+=key;
                flag=true;
            }
        }
        sql+=") VALUES (";
        flag = false;
        for (String key : atributo.keySet()) {
            Method method = null;
            String get = arrumarGet(key);
            String tipo = atributo.get(key);
            try {
                if(flag){
                    sql+=",";
                }else{
                    flag = true;
                }
                method = classe.getMethod(get, new Class[]{});
                if (tipo.equals("Date")) {
                    Date valor = (Date) method.invoke(object, new Object[]{});
                    sql += "'" + DataFormat.formatarSimpleDate(valor) + "'";
                }else if (tipo.equals("Integer")) {
                    Integer valor = (Integer) method.invoke(object, new Object[]{});
                    if((get.equals("getId") && valor!=null) || !get.equals("getId")){
                        sql += valor;
                    }else{
                        sql+="DEFAULT";
                    }
                } else if (tipo.equals("Float")) {
                    Float valor = (Float) method.invoke(object, new Object[]{});
                    sql += valor;
                } else if (tipo.equals("Double")) {
                    Double valor = (Double) method.invoke(object, new Object[]{});
                    sql+= valor;
                } else if (tipo.equals("Boolean")) {
                    Boolean valor = (Boolean) method.invoke(object, new Object[]{});
                    sql += valor;
                }else if (tipo.equals("String")) {
                    String valor = (String) method.invoke(object, new Object[]{});
                    sql += "'" + valor + "'";
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e1) {
                e1.printStackTrace();
            }
        }
        sql+=");";
        return sql;
    }

    public static String arrumarGet(String key) {
        String get = "";
        get += "get";
        String arrumandoGet = key;
        arrumandoGet = arrumandoGet.replaceFirst(key.substring(0,1),key.substring(0,1).toUpperCase());
        get += arrumandoGet;
        return get;
    }

    public static Map<String,String> pegarAtributos(Class classe){//pegar atributos
        Map<String,String> atributo = new HashMap<>();
        for(Field a : classe.getDeclaredFields()){
            atributo.put(a.getName(),a.getType().getSimpleName());
        }
        return atributo;
    }


    public List<Object> pesquisar(Class classe) throws InstantiationException, IllegalAccessException {
        List<Object> lista = new ArrayList<>();
        Map<String,String> atributo = pegarAtributos(classe);
        PreparedStatement stmt = null;
        try {
            stmt = getConexao().prepareStatement("SELECT * FROM " + classe.getSimpleName() +";");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Object aux = classe.newInstance();
                for (String key : atributo.keySet()) {
                    Method method = null;
                    String set = "";
                    set += "set";
                    String arrumandoSet = key;
                    arrumandoSet = arrumandoSet.replaceFirst(key.substring(0,1),key.substring(0,1).toUpperCase());
                    set += arrumandoSet;
                    String tipo = atributo.get(key);
                    try {
                        if (tipo.equals("Date")) {
                            method = classe.getMethod(set,Date.class);
                            method.invoke(aux, rs.getDate(key));
                        }else if (tipo.equals("Integer")) {
                            method = classe.getMethod(set,Integer.class);
                            method.invoke(aux, rs.getInt(key));
                        } else if (tipo.equals("Float")) {
                            method = classe.getMethod(set,Float.class);
                            method.invoke(aux, rs.getFloat(key));
                        } else if (tipo.equals("Double")) {
                            method = classe.getMethod(set,Double.class);
                            method.invoke(aux, rs.getDouble(key));
                        } else if (tipo.equals("Boolean")) {
                            method = classe.getMethod(set,Boolean.class);
                            method.invoke(aux, rs.getBoolean(key));
                        }else if (tipo.equals("String")) {
                            method = classe.getMethod(set,String.class);
                            method.invoke(aux, rs.getString(key));
                        }else if (tipo.equals("Time")) {
                            method = classe.getMethod(set,Time.class);
                            method.invoke(aux, rs.getTime(key));
                        }
                    } catch (NoSuchMethodException | InvocationTargetException e1) {
                        e1.printStackTrace();
                    }
                }
                query(aux);
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
