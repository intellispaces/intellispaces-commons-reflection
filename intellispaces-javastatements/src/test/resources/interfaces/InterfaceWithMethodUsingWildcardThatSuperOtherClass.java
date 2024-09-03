package intellispaces.common.javastatements.samples;

import intellispaces.common.javastatements.support.TesteeType;

import java.util.Collection;

@TesteeType
public interface InterfaceWithMethodUsingWildcardThatSuperOtherClass {

  void methodUsingWildcardThatSuperOtherClass(Collection<? super Number[]> arg);
}