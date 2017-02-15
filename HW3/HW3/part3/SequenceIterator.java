public class SequenceIterator extends Sequence{
  public boolean dummy = false;
  
  public SequenceIterator(Element ele, Sequence se, int siz, boolean dum){
    super(ele,se,siz);
    dummy = dum;
  }
  
  public SequenceIterator advance(){
    if (dummy != true){
      if (super.length() > 1){
        super.subSeq = super.subSeq.subSeq;
        super.element = super.subSeq.element;
        super.minusOne();
      }
      else{
        dummy = true;
      }
    }
  }
  
  public Element get(){
    return super.first();
  }
  
  public boolean equal(SequenceIterator other){
    if (dummy != other.dummy)
      return false;
    if (super.element == other.element && super.subSeq == other.subSeq && super.length() == other.length())
      return true;
    return false;
  }
}
