package edu.colorado.cires.wod.ascii;

import java.util.Objects;

public class PrincipalInvestigator {

  private int variableCode;
  private int piCode;

  public int getVariableCode() {
    return variableCode;
  }

  public void setVariableCode(int variableCode) {
    this.variableCode = variableCode;
  }

  public int getPiCode() {
    return piCode;
  }

  public void setPiCode(int piCode) {
    this.piCode = piCode;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PrincipalInvestigator that = (PrincipalInvestigator) o;
    return variableCode == that.variableCode && piCode == that.piCode;
  }

  @Override
  public int hashCode() {
    return Objects.hash(variableCode, piCode);
  }

  @Override
  public String toString() {
    return "PrincipalInvestigator{" +
        "variableCode=" + variableCode +
        ", piCode=" + piCode +
        '}';
  }
}
