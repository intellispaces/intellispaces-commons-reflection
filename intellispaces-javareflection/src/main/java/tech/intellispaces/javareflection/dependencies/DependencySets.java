package tech.intellispaces.javareflection.dependencies;

import java.util.function.Supplier;

public interface DependencySets {

  static MutableDependencySet get(String currentClassName) {
    return new MutableDependencySetImpl(currentClassName);
  }

  static MutableDependencySet get(Supplier<String> currentClassName) {
    return new MutableDependencySetImpl(currentClassName);
  }
}
