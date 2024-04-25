package intellispaces.javastatements.statement.reference;

import intellispaces.commons.action.ActionBuilders;
import intellispaces.commons.action.Getter;
import intellispaces.javastatements.statement.StatementType;
import intellispaces.javastatements.statement.StatementTypes;
import intellispaces.javastatements.statement.TypeElementFunctions;
import intellispaces.javastatements.context.TypeContext;
import intellispaces.javastatements.statement.custom.CustomType;
import intellispaces.javastatements.session.Session;

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
