package intellispaces.common.javastatement.method;

import intellispaces.common.action.Actions;
import intellispaces.common.action.getter.Getter;
import intellispaces.common.javastatement.StatementType;
import intellispaces.common.javastatement.StatementTypes;
import intellispaces.common.javastatement.customtype.CustomType;
import intellispaces.common.javastatement.reference.NotPrimitiveReference;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * Adapter of {@link Method} to {@link MethodStatement}.
 */
class MethodStatementBasedOnLangMethod implements MethodStatement {
  private final Getter<MethodSignature> signatureGetter;
  private final Getter<List<MethodStatement>> overrideMethodsGetter;

  MethodStatementBasedOnLangMethod(Method method) {
    this.signatureGetter = Actions.cachedLazyGetter(MethodSignatures::get, method);
    this.overrideMethodsGetter = Actions.cachedLazyGetter(MethodFunctions::getOverrideMethods, this);
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.Method;
  }

  @Override
  public CustomType owner() {
    throw new RuntimeException("Not implemented");
  }

  @Override
  public MethodSignature signature() {
    return signatureGetter.get();
  }

  @Override
  public List<MethodStatement> overrideMethods() {
    return overrideMethodsGetter.get();
  }

  @Override
  public MethodStatement specify(Map<String, NotPrimitiveReference> typeMapping) {
    return new MethodStatementImpl(owner(), signature().specify(typeMapping));
  }
}
