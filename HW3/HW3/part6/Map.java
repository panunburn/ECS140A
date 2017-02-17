class Map extends Element{
  public Pair cur;
  public int size;
  public MapIterator begin(){
    return new MapIterator(this.cur,false);
  }
  
  public MapIterator end(){
    return new MapIterator(this.cur, true);
  }
  
  public void addOne(){
    size++;
  }
  
  public void minusOne(){
    size--;
  }
  
  public Map(){
    size = 0;
  }
  
  public void setSize(int value){
    size = value;
  }

  public void add(Pair inval){
    if (size == 0){
      cur = new Pair(inval.name, inval.element, inval.next);
      addOne();
      return;
    }
    Pair prev = null;
    Pair temp;
    for (temp = this.cur; temp != null; temp = temp.next){
      if (inval.name.Get()>=temp.name.Get()){//not this pos
        prev = temp;
        continue;
      }
      else{//find pos
        
        if (prev == null){
          temp.next = new Pair(temp.name, temp.element, temp.next);
          temp.name = inval.name;
          temp.element = inval.element;
        }
        else
          prev.next = new Pair(inval.name, inval.element, temp);
        break;
      }
    }
    if (temp == null){//add to the last
      prev.next = new Pair(inval.name, inval.element, temp);
    }
    addOne();
  }

  public void Print(){
    System.out.print("[ ");
    Pair temp = cur;
    while (temp != null){
      temp.Print();
      System.out.print(" ");
      temp = temp.next;
    }
    System.out.print("]");
  }
  
  public MapIterator find(MyChar key) {
    for (MapIterator t = begin(); !t.equal(end()); t.advance()){
      if (t.get().name.Get() == key.Get())
        return t;
    }
    return end();
  }
}
