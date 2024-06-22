package com.festivalcompany.view;

import com.festivalcompany.model.Company;
import com.festivalcompany.service.FirestoreService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

import java.util.function.Consumer;

public class CompanyListView extends VBox {

    private FirestoreService firestoreService;
    private TableView<Company> companyTable;
    private ComboBox<String> festivalSelector;
    private TextField searchField;
    private ToggleGroup statusToggleGroup;
    private Consumer<Company> onCompanySelectedHandler;

    public CompanyListView(FirestoreService firestoreService) {
        this.firestoreService = firestoreService;

        festivalSelector = new ComboBox<>();
        festivalSelector.setPromptText("Select Festival");
        festivalSelector.getItems().addAll("Festival 1", "Festival 2", "Festival 3"); // Add actual festival names
        festivalSelector.setOnAction(e -> updateCompanyList());

        searchField = new TextField();
        searchField.setPromptText("Search by name, ID, or equipment SN/DID");
        searchField.textProperty().addListener((observable, oldValue, newValue) -> updateCompanyList());

        statusToggleGroup = new ToggleGroup();
        RadioButton allRadio = new RadioButton("All");
        RadioButton installationRadio = new RadioButton("Installation");
        RadioButton demolitionRadio = new RadioButton("Demolition");
        allRadio.setToggleGroup(statusToggleGroup);
        installationRadio.setToggleGroup(statusToggleGroup);
        demolitionRadio.setToggleGroup(statusToggleGroup);
        allRadio.setSelected(true);

        HBox radioBox = new HBox(10, allRadio, installationRadio, demolitionRadio);

        companyTable = new TableView<>();
        setupCompanyTable();

        this.getChildren().addAll(festivalSelector, searchField, radioBox, companyTable);
        this.setSpacing(10);

        updateCompanyList();
    }

    private void setupCompanyTable() {
        TableColumn<Company, String> idColumn = new TableColumn<>("Company ID");
        idColumn.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createStringBinding(
                () -> cellData.getValue().getId()
        ));

        TableColumn<Company, String> nameColumn = new TableColumn<>("Company Name");
        nameColumn.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createStringBinding(
                () -> cellData.getValue().getName()
        ));

        TableColumn<Company, String> lastModifiedColumn = new TableColumn<>("Last Modified");
        lastModifiedColumn.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createStringBinding(
                () -> cellData.getValue().getLastModified().toString()
        ));

        TableColumn<Company, String> programNameColumn = new TableColumn<>("Program Name");
        programNameColumn.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createStringBinding(
                () -> cellData.getValue().getProgramName()
        ));

        companyTable.getColumns().addAll(idColumn, nameColumn, lastModifiedColumn, programNameColumn);

        companyTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null && onCompanySelectedHandler != null) {
                onCompanySelectedHandler.accept(newSelection);
            }
        });
    }

    private void updateCompanyList() {
        String selectedFestival = festivalSelector.getValue();
        String searchTerm = searchField.getText().toLowerCase();
        String status = ((RadioButton) statusToggleGroup.getSelectedToggle()).getText();

        ObservableList<Company> companies = FXCollections.observableArrayList(
                firestoreService.getCompaniesByFestival(selectedFestival)
        );

        companies.removeIf(company ->
                (!company.getName().toLowerCase().contains(searchTerm) &&
                        !company.getId().toLowerCase().contains(searchTerm) &&
                        company.getEquipmentSNs().stream().noneMatch(sn -> sn.toLowerCase().contains(searchTerm))) ||
                        (status.equals("Installation") && !company.getTelepites().equals("KIADVA")) ||
                        (status.equals("Demolition") && !company.getFelderites().equals("TELEPÍTHETŐ"))
        );

        companyTable.setItems(companies);
    }

    public void setOnCompanySelected(Consumer<Company> handler) {
        this.onCompanySelectedHandler = handler;
    }
}