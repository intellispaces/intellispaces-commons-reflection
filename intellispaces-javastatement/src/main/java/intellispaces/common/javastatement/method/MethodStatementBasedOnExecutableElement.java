package intellispaces.common.javastatement.method;

import tech.intellispaces.action.Actions;
import tech.intellispaces.action.cache.CacheActions;
import tech.intellispaces.action.supplier.SupplierAction;
import tech.intellispaces.entity.exception.NotImplementedExceptions;
import intellispaces.common.javastatement.JavaStatements;
import intellispaces.common.javastatement.StatementType;
import intellispaces.common.javastatement.StatementTypes;
import intellispaces.common.javastatement.context.TypeContext;
import intellispaces.common.javastatement.context.TypeContexts;
import intellispaces.common.javastatement.customtype.CustomType;
import intellispaces.common.javastatement.reference.NotPrimitiveReference;
import intellispaces.common.javastatement.session.Session;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.util.List;
import java.util.Map;

/**
 * Adapter of {@link ExecutableElement} to {@link MethodStatement}.
 */
class MethodStatementBasedOnExecutableElement implements MethodStatement {
  private final SupplierAction<CustomType> ownerGetter;
  private final SupplierAction<MethodSignature> signatureGetter;
  private final SupplierAction<List<MethodStatement>> overrideMethodsGetter;

  MethodStatementBasedOnExecutableElement(ExecutableElement executableElement, Session session) {
    this(
        executableElement,
        CacheActions.cachedLazySupplierAction(
            JavaStatements::customTypeStatement, (TypeElement) executableElement.getEnclosingElement()),
        TypeContexts.empty(),
        session
    );
  }

  MethodStatementBasedOnExecutableElement(
      ExecutableElement executableElement, CustomType owner, TypeContext typeContext, Session session
  ) {
    this(executableElement, Actions.supplierAction(owner), typeContext, session);
  }

  MethodStatementBasedOnExecutableElement(
      ExecutableElement executableElement, SupplierAction<CustomType> ownerGetter, TypeContext typeContext, Session session
  ) {
    this.ownerGetter = ownerGetter;
    this.signatureGetter = CacheActions.cachedLazySupplierAction(
        MethodSignatures::of, executableElement, typeContext, session);
    this.overrideMethodsGetter = CacheActions.cachedLazySupplierAction(MethodFunctions::getOverrideMethods, this);
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.Method;
  }

  @Override
  public CustomType owner() {
    return ownerGetter.get();
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
  public MethodStatement effective(Map<String, NotPrimitiveReference> typeMapping) {
    return new MethodStatementImpl(owner(), signature().effective(typeMapping));
  }

  @Override
  public String prettyDeclaration() {
    throw NotImplementedExceptions.withCode("QLOEfw");
  }
}
