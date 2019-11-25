package Tournoi;

import java.util.ArrayList;
import java.util.Stack;

class Ia_simple_without_deacrese extends IA{

	public Table table;
	public Tools tools;

	Ia_simple_without_deacrese() {
    	
        tools = new Tools();
        ArrayList<Part> parts = new ArrayList<Part>();
        
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                Part part = new Part(i,j);
                parts.add(part);
            }
        }
        
        
        table = new Table(parts);
        
    }
    
    public void reset(){
    	
        tools = new Tools();
        ArrayList<Part> parts = new ArrayList<Part>();
        
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                Part part = new Part(i,j);
                parts.add(part);
            }
        }
        
        
        table = new Table(parts);
    	
    }


    public int[] play(Stack<Integer> entre) {
    
        int opponentRow = entre.pop();
        int opponentCol = entre.pop();
        //System.err.println(opponentRow+" : "+opponentCol);
            
        table.getPart(opponentRow/3,opponentCol/3).getCase(opponentRow,opponentCol).setpos(-1);
        //tools.deacreseQuality(opponentRow,opponentCol,table);
            
        int validActionCount = entre.pop();
            
        //System.err.println(validActionCount);
        int x_part=-1;
        int y_part=-1;
            
        for (int i = 0; i < validActionCount; i++) {
            int row = entre.pop();
            int col = entre.pop();
            x_part=row/3;
            y_part=col/3;
        }
            
            
        Part part = table.getPart(x_part,y_part);
            
        int[] uti = tools.untilitiPos(part);
        int x = uti[0];
        int y = uti[1];
            
        //System.err.println("x : "+x);
        //System.err.println("y : "+y);
            
        if(opponentRow == -1){
        	x=4;
            y=4;
        }
            
        if(x==-1){
            ArrayList<Case> poss = part.getposs();
            //System.err.println("taille :"+poss.size());
            poss = tools.getpossPos(poss);
            //System.err.println("taille :"+poss.size());
            poss = tools.getpossBestQuality(poss);
            //System.err.println("taille :"+poss.size());
            Case posAlea = tools.getposRand(poss);
           
            //System.err.println(posAlea.getquality());
                
            x = posAlea.getx();
            y = posAlea.gety();
        }
            
            
        table.getPart(x/3,y/3).getCase(x,y).setpos(1);
  //      tools.increseQuality(opponentRow,opponentCol,table);
        int[] pos_final = {x,y};
        return pos_final;
        //System.out.println(x+" "+y);
    }
}
