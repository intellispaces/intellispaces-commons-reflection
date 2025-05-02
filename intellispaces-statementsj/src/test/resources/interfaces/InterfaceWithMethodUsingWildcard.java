package tech.intellispaces.statementsj.samples;

import java.util.Collection;
import java.util.List;

import tech.intellispaces.statementsj.support.TesteeType;

@TesteeType
public interface InterfaceWithMethodUsingWildcard {

  List<?> methodUsingWildcard(Collection<?> arg);
}