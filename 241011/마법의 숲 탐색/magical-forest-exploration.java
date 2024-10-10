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
            for(int i : map[0]) {
                if(i > 0) {
                    map = new int[r][c];
                    golem.reset();
                }
            }
            answer += golem.getRow();
            //golem.printMap(map);
            //System.out.println(golem.getRow() + "\n\n");
        }

        System.out.println(answer);
    }

    static class Golem{
        int r;
        int c;
        int[][] d = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        int di;

        Golem(int ci, int di){
            r = 0;
            c = ci;
            this.di = di;
        }

        int getRow() {
            return r > 1 ? r - 1 : 0;
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
                }  else {
                    map[r][c] = 2;
                    map[r+1][c] = 1;
                    map[r-1][c] = 1;
                    map[r][c+1] = 1;
                    map[r][c-1] = 1;
                    map[r+d[di][0]][c+d[di][1]]+=2;
                    break;
                }
            }

            Queue<int[]> queue = new LinkedList<>();
            queue.offer(new int[]{r, c});
            boolean[][] visited = new boolean[map.length][map[0].length];
            visited[r][c] = true;

            while(!queue.isEmpty()) {
                int[] cur = queue.poll();

                for(int i = 0; i < d.length; i++) {
                    int cr = cur[0];
                    int cc = cur[1];
                    int nr = cr + d[i][0];
                    int nc = cc + d[i][1];

                    if(nr > 0 && nc > 0 && nr < map.length && nc < map[0].length && !visited[nr][nc]) {
                        if((map[cr][cc] == 2 && map[cr][cc] > 0) || (map[cr][cc] == 3 && map[nr][nc] == 1) 
                        || (map[cr][cc] == 1 && map[nr][nc] == 2)) {
                            queue.offer(new int[]{nr, nc});
                            visited[nr][nc] = true;

                            r = Math.max(r, nr);
                        }
                    }
                }
            }
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

        void reset() {
            r = 0;
            c = 0;
            di = 0;
        }
    }
}