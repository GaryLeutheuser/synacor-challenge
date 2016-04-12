package synacor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SynacorTest {
  
  private static final Logger LOGGER = Logger.getLogger(SynacorVM.class.getName());
  private static final ConsoleHandler HANDLER = new ConsoleHandler();
  
  static final int NUM_ADDRESSES = 32768;
  static final int NUM_REGISTERS = 8;
  
  public static void main(String[] args) {
    // Setup logging output
    HANDLER.setLevel(Level.FINE);
    LOGGER.addHandler(HANDLER);
    
    // Setup the VM
    SynacorVM vm = new SynacorVM(NUM_ADDRESSES, NUM_REGISTERS);
    
    // TODO: Write the program into memory
    
    // TODO: Start the execution
  }
  
  void readBinary(File file) {
    
    // A 2 byte buffer to hold each read value from binary file
    byte[] data = new byte[2];
    int numRead;
    
    try {
      FileInputStream inputStream = new FileInputStream(file);
      try {
        // Read in numRead bytes
        numRead = inputStream.read(data);
        
        switch (numRead) {
          case -1:
            LOGGER.log(Level.INFO, "Reached end of binary file.");
            break;
          case 1:
            // This should never happen - means there's a half-value somewhere
            // in the binary
            LOGGER.log(Level.SEVERE, "Corrupt data in binary.");
            break;
          default:
            
            break;
        }
        
        
      } catch (IOException ex) {
        LOGGER.log(Level.SEVERE, null, ex);
      }
    } catch (FileNotFoundException ex) {
      LOGGER.log(Level.SEVERE, null, ex);
    }
  }
}
