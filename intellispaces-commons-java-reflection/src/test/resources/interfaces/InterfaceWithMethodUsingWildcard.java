package tech.intellispaces.commons.java.reflection.samples;

import tech.intellispaces.commons.java.reflection.support.TesteeType;

import java.util.Collection;
import java.util.List;

@TesteeType
public interface InterfaceWithMethodUsingWildcard {

  List<?> methodUsingWildcard(Collection<?> arg);
}