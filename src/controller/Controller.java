package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import model.MyCircle;
import model.MyRectangle;
import model.MyShape;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {

	@FXML
	private Button save;
	@FXML
	private RadioButton circleRadio;
	@FXML
	private RadioButton rectangleRadio;
	@FXML
	private RadioButton drawRadio;
	@FXML
	private RadioButton selectRadio;
	@FXML
	private ChoiceBox<String> color;
	@FXML
	private TextField width;
	@FXML
	private TextField height;
	@FXML
	private TextField radius;
	@FXML
	private Canvas canvas;

	private GraphicsContext gc;
	private int colorNumber;
	private ArrayList<MyShape> shapes;
	private double newWidth;
	private double newHeight;
	private double newRadius;
	private MyCircle circle = null;
	private MyRectangle rect = null;
	private int selectedIndex = -1;
	private int count = 0;
	private ObservableList<String> colors;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		colors = FXCollections.observableArrayList("Black", "Red", "Blue", "Green");
		color.setItems(colors);
		color.setValue("Black");
		gc = canvas.getGraphicsContext2D();
		colorNumber = 0;
		shapes = new ArrayList<MyShape>();

		width.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.isEmpty() && newValue.matches("\\d*")) {
				newWidth = Double.parseDouble(newValue);
				if (selectRadio.isSelected() && shapes.size() != 0
						&& shapes.get(selectedIndex) instanceof MyRectangle) {
					if (count == 0) {
						count++;
					} else if (count > 0) {
						selectedIndex = shapes.size() - 1;
					}
					System.out.println("Index=" + selectedIndex);
					MyRectangle newRect = new MyRectangle(shapes.get(selectedIndex).getX(),
							shapes.get(selectedIndex).getY(), shapes.get(selectedIndex).getC(), newWidth,
							shapes.get(selectedIndex).getHeight());
					canvas.getGraphicsContext2D().clearRect(shapes.get(selectedIndex).getX(),
							shapes.get(selectedIndex).getY(), shapes.get(selectedIndex).getWidth(),
							shapes.get(selectedIndex).getHeight());
					shapes.remove(selectedIndex);
					shapes.add(newRect);
					newRect.drawRect(gc);
				}
			} else {
				width.setText(newValue.replaceAll("[^\\d]", ""));
			}
		});
		height.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.isEmpty() && newValue.matches("\\d*")) {
				newHeight = Double.parseDouble(newValue);
				if (selectRadio.isSelected() && shapes.size() != 0
						&& shapes.get(selectedIndex) instanceof MyRectangle) {
					if (count == 0) {
						count++;
					} else if (count > 0) {
						selectedIndex = shapes.size() - 1;
					}
					System.out.println("Index=" + selectedIndex);
					MyRectangle newRect = new MyRectangle(shapes.get(selectedIndex).getX(),
							shapes.get(selectedIndex).getY(), shapes.get(selectedIndex).getC(),
							shapes.get(selectedIndex).getWidth(), newHeight);
					canvas.getGraphicsContext2D().clearRect(shapes.get(selectedIndex).getX(),
							shapes.get(selectedIndex).getY(), shapes.get(selectedIndex).getWidth(),
							shapes.get(selectedIndex).getHeight());
					shapes.remove(selectedIndex);
					shapes.add(newRect);
					newRect.drawRect(gc);
				}
			} else {
				height.setText(newValue.replaceAll("[^\\d]", ""));
			}
		});

		radius.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.isEmpty() && newValue.matches("\\d*")) {
				newRadius = Double.parseDouble(newValue);
				if (selectRadio.isSelected() && shapes.size() != 0 && shapes.get(selectedIndex) instanceof MyCircle) {
					if (count == 0) {
						count++;
					} else if (count > 0) {
						selectedIndex = shapes.size() - 1;
					}
					System.out.println("Index=" + selectedIndex);
					MyCircle newCircle = new MyCircle(shapes.get(selectedIndex).getX(),
							shapes.get(selectedIndex).getY(), shapes.get(selectedIndex).getC(), newRadius);
					canvas.getGraphicsContext2D().clearRect(shapes.get(selectedIndex).getX(),
							shapes.get(selectedIndex).getY(), shapes.get(selectedIndex).getWidth(),
							shapes.get(selectedIndex).getHeight());
					shapes.remove(selectedIndex);
					shapes.add(newCircle);
					newCircle.drawCircle(gc);
				}
			} else {
				radius.setText(newValue.replaceAll("[^\\d]", ""));
			}
		});

		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
				WritableImage snapshot = canvas.snapshot(new SnapshotParameters(), writableImage);
				TextInputDialog td = new TextInputDialog("");
				td.setHeaderText("Enter Image File Name");
				String[] filename = new String[1];
				File[] file = new File[1];
				td.showAndWait();
				filename[0] = td.getEditor().getText().toString();
				filename[0] += ".png";
				file[0] = new File(filename[0]);
				try {
					ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", file[0]);
				} catch (IOException ex) {
				}
			}
		};
		save.setOnAction(event);

		color.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number new_value) {
				colorNumber = new_value.intValue();

				if (selectRadio.isSelected() && shapes.size() != 0 && selectedIndex != -1
						&& shapes.get(selectedIndex) instanceof MyCircle) {
					if (count == 0) {
						count++;
					} else if (count > 0) {
						selectedIndex = shapes.size() - 1;
					}
					System.out.println("Selected Shape Index=" + selectedIndex);
					MyCircle newCircle = new MyCircle(shapes.get(selectedIndex).getX(),
							shapes.get(selectedIndex).getY(), shapes.get(selectedIndex).getC(),
							shapes.get(selectedIndex).getWidth());
					canvas.getGraphicsContext2D().clearRect(shapes.get(selectedIndex).getX(),
							shapes.get(selectedIndex).getY(), shapes.get(selectedIndex).getWidth(),
							shapes.get(selectedIndex).getHeight());
					shapes.remove(selectedIndex);
					if (colorNumber == 0) {
						newCircle.setC(Color.BLACK);
					} else if (colorNumber == 2) {
						newCircle.setC(Color.BLUE);
					} else if (colorNumber == 1) {
						newCircle.setC(Color.RED);
					} else if (colorNumber == 3) {
						newCircle.setC(Color.GREEN);
					}
					shapes.add(newCircle);
					newCircle.drawCircle(gc);
				} else if (selectRadio.isSelected() && shapes.size() != 0 && selectedIndex != -1
						&& shapes.get(selectedIndex) instanceof MyRectangle) {
					if (count == 0) {
						count++;
					} else if (count > 0) {
						selectedIndex = shapes.size() - 1;
					}
					System.out.println("Selected Shape Index=" + selectedIndex);
					MyRectangle newRect = new MyRectangle(shapes.get(selectedIndex).getX(),
							shapes.get(selectedIndex).getY(), shapes.get(selectedIndex).getC(),
							shapes.get(selectedIndex).getWidth(), shapes.get(selectedIndex).getHeight());
					canvas.getGraphicsContext2D().clearRect(shapes.get(selectedIndex).getX(),
							shapes.get(selectedIndex).getY(), shapes.get(selectedIndex).getWidth(),
							shapes.get(selectedIndex).getHeight());
					shapes.remove(selectedIndex);
					if (colorNumber == 0) {
						newRect.setC(Color.BLACK);
					} else if (colorNumber == 2) {
						newRect.setC(Color.BLUE);
					} else if (colorNumber == 1) {
						newRect.setC(Color.RED);
					} else if (colorNumber == 3) {
						newRect.setC(Color.GREEN);
					}
					shapes.add(newRect);
					newRect.drawRect(gc);
				}

			}
		});

		canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			double x;
			double y;

			@Override
			public void handle(MouseEvent mouseEvent) {
				x = mouseEvent.getX();
				y = mouseEvent.getY();

				double radii;
				double w;
				double h;

				if (drawRadio.isSelected()) {
					if (circleRadio.isSelected()) {
						if (radius.getText().isEmpty()) {
							radii = 20;
						} else {
							radii = Double.parseDouble(radius.getText());
						}
						System.out.println("ColorNumber=" + colorNumber);
						if (colorNumber == 0) {
							circle = new MyCircle(x, y, Color.BLACK, radii);
						} else if (colorNumber == 1) {
							circle = new MyCircle(x, y, Color.RED, radii);
						} else if (colorNumber == 2) {
							circle = new MyCircle(x, y, Color.BLUE, radii);
						} else if (colorNumber == 3) {
							circle = new MyCircle(x, y, Color.GREEN, radii);
						}

						shapes.add(circle);
						circle.drawCircle(gc);

					} else if (rectangleRadio.isSelected()) {
						if (width.getText().isEmpty()) {
							w = 20;
						} else {
							w = Double.parseDouble(width.getText());
						}
						if (height.getText().isEmpty()) {
							h = 20;
						} else {
							h = Double.parseDouble(height.getText());
						}
						if (colorNumber == 0) {
							rect = new MyRectangle(x, y, Color.BLACK, w, h);
						} else if (colorNumber == 2) {
							rect = new MyRectangle(x, y, Color.BLUE, w, h);
						} else if (colorNumber == 1) {
							rect = new MyRectangle(x, y, Color.RED, w, h);
						} else if (colorNumber == 3) {
							rect = new MyRectangle(x, y, Color.GREEN, w, h);
						}
						shapes.add(rect);
						rect.drawRect(gc);
					}
					rect = null;
					circle = null;
					System.out.println("Array Size= " + shapes.size());
				} else if (selectRadio.isSelected()) {
					for (int i = 0; i < shapes.size(); i++) {
						double x1 = shapes.get(i).getX();
						double x2 = shapes.get(i).getX() + shapes.get(i).getWidth();
						double y1 = shapes.get(i).getY();
						double y2 = shapes.get(i).getY() + shapes.get(i).getHeight();

						if ((x >= x1 && x <= x2) && y >= y1 && y <= y2) {
							if (shapes.get(i) instanceof MyRectangle) {
								selectedIndex = i;
								rect = (MyRectangle) shapes.get(i);
								System.out.println("Rectangle Found at index= " + i);
								count = 0;
								break;
							} else {
								selectedIndex = i;
								circle = (MyCircle) shapes.get(i);
								System.out.println("Circle Found at index= " + i);
								count = 0;
								break;
							}

						}
					}
				}

			}
		});
	}

	public void undo() {
		if (shapes.size() > 0) {
			canvas.getGraphicsContext2D().clearRect(shapes.get(shapes.size() - 1).getX(),
					shapes.get(shapes.size() - 1).getY(), shapes.get(shapes.size() - 1).getWidth(),
					shapes.get(shapes.size() - 1).getHeight());

			if (shapes.get(shapes.size() - 1) instanceof MyCircle) {
				shapes.remove(shapes.size() - 1);
			} else {
				shapes.remove(shapes.size() - 1);
			}
		}
	}

}
