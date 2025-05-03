package tech.intellispaces.jstatements.samples;

import java.util.List;

import tech.intellispaces.jstatements.support.TesteeType;

@TesteeType
public interface InterfaceWithMethodUsingLocalTypeParameter {

  <T> List<T> methodUsingLocalTypeParameter(T arg);
}