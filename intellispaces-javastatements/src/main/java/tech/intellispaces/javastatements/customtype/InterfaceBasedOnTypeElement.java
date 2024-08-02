package tech.intellispaces.javastatements.customtype;

import tech.intellispaces.javastatements.StatementType;
import tech.intellispaces.javastatements.StatementTypes;
import tech.intellispaces.javastatements.context.TypeContext;
import tech.intellispaces.javastatements.session.Session;

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
