package tech.intellispaces.java.reflection.reference;

import tech.intellispaces.java.reflection.StatementType;
import tech.intellispaces.java.reflection.StatementTypes;
import tech.intellispaces.java.reflection.common.JavaModelFunctions;
import tech.intellispaces.java.reflection.context.TypeContext;
import tech.intellispaces.java.reflection.session.Session;
import tech.intellispaces.action.cache.CachedSupplierActions;
import tech.intellispaces.action.supplier.SupplierAction;

import java.util.Map;

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
