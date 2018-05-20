package convenientQueue.fx.controller;

import convenientQueue.fx.windowFactory.WindowFactory;
import convenientQueue.logic.Session;
import convenientQueue.logic.model.DoctorVisit;
import convenientQueue.logic.repository.IDoctorRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MyQueueController {
    private final int ITEMS_PER_PAGE = 4;

    //fxml
    @FXML private Button editBtn;
    @FXML private GridPane gridPane;
    @FXML private Button firstBtn;
    @FXML private Button lastBtn;
    @FXML private Button nextBtn;
    @FXML private Button prevBtn;

    //dependencies
    private IDoctorRepository doctorRepository;
    private WindowFactory windowFactory;

    private int currentPage;
    private List<Integer> visitsToRemove;
    private Scene scene;
    private DateFormat timeFormat;

    public void inject(IDoctorRepository doctorRepository, WindowFactory windowFactory){
        this.doctorRepository = doctorRepository;
        this.windowFactory = windowFactory;
    }

    public void init(){
        scene = gridPane.getScene();
        visitsToRemove = new ArrayList<>();
        timeFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        currentPage = 0;
        initializeView();
    }

    @FXML
    private void edit(ActionEvent event){
        doctorRepository.removeDoctorsVisits(visitsToRemove);
        windowFactory.createHomeWindow();
    }

    @FXML
    private void home(ActionEvent event){
        windowFactory.createHomeWindow();
    }


    private void initializeView(){
        List<DoctorVisit> visits = doctorRepository.getDoctorVisits(Session.USER_ID, currentPage, ITEMS_PER_PAGE);
        for(int i=0; i<ITEMS_PER_PAGE; i++){
            Label name = (Label) scene.lookup("#name_"+i);
            Label surname = (Label) scene.lookup("#surname_"+i);
            Label specialization = (Label) scene.lookup("#specialzation_"+i);
            Label time = (Label) scene.lookup("#visit_"+i);
            CheckBox checkbox = (CheckBox) scene.lookup("#ckeckbox_"+i);

            if(i< visits.size()){
                DoctorVisit currentVisit = visits.get(i);

                name.setText(currentVisit.getDoctor().getName());
                surname.setText(currentVisit.getDoctor().getSurname());
                specialization.setText(currentVisit.getDoctor().getSpecialization());
                time.setText(timeFormat.format(currentVisit.getDate()));

                checkbox.setVisible(true);
                checkbox.setSelected(visitsToRemove.contains(currentVisit.getId()));
                checkbox.setOnAction(e-> updateRemoveList(currentVisit.getId()));
            }
            else{
                name.setText("");
                surname.setText("");
                specialization.setText("");
                time.setText("");
                checkbox.setVisible(false);
            }
        }
        updateEditBtn();
        updatePaging();
    }

    private void updateRemoveList(int visitId){
        if(visitsToRemove.contains(visitId)){
            visitsToRemove.remove(Integer.valueOf(visitId));
        }
        else{
            visitsToRemove.add(visitId);
        }
        updateEditBtn();
    }

    private void updateEditBtn(){
        editBtn.setDisable(visitsToRemove.size() == 0);
    }

    private void updatePaging(){
        int visitsCount = doctorRepository.getDoctorsVisitsCount(Session.USER_ID);
        firstBtn.setDisable(currentPage == 0);
        lastBtn.setDisable((currentPage+1)* ITEMS_PER_PAGE >= visitsCount);
        nextBtn.setDisable((currentPage+1)* ITEMS_PER_PAGE >= visitsCount);
        prevBtn.setDisable(currentPage == 0);
    }

    // region paging

    @FXML
    private void last(ActionEvent event){
        currentPage = doctorRepository.getDoctorsVisitsCount(Session.USER_ID)/ ITEMS_PER_PAGE;
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
