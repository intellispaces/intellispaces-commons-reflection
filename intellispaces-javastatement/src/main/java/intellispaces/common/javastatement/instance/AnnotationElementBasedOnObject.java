package intellispaces.common.javastatement.instance;

import tech.intellispaces.action.Actions;
import tech.intellispaces.action.cache.CacheActions;
import tech.intellispaces.action.supplier.SupplierAction;
import intellispaces.common.javastatement.session.Session;

/**
 * Adapter of {@link Object} to {@link AnnotationElement}.
 */
class AnnotationElementBasedOnObject implements AnnotationElement {
  private final String name;
  private final SupplierAction<Instance> valueGetter;

  AnnotationElementBasedOnObject(String name, Object value, Session session) {
    this.name = name;
    this.valueGetter = CacheActions.cachedLazySupplierAction(InstanceFunctions::objectToInstance, value, session);
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
