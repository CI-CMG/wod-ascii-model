package edu.colorado.cires.wod.ascii.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static edu.colorado.cires.wod.ascii.model.VariableConsts.TEMPERATURE;

public class Depth {

  private final Double depth;
  private final Integer depthErrorFlag;
  private final Integer originatorsFlag;
  private final List<ProfileData> data;

  private Depth(Double depth, Integer depthErrorFlag, Integer originatorsFlag, List<ProfileData> data) {
    this.depth = depth;
    this.depthErrorFlag = depthErrorFlag;
    this.originatorsFlag = originatorsFlag;
    this.data = data;
  }

  public Double getDepth() {
    return depth;
  }

  public Integer getDepthErrorFlag() {
    return depthErrorFlag;
  }

  public Integer getOriginatorsFlag() {
    return originatorsFlag;
  }

  public List<ProfileData> getData() {
    return data;
  }

  public Optional<ProfileData> getTemperature() {
    return data.stream().filter(pd -> pd.getVariable() == TEMPERATURE).findFirst();
  }

  @Override
  public String toString() {
    return "Depth{" +
        "depth=" + depth +
        ", depthErrorFlag=" + depthErrorFlag +
        ", originatorsFlag=" + originatorsFlag +
        ", data=" + data +
        '}';
  }

  public static Builder builder() {
    return new Builder(null);
  }

  public static Builder builder(Depth src) {
    return new Builder(src);
  }

  public static class Builder {

    private Double depth;
    private Integer depthErrorFlag;
    private Integer originatorsFlag;
    private List<ProfileData> data = new ArrayList<>(0);

    private Builder(Depth src) {
      if (src != null) {
        depth = src.depth;
        depthErrorFlag = src.depthErrorFlag;
        originatorsFlag = src.originatorsFlag;
        data = src.data;
      }
    }

    public Builder setDepth(Double depth) {
      this.depth = depth;
      return this;
    }

    public Builder setDepthErrorFlag(Integer depthErrorFlag) {
      this.depthErrorFlag = depthErrorFlag;
      return this;
    }

    public Builder setOriginatorsFlag(Integer originatorsFlag) {
      this.originatorsFlag = originatorsFlag;
      return this;
    }

    public Builder setData(List<ProfileData> data) {
      if (data == null) {
        data = new ArrayList<>(0);
      }
      this.data = new ArrayList<>(data);
      return this;
    }

    public Depth build() {
      return new Depth(
          depth,
          depthErrorFlag,
          originatorsFlag,
          data
      );
    }
  }
}
