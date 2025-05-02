package tech.intellispaces.statementsj.samples;

import java.util.List;

import tech.intellispaces.statementsj.support.TesteeType;

@TesteeType
public interface InterfaceWithMethodUsingLocalTypeParameter {

  <T> List<T> methodUsingLocalTypeParameter(T arg);
}