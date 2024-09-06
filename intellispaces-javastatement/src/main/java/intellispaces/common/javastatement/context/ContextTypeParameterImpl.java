package intellispaces.common.javastatement.context;

import intellispaces.common.javastatement.reference.NotPrimitiveReference;
import intellispaces.common.javastatement.reference.NamedReference;

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
