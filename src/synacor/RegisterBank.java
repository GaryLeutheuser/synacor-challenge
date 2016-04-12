package synacor;

import java.util.ArrayList;

public class RegisterBank {
  private ArrayList<Register> registers;
  
  public RegisterBank(int numRegisters) {
    registers = new ArrayList<>(numRegisters);
    
    for (int i = 0; i < numRegisters; i++) {
      Register register = new Register();
      registers.add(register);
    }
  }
  
  public Register getRegister(int index) {
    return registers.get(index);
  }
}
