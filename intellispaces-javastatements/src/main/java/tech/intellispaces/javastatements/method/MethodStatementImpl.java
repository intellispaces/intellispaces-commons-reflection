package tech.intellispaces.javastatements.method;

import tech.intellispaces.actions.Actions;
import tech.intellispaces.actions.Getter;
import tech.intellispaces.javastatements.StatementType;
import tech.intellispaces.javastatements.StatementTypes;
import tech.intellispaces.javastatements.reference.NotPrimitiveTypeReference;
import tech.intellispaces.javastatements.customtype.CustomType;

import java.util.List;
import java.util.Map;

class MethodStatementImpl implements MethodStatement {
  private final CustomType owner;
  private final MethodSignature signature;
  private final Getter<List<MethodStatement>> overrideMethodsGetter;

  MethodStatementImpl(CustomType owner, MethodSignature signature) {
    this.owner = owner;
    this.signature = signature;
    this.overrideMethodsGetter = Actions.cachedLazyGetter(MethodFunctions::getOverrideMethods, this);
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.Method;
  }

  @Override
  public CustomType owner() {
    return owner;
  }

  @Override
  public MethodSignature signature() {
    return signature;
  }

  @Override
  public List<MethodStatement> overrideMethods() {
    return overrideMethodsGetter.get();
  }

  @Override
  public MethodStatement specify(Map<String, NotPrimitiveTypeReference> typeMapping) {
    return new MethodStatementImpl(owner(), signature().specify(typeMapping));
  }
}
