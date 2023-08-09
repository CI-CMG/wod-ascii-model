package edu.colorado.cires.wod.ascii;

import java.util.Objects;

public class Metadata {

  private int code;
  private double value;

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public double getValue() {
    return value;
  }

  public void setValue(double value) {
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
    Metadata metadata = (Metadata) o;
    return code == metadata.code && Double.compare(metadata.value, value) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, value);
  }

  @Override
  public String toString() {
    return "Metadata{" +
        "code=" + code +
        ", value=" + value +
        '}';
  }
}
