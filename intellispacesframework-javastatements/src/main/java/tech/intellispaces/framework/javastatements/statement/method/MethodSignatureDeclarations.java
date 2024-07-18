package tech.intellispaces.framework.javastatements.statement.method;

public interface MethodSignatureDeclarations {

  static MethodSignatureDeclarationBuilderBasedOnMethodPrototype build(MethodStatement method) {
    return new MethodSignatureDeclarationBuilderBasedOnMethodPrototype(method);
  }
}
