package com.tcs.jdbc_prepared_statement_crud_operation.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class LaptopController {

	public static void main(String[] args) {
		while (true) {
			System.out.println("\n1.INSERT\n2.DISPLAY\n3.UPDATE\n4.DELETE\n5.EXIT");
			System.out.println("SELECT YOUR OPTION");

			Connection con = LaptopConnection.getLaptopConnection();

			Scanner sc = new Scanner(System.in);
			int option = sc.nextInt();

			switch (option) {
			case 1: {
				System.out.println("Enter laptop id: ");
				int id = sc.nextInt();
				System.out.println("Enter laptop name: ");
				String name = sc.next();
				System.out.println("Enter laptop color: ");
				String color = sc.next();
				System.out.println("Enter laptop price: ");
				int price = sc.nextInt();

				String insertLaptopConnection = "insert into laptop(id,name,color,price) values (?,?,?,?)";

				try {
					PreparedStatement psmt = con.prepareStatement(insertLaptopConnection);
					psmt.setInt(1, id);
					psmt.setString(2, name);
					psmt.setString(3, color);
					psmt.setInt(4, price);

					int a = psmt.executeUpdate();

					String msg = a != 0 ? "Success" : "Somthing wrong";
					System.out.println(msg);

				} catch (SQLException e) {
					e.printStackTrace();

				}

			}
				break;
			// case 1 end.............................................

			case 2: {

				String displayLaptopQuery = "SELECT * FROM laptop";

				try (Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
						ResultSet rs = st.executeQuery(displayLaptopQuery)) {

					ResultSetMetaData metaData = rs.getMetaData();
					int columnCount = metaData.getColumnCount();
					int[] columnWidths = new int[columnCount];

					// Calculate the maximum width for each column
					for (int i = 1; i <= columnCount; i++) {
						columnWidths[i - 1] = Math.max(columnWidths[i - 1], metaData.getColumnName(i).length());
					}
					while (rs.next()) {
						for (int i = 1; i <= columnCount; i++) {
							columnWidths[i - 1] = Math.max(columnWidths[i - 1], rs.getString(i).length());
						}
					}

					// Print the header
					for (int i = 1; i <= columnCount; i++) {
						System.out.printf("%-" + (columnWidths[i - 1] + 2) + "s", metaData.getColumnName(i));
					}
					System.out.println();

					// Print the separator line
					for (int i = 1; i <= columnCount; i++) {
						for (int j = 0; j < columnWidths[i - 1] + 2; j++) {
							System.out.print("-");
						}
					}
					System.out.println();

					// Print the data
					rs.beforeFirst(); // Move the cursor back to the start
					while (rs.next()) {
						for (int i = 1; i <= columnCount; i++) {
							System.out.printf("%-" + (columnWidths[i - 1] + 2) + "s", rs.getString(i));
						}
						System.out.println();

					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
				break;
			// case 2 end........................................

			// case 3 update laptop...............................
			case 3: {
				System.out.println("UPDATE LAPTOP\nEnter Laptop id to search: ");
				int id = sc.nextInt();
				String selectQuery = "SELECT * FROM laptops WHERE id = ?";
				if (selectQuery != null)
					System.out.println("Laptop id found");
				else
					System.out.println("Not Found..");
				System.out.println("Enter option to update..\n1.Laptop Name\n2.Laptop color\n3.Laptop price\n4.exit");
				int option1 = sc.nextInt();
				switch (option1) {

				// update name...............
				case 1: {
					String query = "UPDATE laptop SET name = ? WHERE id = " + id;
					try {
						PreparedStatement psmt = con.prepareStatement(query);
						System.out.println("Enter new Name: ");
						String name = sc.next();
						psmt.setString(1, name);
						int a = psmt.executeUpdate();
						if (a > 0)
							System.out.println("Laptop name updated");
						else
							System.out.println("Somthing went wrong");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
					break;

				// update color...........
				case 2: {
					String query = "UPDATE laptop SET color = ? WHERE id = " + id;
					try {
						PreparedStatement psmt = con.prepareStatement(query);
						System.out.println("Enter new color: ");
						String color = sc.next();
						psmt.setString(1, color);
						int a = psmt.executeUpdate();
						if (a > 0)
							System.out.println("Laptop color updated");
						else
							System.out.println("Somthing went wrong");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
					break;

				// update price.......
				case 3: {
					String query = "UPDATE laptop SET price = ? WHERE id = " + id;
					try {
						PreparedStatement psmt = con.prepareStatement(query);
						System.out.println("Enter new price: ");
						String price = sc.next();
						psmt.setString(1, price);
						int a = psmt.executeUpdate();
						if (a > 0)
							System.out.println("Laptop price updated");
						else
							System.out.println("Somthing went wrong");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
					break;
				case 4: {
					System.out.println("Exiting...");
					sc.close();
					return;
				}

				default:
					System.out.println("Please enter valid option!");
				}
			}
				break;
			// case 3 end...........................................

			// case 4 delete laptop............................
			case 4: {
				String deleteQuery = "DELETE FROM laptop WHERE id = ?";

				try {
					PreparedStatement psmt = con.prepareStatement(deleteQuery);
					System.out.println("Enter laptop id: ");
					int laptopId = sc.nextInt();
					psmt.setInt(1, laptopId);
					int a = psmt.executeUpdate();
					if (a > 0)
						System.out.println("Laptop deleted..");
					else
						System.out.println("Somthing went wrong");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
				break;
			// case 4 end.........................................

			// case 5 exit and close all open connections
			case 5: {
				System.out.println("Exiting...");
				sc.close();
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return;
			}
			// case 5 end......................................

			}

		}
	}
}
