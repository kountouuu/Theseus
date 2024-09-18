import java.io.*;
import java.util.Scanner;

class Node{
    private int x,y;
    private int direction;
    private int value;

    public Node(int i , int j, int value){
        this.x = i;
        this.y = j;
        this.direction = 0; // default value is up = 0 , 1 = left , 2 = down , 3 = right
        this.value = value;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(char y) {
        this.y = y;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
public class Thiseas {
    static boolean[][] visited;
    public static void main(String[] args) throws IOException {

        BufferedReader fileInput = new BufferedReader(new FileReader(args[0]));
        String s;
        int N = 0,M = 0;
        int entranceX = 0,entranceY = 0;
        int i = 0;
        String[] stArray = new String[20];

        while ((s = fileInput.readLine()) != null) {

            for (char c : s.toCharArray()) {

                if(i == 0){
                    String[] integers = s.split(" ");
                    N = Integer.valueOf(integers[0]);
                    M = Integer.valueOf(integers[1]);
                } else if (i == 1){
                    String[] ints = s.split(" ");
                    entranceX = Integer.valueOf(ints[0]);
                    entranceY = Integer.valueOf(ints[1]);
                }
            }
            if(i>1){
                stArray[i] = s;
            }
            i++;
        }
        fileInput.close();
        String ss = "";
        int ln = 0;
        for (String n:stArray) {
            if(!(n == null)) {
                if(!(n.equals(" "))){
                    ss += n;
                    ln++;
                    String[] aa = n.split(" ");
                    // if array is other size than that declared in file
                    if (aa.length != M){
                        System.out.println("Array is of other size than declared .");
                        System.exit(0);
                    }
                }

            }
        }
        if(ln != N){ // if array is other size than that declared in file
            System.out.println("Array is of other size than that declared");
            System.exit(0);
        }
        char[] c = ss.toCharArray();
        int n = N;
        int m = M;
        char[][] thiseasArray = new char[n][m];
        int gridCounter = 0,lineCounter = 0 ;

        for ( char cc : c ) {
            if (cc == ' '){
                continue;
            } else {
                thiseasArray[lineCounter][gridCounter] = cc;

                gridCounter++;
                if(gridCounter == m){
                    gridCounter = gridCounter % m;
                    lineCounter++;
                }
            }
        }


        visited = new boolean[n][m];
        setVisited(true,visited);
        int[][] thisArr = new int[n][m];
        int eCounter = 0;
        for(int u = 0; u < n; u++){
            for(int t = 0; t < m; t++){
                if (thiseasArray[u][t] == 'E'){
                    thisArr[u][t] = 2; // E is 2 in intarray
                    eCounter++;
                } else {
                    int tempp = Integer.parseInt(String.valueOf(thiseasArray[u][t]));
                    thisArr[u][t] = tempp;
                }
            }
        }
        if (eCounter > 1){
            System.out.println("More entrances than needed . Entrance should be 1 only , not " + eCounter);
            System.exit(0);
        }

        if(isReachable(thisArr,entranceX,entranceY,n,m)){
            System.out.println("Path Found!\n");
        }else{
            System.out.println("No Path was found !\n");
        }
    }

    private static void setVisited(boolean b,boolean[][] visited){
        for (int i = 0; i < visited.length; i++)
        {
            for (int j = 0; j < visited[i].length; j++)
            {
                visited[i][j] = b;
            }
        }
    }

    private static boolean isReachable(int maze[][],int x_Entrance,int y_Entrance,int endX,int endY) {
        int x = x_Entrance;
        int y = y_Entrance;

        StringStackImpl<Node> stack = new StringStackImpl<Node>();

        Node temp = new Node(x, y,maze[x][y]);

        stack.push(temp);
        boolean check = false;
        while (!stack.isEmpty()) {
            temp = stack.peek();
            int d = temp.getDirection();

            x = temp.getX();
            y = temp.getY();


            temp.setDirection(d + 1);

            stack.pop();
            stack.push(temp);
            int mazeLength = (char) endX - 1;
            int mazeWidth = (char) endY - 1;


            // αν είναι τελική κατάσταση
            if ((x == mazeLength && temp.getValue() == 0) || (x == 0 && temp.getValue() == 0) ||
                    (y == mazeWidth && temp.getValue() == 0) || (y == 0 && temp.getValue() == 0)){
                System.out.println("Exit was found on coordinates : (" + temp.getX() + " , " + temp.getY() + ")");
                return true;
            }

            // αν η κατεύθηνση που θα πάει είναι πάνω
            if (d == 0){
                // αν σε αυτή την κατεύθηνση είναι τελικός κόμβος
                if(x == 0 && maze[x][y] == 0){
                    System.out.println("Exit was found on coordinates : (" + temp.getX() + " , " + temp.getY() + ")");
                    return true;
                }
                // έλεγχος για παραβιάσεις των διαστάσεων του πίνακα και αν είναι μέρος του μονοπατιού
                if(x - 1 >= 0 && maze[x - 1][y] == 0 && visited[x - 1][y]){
                    Node temp1 = new Node(x - 1, y,maze[x - 1][y]);
                    visited[x - 1][y] = false;
                    stack.push(temp1);
                }
            }
            // αριστερά
            else if (d == 1) {
                // αν σε αυτή την κατεύθηνση είναι τελικός κόμβος
                if(y == 0 && maze[x][y] == 0){
                    System.out.println("Exit was found on coordinates : (" + temp.getX() + " , " + temp.getY() + ")");
                    return true;
                }
                // έλεγχος για παραβιάσεις των διαστάσεων του πίνακα και αν είναι μέρος του μονοπατιού
                if(y - 1 >= 0 && maze[x][y-1] == 0 && visited[x][y-1]){
                    Node temp1 = new Node(x, y - 1 , maze[x][y-1]);
                    visited[x][y-1] = false;
                    stack.push(temp1);
                }
            }
            // κάτω
            else if (d == 2){
                // αν σε αυτή την κατεύθηνση είναι τελικός κόμβος
                if(x == mazeLength && maze[x][y] == 0){
                    System.out.println("Exit was found on coordinates : (" + temp.getX() + " , " + temp.getY() + ")");
                    return true;
                }
                // έλεγχος για παραβιάσεις των διαστάσεων του πίνακα και αν είναι μέρος του μονοπατιού
                if(x + 1 <= mazeLength && maze[x+1][y] == 0 && visited[x+1][y]){
                    Node temp1 = new Node(x+1,y,maze[x+1][y]);
                    visited[x+1][y] = false;
                    stack.push(temp1);
                }
            }
            // δεξιά
            else if (d == 3){
                // αν σε αυτή την κατεύθηνση είναι τελικός κόμβος
                if(y  == mazeWidth  && maze[x][y] == 0){
                    System.out.println("Exit was found on coordinates : (" + temp.getX() + " , " + temp.getY() + ")");
                    return true;
                }
                // έλεγχος για παραβιάσεις των διαστάσεων του πίνακα και αν είναι μέρος του μονοπατιού
                if (y+1 <= mazeWidth && maze[x][y+1] == 0 && visited[x][y+1]){
                    Node temp1 = new Node(x,y+1,maze[x][y+1]);
                    visited[x][y+1] = false;
                    stack.push(temp1);
                }
            }
            else{
                visited[temp.getX()][temp.getY()] = true;
                stack.pop();
            }
        }
        return false;
    }
}


