package edu.colorado.cires.wod.ascii.reader;

import edu.colorado.cires.wod.ascii.WodFileReader.CharReader;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileCharReader implements CharReader {

  private final RandomAccessFile file;

  public RandomAccessFileCharReader(RandomAccessFile file) {
    this.file = file;
  }

  @Override
  public char readChar() throws IOException {
    return (char) file.readByte();
  }
}
