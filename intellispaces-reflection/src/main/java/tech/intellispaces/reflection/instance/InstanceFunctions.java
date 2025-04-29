package tech.intellispaces.reflection.instance;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.util.List;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;

import tech.intellispaces.reflection.StatementTypes;
import tech.intellispaces.reflection.common.JavaModelFunctions;
import tech.intellispaces.reflection.customtype.AnnotationFunctions;
import tech.intellispaces.reflection.customtype.Classes;
import tech.intellispaces.reflection.customtype.EnumType;
import tech.intellispaces.reflection.customtype.Enums;
import tech.intellispaces.reflection.exception.JavaStatementExceptions;
import tech.intellispaces.reflection.reference.CustomTypeReference;
import tech.intellispaces.reflection.reference.CustomTypeReferences;
import tech.intellispaces.reflection.reference.PrimitiveReference;
import tech.intellispaces.reflection.reference.PrimitiveReferences;
import tech.intellispaces.reflection.reference.TypeReference;
import tech.intellispaces.reflection.reference.TypeReferenceFunctions;
import tech.intellispaces.reflection.session.Session;

public interface InstanceFunctions {

  static Object instanceToObject(Instance instance) {
    if (instance == null) {
      return null;
    } else if (StatementTypes.PrimitiveInstance.equals(instance.statementType())) {
      return instance.asPrimitive().orElseThrow().value();
    } else if (StatementTypes.StringInstance.equals(instance.statementType())) {
      return instance.asString().orElseThrow().value();
    } else if (StatementTypes.EnumInstance.equals(instance.statementType())) {
      return asEnum(instance.asEnum().orElseThrow());
    } else if (StatementTypes.ClassInstance.equals(instance.statementType())) {
      return asClass(instance.asClass().orElseThrow());
    } else if (StatementTypes.ArrayInstance.equals(instance.statementType())) {
      return asArray(instance.asArray().orElseThrow());
    } else if (StatementTypes.AnnotationInstance.equals(instance.statementType())) {
      return asAnnotation((AnnotationInstance) instance);
    } else {
      throw JavaStatementExceptions.withMessage("Unsupported instance type {0}", instance.getClass().getName());
    }
  }

  @SuppressWarnings("unchecked")
  static Instance objectToInstance(Object value, Session session) {
    final Instance instance;
    if (value instanceof Boolean) {
      instance = PrimitiveInstances.of(value, PrimitiveReferences.Boolean);
    } else if (value instanceof Character) {
      instance = PrimitiveInstances.of(value, PrimitiveReferences.Char);
    } else if (value instanceof Byte) {
      instance = PrimitiveInstances.of(value, PrimitiveReferences.Byte);
    } else if (value instanceof Short) {
      instance = PrimitiveInstances.of(value, PrimitiveReferences.Short);
    } else if (value instanceof Integer) {
      instance = PrimitiveInstances.of(value, PrimitiveReferences.Int);
    } else if (value instanceof Long) {
      instance = PrimitiveInstances.of(value, PrimitiveReferences.Long);
    } else if (value instanceof Float) {
      instance = PrimitiveInstances.of(value, PrimitiveReferences.Float);
    } else if (value instanceof Double) {
      instance = PrimitiveInstances.of(value, PrimitiveReferences.Double);
    } else if (value instanceof String) {
      instance = StringInstances.of((String) value);
    } else if (value instanceof Class<?>) {
      instance = ClassInstances.of((Class<?>) value);
    } else if (value instanceof VariableElement variableElement) {
      // Enum value
      TypeReference typeReference = JavaModelFunctions.getTypeReference(variableElement.asType(), session);
      EnumType enumType = typeReference.asCustomTypeReference().orElseThrow().targetType().asEnum().orElseThrow();
      return EnumInstances.of(enumType, variableElement.getSimpleName().toString());
    } else if (value instanceof DeclaredType declaredType) {
      // Class value
      CustomTypeReference typeReference = JavaModelFunctions.getTypeReference(declaredType, session);
      return ClassInstances.of(typeReference.targetType());
    } else if (value instanceof AnnotationMirror annotationMirror) {
      // Annotation value
      return getAnnotationInstance(annotationMirror, session);
    } else if (value instanceof List) {
      // Array
      List<Instance> values = ((List<AnnotationValue>) value).stream()
          .map(AnnotationValue::getValue)
          .map(v -> InstanceFunctions.objectToInstance(v, session))
          .toList();
      instance = ArrayInstances.of(arrayItemsType(values), values);
    } else if (value.getClass().isEnum()) {
      // Enum
      return EnumInstances.of(Enums.of(value.getClass()), ((Enum<?>) value).name() );
    } else {
      throw JavaStatementExceptions.withMessage("Unsupported instance class {0}", value.getClass().getCanonicalName());
    }
    return instance;
  }

