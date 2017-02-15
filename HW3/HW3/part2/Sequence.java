public class Sequence extends Element{
  public Element element;
  public Sequence subSeq;
  private int size;
  
  public Sequence(){
    size = 0;
  }
  
  public Sequence(int siz){
    size = siz;
  }
  
  public void addOne(){
    size++;
  }
  
  public void minusOne(){
    size--;
  }
  
  public Element first(){
    return element;
  }
  
  public Sequence rest(){
    return subSeq;
  }
  
  public int length(){
    return size;
  }
  
  public void add(Element elm, int pos){
    if (pos > size){
      System.err.println("invalid pos");
      System.exit(1);
    }
    Sequence temp = this;
    if (size == 0){
      element = elm;
      size = 1;
    }
    else{
      if (pos == 0){
        Sequence newSeq = new Sequence(temp.length());
        newSeq.element = element;
        newSeq.subSeq = subSeq;
        subSeq = newSeq;
        addOne();
        element = elm;
      }
      else{
        for (int i = 0; i < pos-1; i++){
          temp.addOne();
          temp = temp.subSeq;
        }
        temp.addOne();
        
        Sequence newSeq = new Sequence(temp.length()-1);
        newSeq.element = elm;
        newSeq.subSeq = temp.subSeq;
        temp.subSeq = newSeq;
      }
    }
  }
  
  public void delete(int pos){
    Sequence temp = this;
    if (pos >= size){
      return;
    }
    else{
      for (int i = 0; i < pos - 1; i++){
        temp.minusOne();
        temp = temp.subSeq;
      }
      if (pos == 0)
        element = subSeq.element;
      temp.minusOne();
      temp.subSeq = temp.subSeq.subSeq;
    }
  }
  
  public void Print(){
    System.out.print("[ ");
    Sequence temp = this;
    while(temp != null){
      temp.element.Print();
      System.out.print(" ");
      temp = temp.subSeq;
    }
    System.out.print("]");
  }
}
