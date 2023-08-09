package edu.colorado.cires.wod.ascii;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class ProfileDepth {

  private BigDecimal depth;
  private int depthErrorCode;
  private int originatorDepthErrorFlag;


  private Map<Integer, ProfileValue> values = new LinkedHashMap<>();

  public Map<Integer, ProfileValue> getValues() {
    return values;
  }

  public void setValues(Map<Integer, ProfileValue> values) {
    if(values == null) {
      values = new LinkedHashMap<>();
    }
    this.values = values;
  }


  public BigDecimal getDepth() {
    return depth;
  }

  public void setDepth(BigDecimal depth) {
    this.depth = depth;
  }

  public int getDepthErrorCode() {
    return depthErrorCode;
  }

  public void setDepthErrorCode(int depthErrorCode) {
    this.depthErrorCode = depthErrorCode;
  }

  public int getOriginatorDepthErrorFlag() {
    return originatorDepthErrorFlag;
  }

  public void setOriginatorDepthErrorFlag(int originatorDepthErrorFlag) {
    this.originatorDepthErrorFlag = originatorDepthErrorFlag;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProfileDepth that = (ProfileDepth) o;
    return depthErrorCode == that.depthErrorCode && originatorDepthErrorFlag == that.originatorDepthErrorFlag && Objects.equals(depth,
        that.depth) && Objects.equals(values, that.values);
  }

  @Override
  public int hashCode() {
    return Objects.hash(depth, depthErrorCode, originatorDepthErrorFlag, values);
  }

  @Override
  public String toString() {
    return "ProfileDepthValue{" +
        "depth=" + depth +
        ", depthErrorCode=" + depthErrorCode +
        ", originatorDepthErrorFlag=" + originatorDepthErrorFlag +
        ", values=" + values +
        '}';
  }
}
