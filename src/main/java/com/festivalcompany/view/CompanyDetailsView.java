package com.festivalcompany.view;

import com.festivalcompany.model.Company;
import com.festivalcompany.model.Comment;
import com.festivalcompany.service.FirestoreService;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

import java.util.Date;

public class CompanyDetailsView extends VBox {

    private FirestoreService firestoreService;
    private Company currentCompany;
    private TextField nameField, programNameField, equipmentSNField;
    private ComboBox<String> surveyComboBox;
    private ComboBox<String> deploymentStatusComboBox;
    private ComboBox<String> dismantlingStatusComboBox;
    private ComboBox<String> packagingStatusComboBox;
    private CheckBox elosztoCheckBox, aramCheckBox, halozatCheckBox, ptgCheckBox, szoftverCheckBox, paramCheckBox, helyszinCheckBox;
    private CheckBox distributorCheckBox, electricityCheckBox, networkCheckBox, softwareCheckBox, parameterCheckBox, locationCheckBox, baseDismantlingCheckBox;
    private TextArea commentsArea;
    private TextField newCommentField;
    private Button editButton, saveButton, cancelButton, deleteButton, addCommentButton;

    public CompanyDetailsView(FirestoreService firestoreService) {
        this.firestoreService = firestoreService;

        GridPane detailsGrid = new GridPane();
        detailsGrid.setHgap(10);
        detailsGrid.setVgap(10);

        nameField = new TextField();
        programNameField = new TextField();
        equipmentSNField = new TextField();

        surveyComboBox = new ComboBox<>();
        surveyComboBox.getItems().addAll("TELEPITHETO", "KIRAKHATO", "NEM_KIRAKHATO");

        deploymentStatusComboBox = new ComboBox<>();
        deploymentStatusComboBox.getItems().addAll("KIADVA", "KIHELYEZESRE_VAR", "KIRAKVA", "HELYSZINEN_TESZTELVE", "STATUSZ_NELKUL");

        dismantlingStatusComboBox = new ComboBox<>();
        dismantlingStatusComboBox.getItems().addAll("BONTHATO", "MEG_NYITVA", "NEM_HOZZAFERHETO");

        packagingStatusComboBox = new ComboBox<>();
        packagingStatusComboBox.getItems().addAll("CSOMAGOLVA", "SZALLITASRA_VAR", "ELSZALLITVA", "NINCS_STATUSZ");

        elosztoCheckBox = new CheckBox("Elosztó");
        aramCheckBox = new CheckBox("Áram");
        halozatCheckBox = new CheckBox("Hálózat");
        ptgCheckBox = new CheckBox("PTG");
        szoftverCheckBox = new CheckBox("Szoftver");
        paramCheckBox = new CheckBox("Param");
        helyszinCheckBox = new CheckBox("Helyszín");
        distributorCheckBox = new CheckBox("Distributor");
        electricityCheckBox = new CheckBox("Electricity");
        networkCheckBox = new CheckBox("Network");
        softwareCheckBox = new CheckBox("Software");
        parameterCheckBox = new CheckBox("Parameter");
        locationCheckBox = new CheckBox("Location");
        baseDismantlingCheckBox = new CheckBox("Base Dismantling");

        // Add components to detailsGrid
        // ...

        commentsArea = new TextArea();
        commentsArea.setEditable(false);
        newCommentField = new TextField();
        addCommentButton = new Button("Add Comment");
        addCommentButton.setOnAction(e -> addComment());

        editButton = new Button("Edit");
        saveButton = new Button("Save");
        cancelButton = new Button("Cancel");
        deleteButton = new Button("Delete");

        editButton.setOnAction(e -> enableEditing(true));
        saveButton.setOnAction(e -> saveCompany());
        cancelButton.setOnAction(e -> enableEditing(false));
        deleteButton.setOnAction(e -> deleteCompany());

        HBox buttonBox = new HBox(10, editButton, saveButton, cancelButton, deleteButton);

        this.getChildren().addAll(detailsGrid,
                new Label("Comments:"), commentsArea,
                new HBox(10, newCommentField, addCommentButton),
                buttonBox);
        this.setSpacing(10);

        enableEditing(false);
    }

    public void displayCompany(Company company) {
        currentCompany = company;
        nameField.setText(company.getName());
        programNameField.setText(company.getProgramName());
        equipmentSNField.setText(company.getEquipmentSN());
        surveyComboBox.setValue(company.getFelderites());
        deploymentStatusComboBox.setValue(company.getTelepites());
        elosztoCheckBox.setSelected(company.isEloszto());
        aramCheckBox.setSelected(company.isAram());
        halozatCheckBox.setSelected(company.isHalozat());
        ptgCheckBox.setSelected(company.isPtg());
        szoftverCheckBox.setSelected(company.isSzoftver());
        paramCheckBox.setSelected(company.isParam());
        helyszinCheckBox.setSelected(company.isHelyszin());
        distributorCheckBox.setSelected(company.isDistributor());
        electricityCheckBox.setSelected(company.isElectricity());
        networkCheckBox.setSelected(company.isNetwork());
        softwareCheckBox.setSelected(company.isSoftware());
        parameterCheckBox.setSelected(company.isParameter());
        locationCheckBox.setSelected(company.isLocation());
        dismantlingStatusComboBox.setValue(company.getDismantlingStatus());
        packagingStatusComboBox.setValue(company.getPackagingStatus());
        baseDismantlingCheckBox.setSelected(company.isBaseDismantling());
        updateCommentsArea();
        enableEditing(false);
    }

    private void enableEditing(boolean editable) {
        // Enable/disable fields based on editable
        // ...
    }

    private void saveCompany() {
        currentCompany.setName(nameField.getText());
        currentCompany.setProgramName(programNameField.getText());
        currentCompany.setEquipmentSN(equipmentSNField.getText());
        currentCompany.setFelderites(surveyComboBox.getValue());
        currentCompany.setTelepites(deploymentStatusComboBox.getValue());
        currentCompany.setEloszto(elosztoCheckBox.isSelected());
        currentCompany.setAram(aramCheckBox.isSelected());
        currentCompany.setHalozat(halozatCheckBox.isSelected());
        currentCompany.setPtg(ptgCheckBox.isSelected());
        currentCompany.setSzoftver(szoftverCheckBox.isSelected());
        currentCompany.setParam(paramCheckBox.isSelected());
        currentCompany.setHelyszin(helyszinCheckBox.isSelected());
        currentCompany.setDistributor(distributorCheckBox.isSelected());
        currentCompany.setElectricity(electricityCheckBox.isSelected());
        currentCompany.setNetwork(networkCheckBox.isSelected());
        currentCompany.setSoftware(softwareCheckBox.isSelected());
        currentCompany.setParameter(parameterCheckBox.isSelected());
        currentCompany.setLocation(locationCheckBox.isSelected());
        currentCompany.setDismantlingStatus(dismantlingStatusComboBox.getValue());
        currentCompany.setPackagingStatus(packagingStatusComboBox.getValue());
        currentCompany.setBaseDismantling(baseDismantlingCheckBox.isSelected());
        currentCompany.setLastModified(new Date());

        firestoreService.updateCompany(currentCompany);
        enableEditing(false);
    }

    private void deleteCompany() {
        // Implement delete functionality
        // ...
    }

    private void addComment() {
        // Implement add comment functionality
        // ...
    }

    private void updateCommentsArea() {
        // Update comments area
        // ...
    }
}