package tech.intellispaces.framework.javastatements.statement.type;

import tech.intellispaces.framework.commons.action.Actions;
import tech.intellispaces.framework.commons.action.Getter;
import tech.intellispaces.framework.javastatements.context.TypeContext;
import tech.intellispaces.framework.javastatements.session.Session;
import tech.intellispaces.framework.javastatements.statement.StatementType;
import tech.intellispaces.framework.javastatements.statement.StatementTypes;
import tech.intellispaces.framework.javastatements.statement.common.TypeElementFunctions;

import java.util.Map;

/**
 * Adapter of {@link javax.lang.model.type.ArrayType} to {@link ArrayType}.
 */
class ArrayTypeReferenceBasedOnArrayType extends AbstractType implements ArrayType {
  private final Getter<Type> elementTypeGetter;

  ArrayTypeReferenceBasedOnArrayType(javax.lang.model.type.ArrayType arrayType, TypeContext typeContext, Session session) {
    super();
    this.elementTypeGetter = Actions.cachedLazyGetter(
        TypeElementFunctions::getTypeReference, arrayType.getComponentType(), typeContext, session);
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.ArrayType;
  }

  @Override
  public Type elementType() {
    return elementTypeGetter.get();
  }

  @Override
  public Type specify(Map<String, NotPrimitiveType> typeMapping) {
    Type elementType = elementType().specify(typeMapping);
    return new ArrayTypeImpl(elementType);
  }
}
