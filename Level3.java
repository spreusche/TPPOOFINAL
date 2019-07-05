package game.backend.level;

import game.backend.GameState;
import game.backend.element.Fruit;
import game.backend.element.Fruits;
import game.backend.element.Nothing;

import java.util.ArrayList;
import java.util.List;



public class Level3 extends Level1{

    private List<Fruit> fruits = new ArrayList<>();
    private Level3State gstate;
    private int MAX_MOVES = 20;

    private static int getRand(int min, int max) {
        return (int) (min + (Math.random() * (max - min)));
    }

    @Override
    public void initialize(){ //inicia el grid con las frutas.
        super.initialize();
        int fruitQty = getRand(1,6);
        for (int j = 0; j <= fruitQty; j++) {
            fruits.add(new Fruit(Fruits.values()[getRand(0, Fruits.values().length) ]));
        }
        for (Fruit f : fruits) {
            setContent(getRand(0, SIZE/2), getRand(0, SIZE-1), f);
        }
        gstate.setReqFruits(fruits.size());
    }

    @Override
    protected GameState newState() {
        gstate = new Level3State(MAX_MOVES);
        return gstate;
    }

    /*@Override
    public void clearContent(int i, int j) { //sera esto para que no se me borren las cosas con una explosion?,ya lo veremos
        if(!(get(i,j).getKey().equals("-FRUIT"))) {
            System.out.println("elimino un " + get(i,j).getKey());
            g()[i][j].clearContent();
        }else
            System.out.println("NO deberia eliminar nada!");

    }*/

    @Override
    public boolean tryMove(int i1, int j1, int i2, int j2) {
        boolean ret;
        int r, c;
        if (ret = super.tryMove(i1, j1, i2, j2)) {
            gstate.addMove();
            //cada vez que hago un movimiento, voy a chequear mi grid y ver si hay frutas abajo

            for (r = SIZE - 1, c = 0; c < SIZE; c++) {
                /*
                System.out.println(r);
                System.out.println(c);
                */
                /*if(get(r,c).getKey().equals("-FRUIT")){
                    System.out.println("ROMPO");
                    System.out.println(r);
                    System.out.println(c);
                   // setContent(r, c, new Nothing());
                    clearContent(r,c);
                  //  if(getCell(r,c).getContent() instanceof Nothing)
                        fallElements();
                    gstate.addDestroyedFruit();

                }*/
                if (get(r, c) instanceof Fruit) {
                    clearContent(r, c);
                    fallElements();
                    gstate.addDestroyedFruit();
                    System.out.println("Rompiste" + gstate.fruitsDestroyed + "frutas");
                    System.out.println("Son: " + gstate.reqFruits);
                }

            }

        }
        return ret;
    }


    //---------------------------------------------------
    private class Level3State extends  GameState{

        private int reqFruits;
        private int fruitsDestroyed = 0;
        private int maxMoves;

        Level3State(int maxMoves){
                        this.maxMoves = maxMoves;
        }

        public void addDestroyedFruit(){
            fruitsDestroyed++;

        }

        public void setReqFruits(int reqFruits) {
            this.reqFruits = reqFruits;
        }

        @Override
        public boolean gameOver() {
            return playerWon() || getMoves() == maxMoves;
        }

        @Override
        public boolean playerWon() {//se llama siempre
            return fruitsDestroyed == reqFruits;
        }
    }

    //-----------------------------------------------------
}
