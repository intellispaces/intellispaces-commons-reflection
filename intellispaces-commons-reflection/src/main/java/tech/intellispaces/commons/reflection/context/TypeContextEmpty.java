package tech.intellispaces.commons.reflection.context;

import java.util.Optional;

class TypeContextEmpty implements TypeContext {

  TypeContextEmpty() {}

  @Override
  public boolean contains(String typeParamName) {
    return false;
  }

  @Override
  public Optional<ContextTypeParameter> get(String typeParamName) {
    return Optional.empty();
  }
}
