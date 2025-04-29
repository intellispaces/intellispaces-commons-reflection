package tech.intellispaces.reflection.samples;

import java.util.List;

import tech.intellispaces.reflection.support.TesteeType;

@TesteeType
public interface InterfaceWithMethodUsingLocalTypeParameter {

  <T> List<T> methodUsingLocalTypeParameter(T arg);
}