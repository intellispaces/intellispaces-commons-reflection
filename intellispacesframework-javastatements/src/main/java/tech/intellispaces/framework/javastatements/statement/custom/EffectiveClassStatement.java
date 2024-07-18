package tech.intellispaces.framework.javastatements.statement.custom;

import tech.intellispaces.framework.commons.action.Actions;
import tech.intellispaces.framework.commons.action.Getter;
import tech.intellispaces.framework.javastatements.statement.StatementType;
import tech.intellispaces.framework.javastatements.statement.StatementTypes;
import tech.intellispaces.framework.javastatements.statement.method.MethodStatement;
import tech.intellispaces.framework.javastatements.statement.reference.CustomTypeReference;
import tech.intellispaces.framework.javastatements.statement.reference.NonPrimitiveTypeReference;

import java.util.List;
import java.util.Map;
import java.util.Optional;

class EffectiveClassStatement extends AbstractEffectiveCustomType implements ClassStatement {
  private final Getter<Optional<CustomTypeReference>> extendedClassGetter;
  private final Getter<List<CustomTypeReference>> implementedInterfacesGetter;

  public EffectiveClassStatement(ClassStatement classStatement, Map<String, NonPrimitiveTypeReference> typeMapping) {
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
  public Optional<CustomTypeReference> extendedClass() {
    return extendedClassGetter.get();
  }

  @Override
  public List<CustomTypeReference> implementedInterfaces() {
    return implementedInterfacesGetter.get();
  }
}
