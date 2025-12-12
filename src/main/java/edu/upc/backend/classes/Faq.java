package edu.upc.backend.classes;

public class Faq {
    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    private int id;
    private String question;
    private String answer;

    public Faq() {}
    public Faq(int id, String question, String answer)
    {
        setId(id);
        setQuestion(question);
        setAnswer(answer);
    }
    @Override
    public String toString()
    {
        return String.format("Faq #%d %s : %s",getId(),getQuestion(),getAnswer());
    }
}
