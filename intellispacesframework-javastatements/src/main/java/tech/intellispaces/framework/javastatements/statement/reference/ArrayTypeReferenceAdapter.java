package tech.intellispaces.framework.javastatements.statement.reference;

import tech.intellispaces.framework.commons.action.ActionBuilders;
import tech.intellispaces.framework.commons.action.Getter;
import tech.intellispaces.framework.javastatements.context.TypeContext;
import tech.intellispaces.framework.javastatements.session.Session;
import tech.intellispaces.framework.javastatements.statement.StatementType;
import tech.intellispaces.framework.javastatements.statement.StatementTypes;
import tech.intellispaces.framework.javastatements.statement.TypeElementFunctions;

import javax.lang.model.type.ArrayType;

class ArrayTypeReferenceAdapter extends AbstractTypeReference implements ArrayTypeReference {
  private final Getter<TypeReference> elementTypeGetter;

  ArrayTypeReferenceAdapter(ArrayType arrayType, TypeContext typeContext, Session session) {
    super();
    this.elementTypeGetter = ActionBuilders.cachedLazyGetter(TypeElementFunctions::getTypeReference, arrayType.getComponentType(), typeContext, session);
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.ArrayTypeReference;
  }

  @Override
  public TypeReference elementType() {
    return elementTypeGetter.get();
  }
}
