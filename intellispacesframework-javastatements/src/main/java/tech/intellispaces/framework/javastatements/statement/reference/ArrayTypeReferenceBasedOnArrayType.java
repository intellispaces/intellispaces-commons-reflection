package tech.intellispaces.framework.javastatements.statement.reference;

import tech.intellispaces.framework.commons.action.Actions;
import tech.intellispaces.framework.commons.action.Getter;
import tech.intellispaces.framework.javastatements.context.TypeContext;
import tech.intellispaces.framework.javastatements.session.Session;
import tech.intellispaces.framework.javastatements.statement.StatementType;
import tech.intellispaces.framework.javastatements.statement.StatementTypes;
import tech.intellispaces.framework.javastatements.statement.common.TypeElementFunctions;

import javax.lang.model.type.ArrayType;
import java.util.Map;

/**
 * Adapter of {@link ArrayType} to {@link ArrayTypeReference}.
 */
class ArrayTypeReferenceBasedOnArrayType extends AbstractTypeReference implements ArrayTypeReference {
  private final Getter<TypeReference> elementTypeGetter;

  ArrayTypeReferenceBasedOnArrayType(ArrayType arrayType, TypeContext typeContext, Session session) {
    super();
    this.elementTypeGetter = Actions.cachedLazyGetter(
        TypeElementFunctions::getTypeReference, arrayType.getComponentType(), typeContext, session);
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.ArrayTypeReference;
  }

  @Override
  public TypeReference elementType() {
    return elementTypeGetter.get();
  }

  @Override
  public TypeReference specify(Map<String, NonPrimitiveTypeReference> typeMapping) {
    TypeReference elementType = elementType().specify(typeMapping);
    return new ArrayTypeReferenceImpl(elementType);
  }
}
