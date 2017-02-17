
public class Pair{
  public MyChar name; //
  public Element element; //
  public Pair next;
  
  public Pair(){
  }
  
  public Pair(MyChar c, Element ele){
    name = c;
    element = ele;
    next = null;
  }
  public Pair(MyChar c, Element ele, Pair next) {
    name = c;
    element = ele;
    this.next = next;
  }
  
  public void Print(){
    System.out.print("(");
    name.Print();
    System.out.print(" ");
    element.Print();
    System.out.print(")");
  }
}
