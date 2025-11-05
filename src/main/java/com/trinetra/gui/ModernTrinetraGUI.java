package com.trinetra.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.effect.DropShadow;
import javafx.scene.chart.PieChart;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.trinetra.core.TrinetraEngine;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ModernTrinetraGUI extends Application {
    private TextArea outputArea;
    private TextField targetField;
    private ComboBox<String> scanTypeCombo;
    private ProgressBar scanProgress;
    private PieChart vulnerabilityChart;
    private TableView<VulnerabilityEntry> vulnerabilityTable;
    private ExecutorService executorService;
    private TrinetraEngine engine;

    @Override
    public void start(Stage primaryStage) {
        executorService = Executors.newSingleThreadExecutor();
        engine = new TrinetraEngine();

        // Create main layout
        BorderPane mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(10));
        mainLayout.setStyle("-fx-background-color: #f0f0f0;");

        // Top section - Header
        VBox headerBox = createHeaderSection();
        mainLayout.setTop(headerBox);

        // Left section - Control Panel
        VBox controlPanel = createControlPanel();
        mainLayout.setLeft(controlPanel);

        // Center section - Results
        TabPane centerPanel = createCenterPanel();
        mainLayout.setCenter(centerPanel);

        // Create scene
        Scene scene = new Scene(mainLayout, 1200, 800);

        // Configure stage
        primaryStage.setTitle("Trinetra Security Scanner");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createHeaderSection() {
        VBox header = new VBox(10);
        header.setPadding(new Insets(15));
        header.setStyle("-fx-background-color: #2c3e50;");

        Label titleLabel = new Label("Trinetra Security Scanner");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setTextFill(Color.WHITE);

        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.BLACK);
        titleLabel.setEffect(shadow);

        header.getChildren().add(titleLabel);
        return header;
    }

    private VBox createControlPanel() {
        VBox controlPanel = new VBox(10);
        controlPanel.setPadding(new Insets(15));
        controlPanel.setStyle("-fx-background-color: #34495e;");
        controlPanel.setPrefWidth(300);

        // Target input
        Label targetLabel = new Label("Target URL:");
        targetLabel.setTextFill(Color.WHITE);
        targetField = new TextField();
        targetField.setPromptText("https://example.com");

        // Scan type selector
        Label scanTypeLabel = new Label("Scan Type:");
        scanTypeLabel.setTextFill(Color.WHITE);
        scanTypeCombo = new ComboBox<>();
        scanTypeCombo.getItems().addAll(
            "Full Scan",
            "Quick Scan",
            "Port Scan",
            "Directory Enumeration",
            "SQL Injection",
            "XSS Analysis",
            "SSL/TLS Analysis"
        );
        scanTypeCombo.setValue("Full Scan");

        // Options
        TitledPane optionsPane = createOptionsPane();

        // Scan button
        Button scanButton = new Button("Start Scan");
        scanButton.setStyle("-fx-background-color: #2ecc71;");
        scanButton.setMaxWidth(Double.MAX_VALUE);
        scanButton.setOnAction(e -> startScan());

        // Progress bar
        scanProgress = new ProgressBar(0);
        scanProgress.setMaxWidth(Double.MAX_VALUE);

        controlPanel.getChildren().addAll(
            targetLabel, targetField,
            scanTypeLabel, scanTypeCombo,
            optionsPane,
            scanButton,
            scanProgress
        );

        return controlPanel;
    }

    private TitledPane createOptionsPane() {
        VBox optionsBox = new VBox(5);
        optionsBox.setPadding(new Insets(5));

        CheckBox sqlCheck = new CheckBox("SQL Injection");
        CheckBox xssCheck = new CheckBox("XSS");
        CheckBox directoryCheck = new CheckBox("Directory Traversal");
        CheckBox sslCheck = new CheckBox("SSL/TLS Analysis");
        CheckBox portCheck = new CheckBox("Port Scanning");
        CheckBox csrfCheck = new CheckBox("CSRF Detection");
        CheckBox wafCheck = new CheckBox("WAF Detection");

        sqlCheck.setTextFill(Color.WHITE);
        xssCheck.setTextFill(Color.WHITE);
        directoryCheck.setTextFill(Color.WHITE);
        sslCheck.setTextFill(Color.WHITE);
        portCheck.setTextFill(Color.WHITE);
        csrfCheck.setTextFill(Color.WHITE);
        wafCheck.setTextFill(Color.WHITE);

        // Set some defaults
        sqlCheck.setSelected(true);
        xssCheck.setSelected(true);
        directoryCheck.setSelected(true);

        optionsBox.getChildren().addAll(
            sqlCheck, xssCheck, directoryCheck, sslCheck, portCheck, csrfCheck, wafCheck
        );

        TitledPane optionsPane = new TitledPane("Scan Options", optionsBox);
        optionsPane.setTextFill(Color.WHITE);
        optionsPane.setStyle("-fx-background-color: #2c3e50;");
        
        return optionsPane;
    }

    private TabPane createCenterPanel() {
        TabPane tabPane = new TabPane();

        // Dashboard tab
        Tab dashboardTab = new Tab("Dashboard");
        dashboardTab.setClosable(false);
        dashboardTab.setContent(createDashboardContent());

        // Results tab
        Tab resultsTab = new Tab("Scan Results");
        resultsTab.setClosable(false);
        resultsTab.setContent(createResultsContent());

        // Logs tab
        Tab logsTab = new Tab("Logs");
        logsTab.setClosable(false);
        outputArea = new TextArea();
        outputArea.setEditable(false);
        logsTab.setContent(outputArea);

        tabPane.getTabs().addAll(dashboardTab, resultsTab, logsTab);
        return tabPane;
    }

    private VBox createDashboardContent() {
        VBox dashboard = new VBox(10);
        dashboard.setPadding(new Insets(10));

        // Create charts
        vulnerabilityChart = new PieChart();
        vulnerabilityChart.setTitle("Vulnerability Distribution");
        updateChartData(new int[]{0, 0, 0, 0}); // Initialize empty chart

        dashboard.getChildren().addAll(vulnerabilityChart);
        return dashboard;
    }

    private VBox createResultsContent() {
        VBox results = new VBox(10);
        results.setPadding(new Insets(10));

        // Create table
        vulnerabilityTable = new TableView<>();
        
        TableColumn<VulnerabilityEntry, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        
        TableColumn<VulnerabilityEntry, String> severityCol = new TableColumn<>("Severity");
        severityCol.setCellValueFactory(cellData -> cellData.getValue().severityProperty());
        
        TableColumn<VulnerabilityEntry, String> descriptionCol = new TableColumn<>("Description");
        descriptionCol.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());

        vulnerabilityTable.getColumns().addAll(typeCol, severityCol, descriptionCol);
        
        results.getChildren().add(vulnerabilityTable);
        return results;
    }

    private void startScan() {
        String target = targetField.getText();
        if (target.isEmpty()) {
            showAlert("Error", "Please enter a target URL");
            return;
        }

        scanProgress.setProgress(0);
        executorService.submit(() -> {
            try {
                // Perform scan using TrinetraEngine
                engine.performScan(target, scanTypeCombo.getValue());
                
                Platform.runLater(() -> {
                    scanProgress.setProgress(1);
                    showAlert("Success", "Scan completed successfully");
                });
            } catch (Exception e) {
                Platform.runLater(() -> {
                    scanProgress.setProgress(0);
                    showAlert("Error", "Scan failed: " + e.getMessage());
                });
            }
        });
    }

    private void updateChartData(int[] data) {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
            new PieChart.Data("High", data[0]),
            new PieChart.Data("Medium", data[1]),
            new PieChart.Data("Low", data[2]),
            new PieChart.Data("Info", data[3])
        );
        vulnerabilityChart.setData(pieChartData);
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @Override
    public void stop() {
        executorService.shutdown();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

class VulnerabilityEntry {
    private final javafx.beans.property.SimpleStringProperty type;
    private final javafx.beans.property.SimpleStringProperty severity;
    private final javafx.beans.property.SimpleStringProperty description;

    public VulnerabilityEntry(String type, String severity, String description) {
        this.type = new javafx.beans.property.SimpleStringProperty(type);
        this.severity = new javafx.beans.property.SimpleStringProperty(severity);
        this.description = new javafx.beans.property.SimpleStringProperty(description);
    }

    public javafx.beans.property.StringProperty typeProperty() { return type; }
    public javafx.beans.property.StringProperty severityProperty() { return severity; }
    public javafx.beans.property.StringProperty descriptionProperty() { return description; }
}