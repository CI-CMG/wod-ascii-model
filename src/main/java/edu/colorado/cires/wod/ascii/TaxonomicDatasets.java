package edu.colorado.cires.wod.ascii;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TaxonomicDatasets {

  private List<TaxonomicDataset> taxonomicDatasets = new ArrayList<>(0);

  public List<TaxonomicDataset> getTaxonomicDatasets() {
    return taxonomicDatasets;
  }

  public void setTaxonomicDatasets(List<TaxonomicDataset> taxonomicDatasets) {
    if (taxonomicDatasets == null) {
      taxonomicDatasets = new ArrayList<>(0);
    }
    this.taxonomicDatasets = taxonomicDatasets;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TaxonomicDatasets that = (TaxonomicDatasets) o;
    return Objects.equals(taxonomicDatasets, that.taxonomicDatasets);
  }

  @Override
  public int hashCode() {
    return Objects.hash(taxonomicDatasets);
  }

  @Override
  public String toString() {
    return "TaxonomicDatasets{" +
        "taxonomicDatasets=" + taxonomicDatasets +
        '}';
  }
}
