package intellispaces.javastatements.statement.reference;

import intellispaces.commons.action.ActionBuilders;
import intellispaces.commons.action.Getter;
import intellispaces.javastatements.statement.StatementType;
import intellispaces.javastatements.statement.StatementTypes;
import intellispaces.javastatements.statement.TypeElementFunctions;
import intellispaces.javastatements.context.TypeContext;
import intellispaces.javastatements.session.Session;

import javax.lang.model.type.WildcardType;
import java.util.Optional;

class WildcardTypeReferenceAdapter extends AbstractTypeReference implements WildcardTypeReference {
  private final Getter<Optional<TypeBoundReference>> extendedBoundGetter;
  private final Getter<Optional<TypeBoundReference>> superBoundGetter;

  WildcardTypeReferenceAdapter(WildcardType wildcardType, TypeContext typeContext, Session session) {
    super();
    this.extendedBoundGetter = ActionBuilders.cachedLazyGetter(TypeElementFunctions::getExtendedBound, wildcardType, typeContext, session);
    this.superBoundGetter = ActionBuilders.cachedLazyGetter(TypeElementFunctions::getSuperBound, wildcardType, typeContext, session);
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.WildcardTypeReference;
  }

  @Override
  public Optional<TypeBoundReference> extendedBound() {
    return extendedBoundGetter.get();
  }

  @Override
  public Optional<TypeBoundReference> superBound() {
    return superBoundGetter.get();
  }
}
