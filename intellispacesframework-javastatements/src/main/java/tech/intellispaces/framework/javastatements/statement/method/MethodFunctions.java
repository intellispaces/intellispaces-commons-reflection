package tech.intellispaces.framework.javastatements.statement.method;

import tech.intellispaces.framework.javastatements.JavaStatements;
import tech.intellispaces.framework.javastatements.context.TypeContext;
import tech.intellispaces.framework.javastatements.session.Session;
import tech.intellispaces.framework.javastatements.statement.common.TypeElementFunctions;
import tech.intellispaces.framework.javastatements.statement.custom.CustomType;
import tech.intellispaces.framework.javastatements.statement.instance.AnnotationInstance;
import tech.intellispaces.framework.javastatements.statement.instance.Instance;
import tech.intellispaces.framework.javastatements.statement.instance.InstanceFunctions;
import tech.intellispaces.framework.javastatements.statement.reference.CustomTypeReference;
import tech.intellispaces.framework.javastatements.statement.reference.ExceptionCompatibleTypeReference;
import tech.intellispaces.framework.javastatements.statement.reference.TypeReference;
import tech.intellispaces.framework.javastatements.statement.reference.TypeReferenceFunctions;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface MethodFunctions {

  static MethodStatement getMethod(ExecutableElement executableElement, Session session) {
    return new MethodStatementBasedOnExecutableElement(executableElement, session);
  }

  static MethodStatement getMethod(Method method) {
    return new MethodStatementBasedOnLangMethod(method);
  }

  static MethodSignature getMethodSignature(
      ExecutableElement executableElement, TypeContext typeContext, Session session
  ) {
    return new MethodSignatureBasedOnExecutableElement(executableElement, typeContext, session);
  }

  static MethodSignature getMethodSignature(Method method) {
    return new MethodSignatureBasedOnLangMethod(method);
  }

  static MethodParam getMethodParam(Parameter parameter) {
    List<AnnotationInstance> annotations = Arrays.stream(parameter.getAnnotations())
        .map(InstanceFunctions::getAnnotationInstance)
        .toList();
    return MethodParams.build()
        .name(parameter.getName())
        .type(JavaStatements.customTypeReference(parameter.getType()))
        .annotations(annotations)
        .get();
  }

  static Optional<TypeReference> getMethodReturnType(
      ExecutableElement executableElement, TypeContext typeContext, Session session
  ) {
    TypeMirror returnTypeMirror = executableElement.getReturnType();
    if (returnTypeMirror.getKind() == TypeKind.VOID || returnTypeMirror.getKind() == TypeKind.NONE
        || returnTypeMirror.getKind() == TypeKind.NULL
    ) {
      return Optional.empty();
    }
    return Optional.of(TypeElementFunctions.getTypeReference(returnTypeMirror, typeContext, session));
  }

  static Optional<Object> getMethodDefaultValue(
      ExecutableElement executableElement
  ) {
    if (executableElement.getDefaultValue() == null || executableElement.getDefaultValue().getValue() == null) {
      return Optional.empty();
    }
    return Optional.of(executableElement.getDefaultValue().getValue());
  }

  static Optional<Instance> getMethodDefaultValueInstance(
      ExecutableElement executableElement, Session session
  ) {
    return getMethodDefaultValue(executableElement).map(v -> InstanceFunctions.objectToInstance(v, session));
  }

  static List<MethodParam> getMethodParams(
      ExecutableElement executableElement, TypeContext typeContext, Session session
  ) {
    return executableElement.getParameters().stream()
        .map(variableElement -> MethodParams.of(variableElement, typeContext, session))
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
    return getOverrideMethods(method.owner(), method, false);
  }

  private static List<MethodStatement> getOverrideMethods(
      CustomType type, MethodStatement method, boolean included
  ) {
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

  static boolean isEquivalentMethods(Method method1, Method method2) {
    if (!method1.getName().equals(method2.getName())) {
      return false;
    }
    if (method1.getParameterCount() != method2.getParameterCount()) {
      return false;
    }
    for (int i = 0; i < method1.getParameterCount(); i++) {
      Class<?> typeParam1 = method1.getParameters()[i].getType();
      Class<?> typeParam2 = method2.getParameters()[i].getType();
      if (typeParam1 != typeParam2) {
        return false;
      }
    }
    return true;
  }
}
