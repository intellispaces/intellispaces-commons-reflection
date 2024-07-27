package tech.intellispaces.framework.javastatements.statement.custom;

import tech.intellispaces.framework.commons.action.Actions;
import tech.intellispaces.framework.commons.action.Getter;
import tech.intellispaces.framework.javastatements.context.TypeContext;
import tech.intellispaces.framework.javastatements.session.Session;
import tech.intellispaces.framework.javastatements.statement.StatementType;
import tech.intellispaces.framework.javastatements.statement.StatementTypes;
import tech.intellispaces.framework.javastatements.statement.type.CustomType;

import javax.lang.model.element.TypeElement;
import java.util.List;

/**
 * Adapter of {@link TypeElement} to {@link RecordStatement}.
 */
class RecordStatementBasedOnStatementElement
    extends AbstractCustomTypeStatementBasedOnStatementElement
    implements RecordStatement
{
  private final Getter<List<CustomType>> implementedInterfacesGetter;

  RecordStatementBasedOnStatementElement(TypeElement typeElement, TypeContext typeContext, Session session) {
    super(typeElement, typeContext, session);
    this.implementedInterfacesGetter = Actions.cachedLazyGetter(CustomTypeFunctions::getImplementedInterfaces, this);
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.Record;
  }

  @Override
  public List<CustomType> implementedInterfaces() {
    return implementedInterfacesGetter.get();
  }
}
