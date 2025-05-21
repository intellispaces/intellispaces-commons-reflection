package tech.intellispaces.javareflection.session;

import tech.intellispaces.javareflection.customtype.CustomType;

/**
 * Parsing session.
 */
public interface Session {

  CustomType getType(String typeName);

  void putType(String typeName, CustomType customType);

  void clear();
}
