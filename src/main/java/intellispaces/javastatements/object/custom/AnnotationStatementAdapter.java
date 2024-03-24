package intellispaces.javastatements.object.custom;

import intellispaces.javastatements.model.context.NameContext;
import intellispaces.javastatements.model.custom.AnnotationStatement;
import intellispaces.javastatements.model.session.Session;

import javax.lang.model.element.TypeElement;

class AnnotationStatementAdapter extends CustomTypeStatementAdapter implements AnnotationStatement {

  AnnotationStatementAdapter(TypeElement typeElement, NameContext nameContext, Session session) {
    super(typeElement, nameContext, session);
  }
}
