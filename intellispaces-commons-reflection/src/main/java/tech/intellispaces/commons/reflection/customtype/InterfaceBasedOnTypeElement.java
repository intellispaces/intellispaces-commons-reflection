package tech.intellispaces.commons.reflection.customtype;

import tech.intellispaces.commons.reflection.StatementType;
import tech.intellispaces.commons.reflection.StatementTypes;
import tech.intellispaces.commons.reflection.context.TypeContext;
import tech.intellispaces.commons.reflection.session.Session;

import javax.lang.model.element.TypeElement;

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
