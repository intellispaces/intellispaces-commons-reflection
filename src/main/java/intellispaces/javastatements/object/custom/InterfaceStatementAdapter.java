package intellispaces.javastatements.object.custom;

import intellispaces.javastatements.model.context.NameContext;
import intellispaces.javastatements.model.custom.InterfaceStatement;
import intellispaces.javastatements.model.session.Session;

import javax.lang.model.element.TypeElement;

public class InterfaceStatementAdapter extends CustomTypeStatementAdapter implements InterfaceStatement {

  InterfaceStatementAdapter(TypeElement typeElement, NameContext nameContext, Session session) {
    super(typeElement, nameContext, session);
  }
}
