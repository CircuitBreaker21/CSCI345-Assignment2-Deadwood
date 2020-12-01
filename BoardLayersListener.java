/*

   Deadwood GUI helper file
   Author: Moushumi Sharmin
   This file shows how to create a simple GUI using Java Swing and Awt Library
   Classes Used: JFrame, JLabel, JButton, JLayeredPane

*/

import java.awt.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.io.IOException;

public class BoardLayersListener extends JFrame {

   // JLabels
   JLabel boardlabel;

   JLabel[] playerLabels;
   JLabel[] cardLabels = new JLabel[10];
   JLabel playerlabel;
   JLabel mLabel;

   // JButtons
   JButton bAct;
   JButton bRehearse;
   JButton bMove;

   // JLayered Pane
   JLayeredPane bPane;

   Player curPlayer;
   private List<Room> rooms;


   // Constructor

   public BoardLayersListener() {

      // Set the title of the JFrame
      super("Deadwood");
      // Set the exit option for the JFrame
      setDefaultCloseOperation(EXIT_ON_CLOSE);

      // Create the JLayeredPane to hold the display, cards, dice and buttons
      bPane = getLayeredPane();

      // Create the deadwood board
      boardlabel = new JLabel();
      ImageIcon icon = new ImageIcon("pics/board.jpg");
      boardlabel.setIcon(icon);
      boardlabel.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());

      // Add the board to the lowest layer
      bPane.add(boardlabel, new Integer(0));

      // Set the size of the GUI
      setSize(icon.getIconWidth() + 200, icon.getIconHeight());

      // Add a scene card to this room

      // Add the card to the lower layer

      // Add a dice to represent a player.
      // Role for Crusty the prospector. The x and y co-ordiantes are taken from
      // Board.xml file
      playerlabel = new JLabel();
      ImageIcon pIcon = new ImageIcon("pics/dice/r2.png");
      playerlabel.setIcon(pIcon);
      // playerlabel.setBounds(114,227,pIcon.getIconWidth(),pIcon.getIconHeight());
      playerlabel.setBounds(114, 227, 46, 46);
      playerlabel.setVisible(false);
      bPane.add(playerlabel, new Integer(3));

      // Create the Menu for action buttons
      mLabel = new JLabel("MENU");
      mLabel.setBounds(icon.getIconWidth() + 40, 0, 100, 20);
      bPane.add(mLabel, new Integer(2));

      // Create Action buttons
      bAct = new JButton("ACT");
      bAct.setBackground(Color.white);
      bAct.setBounds(icon.getIconWidth() + 10, 30, 100, 20);
      bAct.addMouseListener(new boardMouseListener());

      bRehearse = new JButton("REHEARSE");
      bRehearse.setBackground(Color.white);
      bRehearse.setBounds(icon.getIconWidth() + 10, 60, 100, 20);
      bRehearse.addMouseListener(new boardMouseListener());

      bMove = new JButton("MOVE");
      bMove.setBackground(Color.white);
      bMove.setBounds(icon.getIconWidth() + 10, 90, 100, 20);
      bMove.addMouseListener(new boardMouseListener());

