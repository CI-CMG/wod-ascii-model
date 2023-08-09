package edu.colorado.cires.wod.ascii;

import java.math.BigDecimal;
import java.util.Objects;

public class BiologicalHeaderValue {

  private int code;
  private BigDecimal value;

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public BigDecimal getValue() {
    return value;
  }

  public void setValue(BigDecimal value) {
    this.value = value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BiologicalHeaderValue that = (BiologicalHeaderValue) o;
    return code == that.code && Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, value);
  }

  @Override
  public String toString() {
    return "BiologicalHeaderValue{" +
        "code=" + code +
        ", value=" + value +
        '}';
  }
}
