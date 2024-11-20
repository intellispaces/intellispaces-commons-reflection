package intellispaces.common.javastatement.customtype;

import intellispaces.common.javastatement.StatementType;
import intellispaces.common.javastatement.StatementTypes;
import intellispaces.common.javastatement.context.TypeContext;
import intellispaces.common.javastatement.reference.CustomTypeReference;
import intellispaces.common.javastatement.session.Session;
import tech.intellispaces.action.cache.CachedSupplierActions;
import tech.intellispaces.action.supplier.SupplierAction;

import javax.lang.model.element.TypeElement;
import java.util.List;

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
