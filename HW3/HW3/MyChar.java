public class MyChar extends Element{
  char mychar;
  public MyChar(){
    mychar = '0';
  }
  public char Get(){
    return mychar;
  }
  public void Set(char val){
    mychar = val;
  }
  public void Print(){
    System.out.print("'"+mychar+"'");
  }
}
