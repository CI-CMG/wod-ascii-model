package edu.colorado.cires.wod.ascii.model;

public class Attribute {

  private final Integer code;
  private final Double value;

  private Attribute(Integer code, Double value) {
    this.code = code;
    this.value = value;
  }

  public Integer getCode() {
    return code;
  }

  public Double getValue() {
    return value;
  }

  @Override
  public String toString() {
    return "Attribute{" +
        "code=" + code +
        ", value=" + value +
        '}';
  }

  public static Builder builder() {
    return new Builder(null);
  }

  public static Builder builder(Attribute src) {
    return new Builder(src);
  }

  public static class Builder {
    private Integer code;
    private Double value;

    private Builder(Attribute src) {
      if (src != null) {
        code = src.code;
        value = src.value;
      }
    }

    public Builder setCode(Integer code) {
      this.code = code;
      return this;
    }

    public Builder setValue(Double value) {
      this.value = value;
      return this;
    }

    public Attribute build() {
      return new Attribute(code, value);
    }
  }
}
