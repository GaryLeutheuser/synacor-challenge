package synacor;

import java.util.Stack;

public class SynacorVM {
  // Storage regions
  final private Memory memory;
  final private RegisterBank registerBank;
  final private Stack stack;
  
  private int programCounter;
  private boolean runProgram;
  
  public SynacorVM(int numAddresses, int numRegisters) {
    // Setup the storage regions
    memory = new Memory(numAddresses);
    registerBank = new RegisterBank(numRegisters);
    stack = new Stack();
    
    // Initialize the program counter
    programCounter = 0;
  }
  
  // Start the VMs program execution
  private void begin() {
    
    int opcode;
    
    // Execution loop
    // Note - remember to update the pc!
    while (runProgram) {
      // First, read the opcode
      opcode = memory.read(programCounter++);
      
      int a, b, c;
      
      // Then execute the proper instruction
      switch (opcode) {
        case 0:
          halt();
          break;
          
        case 1:
          a = memory.read(programCounter++);
          b = memory.read(programCounter++);
          set(a, b);
          break;
          
        case 2:
          a = memory.read(programCounter++);
          push(a);
          break;
          
        case 3:
          a = memory.read(programCounter++);
          pop(a);
          break;
          
        case 4:
          a = memory.read(programCounter++);
          b = memory.read(programCounter++);
          c = memory.read(programCounter++);
          eq(a, b, c);
          break;
          
        case 19:
          out(memory.read(programCounter++));
          break;
          
        case 21:
          noop();
          break;
          
        default:
          System.out.println("Opcode wasn't understood! Terminating.");
          halt();
          break;
      }
    }
  }
  
  // halt: 0
  //  stop execution and terminate the program
  private void halt() {
    runProgram = false;
  }
  
  // set: 1
  //  Set register <a> to value of <b>
  private void set(int a, int b) {
    Register regA = registerBank.getRegister(a);
    Register regB = registerBank.getRegister(b);
    
    regA.setValue(regB.getValue());
  }
  
  // push: 2
  //  push <a> onto the stack
  private void push(int a) {
    Register regA = registerBank.getRegister(a);
    stack.push(regA.getValue());
  }
  
  // pop: 3
  //  remove the top element from the stack and write it into <a>; empty stack = error
  private void pop(int a) {
    if (stack.empty()) {
      System.out.println("Error, tried to pop empty stack. Exiting.");
      halt();
    } else {
      Register regA = registerBank.getRegister(a);
      regA.setValue((int) stack.pop());
    }
  }
  
  // eq: 4
  //  set <a> to 1 if <b> is equal to <c>; set it to 0 otherwise
  private void eq(int a, int b, int c) {
    Register regA, regB, regC;
    regA = registerBank.getRegister(a);
    regB = registerBank.getRegister(b);
    regC = registerBank.getRegister(c);
    
    if (regB.getValue() == regC.getValue()) {
      regA.setValue(1);
    } else {
      regA.setValue(0);
    }
  }
  
  // out: 19
  //  write the character represented by the ascii code <a> to the terminal
  private void out(int a) {
    int value = registerBank.getRegister(a).getValue();
    System.out.println(Character.toString((char) value));
  }
  
  // noop: 21
  //  no operation
  private void noop() {
  }
  
}
