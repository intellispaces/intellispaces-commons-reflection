package tech.intellispaces.framework.javastatements.statement.common;

import tech.intellispaces.framework.javastatements.exception.JavaStatementException;
import tech.intellispaces.framework.javastatements.statement.StatementTypes;
import tech.intellispaces.framework.javastatements.statement.custom.CustomStatement;
import tech.intellispaces.framework.javastatements.statement.instance.AnnotationInstance;
import tech.intellispaces.framework.javastatements.statement.instance.Instance;
import tech.intellispaces.framework.javastatements.statement.method.MethodParam;
import tech.intellispaces.framework.javastatements.statement.method.MethodStatement;
import tech.intellispaces.framework.javastatements.statement.type.ArrayType;
import tech.intellispaces.framework.javastatements.statement.type.CustomType;
import tech.intellispaces.framework.javastatements.statement.type.NamedType;
import tech.intellispaces.framework.javastatements.statement.type.Type;
import tech.intellispaces.framework.javastatements.statement.type.WildcardType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Functions to calculate statement dependencies.
 */
public interface DependenciesFunctions {

  static Collection<CustomStatement> getCustomTypeDependencies(CustomStatement type) {
    return getCustomTypeDependencies(type, true, Set.of()).stream()
        .filter(customType -> !customType.canonicalName().startsWith("java.lang."))
        .filter(customType -> !customType.canonicalName().equals(type.canonicalName()))
        .filter(customType -> !customType.packageName().equals(type.packageName()))
        .toList();
  }

  static Collection<CustomStatement> getTypeReferenceDependencies(Type type) {
    return getTypeReferenceDependencies(type, false, Set.of()).stream()
        .filter(customType -> !customType.canonicalName().startsWith("java.lang."))
        .toList();
  }

  private static List<CustomStatement> getCustomTypeDependencies(
      CustomStatement customStatement, boolean includeRelations, Set<String> exclusions
  ) {
    if (exclusions.contains(customStatement.canonicalName())) {
      return List.of();
    }
    Set<String> newExclusions = new HashSet<>(exclusions);
    newExclusions.add(customStatement.canonicalName());

    List<CustomStatement> customStatements = new ArrayList<>();
    customStatements.add(customStatement);
    if (includeRelations) {
      customStatement.annotations().stream()
          .map(ant -> getAnnotationDependencies(ant, exclusions))
          .forEach(customStatements::addAll);
      customStatement.parentTypes().stream()
          .map(ref -> getCustomTypeReferenceDependencies(ref, false, newExclusions))
          .forEach(customStatements::addAll);
      customStatement.typeParameters().stream()
          .map(ref -> getNamedTypeReferenceDependencies(ref, false, newExclusions))
          .forEach(customStatements::addAll);
      customStatement.declaredMethods().stream()
          .map(ref -> getMethodDependencies(ref, newExclusions))
          .forEach(customStatements::addAll);
    }
    return customStatements;
  }

  private static List<CustomStatement> getMethodDependencies(MethodStatement method, Set<String> exclusions) {
    List<CustomStatement> customStatements = new ArrayList<>();
    method.typeParameters().stream()
        .map(typeParam -> getNamedTypeReferenceDependencies(typeParam, false, exclusions))
        .forEach(customStatements::addAll);
    if (method.returnType().isPresent()) {
      customStatements.addAll(getTypeReferenceDependencies(method.returnType().orElseThrow(), false, exclusions));
    }
    method.params().stream()
        .map(MethodParam::type)
        .map(paramType -> getTypeReferenceDependencies(paramType, false, exclusions))
        .forEach(customStatements::addAll);
    method.exceptions().stream()
        .map(exceptionType -> getTypeReferenceDependencies(exceptionType, false, exclusions))
        .forEach(customStatements::addAll);
    return customStatements;
  }

