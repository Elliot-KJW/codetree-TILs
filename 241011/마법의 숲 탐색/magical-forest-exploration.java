import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int r, c, k;
        r = Integer.parseInt(st.nextToken()) + 2;
        c = Integer.parseInt(st.nextToken()) + 1;
        k = Integer.parseInt(st.nextToken());

        int[][] map = new int[r][c];
        int answer = 0;

        while(k-- > 0) {
            int ci, di;
            st = new StringTokenizer(br.readLine());
            ci = Integer.parseInt(st.nextToken());
            di = Integer.parseInt(st.nextToken());

            Golem golem = new Golem(ci, di);
            golem.go(map);
            answer += golem.getRow();
        }

        System.out.println(answer);
    }

    static class Golem{
        int r;
        int c;
        int[][] d = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        int di;

        Golem(int ci, int di){
            r = 0;
            c = ci;
            this.di = di;
        }

        int getRow() {
            return r;
        }

        void go(int[][] map) {
            while(true) {
                if(r+2 < map.length && map[r+2][c] == 0 && map[r+1][c+1] == 0 && map[r+1][c-1] == 0) {
                    r++;
                } else {
                    map[r][c] = 2;
                    map[r+1][c] = 1;
                    map[r-1][c] = 1;
                    map[r][c+1] = 1;
                    map[r][c-1] = 1; 
                    break;
                }
            } 

            printMap(map);
        }

        void printMap(int[][] map) {
            for(int i = 2; i < map.length; i++) {
                for(int j = 1; j < map[0].length; j++) {
                    System.out.print(map[i][j]);
                }
                System.out.println();
            }
            System.out.println();
        }
    }
}