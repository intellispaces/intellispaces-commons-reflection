package tech.intellispaces.reflection.reference;

import java.util.Map;
import java.util.Optional;

import tech.intellispaces.actions.cache.CachedSupplierActions;
import tech.intellispaces.actions.supplier.SupplierAction;
import tech.intellispaces.reflection.StatementType;
import tech.intellispaces.reflection.StatementTypes;
import tech.intellispaces.reflection.common.JavaModelFunctions;
import tech.intellispaces.reflection.context.TypeContext;
import tech.intellispaces.reflection.session.Session;

/**
 * Adapter of {@link javax.lang.model.type.WildcardType} to {@link WildcardReference}.
 */
class WildcardReferenceBasedOnWildcardType extends AbstractTypeReference implements WildcardReference {
  private final SupplierAction<Optional<ReferenceBound>> extendedBoundGetter;
  private final SupplierAction<Optional<ReferenceBound>> superBoundGetter;

  WildcardReferenceBasedOnWildcardType(javax.lang.model.type.WildcardType wildcardType, TypeContext typeContext, Session session) {
    super();
    this.extendedBoundGetter = CachedSupplierActions.get(JavaModelFunctions::getExtendedBound, wildcardType, typeContext, session);
    this.superBoundGetter = CachedSupplierActions.get(JavaModelFunctions::getSuperBound, wildcardType, typeContext, session);
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
  public TypeReference effective(Map<String, NotPrimitiveReference> typeMapping) {
    ReferenceBound extendedBound = extendedBound().orElse(null);
    if (extendedBound != null) {
      extendedBound = (ReferenceBound) extendedBound.effective(typeMapping);
    }
    ReferenceBound superBound = superBound().orElse(null);
    if (superBound != null) {
      superBound = (ReferenceBound) superBound.effective(typeMapping);
    }
    return new WildcardReferenceImpl(extendedBound, superBound);
  }

  @Override
  public boolean isVoidType() {
    return false;
  }
}
