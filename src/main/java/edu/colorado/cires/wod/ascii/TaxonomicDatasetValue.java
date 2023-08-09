package edu.colorado.cires.wod.ascii;

import java.math.BigDecimal;
import java.util.Objects;

public class TaxonomicDatasetValue {

  private int code;
  private BigDecimal value;
  private int qcFlag;
  private int originatorFlag;

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

  public int getQcFlag() {
    return qcFlag;
  }

  public void setQcFlag(int qcFlag) {
    this.qcFlag = qcFlag;
  }

  public int getOriginatorFlag() {
    return originatorFlag;
  }

  public void setOriginatorFlag(int originatorFlag) {
    this.originatorFlag = originatorFlag;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TaxonomicDatasetValue that = (TaxonomicDatasetValue) o;
    return code == that.code && qcFlag == that.qcFlag && originatorFlag == that.originatorFlag && Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, value, qcFlag, originatorFlag);
  }

  @Override
  public String toString() {
    return "TaxonomicDatasetValue{" +
        "code=" + code +
        ", value=" + value +
        ", qcFlag=" + qcFlag +
        ", originatorFlag=" + originatorFlag +
        '}';
  }
}
