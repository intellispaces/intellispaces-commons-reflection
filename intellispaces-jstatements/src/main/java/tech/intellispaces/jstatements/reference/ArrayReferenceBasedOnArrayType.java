package tech.intellispaces.jstatements.reference;

import java.util.Map;

import tech.intellispaces.actions.cache.CachedSupplierActions;
import tech.intellispaces.actions.supplier.SupplierAction;
import tech.intellispaces.jstatements.StatementType;
import tech.intellispaces.jstatements.StatementTypes;
import tech.intellispaces.jstatements.common.JavaModelFunctions;
import tech.intellispaces.jstatements.context.TypeContext;
import tech.intellispaces.jstatements.session.Session;

/**
 * Adapter of {@link javax.lang.model.type.ArrayType} to {@link ArrayReference}.
 */
class ArrayReferenceBasedOnArrayType extends AbstractTypeReference implements ArrayReference {
  private final SupplierAction<TypeReference> elementTypeGetter;

  ArrayReferenceBasedOnArrayType(
      javax.lang.model.type.ArrayType arrayType, TypeContext typeContext, Session session
  ) {
    super();
    this.elementTypeGetter = CachedSupplierActions.get(
        JavaModelFunctions::getTypeReference, arrayType.getComponentType(), typeContext, session);
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.ArrayReference;
  }

  @Override
  public TypeReference elementType() {
    return elementTypeGetter.get();
  }

  @Override
  public TypeReference effective(Map<String, NotPrimitiveReference> typeMapping) {
    TypeReference elementTypeReference = elementType().effective(typeMapping);
    return new ArrayReferenceImpl(elementTypeReference);
  }

  @Override
  public boolean isVoidType() {
    return false;
  }
}
