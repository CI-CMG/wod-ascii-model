package edu.colorado.cires.wod.ascii;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProfileData {

  private List<ProfileDepth> depths = new ArrayList<>(0);

  public List<ProfileDepth> getDepths() {
    return depths;
  }

  public void setDepths(List<ProfileDepth> depths) {
    if(depths == null) {
      depths = new ArrayList<>(0);
    }
    this.depths = depths;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProfileData that = (ProfileData) o;
    return Objects.equals(depths, that.depths);
  }

  @Override
  public int hashCode() {
    return Objects.hash(depths);
  }

  @Override
  public String toString() {
    return "ProfileData{" +
        "depths=" + depths +
        '}';
  }
}
