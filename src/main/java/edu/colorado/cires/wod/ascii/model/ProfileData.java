package edu.colorado.cires.wod.ascii.model;

public class ProfileData {

  private final int variable;
  private final double value;
  private final int qcFlag;
  private final int originatorsFlag;

  private ProfileData(int variable, double value, int qcFlag, int originatorsFlag) {
    this.variable = variable;
    this.value = value;
    this.qcFlag = qcFlag;
    this.originatorsFlag = originatorsFlag;
  }

  public int getVariable() {
    return variable;
  }

  public double getValue() {
    return value;
  }

  public int getQcFlag() {
    return qcFlag;
  }

  public int getOriginatorsFlag() {
    return originatorsFlag;
  }

  @Override
  public String toString() {
    return "ProfileData{" +
        "variable=" + variable +
        ", value=" + value +
        ", qcFlag=" + qcFlag +
        ", originatorsFlag=" + originatorsFlag +
        '}';
  }

  public static Builder builder() {
    return new Builder(null);
  }

  public static Builder builder(ProfileData src) {
    return new Builder(src);
  }

  public static class Builder {

    private Integer variable;
    private Double value;
    private Integer qcFlag;
    private Integer originatorsFlag;

    private Builder(ProfileData src) {
      if (src != null) {
        variable = src.variable;
        value = src.value;
        qcFlag = src.qcFlag;
        originatorsFlag = src.originatorsFlag;
      }
    }

    public Builder setVariable(Integer variable) {
      this.variable = variable;
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

    public ProfileData build() {
      return new ProfileData(
          variable,
          value,
          qcFlag,
          originatorsFlag
          );
    }
  }
}
