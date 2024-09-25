public class MyService {

    @DryRun
    @Async
    public void myMethod() {
        System.out.println("Original method execution");
    }
}
