package intellispaces.javastatements.function;

import intellispaces.javastatements.model.context.NameContext;
import intellispaces.javastatements.model.custom.MethodParam;
import intellispaces.javastatements.model.instance.Instance;
import intellispaces.javastatements.model.reference.ExceptionCompatibleTypeReference;
import intellispaces.javastatements.model.reference.TypeReference;
import intellispaces.javastatements.model.session.Session;
import intellispaces.javastatements.object.custom.MethodParamBuilder;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface MethodFunctions {

  static Optional<TypeReference> getMethodReturnType(ExecutableElement executableElement, NameContext nameContext, Session session) {
    TypeMirror returnTypeMirror = executableElement.getReturnType();
    if (returnTypeMirror.getKind() == TypeKind.VOID || returnTypeMirror.getKind() == TypeKind.NONE
        || returnTypeMirror.getKind() == TypeKind.NULL
    ) {
      return Optional.empty();
    }
    return Optional.of(TypeFunctions.getTypeReference(returnTypeMirror, nameContext, session));
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

  static List<MethodParam> getMethodParams(ExecutableElement executableElement, NameContext nameContext, Session session) {
    return executableElement.getParameters().stream()
        .map(variableElement -> MethodParamBuilder.build(variableElement, nameContext, session))
        .toList();
  }

  static List<ExceptionCompatibleTypeReference> getMethodExceptions(
      ExecutableElement executableElement, NameContext nameContext, Session session
  ) {
    return executableElement.getThrownTypes().stream()
        .map(type -> (ExceptionCompatibleTypeReference) TypeFunctions.getTypeReference(type, nameContext, session))
        .collect(Collectors.toList());
  }
}
