package model.tests;

import model.field.Direction;
import model.field.fieldObjects.robot.LittleRobot;
import model.game.Game;
import model.game.GameStatus;
import model.labyrinth.SimpleLabyrinth;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class GameTests {
    @Test
    public void littleRobotTeleportedGameTest() {
        Game game = new Game();
        game.initGame(new SimpleLabyrinth());

        Assertions.assertEquals(GameStatus.GAME_IS_ON, game.getGameStatus());

        LittleRobot littleRobot = null;
        for (var robot : game.getRobotsOnField()) {
            if (robot instanceof LittleRobot) {
                littleRobot = (LittleRobot)robot;
            }
        }

        Assertions.assertTrue(littleRobot.move(Direction.NORTH));
        Assertions.assertTrue(littleRobot.move(Direction.NORTH));
        Assertions.assertTrue(littleRobot.move(Direction.NORTH));

        Assertions.assertEquals(GameStatus.WINNER_IS_LITTLE_ROBOT, game.getGameStatus());
    }

    @Test
    public void littleRobotDestroyedBySwampGameTest() {
        Game game = new Game();
        game.initGame(new SimpleLabyrinth());

        Assertions.assertEquals(GameStatus.GAME_IS_ON, game.getGameStatus());

        LittleRobot littleRobot = null;
        for (var robot : game.getRobotsOnField()) {
            if (robot instanceof LittleRobot) {
                littleRobot = (LittleRobot)robot;
            }
        }

        Assertions.assertTrue(littleRobot.move(Direction.EAST));

        Assertions.assertEquals(GameStatus.WINNER_IS_BIG_ROBOT, game.getGameStatus());
    }
}
