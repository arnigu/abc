import java.io.FileNotFoundException;

class A {
  void m() throws FileNotFoundException {
    n();
  }
  private void n() throws FileNotFoundException {
    int i;
    i = 2;
    for(int j = 0; j < i; ++j) {
      if(j == 4) 
        throw new FileNotFoundException("");
      ++i;
    }
    int j = ++i;
  }
  A() {
    super();
  }
}
