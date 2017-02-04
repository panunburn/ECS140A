import java.util.*;

public class SymTable {
  private Stack<ArrayList<String>> varStack;
  int size;
  public int maxscope(){
    return size-1;
  }
  public SymTable() {
    size = 0;
    varStack = new Stack<ArrayList<String>>();
  }
  
  public void push() {
    ArrayList<String> curBlock = new ArrayList<String>();
    varStack.push(curBlock);
    size++;
  }
  
  public void pop() {
    varStack.pop();
    size--;
  }
  
  public boolean curExist(String string){
    ArrayList<String> curblock = varStack.peek();
    for (String name : curblock) {
      if (name.equals(string)){
        return true;
      }
    }
    return false;
  }
  
  public boolean exist(String string) {
    ListIterator<ArrayList<String>> pitr = varStack.listIterator(size);
    while (pitr.hasPrevious()) {
      ArrayList<String> prevBlock = pitr.previous();
      for (String name : prevBlock) {
        if (name.equals(string)) {
          return true;
        }
      }
    }
    return false;
  }
  
  public boolean scopeExist(String string, int scope) {
    if (scope >= size){
      return false;
    }
    ArrayList<String> curblock;
    curblock = varStack.elementAt(size - 1 -scope);
    for (String name : curblock){
      if (name.equals(string)){
        return true;
      }
    }
    return false;
  }
  
  public void addVar(String string){
    varStack.peek().add(string);
}
}
  
