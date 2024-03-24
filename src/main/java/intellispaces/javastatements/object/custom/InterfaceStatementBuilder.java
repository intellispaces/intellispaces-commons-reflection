package intellispaces.javastatements.object.custom;

import intellispaces.javastatements.model.context.NameContext;
import intellispaces.javastatements.model.custom.InterfaceStatement;
import intellispaces.javastatements.model.session.Session;

import javax.lang.model.element.TypeElement;

public interface InterfaceStatementBuilder {

  static InterfaceStatement build(TypeElement typeElement, NameContext nameContext, Session session) {
    return new InterfaceStatementAdapter(typeElement, nameContext, session);
  }
}
