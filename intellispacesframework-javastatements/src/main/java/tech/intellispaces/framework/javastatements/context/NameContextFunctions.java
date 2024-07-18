package tech.intellispaces.framework.javastatements.context;

import tech.intellispaces.framework.javastatements.statement.reference.NamedTypeReference;
import tech.intellispaces.framework.javastatements.statement.reference.NonPrimitiveTypeReference;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public interface NameContextFunctions {

  static TypeContext getActualNameContext(
      TypeContext parentNamespace, List<NamedTypeReference> typeParams, List<NonPrimitiveTypeReference> typeArguments
  ) {
    TypeContextBuilder contextBuilder = TypeContexts.build();
    NamedTypeReference type;
    NonPrimitiveTypeReference actualType;
    if (!typeParams.isEmpty() && typeParams.size() == typeArguments.size()) {
      Iterator<NamedTypeReference> paramIterator = typeParams.iterator();
      Iterator<NonPrimitiveTypeReference> argumentIterator = typeArguments.iterator();
      while (paramIterator.hasNext() && argumentIterator.hasNext()) {
        NamedTypeReference namedTypeReference = paramIterator.next();
        type = namedTypeReference;
        Optional<NamedTypeReference> parentFormalTypeReference = parentNamespace.get(namedTypeReference.name())
            .map(ContextTypeParameter::reference);
        if (parentFormalTypeReference.isPresent()) {
          type = parentFormalTypeReference.get();
        }

        NonPrimitiveTypeReference actualTypeReference = argumentIterator.next();
        actualType = actualTypeReference;
        if (actualTypeReference.asNamedTypeReference().isPresent()) {
          Optional<NonPrimitiveTypeReference> parentActualTypeReference = parentNamespace.get(actualTypeReference.asNamedTypeReference().orElseThrow().name())
              .map(ContextTypeParameter::type);
          if (parentActualTypeReference.isPresent()) {
            actualType = parentActualTypeReference.get();
          }
        }

        contextBuilder.addTypeParam(namedTypeReference.name(), type, actualType);
      }
    }
    return contextBuilder.get();
  }
}
