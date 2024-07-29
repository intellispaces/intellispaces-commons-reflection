package tech.intellispaces.javastatements.statement.customtype;

import tech.intellispaces.actions.Actions;
import tech.intellispaces.actions.Getter;
import tech.intellispaces.javastatements.statement.StatementType;
import tech.intellispaces.javastatements.statement.StatementTypes;
import tech.intellispaces.javastatements.statement.method.MethodStatement;
import tech.intellispaces.javastatements.statement.reference.CustomTypeReference;
import tech.intellispaces.javastatements.statement.reference.NotPrimitiveTypeReference;

import java.util.List;
import java.util.Map;
import java.util.Optional;

class EffectiveClassType extends AbstractEffectiveCustomType implements ClassType {
  private final Getter<Optional<CustomTypeReference>> extendedClassGetter;
  private final Getter<List<CustomTypeReference>> implementedInterfacesGetter;

  EffectiveClassType(
      ClassType classStatement, Map<String, NotPrimitiveTypeReference> typeMapping
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
    List<MethodStatement> actualConstructors = ((ClassType) actualType).constructors();
    return actualConstructors.stream()
        .map(c -> c.specify(typeMapping))
        .toList();
  }

  @Override
  public Optional<CustomTypeReference> extendedClass() {
    return extendedClassGetter.get();
  }

  @Override
  public List<CustomTypeReference> implementedInterfaces() {
    return implementedInterfacesGetter.get();
  }
}
