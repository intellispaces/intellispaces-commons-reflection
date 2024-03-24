package intellispaces.javastatements.function;

import intellispaces.javastatements.model.context.ContextTypeParameter;
import intellispaces.javastatements.model.context.NameContext;
import intellispaces.javastatements.model.reference.NamedTypeReference;
import intellispaces.javastatements.model.reference.NonPrimitiveTypeReference;
import intellispaces.javastatements.object.context.NameContextBuilder;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public interface NameContextFunctions {

  static NameContext getActualNameContext(
      NameContext parentNamespace, List<NamedTypeReference> typeParams, List<NonPrimitiveTypeReference> typeArguments
  ) {
    NameContextBuilder nameContextBuilder = NameContextBuilder.get();
    NamedTypeReference type;
    NonPrimitiveTypeReference actualType;
    if (!typeParams.isEmpty() && typeParams.size() == typeArguments.size()) {
      Iterator<NamedTypeReference> paramIterator = typeParams.iterator();
      Iterator<NonPrimitiveTypeReference> argumentIterator = typeArguments.iterator();
      while (paramIterator.hasNext() && argumentIterator.hasNext()) {
        NamedTypeReference namedTypeReference = paramIterator.next();
        type = namedTypeReference;
        Optional<NamedTypeReference> parentFormalTypeReference = parentNamespace.get(namedTypeReference.name())
            .map(ContextTypeParameter::type);
        if (parentFormalTypeReference.isPresent()) {
          type = parentFormalTypeReference.get();
        }

        NonPrimitiveTypeReference actualTypeReference = argumentIterator.next();
        actualType = actualTypeReference;
        if (actualTypeReference.asNamedTypeReference().isPresent()) {
          Optional<NonPrimitiveTypeReference> parentActualTypeReference = parentNamespace.get(actualTypeReference.asNamedTypeReference().orElseThrow().name())
              .map(ContextTypeParameter::actualType);
          if (parentActualTypeReference.isPresent()) {
            actualType = parentActualTypeReference.get();
          }
        }

        nameContextBuilder.addTypeParam(namedTypeReference.name(), type, actualType);
      }
    }
    return nameContextBuilder.build();
  }
}