  private static List<CustomStatement> getInstanceDependencies(Instance instance, Set<String> exclusions) {
    if (StatementTypes.ClassInstance == instance.statementType()) {
      CustomStatement classType = instance.asClass().orElseThrow().type();
      if (!exclusions.contains(classType.canonicalName())) {
        return List.of(classType);
      }
    } else if (StatementTypes.EnumInstance == instance.statementType()) {
      CustomStatement enumType = instance.asEnum().orElseThrow().type();
      if (!exclusions.contains(enumType.canonicalName())) {
        return List.of(enumType);
      }
    } else if (StatementTypes.ArrayInstance == instance.statementType()) {
      return getTypeReferenceDependencies(instance.asArray().orElseThrow().elementType(), false, exclusions);
    } else if (StatementTypes.AnnotationInstance == instance.statementType()) {
      return getAnnotationDependencies(instance.asAnnotation().orElseThrow(), exclusions);
    }
    return List.of();
  }

  private static List<CustomStatement> getAnnotationDependencies(AnnotationInstance annotation, Set<String> exclusions) {
    List<CustomStatement> customStatements = new ArrayList<>();
    customStatements.add(annotation.annotationStatement());

    annotation.elements().stream()
        .map(element -> DependenciesFunctions.getInstanceDependencies(element.value(), exclusions))
        .forEach(customStatements::addAll);
    return customStatements;
  }

  private static List<CustomStatement> getTypeReferenceDependencies(
      Type type, boolean includeRelations, Set<String> exclusions
  ) {
    if (type.isPrimitive()) {
      return List.of();
    } else {
      return switch ((StatementTypes) type.statementType()) {
        case CustomType -> getCustomTypeReferenceDependencies(
            type.asCustomType().orElseThrow(), includeRelations, exclusions);
        case NamedType -> getNamedTypeReferenceDependencies(
            type.asNamedType().orElseThrow(), includeRelations, exclusions);
        case WildcardType -> getWildcardTypeReferenceDependencies(
            type.asWildcardType().orElseThrow(), includeRelations, exclusions);
        case ArrayType -> getArrayTypeReferenceDependencies(
            type.asArrayType().orElseThrow(), includeRelations, exclusions);
        default -> throw JavaStatementException.withMessage("Unsupported statement type {}", type.statementType());
      };
    }
  }

  private static List<CustomStatement> getCustomTypeReferenceDependencies(
      CustomType typeReference, boolean includeRelations, Set<String> exclusions
  ) {
    List<CustomStatement> customStatements = getCustomTypeDependencies(typeReference.targetType(), includeRelations, exclusions);
    List<CustomStatement> allCustomStatements = new ArrayList<>(customStatements);
    typeReference.typeArguments().stream()
        .map(ref -> getTypeReferenceDependencies(ref, includeRelations, exclusions))
        .forEach(allCustomStatements::addAll);
    return allCustomStatements;
  }

  private static List<CustomStatement> getNamedTypeReferenceDependencies(
      NamedType typeReference, boolean includeParentsAndTypeParams, Set<String> exclusions
  ) {
    return typeReference.extendedBounds().stream()
        .map(ref -> getTypeReferenceDependencies(ref, includeParentsAndTypeParams, exclusions))
        .flatMap(List::stream)
        .toList();
  }

  private static List<CustomStatement> getWildcardTypeReferenceDependencies(
      WildcardType typeReference, boolean includeRelations, Set<String> exclusions
  ) {
    List<CustomStatement> relatedClasses = new ArrayList<>();
    typeReference.extendedBound()
        .map(ref -> getTypeReferenceDependencies(ref, includeRelations, exclusions))
        .ifPresent(relatedClasses::addAll);
    typeReference.superBound()
        .map(ref -> getTypeReferenceDependencies(ref, includeRelations, exclusions))
        .ifPresent(relatedClasses::addAll);
    return relatedClasses;
  }

  private static List<CustomStatement> getArrayTypeReferenceDependencies(
      ArrayType typeReference, boolean includeRelations, Set<String> exclusions
  ) {
    if (typeReference.elementType().isPrimitive()) {
      return List.of();
    }
    return getTypeReferenceDependencies(
        typeReference.elementType().asNonPrimitiveType().orElseThrow(IllegalStateException::new),
        includeRelations,
        exclusions
    );
  }
}
