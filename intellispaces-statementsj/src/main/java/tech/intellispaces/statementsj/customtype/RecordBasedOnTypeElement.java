package tech.intellispaces.statementsj.customtype;

import java.util.List;
import javax.lang.model.element.TypeElement;

import tech.intellispaces.actions.cache.CachedSupplierActions;
import tech.intellispaces.actions.supplier.SupplierAction;
import tech.intellispaces.statementsj.StatementType;
import tech.intellispaces.statementsj.StatementTypes;
import tech.intellispaces.statementsj.context.TypeContext;
import tech.intellispaces.statementsj.reference.CustomTypeReference;
import tech.intellispaces.statementsj.session.Session;

/**
 * Adapter of {@link TypeElement} to {@link RecordType}.
 */
class RecordBasedOnTypeElement
    extends AbstractCustomTypeStatementBasedOnTypeElement
    implements RecordType
{
  private final SupplierAction<List<CustomTypeReference>> implementedInterfacesGetter;

  RecordBasedOnTypeElement(TypeElement typeElement, TypeContext typeContext, Session session) {
    super(typeElement, typeContext, session);
    this.implementedInterfacesGetter = CachedSupplierActions.get(CustomTypeFunctions::getImplementedInterfaces, this);
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
