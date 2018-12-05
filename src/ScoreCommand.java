class ScoreCommand extends Command {
  private int score;
  private String rank;

  ScoreCommand() {}

  public String execute() {
    this.score = GameState.instance().getScore();
    this.rank = GameState.instance().getRank();

    //return rank + " | " + score + "\n";  //DEBUG score only debugging
    return rank + "\n";
  }

}
