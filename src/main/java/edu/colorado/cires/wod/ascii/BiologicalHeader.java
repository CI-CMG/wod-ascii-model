package edu.colorado.cires.wod.ascii;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BiologicalHeader {

  private int totalBytesForBiologicalHeader;
  private List<BiologicalHeaderValue> values = new ArrayList<>(0);

  public int getTotalBytesForBiologicalHeader() {
    return totalBytesForBiologicalHeader;
  }

  public void setTotalBytesForBiologicalHeader(int totalBytesForBiologicalHeader) {
    this.totalBytesForBiologicalHeader = totalBytesForBiologicalHeader;
  }

  public List<BiologicalHeaderValue> getValues() {
    return values;
  }

  public void setValues(List<BiologicalHeaderValue> values) {
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
    BiologicalHeader that = (BiologicalHeader) o;
    return totalBytesForBiologicalHeader == that.totalBytesForBiologicalHeader && Objects.equals(values, that.values);
  }

  @Override
  public int hashCode() {
    return Objects.hash(totalBytesForBiologicalHeader, values);
  }

  @Override
  public String toString() {
    return "BiologicalHeader{" +
        "totalBytesForBiologicalHeader=" + totalBytesForBiologicalHeader +
        ", values=" + values +
        '}';
  }
}
