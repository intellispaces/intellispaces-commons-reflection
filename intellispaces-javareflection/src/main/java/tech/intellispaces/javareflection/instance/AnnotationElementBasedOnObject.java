package tech.intellispaces.javareflection.instance;

import tech.intellispaces.actions.cache.CachedSupplierActions;
import tech.intellispaces.actions.supplier.SupplierAction;
import tech.intellispaces.javareflection.session.Session;

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
