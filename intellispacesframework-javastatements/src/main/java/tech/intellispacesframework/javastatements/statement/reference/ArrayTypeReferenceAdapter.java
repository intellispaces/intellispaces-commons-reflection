package tech.intellispacesframework.javastatements.statement.reference;

import tech.intellispacesframework.commons.action.ActionBuilders;
import tech.intellispacesframework.commons.action.Getter;
import tech.intellispacesframework.javastatements.context.TypeContext;
import tech.intellispacesframework.javastatements.session.Session;
import tech.intellispacesframework.javastatements.statement.StatementType;
import tech.intellispacesframework.javastatements.statement.StatementTypes;
import tech.intellispacesframework.javastatements.statement.TypeElementFunctions;

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
