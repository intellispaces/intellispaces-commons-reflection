package intellispaces.javastatements.statement.reference;

import intellispaces.commons.action.ActionBuilders;
import intellispaces.commons.action.Getter;
import intellispaces.javastatements.statement.StatementType;
import intellispaces.javastatements.statement.StatementTypes;
import intellispaces.javastatements.context.TypeContext;
import intellispaces.javastatements.statement.TypeElementFunctions;
import intellispaces.javastatements.statement.custom.CustomType;
import intellispaces.javastatements.session.Session;

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
