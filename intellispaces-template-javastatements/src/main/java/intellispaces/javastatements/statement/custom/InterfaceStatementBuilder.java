package intellispaces.javastatements.statement.custom;

import intellispaces.javastatements.context.TypeContext;
import intellispaces.javastatements.session.Session;

import javax.lang.model.element.TypeElement;

public interface InterfaceStatementBuilder {

  static InterfaceStatement build(TypeElement typeElement, TypeContext typeContext, Session session) {
    return new InterfaceStatementAdapter(typeElement, typeContext, session);
  }
}
