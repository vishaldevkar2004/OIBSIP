package com.oasis.ui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.oasis.dao.ReservationDAO;
import com.oasis.model.Reservation;

import javax.swing.JButton;
import java.awt.Font;

public class CancellationFrame extends JFrame {

    private JTextField txtPNR;
    private JTextField txtPassenger;
    private JTextField txtTrain;
    private JTextField txtJourneyDate;
    private JTextField txtSource;
    private JTextField txtDestination;

    private JButton btnFetch;
    private JButton btnCancelTicket;

    public CancellationFrame() {

        setTitle("Ticket Cancellation");
        setSize(600,450);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JLabel lblTitle = new JLabel("CANCEL RESERVATION");
        lblTitle.setFont(new Font("Arial",Font.BOLD,20));
        lblTitle.setBounds(170,20,300,30);
        add(lblTitle);

        JLabel lblPNR = new JLabel("PNR Number");
        lblPNR.setBounds(40,80,100,25);
        add(lblPNR);

        txtPNR = new JTextField();
        txtPNR.setBounds(160,80,150,25);
        add(txtPNR);

        btnFetch = new JButton("Fetch");
        btnFetch.setBounds(330,80,90,25);
        add(btnFetch);
        
        btnFetch.addActionListener(e -> fetchReservation());

        JLabel lblPassenger = new JLabel("Passenger");
        lblPassenger.setBounds(40,130,100,25);
        add(lblPassenger);

        txtPassenger = new JTextField();
        txtPassenger.setBounds(160,130,250,25);
        txtPassenger.setEditable(false);
        add(txtPassenger);

        JLabel lblTrain = new JLabel("Train");
        lblTrain.setBounds(40,170,100,25);
        add(lblTrain);

        txtTrain = new JTextField();
        txtTrain.setBounds(160,170,250,25);
        txtTrain.setEditable(false);
        add(txtTrain);

        JLabel lblDate = new JLabel("Journey Date");
        lblDate.setBounds(40,210,100,25);
        add(lblDate);

        txtJourneyDate = new JTextField();
        txtJourneyDate.setBounds(160,210,250,25);
        txtJourneyDate.setEditable(false);
        add(txtJourneyDate);

        JLabel lblSource = new JLabel("Source");
        lblSource.setBounds(40,250,100,25);
        add(lblSource);

        txtSource = new JTextField();
        txtSource.setBounds(160,250,250,25);
        txtSource.setEditable(false);
        add(txtSource);

        JLabel lblDestination = new JLabel("Destination");
        lblDestination.setBounds(40,290,100,25);
        add(lblDestination);

        txtDestination = new JTextField();
        txtDestination.setBounds(160,290,250,25);
        txtDestination.setEditable(false);
        add(txtDestination);

        btnCancelTicket = new JButton("Cancel Ticket");
        btnCancelTicket.setBounds(200,350,170,35);
        add(btnCancelTicket);
        
        btnCancelTicket.addActionListener(e -> cancelReservation());

        setVisible(true);
    }
    
    private void fetchReservation() {

        String pnrText = txtPNR.getText().trim();

        if (pnrText.isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Please enter PNR Number!");

            return;
        }

        try {

            long pnr = Long.parseLong(pnrText);

            ReservationDAO dao = new ReservationDAO();

            Reservation reservation = dao.getReservationByPNR(pnr);

            if (reservation != null) {

                txtPassenger.setText(reservation.getPassengerName());
                txtTrain.setText(reservation.getTrainName());
                txtJourneyDate.setText(reservation.getJourneyDate().toString());
                txtSource.setText(reservation.getSourceStation());
                txtDestination.setText(reservation.getDestinationStation());

            } else {

                JOptionPane.showMessageDialog(this,
                        "Invalid PNR Number!");

            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(this,
                    "PNR must be numeric.");

        }

    }
    
    private void cancelReservation() {

        String pnrText = txtPNR.getText().trim();

        if (pnrText.isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Please enter PNR Number!");

            return;
        }

        try {

            long pnr = Long.parseLong(pnrText);

            int option = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to cancel this ticket?",
                    "Confirm Cancellation",
                    JOptionPane.YES_NO_OPTION);

            if (option == JOptionPane.YES_OPTION) {

                ReservationDAO dao = new ReservationDAO();

                boolean cancelled = dao.cancelReservation(pnr);

                if (cancelled) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Reservation Cancelled Successfully!");

                    clearFields();

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Reservation not found!");

                }
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "PNR must be numeric.");

        }
    }
    
    private void clearFields() {

        txtPNR.setText("");
        txtPassenger.setText("");
        txtTrain.setText("");
        txtJourneyDate.setText("");
        txtSource.setText("");
        txtDestination.setText("");

        txtPNR.requestFocus();
    }
}