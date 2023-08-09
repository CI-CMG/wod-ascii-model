package edu.colorado.cires.wod.ascii;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CharacterData {

  private int type;
  private String value;
  private List<PrincipalInvestigator> principalInvestigators = new ArrayList<>(0);

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public List<PrincipalInvestigator> getPrincipalInvestigators() {
    return principalInvestigators;
  }

  public void setPrincipalInvestigators(List<PrincipalInvestigator> principalInvestigators) {
    if(principalInvestigators == null) {
      principalInvestigators = new ArrayList<>(0);
    }
    this.principalInvestigators = principalInvestigators;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CharacterData that = (CharacterData) o;
    return type == that.type && Objects.equals(value, that.value) && Objects.equals(principalInvestigators,
        that.principalInvestigators);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, value, principalInvestigators);
  }

  @Override
  public String toString() {
    return "CharacterData{" +
        "type=" + type +
        ", value='" + value + '\'' +
        ", principalInvestigators=" + principalInvestigators +
        '}';
  }
}
