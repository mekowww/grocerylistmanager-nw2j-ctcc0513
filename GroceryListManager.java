package grocerylistmanager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

// Class representing a grocery item with name and category
class GroceryItem {
    private String name;      // Name of the grocery item
    private String category;  // Category of the grocery item

    // Constructor to initialize a new grocery item with name and category
    public GroceryItem(String name, String category) {
        this.name = name;
        this.category = category;
    }

    // Getter method for the name of the item
    public String getName() {
        return name;
    }

    // Getter method for the category of the item
    public String getCategory() {
        return category;
    }

    // Override toString method to display the item in a readable format
    @Override
    public String toString() {
        return name + " (" + category + ")";
    }
}

// Main class to manage the grocery list and shopping queue
public class GroceryListManager extends JFrame {
    // Declare GUI components
    private JTextField itemInput, categoryInput;  // Input fields for item name and category
    private JButton addButton, removeButton, searchButton, sortByNameButton, sortByCategoryButton, viewQueueButton;  // Buttons for actions
    private JTextArea displayArea;  // Text area to display the list, search results, etc.
    private ArrayList<GroceryItem> groceryList;  // ArrayList to hold grocery items
    private Queue<GroceryItem> shoppingQueue;  // Queue to simulate the shopping queue

    // Constructor to set up the GUI and initialize components
    public GroceryListManager() {
        // Initialize data structures
        groceryList = new ArrayList<>();  // To hold all grocery items
        shoppingQueue = new LinkedList<>();  // To hold items in the shopping queue

        // Set up the frame (main window)
        setTitle("Grocery List Manager");  // Set window title
        setSize(600, 600);  // Set window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Close application on exit
        setLayout(new BorderLayout());  // Set layout to BorderLayout

        // Panel for input fields (item and category)
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2, 1));  // Use GridLayout for two rows

        // Panel for input fields
        JPanel inputPanel = new JPanel(new FlowLayout());
        itemInput = new JTextField(15);  // Input field for item name
        categoryInput = new JTextField(15);  // Input field for item category
        inputPanel.add(new JLabel("Item:"));  // Label for item name
        inputPanel.add(itemInput);  // Add item input field
        inputPanel.add(new JLabel("Category:"));  // Label for category
        inputPanel.add(categoryInput);  // Add category input field

        // Panel for action buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        addButton = new JButton("Add Item");  // Button to add item
        removeButton = new JButton("Remove Item");  // Button to remove item
        searchButton = new JButton("Search Item");  // Button to search for an item
        sortByNameButton = new JButton("Sort by Name");  // Button to sort items by name
        sortByCategoryButton = new JButton("Sort by Category");  // Button to sort items by category
        viewQueueButton = new JButton("View Shopping Queue");  // Button to view the shopping queue

        // Add buttons to the button panel
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(sortByNameButton);
        buttonPanel.add(sortByCategoryButton);
        buttonPanel.add(viewQueueButton);

        // Add the input panel and button panel to the topPanel
        topPanel.add(inputPanel);
        topPanel.add(buttonPanel);

        // Text area to display the grocery list, search results, etc.
        displayArea = new JTextArea();
        displayArea.setEditable(false);  // Make the text area non-editable
        JScrollPane scrollPane = new JScrollPane(displayArea);  // Add scroll functionality to text area

        // Add panels to the frame
        add(topPanel, BorderLayout.NORTH);  // Add topPanel to the top of the frame
        add(scrollPane, BorderLayout.CENTER);  // Add scrollable text area to the center

        // Add action listeners for buttons
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addItem();  // Call addItem method when the button is clicked
            }
        });

        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeItem();  // Call removeItem method when the button is clicked
            }
        });

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchItem();  // Call searchItem method when the button is clicked
            }
        });

        sortByNameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sortListByName();  // Call sortListByName method when the button is clicked
            }
        });

        sortByCategoryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sortListByCategory();  // Call sortListByCategory method when the button is clicked
            }
        });

        viewQueueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewShoppingQueue();  // Call viewShoppingQueue method when the button is clicked
            }
        });
    }

    // Method to add an item to the grocery list and shopping queue
    private void addItem() {
        String itemName = itemInput.getText().trim();  // Get item name from input field
        String itemCategory = categoryInput.getText().trim();  // Get category from input field
        if (!itemName.isEmpty() && !itemCategory.isEmpty()) {  // Check if both fields are not empty
            GroceryItem item = new GroceryItem(itemName, itemCategory);  // Create a new grocery item
            groceryList.add(item);  // Add item to the grocery list
            shoppingQueue.add(item);  // Add item to the shopping queue
            displayArea.append(item + " added to the list.\n");  // Display confirmation message
            itemInput.setText("");  // Clear item input field
            categoryInput.setText("");  // Clear category input field
        } else {
            displayArea.append("Please enter both item name and category.\n");  // Display error message
        }
    }

    // Method to remove an item from the grocery list and shopping queue
    private void removeItem() {
        String itemName = itemInput.getText().trim();  // Get item name from input field
        GroceryItem itemToRemove = null;  // Variable to store the item to be removed
        for (GroceryItem item : groceryList) {  // Loop through the grocery list
            if (item.getName().equalsIgnoreCase(itemName)) {  // Check if item matches
                itemToRemove = item;  // Set itemToRemove to the matching item
                break;
            }
        }

        if (itemToRemove != null) {
            groceryList.remove(itemToRemove);  // Remove item from grocery list
            shoppingQueue.remove(itemToRemove);  // Remove item from shopping queue
            displayArea.append(itemToRemove + " removed from the list.\n");  // Display confirmation message
        } else {
            displayArea.append(itemName + " not found in the list.\n");  // Display error message if not found
        }
        itemInput.setText("");  // Clear item input field
    }

    // Method to search for an item in the grocery list
    private void searchItem() {
        String itemName = itemInput.getText().trim();  // Get item name from input field
        boolean found = false;  // Variable to track if item is found
        for (GroceryItem item : groceryList) {  // Loop through the grocery list
            if (item.getName().equalsIgnoreCase(itemName)) {  // Check if item matches
                displayArea.append(item + " is in the list.\n");  // Display item found message
                found = true;  // Set found to true
                break;
            }
        }
        if (!found) {
            displayArea.append(itemName + " is not in the list.\n");  // Display error message if not found
        }
        itemInput.setText("");  // Clear item input field
    }

    // Method to sort the grocery list by item name
    private void sortListByName() {
        groceryList.sort(Comparator.comparing(GroceryItem::getName));  // Sort list by name
        displayArea.append("List sorted by name:\n");  // Display sorting message
        displayGroceryList();  // Display the sorted list
    }

    // Method to sort the grocery list by item category
    private void sortListByCategory() {
        groceryList.sort(Comparator.comparing(GroceryItem::getCategory));  // Sort list by category
        displayArea.append("List sorted by category:\n");  // Display sorting message
        displayGroceryList();  // Display the sorted list
    }

    // Method to view the current shopping queue
    private void viewShoppingQueue() {
        displayArea.append("Current shopping queue:\n");  // Display shopping queue message
        for (GroceryItem item : shoppingQueue) {  // Loop through shopping queue
            displayArea.append(item + "\n");  // Display each item in the queue
        }
    }

    // Method to display all grocery items in the list
    private void displayGroceryList() {
        for (GroceryItem item : groceryList) {  // Loop through grocery list
            displayArea.append(item + "\n");  // Display each item in the list
        }
    }

    // Main method to run the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GroceryListManager manager = new GroceryListManager();  // Create a new instance of GroceryListManager
            manager.setVisible(true);  // Make the window visible
        });
    }
}
