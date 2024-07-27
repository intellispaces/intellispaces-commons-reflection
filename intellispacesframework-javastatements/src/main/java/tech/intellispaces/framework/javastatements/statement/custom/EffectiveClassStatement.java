package tech.intellispaces.framework.javastatements.statement.custom;

import tech.intellispaces.framework.commons.action.Actions;
import tech.intellispaces.framework.commons.action.Getter;
import tech.intellispaces.framework.javastatements.statement.StatementType;
import tech.intellispaces.framework.javastatements.statement.StatementTypes;
import tech.intellispaces.framework.javastatements.statement.method.MethodStatement;
import tech.intellispaces.framework.javastatements.statement.type.CustomType;
import tech.intellispaces.framework.javastatements.statement.type.NotPrimitiveType;

import java.util.List;
import java.util.Map;
import java.util.Optional;

class EffectiveClassStatement extends AbstractEffectiveCustomStatement implements ClassStatement {
  private final Getter<Optional<CustomType>> extendedClassGetter;
  private final Getter<List<CustomType>> implementedInterfacesGetter;

  EffectiveClassStatement(
      ClassStatement classStatement, Map<String, NotPrimitiveType> typeMapping
  ) {
    super(classStatement, typeMapping);
    this.extendedClassGetter = Actions.cachedLazyGetter(CustomTypeFunctions::getExtendedClass, this);
    this.implementedInterfacesGetter = Actions.cachedLazyGetter(CustomTypeFunctions::getImplementedInterfaces, this);
  }

  @Override
  public StatementType statementType() {
    return StatementTypes.Class;
  }

  @Override
  public List<MethodStatement> constructors() {
    List<MethodStatement> actualConstructors = ((ClassStatement) actualType).constructors();
    return actualConstructors.stream()
        .map(c -> c.specify(typeMapping))
        .toList();
  }

  @Override
  public Optional<CustomType> extendedClass() {
    return extendedClassGetter.get();
  }

  @Override
  public List<CustomType> implementedInterfaces() {
    return implementedInterfacesGetter.get();
  }
}
