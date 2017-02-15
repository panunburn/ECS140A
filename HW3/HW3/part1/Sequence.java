

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
      system.error.println("invalid pos");
      system.exit(1);
    }
    Sequence temp = this;
    if (size == 0){
      element = elm;
      size = 1;
    }
    else{
      for (int i = 0; i < pos - 1; i++){
        temp.addOne();
        temp = temp.subSeq;
      }
      temp.addone();
      
      Sequence newSeq = new Sequence(temp.length()-1);
      newSeq.element = elm;
      newSeq.subSeq = temp.subSeq;
      temp.subSeq = newSeq;
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
      temp.minusOne();
      temp.subSeq = temp.subSeq.subSeq;
    }
  }
  
  public void print(){
    system.out.print("[ "+element);
    if (size >= 1)
      subSeq.print();
    else
      system.out.print("] ");
  }
}
