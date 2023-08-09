package edu.colorado.cires.wod.ascii;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SecondaryHeader {

  private int totalBytesForSecondaryHeader;
  private List<SecondaryHeaderValue> values = new ArrayList<>(0);

  public int getTotalBytesForSecondaryHeader() {
    return totalBytesForSecondaryHeader;
  }

  public void setTotalBytesForSecondaryHeader(int totalBytesForSecondaryHeader) {
    this.totalBytesForSecondaryHeader = totalBytesForSecondaryHeader;
  }

  public List<SecondaryHeaderValue> getValues() {
    return values;
  }

  public void setValues(List<SecondaryHeaderValue> values) {
    if(values == null) {
      values = new ArrayList<>(0);
    }
    this.values = values;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SecondaryHeader that = (SecondaryHeader) o;
    return totalBytesForSecondaryHeader == that.totalBytesForSecondaryHeader && Objects.equals(values, that.values);
  }

  @Override
  public int hashCode() {
    return Objects.hash(totalBytesForSecondaryHeader, values);
  }

  @Override
  public String toString() {
    return "SecondaryHeader{" +
        "totalBytesForSecondaryHeader=" + totalBytesForSecondaryHeader +
        ", values=" + values +
        '}';
  }
}
