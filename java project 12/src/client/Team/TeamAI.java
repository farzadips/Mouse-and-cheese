package client.Team;

import Game.*;

import java.util.*;

public class TeamAI extends client.AI {
    ArrayList<Cell> poison = new ArrayList<>();
    int emtiaz = 0;
    int opsemtiaz = 0;
    ArrayList<Cell> blocks = new ArrayList<Cell>();

    @Override
    public String getTeamName() {
        return "Simple Rat";
    }

    @Override
    public void think(Game game) {

        Cell[][] map = game.getMap();
        BreathFirstSearch bfs = new BreathFirstSearch(map, game);
        Rat myRat = game.getMyRat();
        Rat opsRat = game.getOppRat();
        ArrayList<Cell> getcheese = new ArrayList<>();
        for (int x = 0; x < game.getNumberOfRows(); x++) {
            for (int y = 0; y < game.getNumberOfColumns(); y++) {
                if (map[x][y].hasCheese()) {
                    getcheese.add(map[x][y]);

                }
            }
        }
        Eat2(opsRat, game);
        Cell cc = myRat.getCell();
        Cell dc = null;
        double i = 10000, j;
        for (Cell c : getcheese) {
            j = manhattan(cc, c);
            if (j < i) {
                List<Cell> list = bfs.BFS(c , cc);
                if (list.get(0) == c && list.size() == 2) {
                    blocks.add(c);
                } else {
                    if (!poison.contains(c)) {
                        dc = c;
                        i = j;
                    }
                }
            }
        }
        if (i == 10000) {
            for (Cell c : blocks) {
                j = manhattan(cc, c);
                if (j < i) {
                    if (!poison.contains(c)) {
                        dc = c;
                        i = j;
                    }
                }
            }
        }
        if (manhattan(cc, dc) == 0) {
            if (!poison.contains(myRat.getCell())) {
                Eat(myRat, game);
            }
        } else {
            ArrayList<Cell> way = bfs.BFS(cc, dc);
            Cell next_cell = way.get(way.size() - 1);
            int row = next_cell.getRow() - cc.getRow(), col = next_cell.getCol() - cc.getCol();
            if (row == 1)
                myRat.moveDown();
            if (row == -1)
                myRat.moveUp();
            if (col == 1)
                myRat.moveRight();
            if (col == -1)
                myRat.moveLeft();
            emtiaz = game.getMyScore();
            opsemtiaz = game.getOppScore();
        }
    }

    private double manhattan(Cell cell1, Cell cell2) {
        double a = cell1.getCol() - cell2.getCol();
        double b = cell1.getRow() - cell2.getRow();
        return Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
    }

    public void Eat(Rat myRat, Game game) {
        myRat.eat();
        if (emtiaz > game.getMyScore()) {
            poison.add(myRat.getCell());
            think(game);

        }
    }

    public void Eat2(Rat opsRat, Game game) {
        if (opsemtiaz != game.getOppScore()) {
            poison.add(opsRat.getCell());
        }
    }

}