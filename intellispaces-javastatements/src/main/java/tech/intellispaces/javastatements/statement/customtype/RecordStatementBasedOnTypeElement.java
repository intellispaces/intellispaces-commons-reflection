package tech.intellispaces.javastatements.statement.customtype;

import tech.intellispaces.actions.Actions;
import tech.intellispaces.actions.Getter;
import tech.intellispaces.javastatements.context.TypeContext;
import tech.intellispaces.javastatements.session.Session;
import tech.intellispaces.javastatements.statement.StatementType;
import tech.intellispaces.javastatements.statement.StatementTypes;
import tech.intellispaces.javastatements.statement.reference.CustomTypeReference;

import javax.lang.model.element.TypeElement;
import java.util.List;

/**
 * Adapter of {@link TypeElement} to {@link RecordType}.
 */
class RecordStatementBasedOnTypeElement
    extends AbstractCustomTypeStatementBasedOnTypeElement
    implements RecordType
{
  private final Getter<List<CustomTypeReference>> implementedInterfacesGetter;

  RecordStatementBasedOnTypeElement(TypeElement typeElement, TypeContext typeContext, Session session) {
    super(typeElement, typeContext, session);
    this.implementedInterfacesGetter = Actions.cachedLazyGetter(CustomTypeFunctions::getImplementedInterfaces, this);
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.Record;
  }

  @Override
  public List<CustomTypeReference> implementedInterfaces() {
    return implementedInterfacesGetter.get();
  }
}
