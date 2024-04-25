package intellispaces.javastatements.statement.custom;

import intellispaces.javastatements.statement.StatementType;
import intellispaces.javastatements.statement.StatementTypes;
import intellispaces.javastatements.context.TypeContext;
import intellispaces.javastatements.session.Session;

import javax.lang.model.element.TypeElement;

class AnnotationStatementAdapter extends CustomTypeStatementAdapter implements AnnotationStatement {

  AnnotationStatementAdapter(TypeElement typeElement, TypeContext typeContext, Session session) {
    super(typeElement, typeContext, session);
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.Annotation;
  }
}
