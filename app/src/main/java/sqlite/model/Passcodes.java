// K
package sqlite.model;

public class Passcodes {
    public static final String TABLE_NAME = "pascodes";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_QUESTION = "question";
    public static final String COLUMN_ANSWER = "answer";
    public static final String COLUMN_IS_SELECTED = "is_selected";
    public static final String COLUMN_PASSCODE = "passcode";

    private int id;
    private int passcode;
    private String question;
    private String answer;
    private boolean is_selected;

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS" + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_QUESTION + " TEXT,"
                    + COLUMN_ANSWER + " TEXT,"
                    + COLUMN_IS_SELECTED + " BOOLEAN,"
                    + COLUMN_PASSCODE + " INTEGER"
                    + ")";

    public Passcodes() { }

    // method for instantiating unanswered question at app opening
    public Passcodes(int id, String q, String a, boolean s) {
        this.id = id;
        this.question = q;
        this.answer = a;
        this.is_selected = s;
    }

    // getter for question id
    public int getId() {
        return id;
    }

    // getter for passcode
    public int getPasscode() {
        return passcode;
    }

    // setter for passcode
    public void setPasscode(int p) {
        this.passcode = p;
    }

    // method for checking if entered value matches passcode
    public boolean doesPasscodeMatch(int p){
        return (this.passcode == p);
    }

    // getter for question
    public String getQuestion() {
        return question;
    }

    // setter for question (may not be necessary
    public void setQuestion(String q) {
        this.question = q;
    }

    // getter for answer
    public String getAnswer() {
        return answer;
    }

    // setter for answer
    public void setAnswer(String a) {
        this.answer = a;
    }

    // method for checking if entered value matches answer
    public boolean doesAnswerMatch(String a){
        return (this.answer == a);
    }

    // getter for if question is selected
    public boolean getIsSelected() {
        return (this.is_selected == true);
    }

    // setter for if question is selected
    public void setIsSelected(boolean s) {
        is_selected = s;
    }

    // setter for question id (may not be necessary)
    public void setId(int id) {
        this.id = id;
    }
}
