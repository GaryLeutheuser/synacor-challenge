package synacor;

import java.util.ArrayList;

public class Memory {
  
  final private ArrayList<Integer> memory;
          
  // Create a memory with 15-bit address space 
  // (2^15-1 = 32767 addresses) and 16-bit values (2^16-1 = 65535)
  public Memory(int numAddresses) {
     memory = new ArrayList<>(numAddresses);
     
     for (int i = 0; i < numAddresses; i++) {
       memory.add(0);
     }
  }
  
  // Return the value of memory address
  public int read(int address) {
      return memory.get(address);
  }
  
  // Write a value to a memory address
  public void write(int address, int data) {
      memory.set(address, data);
  }
}
