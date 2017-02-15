public class SequenceIterator extends Sequence{
  public boolean dummy;
  Sequence cpy;
  
  public SequenceIterator(Sequence ref, boolean dum){
    dummy = dum;
    cpy = ref.copy();
  }
  
  public SequenceIterator advance(){
    if (dummy != true){
      if (cpy.length() > 1){
        cpy.element = cpy.subSeq.element;
        cpy.subSeq = cpy.subSeq.subSeq;
        cpy.minusOne();
      }
      else{
        dummy = true;
      }
    }
    return this;
  }
  
  public Element get(){
    return cpy.first();
  }
  
  public boolean equal(SequenceIterator other){
    if (dummy != other.dummy)
      return false;
    
    if (cpy.element == other.cpy.element && cpy.subSeq == other.cpy.subSeq && cpy.length() == other.cpy.length())
      return true;
    if (dummy == true && other.dummy == true){
      return true;
    }
    return false;
  }
}
