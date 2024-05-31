package tech.intellispaces.framework.javastatements.statement.custom;

import tech.intellispaces.framework.javastatements.context.TypeContext;
import tech.intellispaces.framework.javastatements.session.Session;

import javax.lang.model.element.TypeElement;

public interface ClassStatementBuilder {

  static ClassStatement build(TypeElement typeElement, TypeContext typeContext, Session session) {
    return new TypeElementToClassStatementAdapter(typeElement, typeContext, session);
  }

  static ClassStatement build(Class<?> aClass) {
    return new ClassToClassStatementAdapter(aClass);
  }
}
