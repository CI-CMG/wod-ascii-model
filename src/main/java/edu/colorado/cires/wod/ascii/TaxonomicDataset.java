package edu.colorado.cires.wod.ascii;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TaxonomicDataset {


  private List<TaxonomicDatasetValue> values = new ArrayList<>();

  public List<TaxonomicDatasetValue> getValues() {
    return values;
  }

  public void setValues(List<TaxonomicDatasetValue> values) {
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
    TaxonomicDataset that = (TaxonomicDataset) o;
    return Objects.equals(values, that.values);
  }

  @Override
  public int hashCode() {
    return Objects.hash(values);
  }

  @Override
  public String toString() {
    return "TaxonomicDataset{" +
        "values=" + values +
        '}';
  }
}
