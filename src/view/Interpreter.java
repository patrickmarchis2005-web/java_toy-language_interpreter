package view;

import controller.Controller;
import model.exception.MyException;
import model.expression.BinaryOperatorExpression;
import model.expression.ConstantExpression;
import model.expression.HeapReadingExpression;
import model.expression.VariableExpression;
import model.state.*;
import model.statement.*;
import model.type.RefType;
import model.type.SimpleType;
import model.value.BooleanValue;
import model.value.IntegerValue;
import model.value.StringValue;
import model.value.Value;
import repository.Repository;
import repository.ProgramsRepository;
import view.command.ExitCommand;
import view.command.RunExample;

import java.io.BufferedReader;

public class Interpreter {
    public static void main(String[] args) {
        IStatement ex1 = new CompoundStatement(
                new VariableDeclarationStatement("v", SimpleType.INTEGER), new CompoundStatement(
                new AssignmentStatement("v", new ConstantExpression(new IntegerValue(2))),
                new PrintStatement(new VariableExpression("v"))));
        try {
            ex1.typecheck(new MapTypeEnvironment());
        } catch (MyException e) {
            System.out.println(ex1.toString());
            System.out.println(e.getMessage());
            return;
        }
        ProgramState program1 = new ProgramState(new StackExecutionStack<IStatement>(), new MapSymbolTable<String, Value>(),
                new ListOut<Value>(), new MapFileTable<StringValue, BufferedReader>(), new MapHeap(), new MapBarrierTable(), ex1, 0);
        Repository repository1 = new ProgramsRepository(program1, "log1.txt");
        Controller controller1 = new Controller(repository1);


        IStatement ex2 = new CompoundStatement(
                new VariableDeclarationStatement("a", SimpleType.INTEGER), new CompoundStatement(
                new VariableDeclarationStatement("b", SimpleType.INTEGER), new CompoundStatement(
                new AssignmentStatement(
                        "a",
                        new BinaryOperatorExpression("+",
                                new ConstantExpression(new IntegerValue(2)),
                                new BinaryOperatorExpression("*",
                                        new ConstantExpression(new IntegerValue(3)),
                                        new ConstantExpression(new IntegerValue(5))))), new CompoundStatement(
                new AssignmentStatement(
                        "b",
                        new BinaryOperatorExpression("+",
                                new VariableExpression("a"),
                                new ConstantExpression(new IntegerValue(1)))),
                new PrintStatement(new VariableExpression("b"))))));
        try {
            ex2.typecheck(new MapTypeEnvironment());
        } catch (MyException e) {
            System.out.println(ex2.toString());
            System.out.println(e.getMessage());
            return;
        }
        ProgramState program2 = new ProgramState(new StackExecutionStack<IStatement>(), new MapSymbolTable<String, Value>(),
                new ListOut<Value>(), new MapFileTable<StringValue, BufferedReader>(), new MapHeap(), new MapBarrierTable(), ex2, 0);
        Repository repository2 = new ProgramsRepository(program2, "log2.txt");
        Controller controller2 = new Controller(repository2);


        IStatement ex3 = new CompoundStatement(
                new VariableDeclarationStatement("a", SimpleType.BOOLEAN), new CompoundStatement(
                new VariableDeclarationStatement("v", SimpleType.INTEGER), new CompoundStatement(
                new AssignmentStatement(
                        "a",
                        new ConstantExpression(new BooleanValue(true))), new CompoundStatement(
                new IfStatement(
                        new VariableExpression("a"),
                        new AssignmentStatement("v", new ConstantExpression(new IntegerValue(2))),
                        new AssignmentStatement("v", new ConstantExpression(new IntegerValue(3)))),
                new PrintStatement(new VariableExpression("v"))))));
        try {
            ex3.typecheck(new MapTypeEnvironment());
        } catch (MyException e) {
            System.out.println(ex3.toString());
            System.out.println(e.getMessage());
            return;
        }
        ProgramState program3 = new ProgramState(new StackExecutionStack<IStatement>(), new MapSymbolTable<String, Value>(),
                new ListOut<Value>(), new MapFileTable<StringValue, BufferedReader>(), new MapHeap(), new MapBarrierTable(), ex3, 0);
        Repository repository3 = new ProgramsRepository(program3, "log3.txt");
        Controller controller3 = new Controller(repository3);


        IStatement ex4 = new CompoundStatement(
                new VariableDeclarationStatement("varf", SimpleType.STRING), new CompoundStatement(
                new AssignmentStatement("varf", new ConstantExpression(new StringValue("test.in"))), new CompoundStatement(
                new OpenRFileStatement(new VariableExpression("varf")), new CompoundStatement(
                new VariableDeclarationStatement("varc", SimpleType.INTEGER), new CompoundStatement(
                new ReadFileStatement(new VariableExpression("varf"), "varc"), new CompoundStatement(
                new PrintStatement(new VariableExpression("varc")), new CompoundStatement(
                new ReadFileStatement(new VariableExpression("varf"), "varc"), new CompoundStatement(
                new PrintStatement(new VariableExpression("varc")),
                new CloseRFileStatement(new VariableExpression("varf"))))))))));
        try {
            ex4.typecheck(new MapTypeEnvironment());
        } catch (MyException e) {
            System.out.println(ex4.toString());
            System.out.println(e.getMessage());
            return;
        }
        ProgramState program4 = new ProgramState(new StackExecutionStack<IStatement>(), new MapSymbolTable<String, Value>(),
                new ListOut<Value>(), new MapFileTable<StringValue, BufferedReader>(), new MapHeap(), new MapBarrierTable(), ex4, 0);
        Repository repository4 = new ProgramsRepository(program4, "log4.txt");
        Controller controller4 = new Controller(repository4);


        IStatement ex5 = new CompoundStatement(
                new VariableDeclarationStatement("v", new RefType(SimpleType.INTEGER)), new CompoundStatement(
                new HeapAllocationStatement("v", new ConstantExpression(new IntegerValue(20))), new CompoundStatement(
                new VariableDeclarationStatement("a", new RefType(new RefType(SimpleType.INTEGER))), new CompoundStatement(
                new HeapAllocationStatement("a", new VariableExpression("v")), new CompoundStatement(
                new PrintStatement(new VariableExpression("v")),
                new PrintStatement(new VariableExpression("a")))))));
        try {
            ex5.typecheck(new MapTypeEnvironment());
        } catch (MyException e) {
            System.out.println(ex5.toString());
            System.out.println(e.getMessage());
            return;
        }
        ProgramState program5 = new ProgramState(new StackExecutionStack<IStatement>(), new MapSymbolTable<String, Value>(),
                new ListOut<Value>(), new MapFileTable<StringValue, BufferedReader>(), new MapHeap(), new MapBarrierTable(), ex5, 0);
        Repository repository5 = new ProgramsRepository(program5, "log5.txt");
        Controller controller5 = new Controller(repository5);


        IStatement ex6 = new CompoundStatement(
                new VariableDeclarationStatement("v", new RefType(SimpleType.INTEGER)), new CompoundStatement(
                new HeapAllocationStatement("v", new ConstantExpression(new IntegerValue(20))), new CompoundStatement(
                new VariableDeclarationStatement("a", new RefType(new RefType(SimpleType.INTEGER))), new CompoundStatement(
                new HeapAllocationStatement("a", new VariableExpression("v")), new CompoundStatement(
                new PrintStatement(new HeapReadingExpression(new VariableExpression("v"))),
                new PrintStatement(new BinaryOperatorExpression("+",
                new HeapReadingExpression(new HeapReadingExpression(new VariableExpression("a"))),
                new ConstantExpression(new IntegerValue(5)))))))));
        try {
            ex6.typecheck(new MapTypeEnvironment());
        } catch (MyException e) {
            System.out.println(ex6.toString());
            System.out.println(e.getMessage());
            return;
        }
        ProgramState program6 = new ProgramState(new StackExecutionStack<IStatement>(), new MapSymbolTable<String, Value>(),
                new ListOut<Value>(), new MapFileTable<StringValue, BufferedReader>(), new MapHeap(), new MapBarrierTable(), ex6, 0);
        Repository repository6 = new ProgramsRepository(program6, "log6.txt");
        Controller controller6 = new Controller(repository6);


        IStatement ex7 = new CompoundStatement(
                new VariableDeclarationStatement("v", new RefType(SimpleType.INTEGER)), new CompoundStatement(
                new HeapAllocationStatement("v", new ConstantExpression(new IntegerValue(20))), new CompoundStatement(
                new PrintStatement(new HeapReadingExpression(new VariableExpression("v"))), new CompoundStatement(
                new HeapWritingStatement("v", new ConstantExpression(new IntegerValue(30))),
                new PrintStatement(
                new BinaryOperatorExpression("+",
                new HeapReadingExpression(new VariableExpression("v")),
                new ConstantExpression(new IntegerValue(5))))))));
        try {
            ex7.typecheck(new MapTypeEnvironment());
        } catch (MyException e) {
            System.out.println(ex7.toString());
            System.out.println(e.getMessage());
            return;
        }
        ProgramState program7 = new ProgramState(new StackExecutionStack<IStatement>(), new MapSymbolTable<String, Value>(),
                new ListOut<Value>(), new MapFileTable<StringValue, BufferedReader>(), new MapHeap(), new MapBarrierTable(), ex7, 0);
        Repository repository7 = new ProgramsRepository(program7, "log7.txt");
        Controller controller7 = new Controller(repository7);


        IStatement ex8 = new CompoundStatement(
                new VariableDeclarationStatement("v", new RefType(SimpleType.INTEGER)), new CompoundStatement(
                new HeapAllocationStatement("v", new ConstantExpression(new IntegerValue(20))), new CompoundStatement(
                new VariableDeclarationStatement("a", new RefType(new RefType(SimpleType.INTEGER))), new CompoundStatement(
                new HeapAllocationStatement("a", new VariableExpression("v")), new CompoundStatement(
                new HeapAllocationStatement("v", new ConstantExpression(new IntegerValue(30))),
                new PrintStatement(new HeapReadingExpression(new HeapReadingExpression(new VariableExpression("a")))))))));
        try {
            ex8.typecheck(new MapTypeEnvironment());
        } catch (MyException e) {
            System.out.println(ex8.toString());
            System.out.println(e.getMessage());
            return;
        }
        ProgramState program8 = new ProgramState(new StackExecutionStack<IStatement>(), new MapSymbolTable<String, Value>(),
                new ListOut<Value>(), new MapFileTable<StringValue, BufferedReader>(), new MapHeap(), new MapBarrierTable(), ex8, 0);
        Repository repository8 = new ProgramsRepository(program8, "log8.txt");
        Controller controller8 = new Controller(repository8);


        IStatement ex9 = new CompoundStatement(
                new VariableDeclarationStatement("v", SimpleType.INTEGER), new CompoundStatement(
                new AssignmentStatement("v", new ConstantExpression(new IntegerValue(4))), new CompoundStatement(
                new WhileStatement(new BinaryOperatorExpression(">", new VariableExpression("v"), new ConstantExpression(new IntegerValue(0))), new CompoundStatement(
                        new PrintStatement(new VariableExpression("v")),
                        new AssignmentStatement("v", new BinaryOperatorExpression("-", new VariableExpression("v"), new ConstantExpression(new IntegerValue(1)))))),
                new PrintStatement(new VariableExpression("v")))));
        try {
            ex9.typecheck(new MapTypeEnvironment());
        } catch (MyException e) {
            System.out.println(ex9.toString());
            System.out.println(e.getMessage());
            return;
        }
        ProgramState program9 = new ProgramState(new StackExecutionStack<IStatement>(), new MapSymbolTable<String, Value>(),
                new ListOut<Value>(), new MapFileTable<StringValue, BufferedReader>(), new MapHeap(), new MapBarrierTable(), ex9, 0);
        Repository repository9 = new ProgramsRepository(program9, "log9.txt");
        Controller controller9 = new Controller(repository9);


        IStatement ex10 = new CompoundStatement(
                new VariableDeclarationStatement("v", SimpleType.INTEGER), new CompoundStatement(
                new VariableDeclarationStatement("a", new RefType(SimpleType.INTEGER)), new CompoundStatement(
                new AssignmentStatement("v", new ConstantExpression(new IntegerValue(10))), new CompoundStatement(
                new HeapAllocationStatement("a", new ConstantExpression(new IntegerValue(22))), new CompoundStatement(
                new ForkStatement(new CompoundStatement(
                        new HeapWritingStatement("a", new ConstantExpression(new IntegerValue(30))), new CompoundStatement(
                        new AssignmentStatement("v", new ConstantExpression(new IntegerValue(32))), new CompoundStatement(
                        new PrintStatement(new VariableExpression("v")),
                        new PrintStatement(new HeapReadingExpression(new VariableExpression("a"))))))), new CompoundStatement(
                new PrintStatement(new VariableExpression("v")),
                new PrintStatement(new HeapReadingExpression(new VariableExpression("a")))))))));
        try {
            ex10.typecheck(new MapTypeEnvironment());
        } catch (MyException e) {
            System.out.println(ex10.toString());
            System.out.println(e.getMessage());
            return;
        }
        ProgramState program10 = new ProgramState(new StackExecutionStack<IStatement>(), new MapSymbolTable<String, Value>(),
                new ListOut<Value>(), new MapFileTable<StringValue, BufferedReader>(), new MapHeap(), new MapBarrierTable(), ex10, 0);
        Repository repository10 = new ProgramsRepository(program10, "log10.txt");
        Controller controller10 = new Controller(repository10);


        IStatement ex11 = new CompoundStatement(
                new VariableDeclarationStatement("v", SimpleType.INTEGER), new CompoundStatement(
                new VariableDeclarationStatement("x", SimpleType.INTEGER), new CompoundStatement(
                new AssignmentStatement("v", new ConstantExpression(new IntegerValue(3))), new CompoundStatement(
                new AssignmentStatement("x", new ConstantExpression(new IntegerValue(2))), new CompoundStatement(
                new RepeatAsStatement(new CompoundStatement(
                        new ForkStatement(new CompoundStatement(
                                new PrintStatement(new VariableExpression("v")), new CompoundStatement(
                                new AssignmentStatement("x", new BinaryOperatorExpression("-",
                                        new VariableExpression("x"), new ConstantExpression(new IntegerValue(1)))),
                                new PrintStatement(new VariableExpression("x"))))),
                        new AssignmentStatement("v", new BinaryOperatorExpression("+",
                                new VariableExpression("v"), new ConstantExpression(new IntegerValue(1))))),
                        new BinaryOperatorExpression("==",
                                new VariableExpression("v"), new ConstantExpression(new IntegerValue(0)))), new CompoundStatement(
                new NoOperationStatement(), new CompoundStatement(
                new NoOperationStatement(), new CompoundStatement(
                new NoOperationStatement(), new CompoundStatement(
                new NoOperationStatement(), new CompoundStatement(
                new NoOperationStatement(), new CompoundStatement(
                new NoOperationStatement(), new CompoundStatement(
                new NoOperationStatement(),
                new PrintStatement(new VariableExpression("x"))))))))))))));
        try {
            ex11.typecheck(new MapTypeEnvironment());
        } catch (MyException e) {
            System.out.println(ex11);
            System.out.println(e.getMessage());
            return;
        }
        ProgramState program11 = new ProgramState(new StackExecutionStack<IStatement>(), new MapSymbolTable<String, Value>(),
                new ListOut<Value>(), new MapFileTable<StringValue, BufferedReader>(), new MapHeap(), new MapBarrierTable(), ex11, 0);
        Repository repository11 = new ProgramsRepository(program11, "log11.txt");
        Controller controller11 = new Controller(repository11);


        IStatement ex12 = new CompoundStatement(
                new VariableDeclarationStatement("v1", new RefType(SimpleType.INTEGER)), new CompoundStatement(
                new VariableDeclarationStatement("v2", new RefType(SimpleType.INTEGER)), new CompoundStatement(
                new VariableDeclarationStatement("v3", new RefType(SimpleType.INTEGER)), new CompoundStatement(
                new VariableDeclarationStatement("id", SimpleType.INTEGER), new CompoundStatement(
                new AssignmentStatement("id", new ConstantExpression(new IntegerValue(5))), new CompoundStatement(
                new HeapAllocationStatement("v1", new ConstantExpression(new IntegerValue(2))), new CompoundStatement(
                new HeapAllocationStatement("v2", new ConstantExpression(new IntegerValue(3))), new CompoundStatement(
                new HeapAllocationStatement("v3", new ConstantExpression(new IntegerValue(4))), new CompoundStatement(
                new NewBarrierStatement("id", new HeapReadingExpression(new VariableExpression("v2"))), new CompoundStatement(
                new ForkStatement(new CompoundStatement(
                        new AwaitBarrierStatement("id"), new CompoundStatement(
                        new HeapWritingStatement("v1", new BinaryOperatorExpression("*",
                                new HeapReadingExpression(new VariableExpression("v1")),
                                new ConstantExpression(new IntegerValue(10)))), new CompoundStatement(
                        new NoOperationStatement(), new CompoundStatement(
                        new NoOperationStatement(),
                        new PrintStatement(new HeapReadingExpression(new VariableExpression("v1")))))))), new CompoundStatement(
                new ForkStatement(new CompoundStatement(
                        new AwaitBarrierStatement("id"), new CompoundStatement(
                        new HeapWritingStatement("v2", new BinaryOperatorExpression("*",
                                new HeapReadingExpression(new VariableExpression("v2")),
                                new ConstantExpression(new IntegerValue(10)))), new CompoundStatement(
                        new NoOperationStatement(), new CompoundStatement(
                        new NoOperationStatement(), new CompoundStatement(
                        new NoOperationStatement(), new CompoundStatement(
                        new HeapWritingStatement("v2", new BinaryOperatorExpression("*",
                                new HeapReadingExpression(new VariableExpression("v2")),
                                new ConstantExpression(new IntegerValue(10)))), new CompoundStatement(
                        new HeapWritingStatement("v2", new BinaryOperatorExpression("*",
                                new HeapReadingExpression(new VariableExpression("v2")),
                                new ConstantExpression(new IntegerValue(10)))),
                        new PrintStatement(new HeapReadingExpression(new VariableExpression("v2"))))))))))), new CompoundStatement(
                new ForkStatement(new CompoundStatement(
                        new AwaitBarrierStatement("id"),
                        new PrintStatement(new BinaryOperatorExpression("*",
                                new HeapReadingExpression(new VariableExpression("v3")),
                                new ConstantExpression(new IntegerValue(40)))))),
                new PrintStatement(new HeapReadingExpression(new VariableExpression("v3")))))))))))))));
        try {
            ex12.typecheck(new MapTypeEnvironment());
        } catch (MyException e) {
            System.out.println(ex12);
            System.out.println(e.getMessage());
            return;
        }
        ProgramState program12 = new ProgramState(new StackExecutionStack<IStatement>(), new MapSymbolTable<String, Value>(),
                new ListOut<Value>(), new MapFileTable<StringValue, BufferedReader>(), new MapHeap(), new MapBarrierTable(), ex12, 0);
        Repository repository12 = new ProgramsRepository(program12, "log12.txt");
        Controller controller12 = new Controller(repository12);


        TextMenu textMenu = new TextMenu();
        textMenu.addCommand(new ExitCommand("00", "exit"));
        textMenu.addCommand(new RunExample("01", ex1.toString(), controller1));
        textMenu.addCommand(new RunExample("02", ex2.toString(), controller2));
        textMenu.addCommand(new RunExample("03", ex3.toString(), controller3));
        textMenu.addCommand(new RunExample("04", ex4.toString(), controller4));
        textMenu.addCommand(new RunExample("05", ex5.toString(), controller5));
        textMenu.addCommand(new RunExample("06", ex6.toString(), controller6));
        textMenu.addCommand(new RunExample("07", ex7.toString(), controller7));
        textMenu.addCommand(new RunExample("08", ex8.toString(), controller8));
        textMenu.addCommand(new RunExample("09", ex9.toString(), controller9));
        textMenu.addCommand(new RunExample("10", ex10.toString(), controller10));
        textMenu.addCommand(new RunExample("11", ex11.toString(), controller11));
        textMenu.addCommand(new RunExample("12", ex12.toString(), controller12));
        textMenu.show();
    }
}
