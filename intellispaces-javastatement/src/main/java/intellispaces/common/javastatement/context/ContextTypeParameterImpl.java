package intellispaces.common.javastatement.context;

import intellispaces.common.javastatement.reference.NamedReference;
import intellispaces.common.javastatement.reference.NotPrimitiveReference;

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
