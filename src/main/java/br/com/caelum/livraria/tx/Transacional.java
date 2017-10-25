package br.com.caelum.livraria.tx;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.interceptor.InterceptorBinding;

@InterceptorBinding
// está associada a um interceptador
@Target({ ElementType.METHOD, ElementType.TYPE })
// anotação q deve ser usada em cima do método e classe
@Retention(RetentionPolicy.RUNTIME)
// A nossa anotação não há um impacto no compilador (source), ela deve funcionar
// na hora de executar (RUNTIME)
public @interface Transacional {

}
