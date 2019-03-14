package ch.heigvd.res.labio.impl;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Olivier Liechti
 */
public class Utils {

  private static final Logger LOG = Logger.getLogger(Utils.class.getName());

  /**
   * This method looks for the next new line separators (\r, \n, \r\n) to extract
   * the next line in the string passed in arguments. 
   * 
   * @param lines a string that may contain 0, 1 or more lines
   * @return an array with 2 elements; the first element is the next line with
   * the line separator, the second element is the remaining text. If the argument does not
   * contain any line separator, then the first element is an empty string.
   */
  public static String[] getNextLine(String lines) {
    final String winSeparator ="\n\r";
    final String unixSeparator ="\n";
    final String MacSeparator ="\r";


    //First search if a separator is present in the line
    String foundedSeparator="";
    if(lines.contains(winSeparator))
      foundedSeparator = winSeparator;
    else if(lines.contains(unixSeparator))
      foundedSeparator = unixSeparator;
    else if(lines.contains(MacSeparator))
      foundedSeparator = MacSeparator;

    // Check if a separator has been found
    if(foundedSeparator.equals("")){
      //if no separator has been found
      return new String[] {"",lines};
    }

    //we split the lines at the first separator
    int index = lines.indexOf(foundedSeparator);
    // Construct and return an array, we need the separator so index+1
    return new String[] {lines.substring(0,index+1),lines.substring(index+1)};
  }

}
