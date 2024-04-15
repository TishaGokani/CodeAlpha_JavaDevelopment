
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

    public class HotelReservationSystem extends JFrame {
        private Hotel hotel;
        public HotelReservationSystem() {
            setTitle("Hotel Reservation System");
            setSize(600, 400);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            hotel = new Hotel();
            hotel.initializeRooms();

            JPanel mainPanel = new JPanel(new GridLayout(3, 1, 10, 10)); // Adjusted spacing
            mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            JButton searchButton = createStyledButton("Search for the Available Rooms");
            searchButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new SearchRoomsFrame(hotel);
                }
            });
            mainPanel.add(searchButton);

            JButton reservationButton = createStyledButton("Make Reservation");
            reservationButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new MakeReservationFrame(hotel);
                }
            });
            mainPanel.add(reservationButton);

            JButton bookingDetailsButton = createStyledButton("View the Booking Details");
            bookingDetailsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new ViewBookingDetailsFrame(hotel);
                }
            });
            mainPanel.add(bookingDetailsButton);

            add(mainPanel);
            setVisible(true);
        }

        private JButton createStyledButton(String text) {
            JButton button = new JButton(text);
            button.setBackground(new Color(34, 139, 34));
            button.setForeground(Color.pink);
            button.setFocusPainted(false);
            button.setFont(new Font("Arial", Font.BOLD, 16));
            return button;
        }

        public static void main(String[] args) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    new HotelReservationSystem();
                }
            });
        }
    }

    class Hotel {
        private Map<String, List<Room>> roomsByCategory;

        public Hotel() {
            roomsByCategory = new HashMap<>();
        }

        public void initializeRooms() {
            List<Room> singleRooms = new ArrayList<>();
            for (int i = 1; i <= 10; i++) {
                singleRooms.add(new Room("Single", i));
            }
            roomsByCategory.put("Single", singleRooms);

            List<Room> doubleRooms = new ArrayList<>();
            for (int i = 1; i <= 5; i++) {
                doubleRooms.add(new Room("Double", i));
            }
            roomsByCategory.put("Double", doubleRooms);

            List<Room> suiteRooms = new ArrayList<>();
            for (int i = 1; i <= 3; i++) {
                suiteRooms.add(new Room("Suite", i));
            }
            roomsByCategory.put("Suite", suiteRooms);
        }

        public List<Room> getAvailableRooms(String category) {
            return roomsByCategory.get(category);
        }

        public boolean makeReservation(String category, String guestName, String telephoneNumber) {
            List<Room> rooms = roomsByCategory.get(category);
            for (Room room : rooms) {
                if (!room.isBooked()) {
                    room.setBooked(true);
                    room.setGuestName(guestName);
                    room.setTelephoneNumber(telephoneNumber);
                    return true;
                }
            }
            return false;
        }

        public List<Room> getAllRooms() {
            List<Room> allRooms = new ArrayList<>();
            for (List<Room> rooms : roomsByCategory.values()) {
                allRooms.addAll(rooms);
            }
            return allRooms;
        }

        public List<String> getRoomCategories() {
            return new ArrayList<>(roomsByCategory.keySet());
        }
    }

    class Room {
        private String category;
        private int number;
        private boolean booked;
        private String guestName;
        private String telephoneNumber; // Added telephone number

        public Room(String category, int number) {
            this.category = category;
            this.number = number;
        }

        public String getCategory() {
            return category;
        }

        public int getNumber() {
            return number;
        }

        public boolean isBooked() {
            return booked;
        }

        public void setBooked(boolean booked) {
            this.booked = booked;
        }

        public String getGuestName() {
            return guestName;
        }

        public void setGuestName(String guestName) {
            this.guestName = guestName;
        }

        public String getTelephoneNumber() {
            return telephoneNumber;
        }

        public void setTelephoneNumber(String telephoneNumber) {
            this.telephoneNumber = telephoneNumber;
        }
    }

    class MakeReservationFrame extends JFrame {
        private Hotel hotel;

        public MakeReservationFrame(Hotel hotel) {
            this.hotel = hotel;
            setTitle("Make Reservation");
            setSize(400, 200);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);

            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            JComboBox<String> categoryComboBox = new JComboBox<>();
            for (String category : hotel.getRoomCategories()) {
                categoryComboBox.addItem(category);
            }
            mainPanel.add(categoryComboBox, BorderLayout.NORTH);

            JPanel inputPanel = new JPanel(new GridLayout(2, 2, 5, 5)); // Adjusted spacing
            JLabel nameLabel = new JLabel("Enter Name:");
            JTextField nameTextField = new JTextField(20);
            JLabel telephoneLabel = new JLabel("Enter contact Number:");
            JTextField telephoneTextField = new JTextField(20);
            inputPanel.add(nameLabel);
            inputPanel.add(nameTextField);
            inputPanel.add(telephoneLabel);
            inputPanel.add(telephoneTextField);
            mainPanel.add(inputPanel, BorderLayout.CENTER);

            JButton reserveButton = new JButton("Reserve");
            reserveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String selectedCategory = (String) categoryComboBox.getSelectedItem();
                    String guestName = nameTextField.getText();
                    String telephoneNumber = telephoneTextField.getText();
                    if (!guestName.isEmpty() && !telephoneNumber.isEmpty()) {
                        boolean reservationStatus = hotel.makeReservation(selectedCategory, guestName, telephoneNumber);
                        if (reservationStatus) {
                            JOptionPane.showMessageDialog(null, "Reservation successful!");
                        } else {
                            JOptionPane.showMessageDialog(null, "Reservation failed. No rooms available.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Please enter guest name and contact number.");
                    }
                }
            });
            mainPanel.add(reserveButton, BorderLayout.SOUTH);

            add(mainPanel);
            setVisible(true);
        }
    }

    class SearchRoomsFrame extends JFrame {
        public SearchRoomsFrame(Hotel hotel) {
            setTitle("Search for the Available Rooms");
            setSize(400, 200);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);

            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            JComboBox<String> categoryComboBox = new JComboBox<>();
            for (String category : hotel.getRoomCategories()) {
                categoryComboBox.addItem(category);
            }
            mainPanel.add(categoryComboBox, BorderLayout.NORTH);

            JTextArea resultTextArea = new JTextArea(10, 30);
            resultTextArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(resultTextArea);
            mainPanel.add(scrollPane, BorderLayout.CENTER);

            JButton searchButton = new JButton("Search");
            searchButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String selectedCategory = (String) categoryComboBox.getSelectedItem();
                    List<Room> availableRooms = hotel.getAvailableRooms(selectedCategory);
                    StringBuilder sb = new StringBuilder();
                    for (Room room : availableRooms) {
                        sb.append("Room ").append(room.getNumber()).append("\n");
                    }
                    resultTextArea.setText(sb.toString());
                }
            });
            mainPanel.add(searchButton, BorderLayout.SOUTH);

            add(mainPanel);
            setVisible(true);
        }
    }

    class ViewBookingDetailsFrame extends JFrame {
        public ViewBookingDetailsFrame(Hotel hotel) {
            setTitle("View The Booking Details");
            setSize(400, 200);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);

            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            JTextArea detailsTextArea = new JTextArea(10, 30);
            detailsTextArea.setEditable(false);
            StringBuilder sb = new StringBuilder();
            for (Room room : hotel.getAllRooms()) {
                if (room.isBooked()) {
                    sb.append("Room ").append(room.getNumber()).append(" - ").append(room.getGuestName()).append(" - ").append(room.getTelephoneNumber()).append("\n");
                }
            }
            detailsTextArea.setText(sb.toString());
            JScrollPane scrollPane = new JScrollPane(detailsTextArea);
            mainPanel.add(scrollPane, BorderLayout.CENTER);

            add(mainPanel);
            setVisible(true);
        }
    }


