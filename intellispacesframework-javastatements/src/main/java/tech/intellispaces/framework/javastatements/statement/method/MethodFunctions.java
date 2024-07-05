package tech.intellispaces.framework.javastatements.statement.method;

import tech.intellispaces.framework.javastatements.context.TypeContext;
import tech.intellispaces.framework.javastatements.session.Session;
import tech.intellispaces.framework.javastatements.statement.TypeElementFunctions;
import tech.intellispaces.framework.javastatements.statement.custom.CustomType;
import tech.intellispaces.framework.javastatements.statement.instance.Instance;
import tech.intellispaces.framework.javastatements.statement.instance.InstanceFunctions;
import tech.intellispaces.framework.javastatements.statement.reference.CustomTypeReference;
import tech.intellispaces.framework.javastatements.statement.reference.ExceptionCompatibleTypeReference;
import tech.intellispaces.framework.javastatements.statement.reference.TypeReference;
import tech.intellispaces.framework.javastatements.statement.reference.TypeReferenceFunctions;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;
import java.util.Iterator;
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

  static List<MethodStatement> getOverrideMethods(MethodStatement method) {
    return getOverrideMethods(method.holder(), method, false);
  }

  private static List<MethodStatement> getOverrideMethods(CustomType type, MethodStatement method, boolean included) {
    List<MethodStatement> overrideMethods = new ArrayList<>();
    if (included) {
      for (MethodStatement parentMethod : type.declaredMethods()) {
        if (isEquivalentMethods(method, parentMethod)) {
          overrideMethods.add(parentMethod);
        }
      }
    }
    for (CustomTypeReference parent : type.parentTypes()) {
      if (parent.isCustomTypeReference()) {
        overrideMethods.addAll(getOverrideMethods(parent.asCustomTypeReferenceSurely().targetType(), method, true));
      }
    }
    return overrideMethods;
  }

  static boolean isEquivalentMethods(MethodStatement method1, MethodStatement method2) {
    if (!method1.name().equals(method2.name())) {
      return false;
    }
    if (method1.params().size() != method2.params().size()) {
      return false;
    }
    Iterator<MethodParam> paramIterator1 = method1.params().iterator();
    Iterator<MethodParam> paramIterator2 = method2.params().iterator();
    while (paramIterator1.hasNext() && paramIterator2.hasNext()) {
      MethodParam param1 = paramIterator1.next();
      MethodParam param2 = paramIterator2.next();
      if (!TypeReferenceFunctions.isEquivalentTypes(param1.type(), param2.type())) {
        return false;
      }
    }
    return true;
  }
}
