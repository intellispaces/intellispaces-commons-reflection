package tech.intellispaces.javastatements.samples;

import tech.intellispaces.javastatements.support.TesteeType;

import java.util.Collection;
import java.util.List;

@TesteeType
public interface InterfaceWithMethodUsingWildcard {

  List<?> methodUsingWildcard(Collection<?> arg);
}