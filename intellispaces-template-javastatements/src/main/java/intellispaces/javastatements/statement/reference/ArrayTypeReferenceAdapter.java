package intellispaces.javastatements.statement.reference;

import intellispaces.commons.action.ActionBuilders;
import intellispaces.commons.action.Getter;
import intellispaces.javastatements.statement.StatementType;
import intellispaces.javastatements.statement.StatementTypes;
import intellispaces.javastatements.statement.TypeElementFunctions;
import intellispaces.javastatements.context.TypeContext;
import intellispaces.javastatements.session.Session;

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
