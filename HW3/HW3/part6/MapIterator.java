class MapIterator extends Map {
  public boolean dummy;
  public Pair ref;
  
  public MapIterator(Pair addr, boolean dum){
      //System.out.println("creating");
    dummy = dum;
    Pair fake = new Pair(addr.name, addr.element, addr.next);
    ref = fake;
    //if (ref == null)
      //System.out.println("wrong");
  }

  public MapIterator advance(){
    if (dummy != true){
      ref = ref.next;
      if (ref == null)
        dummy = true;
    }
    return this;
  }
  
  public Pair get(){
    return ref;
  }
  
  public boolean equal(MapIterator other){
    if (dummy != other.dummy)
      return false;
    
    if (ref == null)
    {
      if (other.dummy = true)
        return true;
    }
    
    if (ref.name.Get() == other.ref.name.Get() && ref.next == other.ref.next)
      return true;
    if (dummy == true && other.dummy == true){
      return true;
    }
    return false;
  }

  public void Print(){
    ref.Print();
  }
}
