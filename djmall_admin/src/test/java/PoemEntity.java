public class PoemEntity {
    /**
     * 古诗
     */
    private String Poem;

    public PoemEntity(String Poem) {
        super();
        this.Poem = Poem;
    }

    @Override
    public String toString() {
        return "古诗:" + Poem;
    }
}
