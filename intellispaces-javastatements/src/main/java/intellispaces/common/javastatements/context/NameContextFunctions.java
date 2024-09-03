package intellispaces.common.javastatements.context;

import intellispaces.common.javastatements.reference.NotPrimitiveReference;
import intellispaces.common.javastatements.reference.NamedReference;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public interface NameContextFunctions {

  static TypeContext getActualNameContext(
      TypeContext parentNamespace,
      List<NamedReference> typeParams,
      List<NotPrimitiveReference> typeArguments
  ) {
    var builder = TypeContexts.build();
    NamedReference type;
    NotPrimitiveReference actualType;
    if (!typeParams.isEmpty() && typeParams.size() == typeArguments.size()) {
      Iterator<NamedReference> paramIterator = typeParams.iterator();
      Iterator<NotPrimitiveReference> argumentIterator = typeArguments.iterator();
      while (paramIterator.hasNext() && argumentIterator.hasNext()) {
        NamedReference namedReference = paramIterator.next();
        type = namedReference;
        Optional<NamedReference> parentFormalTypeReference = parentNamespace.get(namedReference.name())
            .map(ContextTypeParameter::namedType);
        if (parentFormalTypeReference.isPresent()) {
          type = parentFormalTypeReference.get();
        }

        NotPrimitiveReference actualTypeReference = argumentIterator.next();
        actualType = actualTypeReference;
        if (actualTypeReference.asNamedReference().isPresent()) {
          Optional<NotPrimitiveReference> parentActualTypeReference = parentNamespace
              .get(actualTypeReference.asNamedReference().orElseThrow().name())
              .map(ContextTypeParameter::actualType);
          if (parentActualTypeReference.isPresent()) {
            actualType = parentActualTypeReference.get();
          }
        }
        builder.addTypeParam(namedReference.name(), type, actualType);
      }
    }
    return builder.get();
  }
}
