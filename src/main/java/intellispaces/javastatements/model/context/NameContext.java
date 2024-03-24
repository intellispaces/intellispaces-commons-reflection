package intellispaces.javastatements.model.context;

import java.util.Optional;

public interface NameContext {

  boolean contains(String name);

  Optional<ContextTypeParameter> get(String name);
}
