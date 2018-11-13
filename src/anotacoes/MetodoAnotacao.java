package anotacoes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;


import static java.lang.annotation.RetentionPolicy.*;

@Target(ElementType.METHOD)
@Retention(RUNTIME)
public @interface MetodoAnotacao {
    String nome();
    String descricao();
}
