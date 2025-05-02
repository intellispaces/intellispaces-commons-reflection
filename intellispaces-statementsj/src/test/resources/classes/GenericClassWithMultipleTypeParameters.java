import java.io.DataInput;

import tech.intellispaces.statementsj.support.TesteeType;

@TesteeType
public class GenericClassWithMultipleTypeParameters<T1, T2 extends T1, T3 extends Number, T4 extends AutoCloseable & DataInput> {

  public T2 process(T1 arg1, T3 arg2) {
    return null;
  }
}