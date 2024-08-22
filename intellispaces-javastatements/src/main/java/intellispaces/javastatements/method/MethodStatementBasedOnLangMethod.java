package intellispaces.javastatements.method;

import intellispaces.actions.Actions;
import intellispaces.actions.getter.Getter;
import intellispaces.javastatements.StatementType;
import intellispaces.javastatements.StatementTypes;
import intellispaces.javastatements.customtype.CustomType;
import intellispaces.javastatements.reference.NotPrimitiveReference;

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
