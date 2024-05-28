public class TestWrongLineNumber {
    public static void main(String[] args) {
        String path = System.getenv("Path");
        String text = """
                 text0 \
                 text1 \
                 text2 \
                """;
        System.out.println(path.concat(text));
    }
}