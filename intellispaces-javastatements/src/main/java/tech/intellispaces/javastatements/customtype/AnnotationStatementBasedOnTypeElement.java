package tech.intellispaces.javastatements.customtype;

import tech.intellispaces.javastatements.context.TypeContext;
import tech.intellispaces.javastatements.session.Session;
import tech.intellispaces.javastatements.StatementType;
import tech.intellispaces.javastatements.StatementTypes;

import javax.lang.model.element.TypeElement;

/**
 * Adapter of {@link TypeElement} to {@link AnnotationType}.
 */
class AnnotationStatementBasedOnTypeElement
    extends AbstractCustomTypeStatementBasedOnTypeElement
    implements AnnotationType
{
  AnnotationStatementBasedOnTypeElement(TypeElement typeElement, TypeContext typeContext, Session session) {
    super(typeElement, typeContext, session);
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.Annotation;
  }
}
