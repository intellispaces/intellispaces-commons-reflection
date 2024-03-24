package intellispaces.javastatements.object.custom;

import intellispaces.javastatements.model.context.NameContext;
import intellispaces.javastatements.model.custom.RecordStatement;
import intellispaces.javastatements.model.session.Session;

import javax.lang.model.element.TypeElement;

public interface RecordStatementBuilder {

  static RecordStatement build(TypeElement typeElement, NameContext nameContext, Session session) {
    return new RecordStatementAdapter(typeElement, nameContext, session);
  }
}
