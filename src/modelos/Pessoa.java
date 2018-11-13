package modelos;

import anotacoes.*;
import interfaces.Humanos;
import util.DataFormat;

import java.util.Date;

@ClasseAnotacao(nome = "Pessoa",descricao = "Classe para pessoas")
public class Pessoa implements Humanos {
    @AtributoAnotacao(nome = "id",descricao = "id unico para cada pessoa")
    private Integer id;
    @AtributoAnotacao(nome = "nome",descricao = "nome da pessoa")
    @AtributoAnotacao2(nome = "nome",tipo = "String")
    private String nome;
    private Date nascimento;

    @ClasseAnotacao(nome = "ClasseInterna",descricao = "Classe interna da pessoa")
    public class ClasseInterna{
        @AtributoAnotacao(nome = "nome",descricao = "nome da classe interna de Pessoa")
        @AtributoAnotacao2(nome = "nome",tipo = "String da interna")
       private String nome;
       private String sobrenome;
       @AtributoAnotacao(nome = "idade",descricao = "idade interna da pessoa")
       private Integer idade;
        @ContrutorAnotacao(nome = "Interno Contrutor",funcao = "contrutor padrao classe interna")
        public ClasseInterna() {

        }
        @ContrutorAnotacao(nome = "Interno Contrutor 2",funcao = "contrutor padrao2",padrao = "todos os fields")
        public ClasseInterna(String nome, String sobrenome, Integer idade) {
            this.nome = nome;
            this.sobrenome = sobrenome;
            this.idade = idade;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }
        @MetodoAnotacao(nome = "getSobrenome", descricao = "Pegar sobreNome interno")
        public String getSobrenome() {
            return sobrenome;
        }

        public void setSobrenome(String sobrenome) {
            this.sobrenome = sobrenome;
        }

        public Integer getIdade() {
            return idade;
        }

        public void setIdade(Integer idade) {
            this.idade = idade;
        }
    }

    public class Interna2{
        private String outro;
    }

    @MetodoAnotacao(nome = "teste", descricao = "testar funcionamento parametro")
    public void teste(Integer id, String nome, Date nascimento){
    }

    @MetodoAnotacao(nome = "getId", descricao = "Pegar id da pessoa")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getNascimento() {
        return nascimento;
    }

    public void setNascimento(Date nascimento) {
        this.nascimento = nascimento;
    }

    public Pessoa(Integer id, String nome, Date nascimento) {
        this.id = id;
        this.nome = nome;
        this.nascimento = nascimento;
    }
    @ContrutorAnotacao(nome = "Pessoa Contrutor",funcao = "contrutor padrao")
    public Pessoa() {
    }

    @Override
    public String toString() {
        return "(" + getId() +",'"+getNome()+"','"+ DataFormat.formatarSimpleDate(getNascimento())+"');     ";
    }
}
