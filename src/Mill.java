public class Mill {

  private int cell1;

  private int cell2;

  private int cell3;
  
  private String id;

  public Mill(){
	  this.id = null;
	  this.cell1 = 0;
	  this.cell2 = 0;
	  this.cell3 = 0;
  }
  
  public Mill(String id, int c1, int c2, int c3){
	  this();
	  this.id = id;
	  this.cell1 = c1;
	  this.cell2 = c2;
	  this.cell3 = c3;
  }
  
  public String getID(){
	  return this.id;
  }
  
  public int getCell1(){
	  return this.cell1;
  }
  
  public int getCell2(){
	  return this.cell2;
  }

  public int getCell3(){
	  return this.cell3;
  }

}