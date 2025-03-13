package tech.intellispaces.commons.reflection.customtype;

import java.util.function.Supplier;

public interface ImportLists {

  static MutableImportList get(String currentClassName) {
    return new MutableImportListImpl(currentClassName);
  }

  static MutableImportList get(Supplier<String> currentClassName) {
    return new MutableImportListImpl(currentClassName);
  }
}
