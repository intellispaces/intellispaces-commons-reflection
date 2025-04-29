package tech.intellispaces.reflection.reference;

import java.util.Map;
import java.util.Optional;

import tech.intellispaces.reflection.StatementType;
import tech.intellispaces.reflection.StatementTypes;

class WildcardReferenceImpl extends AbstractTypeReference implements WildcardReference {
  private final ReferenceBound extendedBound;
  private final ReferenceBound superBound;

  WildcardReferenceImpl(ReferenceBound extendedBound, ReferenceBound superBound) {
    super();
    this.extendedBound = extendedBound;
    this.superBound = superBound;
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.WildcardReference;
  }

  @Override
  public Optional<ReferenceBound> extendedBound() {
    return Optional.ofNullable(extendedBound);
  }

  @Override
  public Optional<ReferenceBound> superBound() {
    return Optional.ofNullable(superBound);
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
