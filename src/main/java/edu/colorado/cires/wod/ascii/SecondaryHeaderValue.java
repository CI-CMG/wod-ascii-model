package edu.colorado.cires.wod.ascii;

import java.util.Objects;

public class SecondaryHeaderValue {

  private int code;
  private Double value;

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public Double getValue() {
    return value;
  }

  public void setValue(Double value) {
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
    SecondaryHeaderValue that = (SecondaryHeaderValue) o;
    return code == that.code && Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, value);
  }

  @Override
  public String toString() {
    return "SecondaryHeaderValue{" +
        "code=" + code +
        ", value=" + value +
        '}';
  }
}
