package client.Team;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import Game.*;

class BreathFirstSearch {
    private Cell[][] map;
    BreathFirstSearch(Cell[][] map, Game game) {
        this.map = map;
    }
    ArrayList<Cell> BFS(Cell source, Cell target){
        ArrayList<Cell> way = new ArrayList<>();
        Queue<Cell> cellQueue = new LinkedBlockingQueue<>();
        Map<Cell , Cell> comefrom = new HashMap<>();
        Set<Cell> checked = new HashSet<>();
        cellQueue.add(source);
        do{
            Cell work = cellQueue.poll();
            if (work == null)
                break;
            Cell neighbor;
            try{
                neighbor = map[work.getRow() -1][work.getCol()];
                if ((!neighbor.hasWall() || work.hasWall() || work.hasLadder()) && (!checked.contains(neighbor))){
                    checked.add(neighbor);
                    cellQueue.add(neighbor);
                    comefrom.put(neighbor , work);
                }
            }catch (Exception ignored){}
            try{
                neighbor = map[work.getRow() +1][work.getCol()];
                if ((!neighbor.hasWall() || work.hasWall() || work.hasLadder()) && (!checked.contains(neighbor))){
                    checked.add(neighbor);
                    cellQueue.add(neighbor);
                    comefrom.put(neighbor , work);
                }
            }catch (Exception ignored){}
            try{
                neighbor = map[work.getRow()][work.getCol() +1];
                if ((!neighbor.hasWall() || work.hasWall() || work.hasLadder()) && (!checked.contains(neighbor))){
                    checked.add(neighbor);
                    cellQueue.add(neighbor);
                    comefrom.put(neighbor , work);
                }
            }catch (Exception ignored){}
            try{
                neighbor = map[work.getRow()][work.getCol() -1];
                if ((!neighbor.hasWall() || work.hasWall() || work.hasLadder()) && (!checked.contains(neighbor))){
                    checked.add(neighbor);
                    cellQueue.add(neighbor);
                    comefrom.put(neighbor , work);
                }
            }catch (Exception ignored){}
        }while(!cellQueue.isEmpty() || !checked.contains(target));
        Cell cell1 = target;
        way.add(target);
        Cell cell2;
        do{
            cell2 = comefrom.get(cell1);
            if (cell2 == null) {
                way.clear();
                way.add(source);
                way.add(source);
                way.add(source);
                break;
            }
            way.add(cell2);
            cell1 = cell2;
        }while(!way.contains(source));
        way.remove(way.size() - 1);
        return way;
    }
}
