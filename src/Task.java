public class Task {
    private final String NAME;
    private final String DESCRIPTION;
    private final int ID;
    private Progress progress;

    public Task(String NAME, String DESCRIPTION, int ID, Progress progress) {
        this.NAME = NAME;
        this.DESCRIPTION = DESCRIPTION;
        this.ID = ID;
        this.progress = progress;
    }

    public String getNAME() {
        return NAME;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public int getID() {
        return ID;
    }

    public Progress getProgress() {
        return progress;
    }

    public void print(){
        System.out.println(NAME + "\t" + DESCRIPTION + "\t" + progress);
    }

    public void setProgress(Progress progress) {
        this.progress = progress;
    }
}
