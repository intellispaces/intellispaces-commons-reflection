package tech.intellispacesframework.javastatements.statement.instance;

import tech.intellispacesframework.commons.action.ActionBuilders;
import tech.intellispacesframework.commons.action.Getter;
import tech.intellispacesframework.javastatements.session.Session;

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
