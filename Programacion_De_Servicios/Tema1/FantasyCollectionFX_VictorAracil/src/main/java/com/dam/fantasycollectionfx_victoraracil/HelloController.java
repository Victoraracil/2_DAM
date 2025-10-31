package com.dam.fantasycollectionfx_victoraracil;

import javafx.fxml.FXML;

import com.dam.fantasycollectionfx_victoraracil.model.Item;
import com.dam.fantasycollectionfx_victoraracil.utils.FileUtils;
import com.dam.fantasycollectionfx_victoraracil.utils.MessageUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * MainController handles all user interactions in the main view of the FantasyCollectionFX app.
 */
public class HelloController {

    //Elements of the view
    @FXML private TableView<Item> tableView;
    @FXML private TableColumn<Item, String> colCode;
    @FXML private TableColumn<Item, String> colName;
    @FXML private TableColumn<Item, String> colType;
    @FXML private TableColumn<Item, String> colRarity;
    @FXML private TableColumn<Item, LocalDate> colDate;

    @FXML private TextField txtCode;
    @FXML private TextField txtName;
    @FXML private TextField txtType;
    @FXML private TextField txtRarity;
    @FXML private DatePicker datePicker;

    @FXML private ComboBox<String> comboFilter;
    @FXML private TextField txtSearch;

    //List showed to the user
    private ObservableList<Item> itemList;

    //Builder
    public HelloController() {}

    //Method called when FXML is started
    @FXML
    private void initialize() {
        //Load items from the file
        List<Item> loadedItems = FileUtils.loadItems();
        itemList = FXCollections.observableArrayList(loadedItems);
        tableView.setItems(itemList);

        //Column config
        colCode.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCode()));
        colName.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));
        colType.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getType()));
        colRarity.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getRarity()));
        colDate.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getObtainedDate()));

        //Listener: When item is selected, data is show
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, newItem) -> {
            if (newItem != null) {
                txtCode.setText(newItem.getCode());
                txtName.setText(newItem.getName());
                txtType.setText(newItem.getType());
                txtRarity.setText(newItem.getRarity());
                datePicker.setValue(newItem.getObtainedDate());
            }
        });

        //Options of combbox
        comboFilter.setItems(FXCollections.observableArrayList(
                "Show all",
                "Count by type",
                "Show only Legendary",
                "Show 5 most recent",
                "Show rarity percentages"
        ));
    }

    //GETTER used for HelloAplication to save the items
    public List<Item> getItemList() {
        return itemList;
    }

    //Add new item
    @FXML
    private void onAddItem() {
        try {
            String code = txtCode.getText().trim();
            String name = txtName.getText().trim();
            String type = txtType.getText().trim();
            String rarity = txtRarity.getText().trim();
            LocalDate date = datePicker.getValue();

            if (code.isEmpty() || name.isEmpty() || type.isEmpty() || rarity.isEmpty() || date == null) {
                MessageUtils.showError("Missing data", "Please fill in all fields.");
                return;
            }

            //Avoid duplicates
            if (itemList.stream().anyMatch(i -> i.getCode().equalsIgnoreCase(code))) {
                MessageUtils.showError("Duplicate code", "An item with that code already exists.");
                return;
            }

            Item newItem = new Item(code, name, type, rarity, date);
            itemList.add(newItem);
            tableView.refresh();
            MessageUtils.showMessage("Item added", "New item successfully added!");

        } catch (Exception e) {
            MessageUtils.showError("Add error", e.getMessage());
        }
    }

    //Delete item
    @FXML
    private void onDeleteItem() {
        Item selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            MessageUtils.showError("No selection", "Please select an item to delete.");
            return;
        }

        if (MessageUtils.showConfirmation("Delete item", "Are you sure you want to delete " + selected.getName() + "?")) {
            itemList.remove(selected);
            tableView.refresh();
        }
    }

    //Update item
    @FXML
    private void onUpdateItem() {
        Item selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            MessageUtils.showError("No selection", "Please select an item to update.");
            return;
        }

        selected.setName(txtName.getText().trim());
        selected.setType(txtType.getText().trim());
        selected.setRarity(txtRarity.getText().trim());
        selected.setObtainedDate(datePicker.getValue());
        tableView.refresh();
        MessageUtils.showMessage("Updated", "Item updated successfully.");
    }

    //Search from text(OnKeyReleased in FXML)
    @FXML
    private void onSearch(KeyEvent event) {
        String search = txtSearch.getText().toLowerCase().trim();
        if (search.isEmpty()) {
            tableView.setItems(itemList);
            return;
        }

        List<Item> filtered = itemList.stream()
                .filter(i ->
                        i.getCode().toLowerCase().contains(search) ||
                                i.getName().toLowerCase().contains(search) ||
                                i.getType().toLowerCase().contains(search) ||
                                i.getRarity().toLowerCase().contains(search) ||
                                i.getObtainedDate().toString().contains(search)
                )
                .collect(Collectors.toList());

        tableView.setItems(FXCollections.observableArrayList(filtered));
    }

    //Apply filter to combbox
    @FXML
    private void onApplyFilter() {
        String option = comboFilter.getValue();
        if (option == null) return;

        switch (option) {
            case "Show all" -> tableView.setItems(itemList);

            case "Count by type" -> {
                Item selected = tableView.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    long count = itemList.stream()
                            .filter(i -> i.getType().equalsIgnoreCase(selected.getType()))
                            .count();
                    MessageUtils.showMessage("Count by type", "There are " + count + " items of type " + selected.getType() + ".");
                } else {
                    MessageUtils.showError("No selection", "Please select an item first.");
                }
            }

            case "Show only Legendary" -> {
                List<Item> legendaries = itemList.stream()
                        .filter(i -> i.getRarity().equalsIgnoreCase("Legendary"))
                        .collect(Collectors.toList());
                tableView.setItems(FXCollections.observableArrayList(legendaries));
            }

            case "Show 5 most recent" -> {
                List<Item> recent = itemList.stream()
                        .sorted(Comparator.comparing(Item::getObtainedDate).reversed())
                        .limit(5)
                        .collect(Collectors.toList());
                tableView.setItems(FXCollections.observableArrayList(recent));
            }

            case "Show rarity percentages" -> {
                long total = itemList.size();
                if (total == 0) {
                    MessageUtils.showError("No items", "The list is empty.");
                    return;
                }

                var groups = itemList.stream()
                        .collect(Collectors.groupingBy(Item::getRarity, Collectors.counting()));

                String result = groups.entrySet().stream()
                        .map(e -> e.getKey() + ": " + String.format("%.2f", (e.getValue() * 100.0 / total)) + "%")
                        .collect(Collectors.joining("\n"));

                MessageUtils.showMessage("Rarity percentages", result);
            }
        }
    }
}

