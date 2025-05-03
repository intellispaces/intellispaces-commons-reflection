package tech.intellispaces.jstatements.customtype;

import javax.lang.model.element.TypeElement;

import tech.intellispaces.jstatements.StatementType;
import tech.intellispaces.jstatements.StatementTypes;
import tech.intellispaces.jstatements.context.TypeContext;
import tech.intellispaces.jstatements.session.Session;

/**
 * Adapter of {@link TypeElement} to {@link InterfaceType}.
 */
class InterfaceBasedOnTypeElement
    extends AbstractCustomTypeStatementBasedOnTypeElement
    implements InterfaceType
{
  InterfaceBasedOnTypeElement(TypeElement typeElement, TypeContext typeContext, Session session) {
    super(typeElement, typeContext, session);
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.Interface;
  }
}
