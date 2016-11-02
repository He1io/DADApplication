package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import views.MainView;

/**
 *
 * @author Helio
 */
public class MainController implements ActionListener {

    //FIELDS
    MainView mainView;
    StudentController studentController;

    //CONSTRUCTORS
    public MainController() {
            mainView = new MainView(this);
            mainView.setVisible(true);
            mainView.setLocationRelativeTo(null);
    }

    //METHODS
    //Métodos abstractos del ActionListener
    @Override
    public void actionPerformed(ActionEvent e) {
        
        //Botón Alumnos
        if (e.getSource() == mainView.getButtonStudents()) {
                mainView.setVisible(false);
                studentController = new StudentController();
        }
    }
}
