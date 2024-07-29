package tech.intellispaces.javastatements.type;

import tech.intellispaces.javastatements.statement.reference.TypeReference;

import java.util.List;

class TypeImpl<T> implements Type<T> {
  private final TypeReference baseType;
  private final List<TypeReference> qualifiers;

  @SuppressWarnings("unchecked")
  TypeImpl(TypeReference baseType, List<? extends TypeReference> qualifiers) {
    this.baseType = baseType;
    this.qualifiers = (List<TypeReference>) qualifiers;
  }

  @Override
  public TypeReference baseType() {
    return baseType;
  }

  @Override
  public List<TypeReference> qualifiers() {
    return qualifiers;
  }
}
