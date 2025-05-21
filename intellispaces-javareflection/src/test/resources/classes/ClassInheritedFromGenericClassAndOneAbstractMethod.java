package tech.intellispaces.javareflection.samples;

import tech.intellispaces.javareflection.support.TesteeType;

public interface ClassInheritedFromGenericClassAndOneAbstractMethod {

  @TesteeType
  class ChildClass extends ParentClass<String> {
    @Override
    public int genericMethod(String param) {
      return 0;
    }
  }

  abstract class ParentClass<T> {
    public abstract int genericMethod(T param);
  }
}
