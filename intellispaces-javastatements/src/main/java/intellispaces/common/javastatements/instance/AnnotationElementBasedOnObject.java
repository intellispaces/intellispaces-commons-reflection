package intellispaces.common.javastatements.instance;

import intellispaces.common.action.Actions;
import intellispaces.common.action.getter.Getter;
import intellispaces.common.javastatements.session.Session;

/**
 * Adapter of {@link Object} to {@link AnnotationElement}.
 */
class AnnotationElementBasedOnObject implements AnnotationElement {
  private final String name;
  private final Getter<Instance> valueGetter;

  AnnotationElementBasedOnObject(String name, Object value, Session session) {
    this.name = name;
    this.valueGetter = Actions.cachedLazyGetter(InstanceFunctions::objectToInstance, value, session);
  }

  @Override
  public String name() {
    return name;
  }

  @Override
  public Instance value() {
    return valueGetter.get();
  }
}
