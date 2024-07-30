package tech.intellispaces.javastatements.reference;

import tech.intellispaces.javastatements.StatementType;
import tech.intellispaces.javastatements.StatementTypes;

import java.util.Map;
import java.util.Optional;

class WildcardReferenceImpl extends AbstractTypeReference implements WildcardReference {
  private final TypeReferenceBound extendedBound;
  private final TypeReferenceBound superBound;

  WildcardReferenceImpl(TypeReferenceBound extendedBound, TypeReferenceBound superBound) {
    super();
    this.extendedBound = extendedBound;
    this.superBound = superBound;
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.WildcardReference;
  }

  @Override
  public Optional<TypeReferenceBound> extendedBound() {
    return Optional.ofNullable(extendedBound);
  }

  @Override
  public Optional<TypeReferenceBound> superBound() {
    return Optional.ofNullable(superBound);
  }

  @Override
  public TypeReference specify(Map<String, NotPrimitiveTypeReference> typeMapping) {
    TypeReferenceBound extendedBound = extendedBound().orElse(null);
    if (extendedBound != null) {
      extendedBound = (TypeReferenceBound) extendedBound.specify(typeMapping);
    }
    TypeReferenceBound superBound = superBound().orElse(null);
    if (superBound != null) {
      superBound = (TypeReferenceBound) superBound.specify(typeMapping);
    }
    return new WildcardReferenceImpl(extendedBound, superBound);
  }
}
