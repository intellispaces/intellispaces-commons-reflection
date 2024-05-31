package tech.intellispaces.framework.javastatements.statement.reference;

import tech.intellispaces.framework.commons.action.ActionBuilders;
import tech.intellispaces.framework.commons.action.Getter;
import tech.intellispaces.framework.javastatements.context.TypeContext;
import tech.intellispaces.framework.javastatements.session.Session;
import tech.intellispaces.framework.javastatements.statement.StatementType;
import tech.intellispaces.framework.javastatements.statement.StatementTypes;
import tech.intellispaces.framework.javastatements.statement.TypeElementFunctions;

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
