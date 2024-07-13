package tech.intellispaces.framework.javastatements.statement.reference;

import tech.intellispaces.framework.commons.action.ActionBuilders;
import tech.intellispaces.framework.commons.action.Getter;
import tech.intellispaces.framework.javastatements.statement.DependencyFunctions;
import tech.intellispaces.framework.javastatements.statement.custom.CustomType;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

abstract class AbstractTypeReference implements TypeReference {
  private final Getter<Collection<CustomType>> dependenciesGetter;
  private final Getter<Collection<String>> dependencyTypesGetter;
  private final Getter<String> actualDeclarationGetter;
  private final Getter<String> formalFullDeclarationGetter;
  private final Getter<String> formalBriefDeclarationGetter;

  protected AbstractTypeReference() {
    this.dependenciesGetter = ActionBuilders.cachedLazyGetter(DependencyFunctions::getTypeReferenceDependencies, this);
    this.dependencyTypesGetter = ActionBuilders.cachedLazyGetter(AbstractTypeReference::collectDependencyTypenames, this);
    this.actualDeclarationGetter = ActionBuilders.cachedLazyGetter(TypeReferenceFunctions::getActualTypeReferenceDeclaration, this);
    this.formalFullDeclarationGetter = ActionBuilders.cachedLazyGetter(TypeReferenceFunctions::getFormalFullTypeReferenceDeclaration, this);
    this.formalBriefDeclarationGetter = ActionBuilders.cachedLazyGetter(TypeReferenceFunctions::getFormalBriefTypeReferenceDeclaration, this);
  }

  @Override
  public Collection<CustomType> dependencies() {
    return dependenciesGetter.get();
  }

  @Override
  public Collection<String> dependencyTypenames() {
    return dependencyTypesGetter.get();
  }

  private Collection<String> collectDependencyTypenames() {
    return dependencies().stream()
        .map(CustomType::canonicalName)
        .collect(Collectors.toSet());
  }

  @Override
  public String actualDeclaration() {
    return actualDeclarationGetter.get();
  }

  @Override
  public String actualDeclaration(Function<String, String> simpleNameMapper) {
    return TypeReferenceFunctions.getActualTypeReferenceDeclaration(this, simpleNameMapper);
  }

  @Override
  public String actualBlindDeclaration(Function<String, String> simpleNameMapper) {
    return TypeReferenceFunctions.getActualBlindTypeReferenceDeclaration(this, simpleNameMapper);
  }

  @Override
  public String formalFullDeclaration() {
    return formalFullDeclarationGetter.get();
  }

  @Override
  public String formalBriefDeclaration() {
    return formalBriefDeclarationGetter.get();
  }
}
