package edu.colorado.cires.wod.ascii;

class ByteRange {

  private final byte start;
  private final byte end;

  public ByteRange(byte start, byte end) {
    this.start = start;
    this.end = end;
  }

  public byte getStart() {
    return start;
  }

  public byte getEnd() {
    return end;
  }

}
