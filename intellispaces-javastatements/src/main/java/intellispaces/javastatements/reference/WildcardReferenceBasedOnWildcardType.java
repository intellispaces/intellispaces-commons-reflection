package intellispaces.javastatements.reference;

import intellispaces.actions.Actions;
import intellispaces.actions.getter.Getter;
import intellispaces.javastatements.StatementType;
import intellispaces.javastatements.StatementTypes;
import intellispaces.javastatements.common.JavaModelFunctions;
import intellispaces.javastatements.context.TypeContext;
import intellispaces.javastatements.session.Session;

import java.util.Map;
import java.util.Optional;

/**
 * Adapter of {@link javax.lang.model.type.WildcardType} to {@link WildcardReference}.
 */
class WildcardReferenceBasedOnWildcardType extends AbstractTypeReference implements WildcardReference {
  private final Getter<Optional<ReferenceBound>> extendedBoundGetter;
  private final Getter<Optional<ReferenceBound>> superBoundGetter;

  WildcardReferenceBasedOnWildcardType(javax.lang.model.type.WildcardType wildcardType, TypeContext typeContext, Session session) {
    super();
    this.extendedBoundGetter = Actions.cachedLazyGetter(JavaModelFunctions::getExtendedBound, wildcardType, typeContext, session);
    this.superBoundGetter = Actions.cachedLazyGetter(JavaModelFunctions::getSuperBound, wildcardType, typeContext, session);
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.WildcardReference;
  }

  @Override
  public Optional<ReferenceBound> extendedBound() {
    return extendedBoundGetter.get();
  }

  @Override
  public Optional<ReferenceBound> superBound() {
    return superBoundGetter.get();
  }

  @Override
  public TypeReference specify(Map<String, NotPrimitiveReference> typeMapping) {
    ReferenceBound extendedBound = extendedBound().orElse(null);
    if (extendedBound != null) {
      extendedBound = (ReferenceBound) extendedBound.specify(typeMapping);
    }
    ReferenceBound superBound = superBound().orElse(null);
    if (superBound != null) {
      superBound = (ReferenceBound) superBound.specify(typeMapping);
    }
    return new WildcardReferenceImpl(extendedBound, superBound);
  }
}
