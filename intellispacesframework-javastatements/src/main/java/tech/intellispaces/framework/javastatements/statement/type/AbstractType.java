package tech.intellispaces.framework.javastatements.statement.type;

import tech.intellispaces.framework.commons.action.Actions;
import tech.intellispaces.framework.commons.action.Getter;
import tech.intellispaces.framework.javastatements.statement.common.DependenciesFunctions;
import tech.intellispaces.framework.javastatements.statement.custom.CustomStatement;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

abstract class AbstractType implements Type {
  private final Getter<Collection<CustomStatement>> dependenciesGetter;
  private final Getter<Collection<String>> dependencyTypesGetter;
  private final Getter<String> actualDeclarationGetter;
  private final Getter<String> formalFullDeclarationGetter;
  private final Getter<String> formalBriefDeclarationGetter;

  AbstractType() {
    this.dependenciesGetter = Actions.cachedLazyGetter(DependenciesFunctions::getTypeReferenceDependencies, this);
    this.dependencyTypesGetter = Actions.cachedLazyGetter(AbstractType::collectDependencyTypenames, this);
    this.actualDeclarationGetter = Actions.cachedLazyGetter(TypeFunctions::getActualTypeDeclaration, this);
    this.formalFullDeclarationGetter = Actions.cachedLazyGetter(TypeFunctions::getFormalFullTypeReferenceDeclaration, this);
    this.formalBriefDeclarationGetter = Actions.cachedLazyGetter(TypeFunctions::getFormalBriefTypeReferenceDeclaration, this);
  }

  @Override
  public Collection<CustomStatement> dependencies() {
    return dependenciesGetter.get();
  }

  @Override
  public Collection<String> dependencyTypenames() {
    return dependencyTypesGetter.get();
  }

  private Collection<String> collectDependencyTypenames() {
    return dependencies().stream()
        .map(CustomStatement::canonicalName)
        .collect(Collectors.toSet());
  }

  @Override
  public String actualDeclaration() {
    return actualDeclarationGetter.get();
  }

  @Override
  public String actualDeclaration(Function<String, String> simpleNameMapper) {
    return TypeFunctions.getActualTypeDeclaration(this, simpleNameMapper);
  }

  @Override
  public String actualBlindDeclaration(Function<String, String> simpleNameMapper) {
    return TypeFunctions.getActualBlindTypeReferenceDeclaration(this, simpleNameMapper);
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
