package intellispaces.javastatements.statement.custom;

import intellispaces.javastatements.context.TypeContext;
import intellispaces.javastatements.session.Session;

import javax.lang.model.element.TypeElement;

public interface RecordStatementBuilder {

  static RecordStatement build(TypeElement typeElement, TypeContext typeContext, Session session) {
    return new RecordStatementAdapter(typeElement, typeContext, session);
  }
}
