package intellispaces.javastatements.object.custom;

import intellispaces.javastatements.model.custom.CustomType;
import intellispaces.javastatements.model.custom.MethodSignature;
import intellispaces.javastatements.model.custom.MethodStatement;

class MethodStatementObject implements MethodStatement {
  private final CustomType holder;
  private final MethodSignature signature;

  MethodStatementObject(CustomType holder, MethodSignature signature) {
    this.holder = holder;
    this.signature = signature;
  }

  @Override
  public CustomType holder() {
    return holder;
  }

  @Override
  public MethodSignature signature() {
    return signature;
  }
}
