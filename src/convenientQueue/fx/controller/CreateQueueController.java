package convenientQueue.fx.controller;

import convenientQueue.fx.FxTextExtension;
import convenientQueue.fx.windowFactory.WindowFactory;
import convenientQueue.logic.Session;
import convenientQueue.logic.model.Doctor;
import convenientQueue.logic.repository.IDoctorRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class CreateQueueController{
    private final int ITEMS_PER_PAGE = 4;

    //fxml
    @FXML private GridPane gridPane;
    @FXML private Button firstBtn;
    @FXML private Button lastBtn;
    @FXML private Button nextBtn;
    @FXML private Button prevBtn;
    @FXML private Text dialogText;

    //dependencies
    private IDoctorRepository doctorRepository;
    private WindowFactory windowFactory;
    private List<Integer> choosenDoctors;

    int currentPage;
    Scene scene;

    public void inject(IDoctorRepository doctorRepository, WindowFactory windowFactory){
        this.doctorRepository = doctorRepository;
        this.windowFactory = windowFactory;
    }

    public void init(){
        currentPage = 0;
        choosenDoctors = new ArrayList<>();
        scene = gridPane.getScene();
        initializeView();
    }

    @FXML
    private void apply(ActionEvent actionEvent) {
        if(choosenDoctors.size() == 0){
            FxTextExtension.appendText(dialogText, "You haven`t choosen any doctor");
        }
        else{
            doctorRepository.calculateDoctorVisits(Session. USER_ID, choosenDoctors);
            windowFactory.createMyQueueWindow();
        }
    }

    private void initializeView(){
        List<Doctor> doctors = doctorRepository.getDoctors(currentPage, ITEMS_PER_PAGE);
        for(int i=0; i<ITEMS_PER_PAGE; i++){
            Label name = (Label) scene.lookup("#name_"+i);
            Label surname = (Label) scene.lookup("#surname_"+i);
            Label specialization = (Label) scene.lookup("#specialzation_"+i);
            CheckBox checkbox = (CheckBox) scene.lookup("#ckeckbox_"+i);

            if(i< doctors.size()){
                Doctor currentDoctor = doctors.get(i);

                name.setText(currentDoctor.getName());
                surname.setText(currentDoctor.getSurname());
                specialization.setText(currentDoctor.getSpecialization());

                checkbox.setVisible(true);
                checkbox.setSelected(choosenDoctors.contains(currentDoctor.getId()));
                checkbox.setOnAction(e-> updateChoosenDoctors(currentDoctor.getId()));
            }
            else{
                name.setText("");
                surname.setText("");
                specialization.setText("");
                checkbox.setVisible(false);
            }
        }
        updatePaging();
    }

    private void updateChoosenDoctors(int doctorId){
        if(choosenDoctors.contains(doctorId)){
            choosenDoctors.remove(Integer.valueOf(doctorId));
        }
        else{
            choosenDoctors.add(doctorId);
        }
    }

    private void updatePaging(){
        int visitsCount = doctorRepository.getDoctorsCount();
        firstBtn.setDisable(currentPage == 0);
        lastBtn.setDisable((currentPage+1)* ITEMS_PER_PAGE >= visitsCount);
        nextBtn.setDisable((currentPage+1)* ITEMS_PER_PAGE >= visitsCount);
        prevBtn.setDisable(currentPage == 0);
    }

    // region paging

    @FXML
    private void last(ActionEvent event){
        currentPage = doctorRepository.getDoctorsCount()/ ITEMS_PER_PAGE;
        initializeView();
    }

    @FXML
    private void first(ActionEvent event){
        currentPage = 0;
        initializeView();
    }

    @FXML
    private void next(ActionEvent event){
        currentPage++;
        initializeView();
    }

    @FXML
    private void prev(ActionEvent event){
        currentPage--;
        initializeView();
    }

    //endregion
}
