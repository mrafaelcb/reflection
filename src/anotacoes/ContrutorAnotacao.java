package anotacoes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(ElementType.CONSTRUCTOR)
@Retention(RUNTIME)
public @interface ContrutorAnotacao {
    String nome();
    String funcao();
    String padrao() default "valor padrão";
}
