package tech.intellispaces.statementsj.customtype;

import javax.lang.model.element.TypeElement;

import tech.intellispaces.statementsj.StatementType;
import tech.intellispaces.statementsj.StatementTypes;
import tech.intellispaces.statementsj.context.TypeContext;
import tech.intellispaces.statementsj.session.Session;

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
