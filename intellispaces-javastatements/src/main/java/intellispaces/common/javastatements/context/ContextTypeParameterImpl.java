package intellispaces.common.javastatements.context;

import intellispaces.common.javastatements.reference.NotPrimitiveReference;
import intellispaces.common.javastatements.reference.NamedReference;

class ContextTypeParameterImpl implements ContextTypeParameter {
  private final NamedReference namedType;
  private final NotPrimitiveReference actualType;

  ContextTypeParameterImpl(NamedReference namedType, NotPrimitiveReference actualType) {
    this.namedType = namedType;
    this.actualType = actualType;
  }

  @Override
  public NamedReference namedType() {
    return namedType;
  }

  @Override
  public NotPrimitiveReference actualType() {
    return actualType;
  }
}
