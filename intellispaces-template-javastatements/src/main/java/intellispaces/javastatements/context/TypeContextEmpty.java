package intellispaces.javastatements.context;

import java.util.Optional;

class TypeContextEmpty implements TypeContext {

  @Override
  public boolean contains(String typeParamName) {
    return false;
  }

  @Override
  public Optional<ContextTypeParameter> get(String typeParamName) {
    return Optional.empty();
  }
}
