import model.events.GameStatusChangeEvent;
import model.game.Game;
import model.labyrinth.SeasonLabyrinth;
import model.listeners.GameStatusChangedListener;
import ui.GameWidget;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(GamePanel::new);
    }

    private static class GamePanel extends JFrame {
        private Game _game;

        public GamePanel() throws HeadlessException {
            setVisible(true);
            startGame();
            setResizable(false);

            JMenuBar menuBar = new JMenuBar();
            menuBar.add(createGameMenu());
            setJMenuBar(menuBar);

            setDefaultCloseOperation(EXIT_ON_CLOSE);
        }

        private JMenu createGameMenu() {
            JMenu gameMenu = new JMenu("Игра");
            JMenuItem newGameMenuItem = new JMenuItem(new NewGameAction());
            JMenuItem exitMenuItem = new JMenuItem(new ExitAction());
            gameMenu.add(newGameMenuItem);
            gameMenu.add(exitMenuItem);
            return gameMenu;
        }

        private void startGame() {
            _game = new Game();

            _game.initGame(new SeasonLabyrinth());

            _game.addGameStatusChangedListener(new GameController());

            JPanel content = (JPanel) this.getContentPane();
            content.removeAll();
            content.add(new GameWidget(_game));

            pack();
        }

        private class NewGameAction extends AbstractAction {

            public NewGameAction() {
                putValue(NAME, "Новая");
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(GamePanel.this,
                        "Начать новую игру?", "Новая игра",JOptionPane.YES_NO_OPTION);
                if(result == JOptionPane.YES_OPTION) startGame();
            }
        }

        private static class ExitAction extends AbstractAction {

            public ExitAction() {
                putValue(NAME, "Выход");
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        }

        private final class GameController implements GameStatusChangedListener {

            @Override
            public void gameStatusChanged(GameStatusChangeEvent e) {
                String message = "";
                switch (e.getGameStatus()) {
                    case WINNER_IS_BIG_ROBOT -> message = "Выиграл большой робот";
                    case WINNER_IS_LITTLE_ROBOT -> message = "Выиграл маленький робот";
                    case GAME_ABORTED -> message = "Игра досрочно завершена";
                }

                String[] options = {"ok"};
                int value = JOptionPane.showOptionDialog(GamePanel.this, message, "Игра окончена", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                if(value == 0 || value == 1) {
                    startGame();
                    GamePanel.this.repaint();
                }
            }
        }
    }
}