      // Place the action buttons in the top layer
      bPane.add(bAct, new Integer(2));
      bPane.add(bRehearse, new Integer(2));
      bPane.add(bMove, new Integer(2));

   }

   public void addCard(HashMap<String, Integer> area, int i) {

      cardLabels[i] = new JLabel();
      ImageIcon cIcon = new ImageIcon("pics/CardBack-small.jpg");
      cardLabels[i].setIcon(cIcon);
      cardLabels[i].setBounds(area.get("x"), area.get("y"), cIcon.getIconWidth() + 2, cIcon.getIconHeight());
      cardLabels[i].setOpaque(true);

      bPane.add(cardLabels[i], new Integer(1));
      // cardPanel.add(cardLabels[i]);
   }

   public void initiatePlayers(List<Player> players) {

      playerLabels = new JLabel[players.size()];
      for (int i = 0; i < players.size(); i++) {
         playerLabels[i] = new JLabel();

         // get pic
         ImageIcon pIcon = new ImageIcon("pics/dice/" + players.get(i).getDiceName());
         // ImageIcon pIcon = new ImageIcon("pics/dice/b1.png");


         // set bounds
         int x, y;

         int minX = 1000;
         int maxX = 1150;

         int minY = 300;
         int maxY = 400;

         Random random = new Random();
         x = random.nextInt(maxX + 1 - minX) + minX;
         y = random.nextInt(maxY + 1 - minY) + minY;

         // add
         playerLabels[i].setIcon(pIcon);
         playerLabels[i].setBounds(x, y, pIcon.getIconWidth() + 2, pIcon.getIconHeight());
         playerLabels[i].setOpaque(true);
         bPane.add(playerLabels[i], new Integer(1));
         bPane.repaint();

         System.out.println("Player " + players.get(i).getName() + " added");
      }

   }


   public void printRoleLocations(List<Room> rooms){
      for(int i = 0; i < 10; i++){
         List<Role> offCardRoles = rooms.get(i).getOffCardRoles();

         for (int j = 0; j < offCardRoles.size(); j++) {

            // cardLabels[i].setBounds(area.get("x"), area.get("y"), cIcon.getIconWidth() +
            // 2, cIcon.getIconHeight());

            HashMap<String, Integer> areaHashMap = offCardRoles.get(j).getAreaHashMap();

            JPanel redPanel = new JPanel();
            
            redPanel.setBackground(Color.red);
            redPanel.setBounds(areaHashMap.get("x"), areaHashMap.get("y"), areaHashMap.get("w"), areaHashMap.get("h"));
            // redPanel.setBounds(500, 100, 50, 50);
            redPanel.setOpaque(true);
            bPane.add(redPanel, new Integer(1));
         }         
      }
   }


   public void printTakesLocations(List<Room> rooms){
      for (int i = 0; i < 10; i++) {

         List<HashMap<String, Integer>> takesAreaHashList = rooms.get(i).getTakesAreaHashList();

         for (int j = 0; j < takesAreaHashList.size(); j++) {

            // cardLabels[i].setBounds(area.get("x"), area.get("y"), cIcon.getIconWidth() +
            // 2, cIcon.getIconHeight());



            HashMap<String, Integer> takesAreaHashMap = takesAreaHashList.get(j);

            ImageIcon boardIcon = new ImageIcon("pics/shot.png");
            JLabel shotLable = new JLabel();
            shotLable.setIcon(boardIcon);
            shotLable.setBounds(takesAreaHashMap.get("x"), takesAreaHashMap.get("y"), 
                              takesAreaHashMap.get("w"), takesAreaHashMap.get("h"));
            // redPanel.setBounds(500, 100, 50, 50);
            shotLable.setOpaque(true);
            bPane.add(shotLable, new Integer(1));
         }
      }
   }

   public void updatePlayer(Player CurPlayer) {
      curPlayer = CurPlayer;
   }

   public void updateRooms(List<Room> Rooms){
      rooms = Rooms;
   }
   // This class implements Mouse Events

   class boardMouseListener implements MouseListener {

      // Code for the different button clicks
      public void mouseClicked(MouseEvent e) {

         if (e.getSource() == bAct) {
            if (curPlayer.hasRole()) {

               curPlayer.act();

            } else {
               System.out.println("Player does not have role");
            }
         } else if (e.getSource() == bRehearse) {
            System.out.println("Rehearse is Selected\n");

            if (curPlayer.hasRole()) {
               curPlayer.rehearse();
            } else {
               System.out.println("Player does not have role");
            }
         } else if (e.getSource() == bMove) {

            if (curPlayer.hasRole()) {
               System.out.println("Player does not have role and cannot cove");

            } else if (curPlayer.hasMoved()) {
               System.out.println("Player has already moved this turn");
            } else {
               System.out.println("Where would you like to move?");
               curPlayer.printNeighbors();
               while (!curPlayer.hasMoved()) {
                  curPlayer.showRoles();
                  System.out.print("Please pick a number: ");
                  //try {
                     int roomNum = 1;
                     
                     String newLocation = curPlayer.print1Neighbor(roomNum);
                     System.out.println("You have chosen: " + newLocation);
                     for (int k = 0; k < rooms.size(); k++) {
                        if (rooms.get(k).getName().equals(newLocation)) {
                           Room newRoom = rooms.get(k);
                           curPlayer.setRoom(newRoom);
                           curPlayer.finishedMove();
                        }
                     }
                  // } catch (Exception e) {
                  //    System.out.println("Invalid input, please select again");
                  // }
               }
            }
            System.out.println("Move is Selected\n");
         }
      }

      public void mousePressed(MouseEvent e) {
      }

      public void mouseReleased(MouseEvent e) {
      }

      public void mouseEntered(MouseEvent e) {
      }

      public void mouseExited(MouseEvent e) {
      }
   }
}