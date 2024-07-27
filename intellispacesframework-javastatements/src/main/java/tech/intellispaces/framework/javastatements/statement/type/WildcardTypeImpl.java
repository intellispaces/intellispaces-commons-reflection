package tech.intellispaces.framework.javastatements.statement.type;

import tech.intellispaces.framework.javastatements.statement.StatementType;
import tech.intellispaces.framework.javastatements.statement.StatementTypes;

import java.util.Map;
import java.util.Optional;

class WildcardTypeImpl extends AbstractType implements WildcardType {
  private final TypeBound extendedBound;
  private final TypeBound superBound;

  WildcardTypeImpl(TypeBound extendedBound, TypeBound superBound) {
    super();
    this.extendedBound = extendedBound;
    this.superBound = superBound;
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.WildcardType;
  }

  @Override
  public Optional<TypeBound> extendedBound() {
    return Optional.ofNullable(extendedBound);
  }

  @Override
  public Optional<TypeBound> superBound() {
    return Optional.ofNullable(superBound);
  }

  @Override
  public Type specify(Map<String, NotPrimitiveType> typeMapping) {
    TypeBound extendedBound = extendedBound().orElse(null);
    if (extendedBound != null) {
      extendedBound = (TypeBound) extendedBound.specify(typeMapping);
    }
    TypeBound superBound = superBound().orElse(null);
    if (superBound != null) {
      superBound = (TypeBound) superBound.specify(typeMapping);
    }
    return new WildcardTypeImpl(extendedBound, superBound);
  }
}
