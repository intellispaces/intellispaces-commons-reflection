package intellispaces.javastatements.statement.custom;

import intellispaces.javastatements.context.TypeContext;
import intellispaces.javastatements.session.Session;

import javax.lang.model.element.TypeElement;

public interface EnumStatementBuilder {

  static EnumStatement build(TypeElement typeElement, TypeContext typeContext, Session session) {
    return new EnumStatementAdapter(typeElement, typeContext, session);
  }
}
