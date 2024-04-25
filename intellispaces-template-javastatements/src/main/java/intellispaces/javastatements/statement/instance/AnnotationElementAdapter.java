package intellispaces.javastatements.statement.instance;

import intellispaces.commons.action.ActionBuilders;
import intellispaces.commons.action.Getter;
import intellispaces.javastatements.session.Session;

class AnnotationElementAdapter implements AnnotationElement {
  private final String name;
  private final Getter<Instance> valueGetter;

  AnnotationElementAdapter(String name, Object value, Session session) {
    this.name = name;
    this.valueGetter = ActionBuilders.cachedLazyGetter(InstanceFunctions::objectToInstance, value, session);
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
