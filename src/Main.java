import repository.TareaRepository;
import view.TodoView;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        TareaRepository repository = new TareaRepository();

        SwingUtilities.invokeLater(() -> {
            new TodoView(repository).setVisible(true);
        });
    }
}