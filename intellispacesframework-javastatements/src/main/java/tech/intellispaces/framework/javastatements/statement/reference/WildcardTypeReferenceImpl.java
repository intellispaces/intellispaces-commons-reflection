package tech.intellispaces.framework.javastatements.statement.reference;

import tech.intellispaces.framework.javastatements.statement.StatementType;
import tech.intellispaces.framework.javastatements.statement.StatementTypes;

import java.util.Map;
import java.util.Optional;

class WildcardTypeReferenceImpl extends AbstractTypeReference implements WildcardTypeReference {
  private final TypeBoundReference extendedBound;
  private final TypeBoundReference superBound;

  WildcardTypeReferenceImpl(TypeBoundReference extendedBound, TypeBoundReference superBound) {
    super();
    this.extendedBound = extendedBound;
    this.superBound = superBound;
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.WildcardTypeReference;
  }

  @Override
  public Optional<TypeBoundReference> extendedBound() {
    return Optional.ofNullable(extendedBound);
  }

  @Override
  public Optional<TypeBoundReference> superBound() {
    return Optional.ofNullable(superBound);
  }

  @Override
  public TypeReference specify(Map<String, NonPrimitiveTypeReference> typeMapping) {
    TypeBoundReference extendedBound = extendedBound().orElse(null);
    if (extendedBound != null) {
      extendedBound = (TypeBoundReference) extendedBound.specify(typeMapping);
    }
    TypeBoundReference superBound = superBound().orElse(null);
    if (superBound != null) {
      superBound = (TypeBoundReference) superBound.specify(typeMapping);
    }
    return new WildcardTypeReferenceImpl(extendedBound, superBound);
  }
}
