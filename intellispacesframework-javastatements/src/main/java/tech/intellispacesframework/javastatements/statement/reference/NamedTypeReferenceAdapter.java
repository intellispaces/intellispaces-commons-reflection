package tech.intellispacesframework.javastatements.statement.reference;

import tech.intellispacesframework.commons.action.ActionBuilders;
import tech.intellispacesframework.commons.action.Getter;
import tech.intellispacesframework.javastatements.context.TypeContext;
import tech.intellispacesframework.javastatements.session.Session;
import tech.intellispacesframework.javastatements.statement.StatementType;
import tech.intellispacesframework.javastatements.statement.StatementTypes;
import tech.intellispacesframework.javastatements.statement.TypeElementFunctions;

import javax.lang.model.element.TypeParameterElement;
import java.util.List;

class NamedTypeReferenceAdapter extends AbstractTypeReference implements NamedTypeReference {
  private final String name;
  private final Getter<List<TypeBoundReference>> extendedBoundsGetter;

  NamedTypeReferenceAdapter(TypeParameterElement typeParameter, TypeContext typeContext, Session session) {
    super();
    this.name = typeParameter.getSimpleName().toString();
    this.extendedBoundsGetter = ActionBuilders.cachedLazyGetter(TypeElementFunctions::getExtendedBounds, typeParameter, typeContext, session);
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.NamedTypeReference;
  }

  @Override
  public String name() {
    return name;
  }

  @Override
  public List<TypeBoundReference> extendedBounds() {
    return extendedBoundsGetter.get();
  }
}
