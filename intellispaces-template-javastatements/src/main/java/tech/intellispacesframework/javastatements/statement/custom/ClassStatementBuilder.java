package tech.intellispacesframework.javastatements.statement.custom;

import tech.intellispacesframework.javastatements.context.TypeContext;
import tech.intellispacesframework.javastatements.session.Session;

import javax.lang.model.element.TypeElement;

public interface ClassStatementBuilder {

  static ClassStatement build(TypeElement typeElement, TypeContext typeContext, Session session) {
    return new TypeElementToClassStatementAdapter(typeElement, typeContext, session);
  }

  static ClassStatement build(Class<?> aClass) {
    return new ClassToClassStatementAdapter(aClass);
  }
}
