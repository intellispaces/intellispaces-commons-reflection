package tech.intellispacesframework.javastatements.statement.reference;

import tech.intellispacesframework.commons.action.ActionBuilders;
import tech.intellispacesframework.commons.action.Getter;
import tech.intellispacesframework.javastatements.statement.StatementType;
import tech.intellispacesframework.javastatements.statement.StatementTypes;
import tech.intellispacesframework.javastatements.statement.TypeElementFunctions;
import tech.intellispacesframework.javastatements.context.TypeContext;
import tech.intellispacesframework.javastatements.statement.custom.CustomType;
import tech.intellispacesframework.javastatements.session.Session;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import java.util.List;

class CustomTypeReferenceFromDeclaredTypeAdapter extends AbstractTypeReference implements CustomTypeReference {
  private final Getter<CustomType> targetTypeGetter;
  private final Getter<List<NonPrimitiveTypeReference>> typeArgumentsGetter;

  CustomTypeReferenceFromDeclaredTypeAdapter(DeclaredType declaredType, TypeContext typeContext, Session session) {
    super();
    TypeElement typeElement = (TypeElement) declaredType.asElement();
    this.targetTypeGetter = ActionBuilders.cachedLazyGetter(TypeElementFunctions::asCustomTypeStatement, typeElement, session);
    this.typeArgumentsGetter = ActionBuilders.cachedLazyGetter(TypeElementFunctions::getTypeArguments, declaredType, typeContext, session);
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
    return typeArgumentsGetter.get();
  }
}
