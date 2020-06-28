#include <iostream>
#include <fstream>

int main() {
  std::ifstream input("../challenge.bin");
  if (input.is_open()) {
    std::string line;
    while (getline(input, line)) {
      std::cout << line << std::endl;
    }
  }
  input.close();
  
  return EXIT_SUCCESS;
}
