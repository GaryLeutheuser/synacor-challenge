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
  
  private static SynacorVM vm;
  
  static final int NUM_ADDRESSES = 32768;
  static final int NUM_REGISTERS = 8;
  
  public static void main(String[] args) {
    // Setup logging output
    HANDLER.setLevel(Level.FINE);
    LOGGER.addHandler(HANDLER);
    
    // Setup the VM
    vm = new SynacorVM(NUM_ADDRESSES, NUM_REGISTERS);
    
    // TODO: Write the program into memory
    File program = new File("C:/Users/gleut/Documents/NetBeansProjects/SynacorChallenge/challenge.bin");
    readBinary(program);
    
    // TODO: Start the execution
    vm.begin();
  }
  
  static void readBinary(File file) {
    
    // A 2 byte buffer to hold each read value from binary file
    byte[] data = new byte[2];
    int numRead = 0;
    
    // Values which will be used to load the VM memory
    int currentAddress = 0; // Current address in VM memory
    int combinedData;       // 16-bit value (combination of data[0] and data[1];
    
    try {
      FileInputStream inputStream = new FileInputStream(file);
      try {
          
        
        while (numRead != -1) {
          // Read in numRead bytes
          numRead = inputStream.read(data);
          
          if (numRead == 1) {
            // This should never happen - means there's a half-value somewhere
            // in the binary
            LOGGER.log(Level.SEVERE, "Corrupt data in binary.");
          } else {
            // 2 bytes read in - combine them and put them in VM memory
            combinedData = (data[0] | ((data[1] >>> 4) & 0x00FF));
            
            if (combinedData < 0) {
              combinedData = ~combinedData + 1;
            }
            
            vm.writeMemory(currentAddress++, combinedData);
            LOGGER.log(Level.FINER, "Wrote memory: {0} to {1}", new Object[]{combinedData, currentAddress - 1});
          }
        }
        
        LOGGER.log(Level.INFO, "Finished loading binary.");
        
      } catch (IOException ex) {
        LOGGER.log(Level.SEVERE, null, ex);
      }
    } catch (FileNotFoundException ex) {
      LOGGER.log(Level.SEVERE, null, ex);
    }
  }
}
