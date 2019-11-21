package Tournoi;
import java.util.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Ia_simple extends IA{

	public Table table;
	public Tools tools;

    Ia_simple() {
    	
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
        tools.deacreseQuality(opponentRow,opponentCol,table);
            
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

class Tools{
    int[][][] allignes = {{{0,0},{0,1},{0,2}},{{1,0},{1,1},{1,2}},{{2,0},{2,1},{2,2}},{{0,0},{1,0},{2,0}},{{0,1},{1,1},{2,1}},{{0,2},{1,2},{2,2}},{{0,0},{1,1},{2,2}},{{0,2},{1,1},{2,0}}};
    
    
    Case getposRand(ArrayList<Case> poss) {
        int x = new Random().nextInt(poss.size());
        return poss.get(x);
    }
    
    ArrayList<Case> getpossBestQuality(ArrayList<Case> poss) {
        ArrayList<Case> newPoss = new ArrayList<Case>();
        int quality = -100000;
        for(Case pos : poss){
            if (quality == pos.getquality()) {
                newPoss.add(pos);
            }
            else if ( quality < pos.getquality()) {
                newPoss.clear();
                newPoss.add(pos);
                quality = pos.getquality();
            }
            //System.err.println("quality: "+quality+" pos.getquality(): "+pos.getquality());
        }
        return newPoss;
    }
    
    ArrayList<Case> getpossPos(ArrayList<Case> poss){
        ArrayList<Case> newPoss = new ArrayList<Case>();
        for(Case pos : poss){
        	//System.err.println("x:"+pos.getx()+" y:"+pos.gety()+" p:"+pos.getpos());
            if (pos.getpos() == 0) {
                newPoss.add(pos);
            }
        }
        return newPoss;
    }
    
    int[] untilitiPos(Part part){
        int x_part = part.getx();
        int y_part = part.gety();
        int[] save = {-1,-1};
        for(int[][] alligne : allignes){
            int compt=0;
            for(int[] pos: alligne){
                compt+=part.getCase(x_part*3+pos[0],y_part*3+pos[1]).getpos();
                //System.err.println("     compt : "+part.getCase(x_part+pos[0],y_part+pos[1]).getpos()+" pos : ("+(x_part*3+pos[0])+","+pos[1]+")");
            }
            //System.err.println("compt : "+compt+" pos : ("+alligne[0][0]+","+alligne[0][1]+")");
            if(compt==2){
                return recup(alligne, part);
            }
            else if(compt==-2){
                save = recup(alligne, part);
            }
        }
        return save;
    }
    
    
    int[] recup(int[][] alligne, Part part){
        for(int[] pos: alligne){
            if(part.getCase(part.getx()*3+pos[0],part.gety()*3+pos[1]).getpos()==0){
                int[] sol = {part.getx()*3+pos[0],part.gety()*3+pos[1]};
                return sol;
            }
        }
        int[] save = {-1,-1};
        return save;
    }
    
    void deacreseQuality(int x,int y,Table table){
        for(Part part: table.getParts()){
            int x_mod = x/3+part.getx()*3;
            int y_mod = y/3+part.gety()*3;
            part.getCase(x_mod,y_mod).degradQuality();
        }
    }
    
    void increseQuality(int x,int y,Table table){
        for(Part part: table.getParts()){
            part.getCase(x+part.getx(),y+part.gety()).upgradQuality();
        }
    }
        
}



class Table{
    
    ArrayList<Part> parts;
    
    Table(ArrayList<Part> parts){
        this.parts = parts;
    }
    
    ArrayList<Part> getParts(){
        return parts;
    }
    
    Part getPart(int x, int y){
        for(Part part: parts){
            if(x==part.getx()&&y==part.gety()){
                return part;
            }
        }
        return new Part(-1,-1);
    }
    
    
}


class Part {
    
    int x ;
    int y ;
    ArrayList<Case> poss;
    
    Part(int x, int y) {
        this.x = x;
        this.y = y;
        
        poss = new ArrayList<Case>();
        
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                Case pos = new Case(3*x+i,3*y+j);
                poss.add(pos);
            }
        }
    }
    
    ArrayList<Case> getposs() {
        return poss;
    }
    
    Case getCase(int x,int y){
        for(Case pos: poss){
            if(x==pos.getx()&&y==pos.gety()){
                return pos;
            }
        }
        return new Case(-1,-1);
    }
    
    int getx() {
        return x;
    }
    
    int gety() {
        return y;
    }
    

    
}




class Case {
    int x;
    int y;
    
    int quality;
    // 0 cote 1 coin 2 centre
    
    int pos;
    // 0 personne 1 moi -1 toi
    
    
    
    
    Case(int x,int y) {
        this.x = x;
        this.y = y;
        quality = quality(x,y);
        pos = 0;
    }
    
    int getx() {
        return x;
    }
    
    int gety() {
        return y;
    }
    
    int getquality() {
        return quality;
    }
    
    int getpos() {
        return pos;
    }
    
    void setpos(int pos){
        this.pos = pos;
    }
    
    void degradQuality(){
        quality -=1;
    }
    
    void upgradQuality(){
        quality +=1;
    }
    
    int quality(int x,int y){
        if (x%3==1 && y%3==1){
            return 10;
        }
        if (x%3==1 || y%3==1){
            return 0;
        }
        return 20;
    }
}
