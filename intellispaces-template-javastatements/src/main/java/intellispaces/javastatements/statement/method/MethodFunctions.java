package intellispaces.javastatements.statement.method;

import intellispaces.javastatements.context.TypeContext;
import intellispaces.javastatements.statement.TypeElementFunctions;
import intellispaces.javastatements.statement.custom.MethodParam;
import intellispaces.javastatements.statement.instance.Instance;
import intellispaces.javastatements.statement.instance.InstanceFunctions;
import intellispaces.javastatements.statement.reference.ExceptionCompatibleTypeReference;
import intellispaces.javastatements.statement.reference.TypeReference;
import intellispaces.javastatements.session.Session;
import intellispaces.javastatements.statement.custom.MethodParamBuilder;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface MethodFunctions {

  static Optional<TypeReference> getMethodReturnType(ExecutableElement executableElement, TypeContext typeContext, Session session) {
    TypeMirror returnTypeMirror = executableElement.getReturnType();
    if (returnTypeMirror.getKind() == TypeKind.VOID || returnTypeMirror.getKind() == TypeKind.NONE
        || returnTypeMirror.getKind() == TypeKind.NULL
    ) {
      return Optional.empty();
    }
    return Optional.of(TypeElementFunctions.getTypeReference(returnTypeMirror, typeContext, session));
  }

  static Optional<Object> getMethodDefaultValue(ExecutableElement executableElement) {
    if (executableElement.getDefaultValue() == null || executableElement.getDefaultValue().getValue() == null) {
      return Optional.empty();
    }
    return Optional.of(executableElement.getDefaultValue().getValue());
  }

  static Optional<Instance> getMethodDefaultValueInstance(ExecutableElement executableElement, Session session) {
    return getMethodDefaultValue(executableElement).map(v -> InstanceFunctions.objectToInstance(v, session));
  }

  static List<MethodParam> getMethodParams(ExecutableElement executableElement, TypeContext typeContext, Session session) {
    return executableElement.getParameters().stream()
        .map(variableElement -> MethodParamBuilder.build(variableElement, typeContext, session))
        .toList();
  }

  static List<ExceptionCompatibleTypeReference> getMethodExceptions(
      ExecutableElement executableElement, TypeContext typeContext, Session session
  ) {
    return executableElement.getThrownTypes().stream()
        .map(type -> (ExceptionCompatibleTypeReference) TypeElementFunctions.getTypeReference(type, typeContext, session))
        .collect(Collectors.toList());
  }
}
