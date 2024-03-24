package intellispaces.javastatements.object.context;

import intellispaces.javastatements.model.context.ContextTypeParameter;
import intellispaces.javastatements.model.context.NameContext;

import java.util.Optional;

class EmptyNameContext implements NameContext {

  @Override
  public boolean contains(String name) {
    return false;
  }

  @Override
  public Optional<ContextTypeParameter> get(String name) {
    return Optional.empty();
  }
}
