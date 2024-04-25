package intellispaces.javastatements.statement.reference;

import intellispaces.commons.action.ActionBuilders;
import intellispaces.commons.action.Getter;
import intellispaces.javastatements.statement.StatementType;
import intellispaces.javastatements.statement.StatementTypes;
import intellispaces.javastatements.statement.TypeElementFunctions;
import intellispaces.javastatements.context.TypeContext;
import intellispaces.javastatements.session.Session;

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
