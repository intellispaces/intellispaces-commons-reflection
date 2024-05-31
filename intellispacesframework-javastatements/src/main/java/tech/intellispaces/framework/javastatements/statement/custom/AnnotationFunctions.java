package tech.intellispaces.framework.javastatements.statement.custom;

import tech.intellispaces.framework.javastatements.statement.instance.AnnotationElementBuilder;
import tech.intellispaces.framework.javastatements.statement.instance.AnnotationInstanceBuilder;
import tech.intellispaces.framework.javastatements.statement.instance.InstanceFunctions;
import tech.intellispaces.framework.javastatements.statement.method.MethodFunctions;
import tech.intellispaces.framework.javastatements.exception.JavaStatementException;
import tech.intellispaces.framework.javastatements.session.Session;
import tech.intellispaces.framework.javastatements.statement.AnnotatedStatement;
import tech.intellispaces.framework.javastatements.statement.instance.AnnotationElement;
import tech.intellispaces.framework.javastatements.statement.instance.AnnotationInstance;
import tech.intellispaces.framework.javastatements.statement.instance.Instance;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * Annotation related functions.
 */
public interface AnnotationFunctions {

  static List<AnnotationInstance> getAnnotations(List<? extends AnnotationMirror> annotationMirrors, Session session) {
    return annotationMirrors.stream()
        .map(mirror -> AnnotationInstanceBuilder.build(mirror, session))
        .toList();
  }

  static Optional<AnnotationInstance> selectAnnotation(AnnotatedStatement statement, String annotationClass) {
    for (AnnotationInstance annotation : statement.annotations()) {
      if (annotationClass.equals(annotation.annotationStatement().canonicalName())) {
        return Optional.of(annotation);
      }
    }
    return Optional.empty();
  }

  static <A extends Annotation> Optional<A> selectAnnotation(AnnotatedStatement statement, Class<A> annotationClass) {
    String annotationTypeName = annotationClass.getCanonicalName();
    for (AnnotationInstance annotation : statement.annotations()) {
      if (annotationTypeName.equals(annotation.annotationStatement().canonicalName())) {
        return Optional.of(asAnnotation(annotation, annotationClass));
      }
    }
    return Optional.empty();
  }

  static boolean hasAnnotation(AnnotatedStatement statement, Class<? extends Annotation> annotationClass) {
    return selectAnnotation(statement, annotationClass).isPresent();
  }

  @SuppressWarnings("unchecked")
  static <A extends Annotation> A asAnnotation(AnnotationInstance instance, Class<A> annotationClass) {
    if (!annotationClass.getCanonicalName().equals(instance.annotationStatement().canonicalName())) {
      throw JavaStatementException.withMessage("Illegal annotation class. Expected {}. Actual {}", annotationClass.getCanonicalName(),
          instance.annotationStatement().canonicalName());
    }
    return (A) Proxy.newProxyInstance(
        AnnotationFunctions.class.getClassLoader(),
        new Class[] { annotationClass },
        (proxy, method, methodArgs) -> {
          Optional<Instance> elementValue = instance.elementValue(method.getName());
          if (elementValue.isPresent()) {
            Object result = InstanceFunctions.instanceToObject(elementValue.get());
            if (method.getReturnType().isArray()) {
              return convertToArray((Object[]) result, method.getReturnType().componentType());
            }
            return result;
          } else {
            throw JavaStatementException.withMessage("Unsupported method {}", method.getName());
          }
        }
    );
  }

  static Collection<AnnotationElement> getAnnotationElements(AnnotationMirror annotationMirror, Session session) {
    Map<String, AnnotationElement> elements = new HashMap<>();;
    for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : annotationMirror.getElementValues().entrySet()) {
      String elementName = entry.getKey().getSimpleName().toString();
      Object elementValue = entry.getValue().getValue();
      elements.put(elementName, AnnotationElementBuilder.build(elementName, elementValue, session));
    }

    // Populate default values
    annotationMirror.getAnnotationType().asElement().getEnclosedElements().stream()
        .filter(element -> element.getKind() == ElementKind.METHOD)
        .map(element -> (ExecutableElement) element)
        .forEach(
            executableElement -> {
              String elementName = executableElement.getSimpleName().toString();
              Optional<Object> defaultValue = MethodFunctions.getMethodDefaultValue(executableElement);
              defaultValue.ifPresent(df -> elements.putIfAbsent(elementName, AnnotationElementBuilder.build(elementName, df, session)));
            }
        );
    return elements.values();
  }

  private static Object convertToArray(Object[] values, Class<?> elementClass) {
    if (elementClass == byte.class) {
      byte[] byteArray = new byte[values.length];
      IntStream.range(0, values.length).forEach(v -> byteArray[v] = (byte) values[v]);
      return byteArray;
    } else if (elementClass == short.class) {
      short[] shortArray = new short[values.length];
      IntStream.range(0, values.length).forEach(v -> shortArray[v] = (short) values[v]);
      return shortArray;
    } else if (elementClass == int.class) {
      int[] intArray = new int[values.length];
      IntStream.range(0, values.length).forEach(v -> intArray[v] = (int) values[v]);
      return intArray;
    } else if (elementClass == long.class) {
      long[] longArray = new long[values.length];
      IntStream.range(0, values.length).forEach(v -> longArray[v] = (long) values[v]);
      return longArray;
    } else if (elementClass == float.class) {
      float[] floatArray = new float[values.length];
      IntStream.range(0, values.length).forEach(v -> floatArray[v] = (float) values[v]);
      return floatArray;
    } else if (elementClass == double.class) {
      double[] doubleArray = new double[values.length];
      IntStream.range(0, values.length).forEach(v -> doubleArray[v] = (double) values[v]);
      return doubleArray;
    } else if (elementClass == char.class) {
      char[] charArray = new char[values.length];
      IntStream.range(0, values.length).forEach(v -> charArray[v] = (char) values[v]);
      return charArray;
    } else if (elementClass == boolean.class) {
      boolean[] booleanArray = new boolean[values.length];
      IntStream.range(0, values.length).forEach(v -> booleanArray[v] = (boolean) values[v]);
      return booleanArray;
    } else {
      Object[] array = (Object[]) Array.newInstance(elementClass, values.length);
      IntStream.range(0, values.length).forEach(v -> array[v] = elementClass.cast(values[v]));
      return array;
    }
  }
}
