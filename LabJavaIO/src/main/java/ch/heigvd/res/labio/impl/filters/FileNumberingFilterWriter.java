package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;

/**
 * This class transforms the streams of character sent to the decorated writer.
 * When filter encounters a line separator, it sends it to the decorated writer.
 * It then sends the line number and a tab character, before resuming the write
 * process.
 *
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());

  private int lineNumerotation;
  private boolean isReturnChar;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
    lineNumerotation = 0;
    isReturnChar = false;
  }

  @Override
  public void write(String str, int off, int len) throws IOException {

    write(str.toCharArray(), off, len);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    for (int i = off; i < off + len; i++)
      write(cbuf[i]);
  }

  @Override
  public void write(int c) throws IOException {
    Character a = (char)c;

    if (lineNumerotation == 0) {
      out.write(Integer.toString(++lineNumerotation) + '\t');
    }

    if (a=='\r') {
      isReturnChar = true;
      out.write(c);
    } else if (a == '\n') {
      isReturnChar = false;
      out.write(c);
      out.write(Integer.toString(++lineNumerotation) + '\t');
    } else if (isReturnChar) {
      isReturnChar = false;
      out.write(Integer.toString(++lineNumerotation) + '\t');
      out.write(c);
    } else {
      out.write(c);
    }
  }
}
