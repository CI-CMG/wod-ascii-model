package edu.colorado.cires.wod.ascii;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CharacterDataHeader {

  private int dataBytes;
  private List<CharacterData> characterData = new ArrayList<>(0);

  public int getDataBytes() {
    return dataBytes;
  }

  public void setDataBytes(int dataBytes) {
    this.dataBytes = dataBytes;
  }

  public List<CharacterData> getCharacterData() {
    return characterData;
  }

  public void setCharacterData(List<CharacterData> characterData) {
    if(characterData == null) {
      characterData = new ArrayList<>(0);
    }
    this.characterData = characterData;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CharacterDataHeader that = (CharacterDataHeader) o;
    return dataBytes == that.dataBytes && Objects.equals(characterData, that.characterData);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dataBytes, characterData);
  }

  @Override
  public String toString() {
    return "CharacterDataHeader{" +
        "dataBytes=" + dataBytes +
        ", characterData=" + characterData +
        '}';
  }
}
