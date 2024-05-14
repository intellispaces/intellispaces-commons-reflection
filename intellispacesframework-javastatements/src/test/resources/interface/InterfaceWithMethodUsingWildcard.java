package tech.intellispacesframework.javastatements.samples;

import tech.intellispacesframework.javastatements.support.TesteeType;

import java.util.Collection;
import java.util.List;

@TesteeType
public interface InterfaceWithMethodUsingWildcard {

  List<?> methodUsingWildcard(Collection<?> arg);
}