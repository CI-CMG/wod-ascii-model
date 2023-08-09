package edu.colorado.cires.wod.ascii;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Variable {

  private int code;
  private int qcFlag;
  private List<Metadata> metadata = new ArrayList<>(0);

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public int getQcFlag() {
    return qcFlag;
  }

  public void setQcFlag(int qcFlag) {
    this.qcFlag = qcFlag;
  }

  public List<Metadata> getMetadata() {
    return metadata;
  }

  public void setMetadata(List<Metadata> metadata) {
    if(metadata == null) {
      metadata = new ArrayList<>(0);
    }
    this.metadata = metadata;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Variable variable = (Variable) o;
    return code == variable.code && qcFlag == variable.qcFlag && Objects.equals(metadata, variable.metadata);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, qcFlag, metadata);
  }

  @Override
  public String toString() {
    return "Variable{" +
        "code=" + code +
        ", qcFlag=" + qcFlag +
        ", metadata=" + metadata +
        '}';
  }
}
