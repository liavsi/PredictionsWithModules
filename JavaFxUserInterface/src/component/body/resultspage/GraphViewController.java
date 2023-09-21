package component.body.resultspage;

import DTOManager.impl.EntityInstanceDTO;
import DTOManager.impl.EntityInstanceManagerDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import utils.results.EntityPopulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GraphViewController {

    @FXML private AnchorPane graphPane;
    @FXML private LineChart<Number, Number> populationChart;
    @FXML private Button ButtonBack;

    Map<Integer, EntityInstanceManagerDTO> dataAroundTicks;
    ResultsPageController resultsPageController;

    @FXML
    private void initialize() {
        NumberAxis xAxis = (NumberAxis) populationChart.getXAxis();
        xAxis.setLabel("Time");

        NumberAxis yAxis = (NumberAxis) populationChart.getYAxis();
        yAxis.setLabel("Population");

        populationChart.setTitle("Population Over Time");
    }

    public void setMainController(ResultsPageController resultsPageController) {
        this.resultsPageController = resultsPageController;
    }

    public void setData(Map<Integer, EntityInstanceManagerDTO> dataAroundTicks) {
        this.dataAroundTicks = dataAroundTicks;
        Map<String, XYChart.Series<Number,Number>> listOfCharts = new HashMap<>();
        dataAroundTicks.forEach((tick, instanceDTO)-> {
            Map<String, Long> countMap = instanceDTO.getInstancesDTO().values().stream()
                    .collect(Collectors.groupingBy(EntityInstanceDTO::getName,Collectors.counting()));
            countMap.forEach((name, count) -> {
                if(listOfCharts.get(name) == null){
                    listOfCharts.put(name, new XYChart.Series<>());
                    listOfCharts.get(name).setName(name);
                }
                listOfCharts.get(name).getData().add(new XYChart.Data<>(tick, count));
            });
        });
        populationChart.getData().addAll(listOfCharts.values());
    }
    public void onBackFromGraphButton(ActionEvent event) {
        resultsPageController.removeGraph();
    }
}
