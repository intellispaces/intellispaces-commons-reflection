package intellispaces.common.javastatement.reference;

import intellispaces.common.javastatement.StatementType;
import intellispaces.common.javastatement.StatementTypes;
import intellispaces.common.javastatement.common.JavaModelFunctions;
import intellispaces.common.javastatement.context.TypeContext;
import intellispaces.common.javastatement.session.Session;
import tech.intellispaces.action.cache.CachedSupplierActions;
import tech.intellispaces.action.supplier.SupplierAction;

import java.util.Map;
import java.util.Optional;

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
}
