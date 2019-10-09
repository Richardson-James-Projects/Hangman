import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.Group;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Hangman extends Application {

    @Override
    public void start(Stage primaryStage) {
        final String WORD = findWord().toLowerCase();
        System.out.println(WORD);
        VBox pane = new VBox(4);
        Group root = new Group();
        Scene scene = new Scene(root, 500, 600);
        primaryStage.setScene(scene);

        Text header = new Text("HANGMAN!");
        pane.getChildren().add(header);

        TextField choice = new TextField();
        Text text = new Text("Guess a Letter:");

        // this sets up the Hangman
        Circle head = new Circle(200, 200, 40);
        Line body = new Line(200, 240, 200, 390);
        Line lftArm = new Line(200, 290, 150, 340);
        Line rightArm = new Line(200, 290, 250, 340);
        Line lftLeg = new Line(200, 390, 150, 490);
        Line rightLeg = new Line(200, 390, 250, 490);
        Line noose = new Line(200, 160, 200, 100);
        Line beam = new Line(200, 100, 300, 100);
        Line pole = new Line(300, 100, 300, 500);
        Rectangle base = new Rectangle(200, 25);
        base.setX(100);
        base.setY(500);

        // this adds the hangman to a group
        Group badGuess = new Group();
        badGuess.getChildren().addAll(base, pole, beam, noose, head, body, rightArm, lftArm, rightLeg, lftLeg);
        Group displayWord = new Group();

        // this is for the spacing of the word display
        int x = 10;
        for (int i = 0; i < WORD.length(); i++) {
            Text blank = new Text("_");
            blank.setX(x);
            x += 10;
            displayWord.getChildren().add(blank);
        }

        // adds the blank word to the pane
        pane.getChildren().addAll(displayWord);

        // this generates the buttons to stop playing or to continue playing
        Button play = new Button();
        Button no = new Button();
        play.setText("Play Again?");
        no.setText("Stop Playing.");
        play.setOnMouseClicked(click -> {
            primaryStage.close();
            Stage stage = new Stage();
            start(stage);
        });
        play.setOnAction(action -> {
            primaryStage.close();
            Stage stage = new Stage();
            start(stage);
        });
        no.setOnMouseClicked(click -> {
            primaryStage.close();
        });
        no.setOnAction(action -> {
            primaryStage.close();
        });


        choice.setOnAction(actionEvent -> {
            boolean granted = WORD.contains(choice.getText().toLowerCase());
            if (granted) {
                // this is if you correctly guess a letter
                primaryStage.setTitle("You Got One!!!");
                char[] array = WORD.toCharArray();
                int xNew = 0;
                for(int i = 0; i < array.length; i++) {
                    xNew += 10;
                    if (choice.getText().toLowerCase().toCharArray()[0] == array[i]) {
                        Text text1 = new Text(choice.getText().toLowerCase());
                        text1.setX(xNew);
                        displayWord.getChildren().set(i, text1);
                    }
                }
                // this is if you finally get all of the right letters
                if(!displayWord.getChildren().toString().contains("_")) {
                    Text win = new Text("YOU WIN!!!");
                    Text answer = new Text("The word was " + WORD);
                    pane.getChildren().addAll(win, answer);
                    choice.setDisable(true);
                    pane.getChildren().addAll(play, no);
                }
                choice.clear();
            }
            else {
                // this is the display when you guess a wrong letter.
                primaryStage.setTitle("Nope");
                // if you run out of guesses this is the display
                if(badGuess.getChildren().size() < 1) {
                    Text lose = new Text("YOU LOSE!!!");
                    Text answer = new Text("The word was " + WORD);
                    pane.getChildren().addAll(lose, answer);
                    choice.setDisable(true);
                    pane.getChildren().addAll(play, no);
                }
                else {
                    root.getChildren().add(badGuess.getChildren().get(0));
                    pane.getChildren().add(new Text(choice.getText()));
                }
                choice.clear();
            }
        });


        root.getChildren().add(pane);
        pane.getChildren().addAll(text, choice);

        pane.setLayoutX(12);
        pane.setLayoutY(12);


        primaryStage.setTitle("Hangman");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static String findWord() {
        String word = "";
        List<String> words = new ArrayList<>();
        // tests to see if the file exists
        try {
            File file = new File("Dictionary.txt");
            Scanner scanner = new Scanner(file);

            long length = file.length();
            for(int i = 0; i <= length; i++) {
                words.add(scanner.nextLine());
            }
        }
        catch (FileNotFoundException ex) {
            System.out.println("File not Found");
        }

        catch (Exception ex) {
            word += words.get((int)(Math.random() * words.size()));
            System.out.println();
        }


        return word;
    }

}

