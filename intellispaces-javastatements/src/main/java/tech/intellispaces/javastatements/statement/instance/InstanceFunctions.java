package tech.intellispaces.javastatements.statement.instance;

import tech.intellispaces.javastatements.exception.JavaStatementException;
import tech.intellispaces.javastatements.session.Session;
import tech.intellispaces.javastatements.statement.StatementTypes;
import tech.intellispaces.javastatements.statement.common.TypeElementFunctions;
import tech.intellispaces.javastatements.statement.reference.CustomTypeReference;
import tech.intellispaces.javastatements.statement.reference.CustomTypeReferences;
import tech.intellispaces.javastatements.statement.reference.PrimitiveReference;
import tech.intellispaces.javastatements.statement.reference.PrimitiveReferences;
import tech.intellispaces.javastatements.statement.reference.TypeReference;
import tech.intellispaces.javastatements.statement.customtype.AnnotationFunctions;
import tech.intellispaces.javastatements.statement.customtype.ClassStatements;
import tech.intellispaces.javastatements.statement.customtype.EnumType;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.util.List;

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
      throw JavaStatementException.withMessage("Unsupported instance type {}", instance.getClass().getName());
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
      instance = PrimitiveInstances.of(value, PrimitiveReferences.Integer);
    } else if (value instanceof Long) {
      instance = PrimitiveInstances.of(value, PrimitiveReferences.Long);
    } else if (value instanceof Float) {
      instance = PrimitiveInstances.of(value, PrimitiveReferences.Float);
    } else if (value instanceof Double) {
      instance = PrimitiveInstances.of(value, PrimitiveReferences.Double);
    } else if (value instanceof String) {
      instance = StringInstances.of((String) value);
    } else if (value instanceof VariableElement variableElement) {
      // Enum value
      TypeReference typeReference = TypeElementFunctions.getTypeReference(variableElement.asType(), session);
      EnumType enumStatement = typeReference.asCustomType().orElseThrow().customType().asEnum().orElseThrow();
      return EnumInstances.of(enumStatement, variableElement.getSimpleName().toString());
    } else if (value instanceof DeclaredType declaredType) {
      // Class value
      CustomTypeReference typeReference = TypeElementFunctions.getTypeReference(declaredType, session);
      return ClassInstances.of(typeReference.customType());
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
    } else {
      throw JavaStatementException.withMessage("Unsupported object class {}", value.getClass().getCanonicalName());
    }
    return instance;
  }

  private static TypeReference arrayItemsType(List<Instance> values) {
    if (values.isEmpty()) {
      return CustomTypeReferences.of(ClassStatements.of(Object.class));
    }

    Instance value = values.get(0);
    if (StatementTypes.PrimitiveInstance.equals(value.statementType())) {
      PrimitiveReference primitiveType = value.asPrimitive().orElseThrow().type();
      final Class<?> wrapperClass = primitiveType.wrapperClass();
      return CustomTypeReferences.of(ClassStatements.of(wrapperClass));
    } else if (StatementTypes.StringInstance.equals(value.statementType())) {
      return CustomTypeReferences.of(ClassStatements.of(String.class));
    } else if (StatementTypes.ClassInstance.equals(value.statementType())) {
      return CustomTypeReferences.of(ClassStatements.of(Class.class));
    } else if (StatementTypes.EnumInstance.equals(value.statementType())) {
      return CustomTypeReferences.of(value.asEnum().orElseThrow().type());
    } else if (StatementTypes.AnnotationInstance.equals(value.statementType())) {
      return CustomTypeReferences.of(value.asAnnotation().orElseThrow().annotationStatement());
    } else {
      throw JavaStatementException.withMessage("Unsupported array element type in annotation element: " + value.statementType().typename());
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
      throw JavaStatementException.withCauseAndMessage(e, "Class by name {} is not found", instance.type().canonicalName());
    }
  }

  private static Class<?> asClass(ClassInstance instance) {
    try {
      return Class.forName(instance.type().canonicalName());
    } catch (ClassNotFoundException e) {
      throw JavaStatementException.withCauseAndMessage(e, "Class by name {} is not found", instance.type().canonicalName());
    }
  }

  private static Object[] asArray(ArrayInstance instance) {
    Class<?> elementClass = TypeElementFunctions.getClass(instance.elementType());
    return instance.elements().stream()
        .map(InstanceFunctions::instanceToObject)
        .toArray(length -> (Object[]) Array.newInstance(Object.class, length)
    );
  }

  @SuppressWarnings("unchecked")
  private static Annotation asAnnotation(AnnotationInstance instance) {
    String annotationClassName = instance.annotationStatement().canonicalName();
    Class<?> annotationClass = TypeElementFunctions.getClass(annotationClassName);
    if (!Annotation.class.isAssignableFrom(annotationClass)) {
      throw JavaStatementException.withMessage("Class {} does not extend {}", annotationClassName, Annotation.class.getName());
    }
    return AnnotationFunctions.asAnnotation(instance, (Class<Annotation>) annotationClass);
  }

  static AnnotationInstance getAnnotationInstance(AnnotationMirror annotationMirror, Session session) {
    return new AnnotationInstanceBasedOnAnnotationMirror(annotationMirror, session);
  }

  static AnnotationInstance getAnnotationInstance(Annotation annotation) {
    return new AnnotationInstanceBasedOnLangAnnotation(annotation);
  }
}
