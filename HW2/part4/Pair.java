import java.util.*;


public class Pair{
  private String name = new String(); //
  private int occur = 0; //
  
  public Pair(String string, int num) {
    this.name = string;
    this.occur = num;
  }
  
  public void addOne() {
    occur++;
  }
  public void minusOne(){
    occur--;
  }
  
  public void setFirst(String string) {
    this.name = string;
  }
  
  public void setSecond(int num) {
    this.occur = num;
  }
  
  public String getFirst() {
    return name;
  }
  
  public int getSecond() {
    return occur;
  }
}
