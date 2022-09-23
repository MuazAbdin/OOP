import org.junit.Test;

import static org.junit.Assert.*;

public class SpaceShipFactoryTest {
    private final static String HUMAN = "h";
    private final static String DRUNKARD = "d";
    private final static String RUNNER = "r";
    private final static String AGGRESSIVE = "a";
    private final static String BASHER = "b";
    private final static String SPECIAL = "s";

    @Test
    public void falseArgumentTest() {
        System.out.println("FALSE ARGS");
        assertNull(SpaceShipFactory.createSpaceShips(new String[]{"x"}));
//        System.out.println(SpaceShipFactory.createSpaceShips(new String[]{"x"}));
        assertNull(SpaceShipFactory.createSpaceShips(new String[]{"h", "x"}));
//        System.out.println(SpaceShipFactory.createSpaceShips(new String[]{"h", "x"}));
        assertNull(SpaceShipFactory.createSpaceShips(new String[]{}));
//        System.out.println(SpaceShipFactory.createSpaceShips(new String[]{}));
        assertNull(SpaceShipFactory.createSpaceShips(new String[]{"h", "hd"}));

    }

    @Test
    public void createHumanShip() {
        String[] args = {"h"};
        SpaceShip[] ships =  SpaceShipFactory.createSpaceShips(args);
        SpaceShip HumanShip = new Human();
        assertEquals(1, ships.length);
        assertEquals(HumanShip.getClass().getName(), ships[0].getClass().getName());
    }

    @Test
    public void createOneShipArrayTest() {
        String[] shipTypes = {HUMAN, DRUNKARD, RUNNER, AGGRESSIVE, BASHER, SPECIAL};
        String[] typesNames = {"Human", "Drunkard", "Runner", "Aggressive", "Basher", "Special"};

        for (int i=0; i<shipTypes.length; ++i) {
            String[] args = {shipTypes[i]};
            System.out.println(typesNames[i] + " =?= " +
                    SpaceShipFactory.createSpaceShips(args)[0].getClass().getName());
            assertEquals(typesNames[i], SpaceShipFactory.createSpaceShips(args)[0].getClass().getName());
        }

    }

    @Test
    public void differentShipsTest() {
        String[] shipTypes = {HUMAN, DRUNKARD, RUNNER, AGGRESSIVE, BASHER, SPECIAL};
        String[] typesNames = {"Human", "Drunkard", "Runner", "Aggressive", "Basher", "Special"};

        SpaceShip[] ships = SpaceShipFactory.createSpaceShips(shipTypes);
        assert ships != null;
        for (int i=0; i<shipTypes.length; ++i) {
            System.out.println(typesNames[i] + " =?= " +
                    SpaceShipFactory.createSpaceShips(shipTypes)[i].getClass().getName());
            assertEquals(typesNames[i], SpaceShipFactory.createSpaceShips(shipTypes)[i].getClass().getName());
        }
    }


}