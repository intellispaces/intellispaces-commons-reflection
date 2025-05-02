package tech.intellispaces.statementsj.method;

public interface MethodSignatureDeclarations {

  static MethodSignatureDeclarationBuilderBasedOnMethodPrototype build(MethodStatement method) {
    return new MethodSignatureDeclarationBuilderBasedOnMethodPrototype(method);
  }
}
