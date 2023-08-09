package edu.colorado.cires.wod.ascii.model;

public class PrincipalInvestigator {

  private final Integer variable;
  private final Integer code;

  private PrincipalInvestigator(Integer variable, Integer code) {
    this.variable = variable;
    this.code = code;
  }

  public Integer getVariable() {
    return variable;
  }

  public Integer getCode() {
    return code;
  }

  @Override
  public String toString() {
    return "PrincipalInvestigator{" +
        "variable=" + variable +
        ", code=" + code +
        '}';
  }

  public static Builder builder() {
    return new Builder(null);
  }

  public static Builder builder(PrincipalInvestigator src) {
    return new Builder(src);
  }

  public static class Builder {

    private Integer variable;
    private Integer code;


    private Builder(PrincipalInvestigator src) {
      if (src != null) {
        variable = src.variable;
        code = src.code;
      }
    }

    public Builder setVariable(Integer variable) {
      this.variable = variable;
      return this;
    }

    public Builder setCode(Integer code) {
      this.code = code;
      return this;
    }

    public PrincipalInvestigator build() {
      return new PrincipalInvestigator(
          variable,
          code
      );
    }
  }
}
