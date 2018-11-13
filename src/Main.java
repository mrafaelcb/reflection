import modelos.Pessoa;
import util.Arquivo;

public class Main {

    public static void main(String[] args) {
       /* Banco b = new Banco();
        List<modelos.Pessoa.Pessoa> list = b.pesquisarPessoa();
        modelos.Pessoa.Pessoa p = new modelos.Pessoa.Pessoa(list.size(), "Maycon-" + list.size(), new Date());
        b.inserir(p.getClass().getSimpleName(), p.toString());
        List<modelos.Pessoa.Pessoa> lista = b.pesquisarPessoa();*/
        /*lista.forEach(e -> {
            System.out.println(e.toString());
            //b.deletarPessoa(e.getId());
        });*/
       // Arquivo.escreverArquivo("C:\\Users\\Mayco\\Desktop",lista);
        /*List<modelos.Pessoa.Pessoa> lista2 = Arquivo.lerArquivo("C:\\Users\\Mayco\\Desktop\\");
        System.out.println("Lido do Arquivo:");
        lista2.forEach(e -> {
            System.out.println(e.toString());
        });*/
        //Arquivo.escreverArquivoReflexao("C:\\Users\\Mayco\\Desktop",Pessoa.class);
        Arquivo.escreverArquivoReflexao("C:\\Users\\Mayco\\Desktop",new Pessoa());
        //Arquivo.escreverArquivoReflexao("C:\\Users\\Mayco\\Desktop","modelos.Pessoa");
    }
}