package tech.intellispaces.javastatements.statement.reference;

import tech.intellispaces.actions.Actions;
import tech.intellispaces.actions.Getter;
import tech.intellispaces.javastatements.context.TypeContext;
import tech.intellispaces.javastatements.session.Session;
import tech.intellispaces.javastatements.statement.StatementType;
import tech.intellispaces.javastatements.statement.StatementTypes;
import tech.intellispaces.javastatements.statement.common.TypeElementFunctions;

import java.util.Map;

/**
 * Adapter of {@link javax.lang.model.type.ArrayType} to {@link ArrayReference}.
 */
class ArrayTypeReferenceBasedOnArrayReference extends AbstractTypeReference implements ArrayReference {
  private final Getter<TypeReference> elementTypeGetter;

  ArrayTypeReferenceBasedOnArrayReference(
      javax.lang.model.type.ArrayType arrayType, TypeContext typeContext, Session session
  ) {
    super();
    this.elementTypeGetter = Actions.cachedLazyGetter(
        TypeElementFunctions::getTypeReference, arrayType.getComponentType(), typeContext, session);
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
  public TypeReference specify(Map<String, NotPrimitiveTypeReference> typeMapping) {
    TypeReference elementTypeReference = elementType().specify(typeMapping);
    return new ArrayReferenceImpl(elementTypeReference);
  }
}
