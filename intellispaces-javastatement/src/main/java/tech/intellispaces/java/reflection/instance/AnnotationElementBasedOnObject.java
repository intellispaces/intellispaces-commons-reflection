package tech.intellispaces.java.reflection.instance;

import tech.intellispaces.java.reflection.session.Session;
import tech.intellispaces.action.cache.CachedSupplierActions;
import tech.intellispaces.action.supplier.SupplierAction;

/**
 * Adapter of {@link Object} to {@link AnnotationElement}.
 */
class AnnotationElementBasedOnObject implements AnnotationElement {
  private final String name;
  private final SupplierAction<Instance> valueGetter;

  AnnotationElementBasedOnObject(String name, Object value, Session session) {
    this.name = name;
    this.valueGetter = CachedSupplierActions.get(InstanceFunctions::objectToInstance, value, session);
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
