package edu.colorado.cires.wod.ascii.model;

public class QcAttribute {

  private final Integer code;
  private final Double value;
  private final Integer qcFlag;
  private final Integer originatorsFlag;

  private QcAttribute(Integer code, Double value, Integer qcFlag, Integer originatorsFlag) {
    this.code = code;
    this.value = value;
    this.qcFlag = qcFlag;
    this.originatorsFlag = originatorsFlag;
  }

  public Integer getCode() {
    return code;
  }

  public Double getValue() {
    return value;
  }

  public Integer getQcFlag() {
    return qcFlag;
  }

  public Integer getOriginatorsFlag() {
    return originatorsFlag;
  }

  @Override
  public String toString() {
    return "QcAttribute{" +
        "code=" + code +
        ", value=" + value +
        ", qcFlag=" + qcFlag +
        ", originatorsFlag=" + originatorsFlag +
        '}';
  }

  public static Builder builder() {
    return new Builder(null);
  }

  public static Builder builder(QcAttribute src) {
    return new Builder(src);
  }

  public static class Builder {

    private Integer code;
    private Double value;
    private Integer qcFlag;
    private Integer originatorsFlag;

    private Builder(QcAttribute src) {
      if (src != null) {
        code = src.code;
        value = src.value;
        qcFlag = src.qcFlag;
        originatorsFlag = src.originatorsFlag;
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

    public Builder setQcFlag(Integer qcFlag) {
      this.qcFlag = qcFlag;
      return this;
    }

    public Builder setOriginatorsFlag(Integer originatorsFlag) {
      this.originatorsFlag = originatorsFlag;
      return this;
    }

    public QcAttribute build() {
      return new QcAttribute(
          code,
          value,
          qcFlag,
          originatorsFlag);
    }
  }
}
