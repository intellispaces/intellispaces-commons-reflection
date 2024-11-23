package tech.intellispaces.java.reflection.samples;

import tech.intellispaces.java.reflection.support.TesteeType;

import java.util.List;

@TesteeType
public interface InterfaceWithMethodUsingLocalTypeParameter {

  <T> List<T> methodUsingLocalTypeParameter(T arg);
}