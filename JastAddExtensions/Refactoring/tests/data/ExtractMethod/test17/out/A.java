import java.io.FileNotFoundException;

class A {
  static void m() throws FileNotFoundException {
    int i;
    i = n();
    int j = ++i;
  }
  private static int n() throws FileNotFoundException {
    int i;
    i = 2;
    for(int j = 0; j < i; ++j) {
      if(j == 4) 
        throw new FileNotFoundException("");
      ++i;
    }
    return i;
  }
  A() {
    super();
  }
}
