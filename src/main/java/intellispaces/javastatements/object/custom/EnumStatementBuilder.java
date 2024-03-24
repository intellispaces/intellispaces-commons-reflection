package intellispaces.javastatements.object.custom;

import intellispaces.javastatements.model.context.NameContext;
import intellispaces.javastatements.model.custom.EnumStatement;
import intellispaces.javastatements.model.session.Session;

import javax.lang.model.element.TypeElement;

public interface EnumStatementBuilder {

  static EnumStatement build(TypeElement typeElement, NameContext nameContext, Session session) {
    return new EnumStatementAdapter(typeElement, nameContext, session);
  }
}
