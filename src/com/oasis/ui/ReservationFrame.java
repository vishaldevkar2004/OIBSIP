package com.oasis.ui;

import java.awt.Font;
import java.sql.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.oasis.dao.ReservationDAO;
import com.oasis.dao.TrainDAO;
import com.oasis.model.Reservation;
import com.oasis.model.Train;

public class ReservationFrame extends JFrame {

    private JTextField txtPassengerName;
    private JTextField txtTrainNo;
    private JTextField txtTrainName;
    private JTextField txtJourneyDate;
    private JTextField txtSource;
    private JTextField txtDestination;

    private JComboBox<String> cmbClassType;

    private JButton btnFetch;
    private JButton btnBook;
    private JButton btnClear;
    private JButton btnCancel;

    public ReservationFrame() {

        setTitle("Online Reservation System - Reservation");
        setSize(650, 500);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JLabel lblTitle = new JLabel("ONLINE RESERVATION SYSTEM");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setBounds(150, 20, 350, 30);
        add(lblTitle);

        JLabel lblPassenger = new JLabel("Passenger Name");
        lblPassenger.setBounds(50, 80, 120, 25);
        add(lblPassenger);

        txtPassengerName = new JTextField();
        txtPassengerName.setBounds(200, 80, 220, 25);
        add(txtPassengerName);

        JLabel lblTrainNo = new JLabel("Train Number");
        lblTrainNo.setBounds(50, 120, 120, 25);
        add(lblTrainNo);

        txtTrainNo = new JTextField();
        txtTrainNo.setBounds(200, 120, 120, 25);
        add(txtTrainNo);

        btnFetch = new JButton("Fetch");
        btnFetch.setBounds(340, 120, 80, 25);
        add(btnFetch);

        JLabel lblTrainName = new JLabel("Train Name");
        lblTrainName.setBounds(50, 160, 120, 25);
        add(lblTrainName);

        txtTrainName = new JTextField();
        txtTrainName.setBounds(200, 160, 220, 25);
        txtTrainName.setEditable(false);
        add(txtTrainName);

        JLabel lblClass = new JLabel("Class Type");
        lblClass.setBounds(50, 200, 120, 25);
        add(lblClass);

        cmbClassType = new JComboBox<>();

        cmbClassType.addItem("Sleeper");
        cmbClassType.addItem("AC");
        cmbClassType.addItem("First Class");
        cmbClassType.addItem("Second Sitting");

        cmbClassType.setBounds(200, 200, 220, 25);
        add(cmbClassType);

        JLabel lblDate = new JLabel("Journey Date");
        lblDate.setBounds(50, 240, 120, 25);
        add(lblDate);

        txtJourneyDate = new JTextField();
        txtJourneyDate.setBounds(200, 240, 220, 25);
        txtJourneyDate.setToolTipText("yyyy-MM-dd");
        add(txtJourneyDate);

        JLabel lblSource = new JLabel("Source Station");
        lblSource.setBounds(50, 280, 120, 25);
        add(lblSource);

        txtSource = new JTextField();
        txtSource.setBounds(200, 280, 220, 25);
        add(txtSource);

        JLabel lblDestination = new JLabel("Destination Station");
        lblDestination.setBounds(50, 320, 140, 25);
        add(lblDestination);

        txtDestination = new JTextField();
        txtDestination.setBounds(200, 320, 220, 25);
        add(txtDestination);

        btnBook = new JButton("Book Ticket");
        btnBook.setBounds(120, 390, 140, 35);
        add(btnBook);
        
        btnBook.addActionListener(e -> bookTicket());

        btnClear = new JButton("Clear");
        btnClear.setBounds(310, 390, 110, 35);
        add(btnClear);
        
        btnCancel = new JButton("Cancel Ticket");
        btnCancel.setBounds(450, 390, 140, 35);
        add(btnCancel);
        
        btnFetch.addActionListener(e -> fetchTrain());
        
        btnClear.addActionListener(e -> clearFields());
        
        btnCancel.addActionListener(e -> {

            new CancellationFrame();

        });

        setVisible(true);
    }
    
    private void fetchTrain() {

        String trainNumber = txtTrainNo.getText().trim();

        if (trainNumber.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter Train Number!");

            return;
        }

        try {

            int trainNo = Integer.parseInt(trainNumber);

            TrainDAO dao = new TrainDAO();

            Train train = dao.getTrainByNumber(trainNo);

            if (train != null) {

                txtTrainName.setText(train.getTrainName());

            } else {

                txtTrainName.setText("");

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid Train Number!");

            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Train Number must be numeric!");
        }
    }
    
    private void bookTicket() throws NumberFormatException {

        String passengerName = txtPassengerName.getText().trim();
        String trainNumber = txtTrainNo.getText().trim();
        String trainName = txtTrainName.getText().trim();
        String classType = cmbClassType.getSelectedItem().toString();
        String journeyDate = txtJourneyDate.getText().trim();
        String source = txtSource.getText().trim();
        String destination = txtDestination.getText().trim();

        if (passengerName.isEmpty() ||
            trainNumber.isEmpty() ||
            trainName.isEmpty() ||
            journeyDate.isEmpty() ||
            source.isEmpty() ||
            destination.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all required fields!");

            return;
        }

        try {

            Reservation reservation = new Reservation();

            reservation.setPassengerName(passengerName);
            reservation.setTrainNo(Integer.parseInt(trainNumber));
            reservation.setTrainName(trainName);
            reservation.setClassType(classType);
            reservation.setJourneyDate(Date.valueOf(journeyDate));
            reservation.setSourceStation(source);
            reservation.setDestinationStation(destination);

            ReservationDAO dao = new ReservationDAO();

            long pnr = dao.bookTicket(reservation);

            if (pnr != -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Booking Successful!\n\n"
                        + "PNR : " + pnr
                        + "\nPassenger : " + passengerName
                        + "\nTrain : " + trainName
                        + "\nJourney Date : " + journeyDate
                        + "\nSource : " + source
                        + "\nDestination : " + destination);
                clearFields();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Booking Failed!");

            }

        } catch (IllegalArgumentException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid Date!\nUse yyyy-MM-dd format.");
        }

    }
    
    private void clearFields() {

        txtPassengerName.setText("");
        txtTrainNo.setText("");
        txtTrainName.setText("");
        txtJourneyDate.setText("");
        txtSource.setText("");
        txtDestination.setText("");

        cmbClassType.setSelectedIndex(0);

        txtPassengerName.requestFocus();
    }
}