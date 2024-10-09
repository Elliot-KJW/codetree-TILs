import java.util.*;
import java.io.*;
import java.awt.Point;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int row, col, k;
        row = Integer.parseInt(st.nextToken()) + 2;
        col = Integer.parseInt(st.nextToken()) + 1;
        k = Integer.parseInt(st.nextToken());

        int[][] map = new int[row][col];
        int answer = 0;

        while(k-- > 0) {
            int c, d;
            st = new StringTokenizer(br.readLine());
            c = Integer.parseInt(st.nextToken());
            d = Integer.parseInt(st.nextToken());

            Golem golem = new Golem(c, d);
            golem.go(map);
            for(int i : map[1]) {
                if(i > 0) {
                    map = new int[row][col];
                    golem.reset();
                }
            }
            answer += golem.getPosition().x;
            /*for(int i = 2; i < map.length; i++) {
                for(int j = 1; j < map[0].length; j++) {
                    System.out.print(map[i][j]);
                }
                System.out.println();
            }
            System.out.println(golem.getPosition().x);
            System.out.println();*/
        }

        System.out.println(answer);
    }

    static class Golem{
        int r;
        int c;
        int[][] d = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        int di;

        Golem(int c, int d){
            r = 1;
            this.c = c;
            this.di = d;
        }

        Point getPosition() {
            if(r > 2) {
                return new Point(this.r - 1, this.c);  
            } else {
                return new Point(0, 0); 
            }
        }

        void go(int[][] map) {
            while(true) {
                if(r+2 < map.length && map[r+2][c] == 0 && map[r+1][c+1] == 0 && map[r+1][c-1] == 0) {
                    r++;
                } else if(r+2 < map.length && c-2 > 0 && map[r-1][c-1] == 0 && map[r][c-2] == 0 && map[r+1][c-1] == 0 && map[r+2][c-1] == 0 && map[r+1][c-2] == 0){
                    r++;
                    c--;
                    di = (di+3) % 4;
                } else if(r+2 < map.length && c+2 < map[0].length && map[r-1][c+1] == 0 && map[r][c+2] == 0 && map[r+1][c+1] == 0 && map[r+2][c+1] == 0 && map[r+1][c+2] == 0){
                    r++;
                    c++;
                    di = (di+1) % 4;
                } else {
                    map[r+1][c] = 1;
                    map[r][c] = 1;
                    map[r][c+1] = 1;
                    map[r][c-1] = 1;
                    map[r-1][c] = 1;
                    map[r+d[di][0]][c+d[di][1]]++;
                    break;
                }
            }

            boolean[][] visited = new boolean[map.length][map[0].length];
            visited[r][c] = true;
            Queue<int[]> queue = new LinkedList<>();
            queue.offer(new int[]{r, c, 0});
            while(!queue.isEmpty()) {
                if(r == map.length - 1) {
                    break;
                }
                int[] cur = queue.poll();

                for(int i = 0; i < 4; i++) {
                    int cr = cur[0];
                    int cc = cur[1];
                    int nr = cr + d[i][0];
                    int nc = cc + d[i][1];
                    int move = cur[2];

                    if(nr > 0 && nr < map.length && nc > 0 && nc < map[0].length) {
                        if(!visited[nr][nc] && map[nr][nc] > 0) {
                            if((move > 0 && map[cr][cc] == 2) || move <= 0) {
                                visited[nr][nc] = true;
                                move = map[cr][cc] == 2 ? -1 : move+1;
                                queue.offer(new int[]{nr, nc, move});
                                //System.out.println(nr + " " + nc + " " + move);
                                r = Math.max(r, nr);
                            }
                        }
                    }
                }
            } 
        }

        void reset() {
            r = 0;
            c = 0;
        }
    }
}