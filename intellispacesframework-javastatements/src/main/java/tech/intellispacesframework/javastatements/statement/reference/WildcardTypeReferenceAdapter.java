package tech.intellispacesframework.javastatements.statement.reference;

import tech.intellispacesframework.commons.action.ActionBuilders;
import tech.intellispacesframework.commons.action.Getter;
import tech.intellispacesframework.javastatements.context.TypeContext;
import tech.intellispacesframework.javastatements.session.Session;
import tech.intellispacesframework.javastatements.statement.StatementType;
import tech.intellispacesframework.javastatements.statement.StatementTypes;
import tech.intellispacesframework.javastatements.statement.TypeElementFunctions;

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
