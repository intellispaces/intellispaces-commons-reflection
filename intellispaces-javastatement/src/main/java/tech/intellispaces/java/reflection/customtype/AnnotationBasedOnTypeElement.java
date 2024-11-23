package tech.intellispaces.java.reflection.customtype;

import tech.intellispaces.java.reflection.StatementType;
import tech.intellispaces.java.reflection.StatementTypes;
import tech.intellispaces.java.reflection.context.TypeContext;
import tech.intellispaces.java.reflection.session.Session;

import javax.lang.model.element.TypeElement;

/**
 * Adapter of {@link TypeElement} to {@link AnnotationType}.
 */
class AnnotationBasedOnTypeElement
    extends AbstractCustomTypeStatementBasedOnTypeElement
    implements AnnotationType
{
  AnnotationBasedOnTypeElement(TypeElement typeElement, TypeContext typeContext, Session session) {
    super(typeElement, typeContext, session);
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.Annotation;
  }
}