  private static TypeReference arrayItemsType(List<Instance> values) {
    if (values.isEmpty()) {
      return CustomTypeReferences.get(Classes.of(Object.class));
    }

    Instance value = values.get(0);
    if (StatementTypes.PrimitiveInstance.equals(value.statementType())) {
      PrimitiveReference primitiveTypeReference = value.asPrimitive().orElseThrow().type();
      final Class<?> wrapperClass = primitiveTypeReference.primitiveType().wrapperClass();
      return CustomTypeReferences.get(Classes.of(wrapperClass));
    } else if (StatementTypes.StringInstance.equals(value.statementType())) {
      return CustomTypeReferences.get(Classes.of(String.class));
    } else if (StatementTypes.ClassInstance.equals(value.statementType())) {
      return CustomTypeReferences.get(Classes.of(Class.class));
    } else if (StatementTypes.EnumInstance.equals(value.statementType())) {
      return CustomTypeReferences.get(value.asEnum().orElseThrow().type());
    } else if (StatementTypes.AnnotationInstance.equals(value.statementType())) {
      return CustomTypeReferences.get(value.asAnnotation().orElseThrow().annotationStatement());
    } else {
      throw JavaStatementExceptions.withMessage("Unsupported array element type in annotation element: " + value.statementType().typename());
    }
  }

  @SuppressWarnings("unchecked, rawtypes")
  private static Enum asEnum(EnumInstance instance) {
    try {
      return Enum.valueOf(
          (Class<? extends Enum>) Class.forName(instance.type().canonicalName()),
          instance.name()
      );
    } catch (ClassNotFoundException e) {
      throw JavaStatementExceptions.withCauseAndMessage(e, "Class by name {0} is not found", instance.type().canonicalName());
    }
  }

  private static Class<?> asClass(ClassInstance instance) {
    try {
      return Class.forName(instance.type().canonicalName());
    } catch (ClassNotFoundException e) {
      throw JavaStatementExceptions.withCauseAndMessage(e, "Class by name {0} is not found", instance.type().canonicalName());
    }
  }

  private static Object[] asArray(ArrayInstance instance) {
    Class<?> elementClass = TypeReferenceFunctions.getClass(instance.elementType());
    return instance.elements().stream()
        .map(InstanceFunctions::instanceToObject)
        .toArray(length -> (Object[]) Array.newInstance(Object.class, length)
    );
  }

  @SuppressWarnings("unchecked")
  private static Annotation asAnnotation(AnnotationInstance instance) {
    String annotationClassName = instance.annotationStatement().canonicalName();
    Class<?> annotationClass = TypeReferenceFunctions.getClass(annotationClassName);
    if (!Annotation.class.isAssignableFrom(annotationClass)) {
      throw JavaStatementExceptions.withMessage("Class {0} does not extend {1}", annotationClassName, Annotation.class.getName());
    }
    return AnnotationFunctions.asAnnotation(instance, (Class<Annotation>) annotationClass);
  }

  static AnnotationInstance getAnnotationInstance(AnnotationMirror annotationMirror, Session session) {
    return new AnnotationInstanceBasedOnAnnotationMirror(annotationMirror, session);
  }
}
