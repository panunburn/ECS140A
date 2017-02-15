class Matrix extends Sequence {
// constructor for creating a matrix of specific number of rows and columns
  private Sequence MyMatrix;
  private int r;
  private int c;
  public int getr() { return r;}
  public int getc() { return c;}
  
  public Matrix(int rowsize, int colsize){
    r = rowsize;
    c = colsize;
    Sequence matrix = new Sequence();
    for (int i = 0; i < rowsize; i++){
      Sequence row = new Sequence();
      for (int j = 0; j < colsize; j++){
        MyInteger col = new MyInteger();
        col.Set(0);
        row.add(col, j);
      }
      matrix.add(row, i);
    }
    MyMatrix = matrix;
  }
  
  public void Set(int rowsize, int colsize, int value) // set the value of an element
  {
    Sequence row = MyMatrix;
    for (int i = 0; i < rowsize; i++){
      row = row.subSeq;
    }
    Sequence col = (Sequence)row.element;
    for (int j = 0; j < colsize; j++){
      col = col.subSeq;
    }
    ((MyInteger)col.element).Set(value);
  }
  
  public int Get(int rowsize, int colsize) // get the value of an element
  {
    Sequence row = MyMatrix;
    for (int i = 0; i < rowsize; i++){
      row = row.subSeq;
    }
    Sequence col = (Sequence)row.element;
    for (int j = 0; j < colsize; j++){
      col = col.subSeq;
    }
    return ((MyInteger)col.element).Get();
  }
  public Matrix Sum(Matrix mat) // return the sum of two matrices: mat & this
  {
    if (r != mat.getr() || c != mat.getc()){
      System.err.println("Matrix not same size");
      System.exit(1);
    }
    Matrix comb = new Matrix(r,c);
    for (int i = 0; i < r; i++){
      for (int j = 0; j < c; j++){
        comb.Set(i,j,this.Get(i,j)+ mat.Get(i,j));
      }
    }
    return comb;
  }
  public Matrix Product(Matrix mat) // return the product of two matrices: mat & this
  {
    Matrix mul = new Matrix(r,mat.getc());
    if (c != mat.getr()){
      System.out.println("Matrix dimensions incompatible for Product");
      System.exit(1);
    }
    
      for (int i = 0; i < r; i++){
        for (int j = 0; j < mat.getc(); j++){
          int thisEle = 0;
          for (int k = 0; k < c; k++)
            thisEle += this.Get(i,k)*mat.Get(k,j);
          mul.Set(i,j, thisEle);
        }
      }
    return mul;
  }
  
  public void Print()  // print the elements of matrix
  {
    for (Sequence temp = MyMatrix; temp != null; temp = temp.subSeq){
      temp.element.Print();
      System.out.println();
    }
  }
}
