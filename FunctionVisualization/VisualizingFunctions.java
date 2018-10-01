package def;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class VisualizingFunctions extends Application{
	
	public double width1 = 100, width2 = 40;
	private boolean bool1 = true, bool2 = false;
	private Circle c,c2;
	private boolean[] arrB = {bool1, bool2};
	
	@Override
	public void start(Stage stage) throws Exception {
		StackPane p = new StackPane();
		
		//Circles or eyes if you will...
		c = new Circle(40);
		c.setFill(Color.TRANSPARENT);
		c.setStroke(Color.BLACK);
		c.setStrokeWidth(width1);
		
		c2 = new Circle(40);
		c2.setFill(Color.TRANSPARENT);
		c2.setStroke(Color.WHITE);
		c2.setStrokeWidth(width1);
		
		//Thread for changing the width of the circles' strokes
		Timeline thread = new Timeline(new KeyFrame(
		        Duration.millis(30),
		        ae -> runit()));
		thread.setCycleCount(Animation.INDEFINITE);
		thread.play();		
		
		//Description
		Text des = new Text("\n      White = % eye open"
						  + "\n      Black = % eye closed");
		
		//Reset Button
		Button reset = new Button("Reset");
			reset.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					width1 = 100;
					width2 = 40;
				}		
			});
		
		//Add
		p.getChildren().addAll(c,c2,des,reset);
		StackPane.setAlignment(des, Pos.TOP_LEFT);
		StackPane.setAlignment(reset, Pos.BOTTOM_CENTER);
		
		//Set the scene and open the stage
		stage.setScene(new Scene(p,400,400));
		stage.setTitle("My Eyes in School");
		stage.show();
	}	
	
	//method to continuously decrease stroke width and increase stroke width
	private double width_size(double x, double rate, double low, double high, int index) {
		if (x > low && arrB[index]) {
			x -= mysteryF(x)*rate;
		}
		else if (x == low || x <= high) {
			arrB[index] = false;
			x += mysteryF(x)*rate;
		}
		else {
			arrB[index] = true;
		}
		return x;
	}
	
	//method to initiate the variables for the method "width_size"
	private void runit(){
		//first circle
		width1 = width_size(width1,2,100,200, 0);
		c.setStrokeWidth(width1);
		
		//second circle
		width2 = width_size(width2,10,10,25, 1);
		c2.setStrokeWidth(width2);
	}
	
	//Insert function here (output has to be a relatively small number (i.e |x|<10) but can be controlled with the weight parameter)
	private double mysteryF(double x) {
		return Math.sin(x);
	}
	
	private double sigmoidish(double x) {
		return (1)/(0.5+Math.pow(Math.E, -(x-10)));
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	

}
