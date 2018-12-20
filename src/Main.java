import modelos.Pessoa;
import util.Arquivo;
import util.Banco;
import util.DataFormat;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        //Arquivo.escreverArquivoReflexao("C:\\Users\\Mayco\\Desktop",Pessoa.class);
        //Arquivo.escreverArquivoReflexao("C:\\Users\\Mayco\\Desktop",new Pessoa());
        //Arquivo.escreverArquivoReflexao("C:\\Users\\Mayco\\Desktop","modelos.Pessoa");
        Banco b = new Banco();
        try {
            List<Pessoa> p = (List<Pessoa>) (Object) b.pesquisar(Pessoa.class);
            p.forEach(e ->{
                System.out.println(e);
            });
        }catch (InstantiationException | IllegalAccessException e){

        }

        //System.out.println(query(new Pessoa(1,"maycon",new Date())));
    }
}