package util;

import modelos.Pessoa;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Arquivo {

    public Arquivo() {
    }

    public static StringBuffer pegarClasseInterna(Class classe){
        StringBuffer classeInterna = new StringBuffer();
        String externa = classe.getPackageName() + "." + classe.getSimpleName();
        for (Class k: classe.getClasses()) {
            classeInterna.append(preencherClasseInterna(k));
            classeInterna.append("\n");
        }
        return classeInterna;
    }

    public static StringBuffer preencherClasseInterna(Class classe){
        StringBuffer classeInterna = new StringBuffer();
        Annotation[] c = classe.getAnnotations();
        classeInterna.append(pegarAnotacoes(c));
        classeInterna.append("\t");
        classeInterna.append(Modifier.toString(classe.getModifiers()));//modificador
        classeInterna.append(" class ");
        classeInterna.append(classe.getSimpleName());//nome da classe
        classeInterna.append(pegarExtends(classe));
        classeInterna.append(pegarImplements(classe));
        classeInterna.append(" {\n");
        classeInterna.append("\n");
        classeInterna.append(pegarAtributos(classe));//pegar atributos
        classeInterna.append("\n");
        classeInterna.append(pegarClasseInterna(classe));
        classeInterna.append(pegarConstrutor(classe));//pegarConstrutor
        classeInterna.append("\n");
        classeInterna.append(pegarMetodos(classe));
        classeInterna.append("\n");
        classeInterna.append("\t}\n");
        return classeInterna;
    }

    public static StringBuffer pegarAnotacoes(Annotation[] a){
        StringBuffer anotacao = new StringBuffer();
        for (int i = 0; i < a.length; i++) {
            anotacao.append("\t@");
            anotacao.append(a[i].annotationType().getSimpleName());
            for(int j = a[i].toString().indexOf("(");j <a[i].toString().length(); j++){
                anotacao.append(a[i].toString().charAt(j));
            }
            anotacao.append("\n");
        }
        return anotacao;
    }

    public static StringBuffer pegarExtends(Class classe){
        StringBuffer extende = new StringBuffer();
        if(classe.getSuperclass() != null && classe.getSuperclass()!=Object.class){//pegar supers
            extende.append(" extends " + classe.getSuperclass().getSimpleName());
        }
        return extende;
    }

    public static StringBuffer pegarImplements(Class classe){
        StringBuffer implementa = new StringBuffer();
        for(Class c : classe.getInterfaces()){//pegar interface
            implementa.append(" implements " + c.getSimpleName());
        }
        return implementa;
    }

    public static StringBuffer pegarAtributos(Class classe){
        StringBuffer atributo = new StringBuffer();
        for(Field a : classe.getDeclaredFields()){//pegar atributos
            Annotation[] n = a.getAnnotations();
            atributo.append(pegarAnotacoes(n));
            atributo.append("\t");
            atributo.append(Modifier.toString(a.getModifiers()) + " ");
            atributo.append(a.getType().getSimpleName() + " ");
            atributo.append(a.getName() + ";");
            atributo.append("\n");
        }
        return atributo;
    }

    public static StringBuffer pegarParametro(Class[] p){
        StringBuffer parametro = new StringBuffer();
        for(int i = 0; i < p.length;i++){
            if(i>=1) {
                parametro.append(", ");
            }
            parametro.append(p[i].getSimpleName() + " ");
            parametro.append(p[i].getSimpleName().toLowerCase());
        }
        return parametro;
    }

    public static StringBuffer pegarConstrutor(Class classe){
        StringBuffer construtor = new StringBuffer();
        for(Constructor c : classe.getDeclaredConstructors()){//pegar construtores
            Annotation[] n = c.getAnnotations();
            construtor.append(pegarAnotacoes(n));
            construtor.append("\t");
            construtor.append(Modifier.toString((c.getModifiers()))+" ");
            construtor.append(classe.getSimpleName() + "(");
            Class[] p = c.getParameterTypes();//pegar parametros
            construtor.append(pegarParametro(p));
            construtor.append("){");
            construtor.append("...");
            construtor.append("}\n\n");
        }

        return construtor;
    }

    public static StringBuffer pegarMetodos(Class classe){
        StringBuffer metodos = new StringBuffer();
        for(Method m : classe.getDeclaredMethods()){//pegar metodos
            Annotation[] a = m.getAnnotations();
            metodos.append(pegarAnotacoes(a));
            metodos.append("\t");
            metodos.append(Modifier.toString((m.getModifiers()))+" ");
            metodos.append(m.getReturnType().getSimpleName() + " ");
            metodos.append(m.getName() + "(");
            Class[] p = m.getParameterTypes();//pegar parametros
            metodos.append(pegarParametro(p));
            metodos.append("){");
            metodos.append("...");
            metodos.append("}\n\n");
        }

        return metodos;
    }

    public static boolean escreverArquivoReflexao(String caminho, String classe){
        Class a = null;
        try {
            Object o = Class.forName(classe).newInstance();
            return escreverArquivoReflexao(caminho,o.getClass());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        System.out.println("Classe não encontrada");
        return false;
    }

    public static boolean escreverArquivoReflexao(String caminho, Object objeto){
        return escreverArquivoReflexao(caminho,objeto.getClass());
    }

    public static boolean escreverArquivoReflexao(String caminho, Class classe){
        OutputStream os;
        try{
            os = new FileOutputStream(caminho+"\\"+classe.getSimpleName()+".java");
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            Annotation[] c = classe.getAnnotations();
            bw.append(pegarAnotacoes(c));
            bw.append("\t");
            bw.append(Modifier.toString(classe.getModifiers()));//modificador
            bw.append(" class ");
            bw.append(classe.getSimpleName());//nome da classe
            bw.append(pegarExtends(classe));
            bw.append(pegarImplements(classe));
            bw.append(" {\n");
            bw.newLine();
            bw.append(pegarAtributos(classe));//pegar atributos
            bw.newLine();
            bw.append(pegarClasseInterna(classe));
            bw.append(pegarConstrutor(classe));//pegarConstrutor
            bw.newLine();
            bw.append(pegarMetodos(classe));
            bw.newLine();
            bw.append("\t}\n");
            bw.close();
            return true;
        }catch (IOException m){
            Logger.getLogger(Arquivo.class.getName()).log(Level.SEVERE,null,m);
        }
        return false;
    }

    public static boolean escreverArquivo(String caminho, List<Pessoa> lista){
        /*
        Escrever todas as informações de uma classe
         */
        OutputStream os;
        try{
            os = new FileOutputStream(caminho+"\\"+lista.get(0).getClass().getSimpleName()+".txt");
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            lista.forEach(p ->{
                try {
                    bw.append(p.getId().toString()).append(" ");
                    bw.append(p.getNome()).append(" ");
                    bw.append(DataFormat.formatarSimpleDate(p.getNascimento())).append("\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            bw.close();
            return true;
        }catch (IOException m){
            Logger.getLogger(Arquivo.class.getName()).log(Level.SEVERE,null,m);
        }
        return false;
        //Metodo antigo
        /*try{
            Formatter outPut = new Formatter(caminho);
            SimpleDateFormat formata = new SimpleDateFormat("yyyy-MM-dd");
            lista.forEach(p ->{
                outPut.format("%d %s %s\n",p.getId(),p.getNome(), DataFormat.formatarSimpleDate(p.getNascimento()));
            });
            outPut.close();
            return true;
        }catch(FileNotFoundException ex){
            Logger.getLogger(Arquivo.class.getName()).log(Level.SEVERE,null,ex);
        }
        return false;*/
    }

    public static List<Pessoa> lerArquivo(String caminho){
        Scanner input;
        List<Pessoa> lista = new ArrayList<>();
        try {
            input = new Scanner(new File(caminho));
            while(input.hasNext()){
                Pessoa pessoa = new Pessoa();
                pessoa.setId(input.nextInt());
                pessoa.setNome(input.next());
                //pessoa.setNascimento(DataFormat.stringDate(input.next()));
                lista.add(pessoa);
            }
            return lista;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return lista;
    }

}
