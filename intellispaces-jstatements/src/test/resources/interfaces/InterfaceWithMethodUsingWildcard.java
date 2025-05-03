package tech.intellispaces.jstatements.samples;

import java.util.Collection;
import java.util.List;

import tech.intellispaces.jstatements.support.TesteeType;

@TesteeType
public interface InterfaceWithMethodUsingWildcard {

  List<?> methodUsingWildcard(Collection<?> arg);
}