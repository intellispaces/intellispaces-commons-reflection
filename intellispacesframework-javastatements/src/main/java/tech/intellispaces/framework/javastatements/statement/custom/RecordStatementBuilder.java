package tech.intellispaces.framework.javastatements.statement.custom;

import tech.intellispaces.framework.javastatements.context.TypeContext;
import tech.intellispaces.framework.javastatements.session.Session;

import javax.lang.model.element.TypeElement;

public interface RecordStatementBuilder {

  static RecordStatement build(TypeElement typeElement, TypeContext typeContext, Session session) {
    return new RecordStatementAdapter(typeElement, typeContext, session);
  }
}
