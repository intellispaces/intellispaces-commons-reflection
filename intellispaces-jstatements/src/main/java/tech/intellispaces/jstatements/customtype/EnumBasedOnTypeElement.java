package tech.intellispaces.jstatements.customtype;

import java.util.List;
import javax.lang.model.element.TypeElement;

import tech.intellispaces.actions.cache.CachedSupplierActions;
import tech.intellispaces.actions.supplier.SupplierAction;
import tech.intellispaces.jstatements.StatementType;
import tech.intellispaces.jstatements.StatementTypes;
import tech.intellispaces.jstatements.context.TypeContext;
import tech.intellispaces.jstatements.reference.CustomTypeReference;
import tech.intellispaces.jstatements.session.Session;

/**
 * Adapter of {@link TypeElement} to {@link EnumType}.
 */
class EnumBasedOnTypeElement
    extends AbstractCustomTypeStatementBasedOnTypeElement
    implements EnumType
{
  private final SupplierAction<List<CustomTypeReference>> implementedInterfacesGetter;

  EnumBasedOnTypeElement(
      TypeElement typeElement, TypeContext typeContext, Session session
  ) {
    super(typeElement, typeContext, session);
    this.implementedInterfacesGetter = CachedSupplierActions.get(
        CustomTypeFunctions::getImplementedInterfaces, this);
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.Enum;
  }

  @Override
  public List<CustomTypeReference> implementedInterfaces() {
    return implementedInterfacesGetter.get();
  }
}
