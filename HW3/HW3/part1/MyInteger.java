public class MyInteger extends Element{
  private int myint;
  public MyInteger(){
    myint = 0;
  }
  public int Get(){
    return myint;
  }
  public void Set(int val){
    myint = val;
  }
  public void print(){
    system.out.print(myint);
  }
}
