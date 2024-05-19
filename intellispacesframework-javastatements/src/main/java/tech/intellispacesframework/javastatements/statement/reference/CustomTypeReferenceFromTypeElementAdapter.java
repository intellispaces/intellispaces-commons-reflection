package tech.intellispacesframework.javastatements.statement.reference;

import tech.intellispacesframework.commons.action.ActionBuilders;
import tech.intellispacesframework.commons.action.Getter;
import tech.intellispacesframework.javastatements.context.TypeContext;
import tech.intellispacesframework.javastatements.session.Session;
import tech.intellispacesframework.javastatements.statement.StatementType;
import tech.intellispacesframework.javastatements.statement.StatementTypes;
import tech.intellispacesframework.javastatements.statement.TypeElementFunctions;
import tech.intellispacesframework.javastatements.statement.custom.CustomType;

import javax.lang.model.element.TypeElement;
import java.util.List;

class CustomTypeReferenceFromTypeElementAdapter extends AbstractTypeReference implements CustomTypeReference {
  private final Getter<CustomType> targetTypeGetter;

  CustomTypeReferenceFromTypeElementAdapter(TypeElement typeElement, TypeContext typeContext, Session session) {
    super();
    this.targetTypeGetter = ActionBuilders.cachedLazyGetter(TypeElementFunctions::asCustomTypeStatement, typeElement, session);
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.CustomTypeReference;
  }

  @Override
  public CustomType targetType() {
    return targetTypeGetter.get();
  }

  @Override
  public List<NonPrimitiveTypeReference> typeArguments() {
    return List.of();
  }
}
