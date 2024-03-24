package intellispaces.javastatements.object.instance;

import intellispaces.commons.function.ActionFunctions;
import intellispaces.commons.model.action.GetterAction;
import intellispaces.javastatements.function.InstanceFunctions;
import intellispaces.javastatements.model.instance.AnnotationElement;
import intellispaces.javastatements.model.instance.Instance;
import intellispaces.javastatements.model.session.Session;

class AnnotationElementAdapter implements AnnotationElement {
  private final String name;
  private final GetterAction<Instance> valueGetter;

  AnnotationElementAdapter(String name, Object value, Session session) {
    this.name = name;
    this.valueGetter = ActionFunctions.cachedLazyGetter(InstanceFunctions::objectToInstance, value, session);
  }

  @Override
  public String name() {
    return name;
  }

  @Override
  public Instance value() {
    return valueGetter.execute();
  }
}
