import commands.ToDoResolver;
import picocli.CommandLine;

public class Application {
    public static void main(String[] args) {
        String[] args1 = new String[]{"list", "todo.csv"};
        String[] args2 = new String[]{"add", "Купить билеты", "todo.csv"};
        String[] args3 = new String[]{"done", "1", "todo.csv"};
        CommandLine cmd = new CommandLine(new ToDoResolver.ToDoResolverParent());
        cmd.setExecutionStrategy(new CommandLine.RunAll());
        cmd.execute(args);

        if (args.length == 0) {
            cmd.usage(System.out);
        }
    }
}
