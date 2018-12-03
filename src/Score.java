import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
public class Score {
  static int userScore;
  static String[] ranks = {"Apprentice", "Knight", "Earl", "Duke", "Prince", "King", "Emperor"};
  public Score(){

  }
  int getScore(){
    return userScore;
  }
  void addToScore(int ammount){
    userScore = userScore + ammount;
  }
  void addToScore(){
    userScore = userScore + 1;
  }
  static String Win(){
    return "Congratulations! You won! Your finished with a score of" + GameState.instance().getScore() + " and a rank of " + GameState.instance().getRank();
  }
  String Print(){
    return "Your score is " + userScore + "and your rank is" + getRank() + "!";
  }
  String getRank(){
    if(userScore <= 5){
      return ranks[0];
    }
    if(userScore > 5 && userScore <= 10){
      return ranks[1];
    }
    if(userScore > 10 && userScore >= 15){
      return ranks[2];
    }
    if(userScore > 15 && userScore >= 20){
      return ranks[3];
    }
    if(userScore > 25 && userScore >= 30){
      return ranks[4];
    }
    if(userScore > 30 && userScore >= 35){
      return ranks[5];
    }
    if(userScore > 35 && userScore >= 40){
      return ranks[6];
    }
    else{
      return ranks[0];
    }
  }
}
