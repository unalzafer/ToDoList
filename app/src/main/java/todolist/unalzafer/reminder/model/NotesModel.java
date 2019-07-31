package todolist.unalzafer.reminder.model;

public class NotesModel {

    private String idNote, title,text,color;
    private Object date;
    private Object updateDate;
    private Object selectDate;
    private String state;

    public NotesModel() {
    }

    public NotesModel(String title, String text, String color, Object date) {

        this.title = title;
        this.text = text;
        this.color = color;
        this.date=date;
    }

    public String getIdNote() {
        return idNote;
    }

    public void setIdNote(String idNote) {
        this.idNote = idNote;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Object getDate() {
        return date;
    }

    public void setDate(Object date) {
        this.date = date;
    }

    public Object getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Object updateDate) {
        this.updateDate = updateDate;
    }

    public Object getSelectDate() {
        return selectDate;
    }

    public void setSelectDate(Object selectDate) {
        this.selectDate = selectDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
