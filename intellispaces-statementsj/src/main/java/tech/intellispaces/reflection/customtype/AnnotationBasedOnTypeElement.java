package tech.intellispaces.statementsj.customtype;

import javax.lang.model.element.TypeElement;

import tech.intellispaces.statementsj.StatementType;
import tech.intellispaces.statementsj.StatementTypes;
import tech.intellispaces.statementsj.context.TypeContext;
import tech.intellispaces.statementsj.session.Session;

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
