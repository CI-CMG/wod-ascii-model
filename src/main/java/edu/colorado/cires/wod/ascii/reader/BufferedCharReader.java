package edu.colorado.cires.wod.ascii.reader;

import edu.colorado.cires.wod.ascii.WodFileReader.CharReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class BufferedCharReader implements CharReader {

  private static final Logger LOGGER = LoggerFactory.getLogger(BufferedCharReader.class);

  private static final char ETX = (char) 3;
  private static final char NAK = (char) 21;

  private static final int DEFAULT_BUFFER_SIZE = 1000;

  private final BufferedReader bufferedReader;
  private final BlockingQueue<Character> readQueue;
  private final Thread consumer;

  public BufferedCharReader(BufferedReader bufferedReader) {
    this(bufferedReader, DEFAULT_BUFFER_SIZE);
  }

  public BufferedCharReader(BufferedReader bufferedReader, int bufferSize) {
    if (bufferSize < 1) {
      throw new IllegalArgumentException("Buffer size must be at least 1");
    }
    this.bufferedReader = bufferedReader;
    this.readQueue = new LinkedBlockingDeque<>(bufferSize);
    consumer = new Thread(new ReadRunner());
    consumer.start();
  }

  private class ReadRunner implements Runnable {

    @Override
    public void run() {
      try {
        String line;
        while ((line = bufferedReader.readLine()) != null) {
          for (char c : line.toCharArray()) {
            readQueue.put(c);
          }
        }
        readQueue.put(ETX);
      } catch (IOException e) {
        LOGGER.error("Unable to read data", e);
        try {
          readQueue.put(NAK);
        } catch (InterruptedException ex) {
          Thread.currentThread().interrupt();
          throw new IllegalStateException("Character reader was interrupted", e);
        }
        throw new IllegalStateException("Unable to read data", e);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        throw new IllegalStateException("Character reader was interrupted", e);
      }
    }
  }

  @Override
  public char readChar() throws IOException {
    try {
      char c = readQueue.take();
      if (c == ETX) {
        throw new EOFException("End of data");
      } else if (c == NAK) {
        throw new IllegalStateException("An error occurred reading data");
      }
      return c;
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new IllegalStateException("Character reader was interrupted", e);
    }
  }
}
