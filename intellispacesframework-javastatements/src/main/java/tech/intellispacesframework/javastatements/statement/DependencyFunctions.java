package tech.intellispacesframework.javastatements.statement;

import tech.intellispacesframework.javastatements.exception.JavaStatementException;
import tech.intellispacesframework.javastatements.statement.custom.CustomType;
import tech.intellispacesframework.javastatements.statement.custom.MethodParam;
import tech.intellispacesframework.javastatements.statement.custom.MethodStatement;
import tech.intellispacesframework.javastatements.statement.instance.AnnotationInstance;
import tech.intellispacesframework.javastatements.statement.instance.Instance;
import tech.intellispacesframework.javastatements.statement.reference.ArrayTypeReference;
import tech.intellispacesframework.javastatements.statement.reference.CustomTypeReference;
import tech.intellispacesframework.javastatements.statement.reference.NamedTypeReference;
import tech.intellispacesframework.javastatements.statement.reference.TypeReference;
import tech.intellispacesframework.javastatements.statement.reference.WildcardTypeReference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Functions to calculate statement dependencies.
 */
public interface DependencyFunctions {

  static Collection<CustomType> getCustomTypeDependencies(CustomType type) {
    return getCustomTypeDependencies(type, true, Set.of()).stream()
        .filter(customType -> !customType.canonicalName().startsWith("java.lang."))
        .filter(customType -> !customType.canonicalName().equals(type.canonicalName()))
        .filter(customType -> !customType.packageName().equals(type.packageName()))
        .toList();
  }

  static Collection<CustomType> getTypeReferenceDependencies(TypeReference typeReference) {
    return getTypeReferenceDependencies(typeReference, false, Set.of()).stream()
        .filter(customType -> !customType.canonicalName().startsWith("java.lang."))
        .toList();
  }

  private static List<CustomType> getCustomTypeDependencies(CustomType customType, boolean includeRelations, Set<String> exclusions) {
    if (exclusions.contains(customType.canonicalName())) {
      return List.of();
    }
    Set<String> newExclusions = new HashSet<>(exclusions);
    newExclusions.add(customType.canonicalName());

    List<CustomType> customTypes = new ArrayList<>();
    customTypes.add(customType);
    if (includeRelations) {
      customType.annotations().stream()
          .map(ant -> getAnnotationDependencies(ant, exclusions))
          .forEach(customTypes::addAll);
      customType.parentTypes().stream()
          .map(ref -> getCustomTypeReferenceDependencies(ref, false, newExclusions))
          .forEach(customTypes::addAll);
      customType.typeParameters().stream()
          .map(ref -> getNamedTypeReferenceDependencies(ref, false, newExclusions))
          .forEach(customTypes::addAll);
      customType.declaredMethods().stream()
          .map(ref -> getMethodDependencies(ref, newExclusions))
          .forEach(customTypes::addAll);
    }
    return customTypes;
  }

  private static List<CustomType> getMethodDependencies(MethodStatement method, Set<String> exclusions) {
    List<CustomType> customTypes = new ArrayList<>();
    method.typeParameters().stream()
        .map(typeParam -> getNamedTypeReferenceDependencies(typeParam, false, exclusions))
        .forEach(customTypes::addAll);
    if (method.returnType().isPresent()) {
      customTypes.addAll(getTypeReferenceDependencies(method.returnType().orElseThrow(), false, exclusions));
    }
    method.params().stream()
        .map(MethodParam::type)
        .map(paramType -> getTypeReferenceDependencies(paramType, false, exclusions))
        .forEach(customTypes::addAll);
    method.exceptions().stream()
        .map(exceptionType -> getTypeReferenceDependencies(exceptionType, false, exclusions))
        .forEach(customTypes::addAll);
    return customTypes;
  }

  private static List<CustomType> getInstanceDependencies(Instance instance, Set<String> exclusions) {
    if (StatementTypes.ClassInstance == instance.statementType()) {
      CustomType classType = instance.asClass().orElseThrow().type();
      if (!exclusions.contains(classType.canonicalName())) {
        return List.of(classType);
      }
    } else if (StatementTypes.EnumInstance == instance.statementType()) {
      CustomType enumType = instance.asEnum().orElseThrow().type();
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

  private static List<CustomType> getAnnotationDependencies(AnnotationInstance annotation, Set<String> exclusions) {
    List<CustomType> customTypes = new ArrayList<>();
    customTypes.add(annotation.annotationStatement());

    annotation.elements().stream()
        .map(element -> DependencyFunctions.getInstanceDependencies(element.value(), exclusions))
        .forEach(customTypes::addAll);
    return customTypes;
  }

  private static List<CustomType> getTypeReferenceDependencies(TypeReference typeReference, boolean includeRelations, Set<String> exclusions) {
    if (typeReference.isPrimitive()) {
      return List.of();
    } else {
      return switch ((StatementTypes) typeReference.statementType()) {
        case CustomTypeReference -> getCustomTypeReferenceDependencies(
            typeReference.asCustomTypeReference().orElseThrow(), includeRelations, exclusions);
        case NamedTypeReference -> getNamedTypeReferenceDependencies(
            typeReference.asNamedTypeReference().orElseThrow(), includeRelations, exclusions);
        case WildcardTypeReference -> getWildcardTypeReferenceDependencies(
            typeReference.asWildcardTypeReference().orElseThrow(), includeRelations, exclusions);
        case ArrayTypeReference -> getArrayTypeReferenceDependencies(
            typeReference.asArrayTypeReference().orElseThrow(), includeRelations, exclusions);
        default -> throw JavaStatementException.withMessage("Unsupported statement type {}", typeReference.statementType());
      };
    }
  }

  private static List<CustomType> getCustomTypeReferenceDependencies(CustomTypeReference typeReference, boolean includeRelations, Set<String> exclusions) {
    List<CustomType> customTypes = getCustomTypeDependencies(typeReference.targetType(), includeRelations, exclusions);
    List<CustomType> allCustomTypes = new ArrayList<>(customTypes);
    typeReference.typeArguments().stream()
        .map(ref -> getTypeReferenceDependencies(ref, includeRelations, exclusions))
        .forEach(allCustomTypes::addAll);
    return allCustomTypes;
  }

  private static List<CustomType> getNamedTypeReferenceDependencies(
      NamedTypeReference typeReference, boolean includeParentsAndTypeParams, Set<String> exclusions
  ) {
    return typeReference.extendedBounds().stream()
        .map(ref -> getTypeReferenceDependencies(ref, includeParentsAndTypeParams, exclusions))
        .flatMap(List::stream)
        .toList();
  }

  private static List<CustomType> getWildcardTypeReferenceDependencies(WildcardTypeReference typeReference, boolean includeRelations, Set<String> exclusions) {
    List<CustomType> relatedClasses = new ArrayList<>();
    typeReference.extendedBound()
        .map(ref -> getTypeReferenceDependencies(ref, includeRelations, exclusions))
        .ifPresent(relatedClasses::addAll);
    typeReference.superBound()
        .map(ref -> getTypeReferenceDependencies(ref, includeRelations, exclusions))
        .ifPresent(relatedClasses::addAll);
    return relatedClasses;
  }

  private static List<CustomType> getArrayTypeReferenceDependencies(ArrayTypeReference typeReference, boolean includeRelations, Set<String> exclusions) {
    if (typeReference.elementType().isPrimitive()) {
      return List.of();
    }
    return getTypeReferenceDependencies(
        typeReference.elementType().asNonPrimitiveTypeReference().orElseThrow(IllegalStateException::new), includeRelations, exclusions
    );
  }
}
