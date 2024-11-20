package intellispaces.common.javastatement.method;

import intellispaces.common.javastatement.StatementType;
import intellispaces.common.javastatement.StatementTypes;
import intellispaces.common.javastatement.customtype.CustomType;
import intellispaces.common.javastatement.reference.NotPrimitiveReference;
import tech.intellispaces.action.cache.CachedSupplierActions;
import tech.intellispaces.action.supplier.SupplierAction;
import tech.intellispaces.entity.exception.NotImplementedExceptions;

import java.util.List;
import java.util.Map;

class MethodStatementImpl implements MethodStatement {
  private final CustomType owner;
  private final MethodSignature signature;
  private final SupplierAction<List<MethodStatement>> overrideMethodsGetter;

  MethodStatementImpl(CustomType owner, MethodSignature signature) {
    this.owner = owner;
    this.signature = signature;
    this.overrideMethodsGetter = CachedSupplierActions.get(MethodFunctions::getOverrideMethods, this);
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
  public MethodStatement effective(Map<String, NotPrimitiveReference> typeMapping) {
    return new MethodStatementImpl(owner(), signature().effective(typeMapping));
  }

  @Override
  public String prettyDeclaration() {
    throw NotImplementedExceptions.withCode("nyHkqQ");
  }
}
