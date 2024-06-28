package tech.intellispaces.framework.javastatements.statement.instance;

import tech.intellispaces.framework.javastatements.exception.JavaStatementException;
import tech.intellispaces.framework.javastatements.session.Session;
import tech.intellispaces.framework.javastatements.statement.StatementTypes;
import tech.intellispaces.framework.javastatements.statement.TypeElementFunctions;
import tech.intellispaces.framework.javastatements.statement.custom.AnnotationFunctions;
import tech.intellispaces.framework.javastatements.statement.custom.ClassStatementBuilder;
import tech.intellispaces.framework.javastatements.statement.custom.EnumStatement;
import tech.intellispaces.framework.javastatements.statement.reference.CustomTypeReference;
import tech.intellispaces.framework.javastatements.statement.reference.CustomTypeReferenceBuilder;
import tech.intellispaces.framework.javastatements.statement.reference.PrimitiveTypeReference;
import tech.intellispaces.framework.javastatements.statement.reference.PrimitiveTypeReferences;
import tech.intellispaces.framework.javastatements.statement.reference.TypeReference;

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
      instance = PrimitiveInstanceBuilder.build(value, PrimitiveTypeReferences.Boolean);
    } else if (value instanceof Character) {
      instance = PrimitiveInstanceBuilder.build(value, PrimitiveTypeReferences.Char);
    } else if (value instanceof Byte) {
      instance = PrimitiveInstanceBuilder.build(value, PrimitiveTypeReferences.Byte);
    } else if (value instanceof Short) {
      instance = PrimitiveInstanceBuilder.build(value, PrimitiveTypeReferences.Short);
    } else if (value instanceof Integer) {
      instance = PrimitiveInstanceBuilder.build(value, PrimitiveTypeReferences.Integer);
    } else if (value instanceof Long) {
      instance = PrimitiveInstanceBuilder.build(value, PrimitiveTypeReferences.Long);
    } else if (value instanceof Float) {
      instance = PrimitiveInstanceBuilder.build(value, PrimitiveTypeReferences.Float);
    } else if (value instanceof Double) {
      instance = PrimitiveInstanceBuilder.build(value, PrimitiveTypeReferences.Double);
    } else if (value instanceof String) {
      instance = StringInstanceBuilder.build((String) value);
    } else if (value instanceof VariableElement) {
      // Enum value
      VariableElement variableElement = (VariableElement) value;
      TypeReference typeReference = TypeElementFunctions.getTypeReference(variableElement.asType(), session);
      EnumStatement enumStatement = typeReference.asCustomTypeReference().orElseThrow().targetType().asEnum().orElseThrow();
      return EnumInstanceBuilder.build(enumStatement, variableElement.getSimpleName().toString());
    } else if (value instanceof DeclaredType) {
      // Class value
      DeclaredType declaredType = (DeclaredType) value;
      CustomTypeReference typeReference = TypeElementFunctions.getTypeReference(declaredType, session);
      return ClassInstanceBuilder.build(typeReference.targetType());
    } else if (value instanceof AnnotationMirror) {
      // Annotation value
      AnnotationMirror annotationMirror = (AnnotationMirror) value;
      return AnnotationInstanceBuilder.build(annotationMirror, session);
    } else if (value instanceof List) {
      // Array
      List<Instance> values = ((List<AnnotationValue>) value).stream()
          .map(AnnotationValue::getValue)
          .map(v -> InstanceFunctions.objectToInstance(v, session))
          .toList();
      instance = ArrayInstanceBuilder.build(arrayItemsType(values), values);
    } else {
      throw JavaStatementException.withMessage("Unsupported object class {}", value.getClass().getCanonicalName());
    }
    return instance;
  }

  private static TypeReference arrayItemsType(List<Instance> values) {
    if (values.isEmpty()) {
      return CustomTypeReferenceBuilder.build(ClassStatementBuilder.build(Object.class));
    }

    Instance value = values.get(0);
    if (StatementTypes.PrimitiveInstance.equals(value.statementType())) {
      PrimitiveTypeReference primitiveType = value.asPrimitive().orElseThrow().type();
      final Class<?> wrapperClass = primitiveType.wrapperClass();
      return CustomTypeReferenceBuilder.build(ClassStatementBuilder.build(wrapperClass));
    } else if (StatementTypes.StringInstance.equals(value.statementType())) {
      return CustomTypeReferenceBuilder.build(ClassStatementBuilder.build(String.class));
    } else if (StatementTypes.ClassInstance.equals(value.statementType())) {
      return CustomTypeReferenceBuilder.build(ClassStatementBuilder.build(Class.class));
    } else if (StatementTypes.EnumInstance.equals(value.statementType())) {
      return CustomTypeReferenceBuilder.build(value.asEnum().orElseThrow().type());
    } else if (StatementTypes.AnnotationInstance.equals(value.statementType())) {
      return CustomTypeReferenceBuilder.build(value.asAnnotation().orElseThrow().annotationStatement());
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
}
