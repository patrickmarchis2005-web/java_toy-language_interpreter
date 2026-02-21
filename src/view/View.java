package view;

import model.expression.BinaryOperatorExpression;
import model.expression.ConstantExpression;
import model.expression.VariableExpression;
import model.state.*;
import model.statement.*;
import model.type.SimpleType;
import model.value.BooleanValue;
import model.value.IntegerValue;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;
import java.util.Scanner;

public class View {
    public static void  main(String[] args) {
        ExecutionStack<IStatement> stack = new StackExecutionStack<>();
        SymbolTable<String, Value> symbolTable = new MapSymbolTable<>();
        Out<Value> output = new ListOut<>();
        FileTable<StringValue, BufferedReader> fileTable = new MapFileTable<>();
        Heap heap = new MapHeap();
        BarrierTable barrierTable = new MapBarrierTable();

        IStatement ex1 = new CompoundStatement(
                new VariableDeclarationStatement("v", SimpleType.INTEGER),
                new CompoundStatement(
                        new AssignmentStatement("v", new ConstantExpression(new IntegerValue(2))),
                        new PrintStatement(new VariableExpression("v"))
                )
        );

        IStatement ex2 = new CompoundStatement(
                new VariableDeclarationStatement("a", SimpleType.INTEGER),
                    new CompoundStatement(
                        new VariableDeclarationStatement("b", SimpleType.INTEGER),
                        new CompoundStatement(
                            new AssignmentStatement(
                                "a",
                                new BinaryOperatorExpression("+",
                                        new ConstantExpression(new IntegerValue(2)),
                                        new BinaryOperatorExpression("*",
                                            new ConstantExpression(new IntegerValue(3)),
                                            new ConstantExpression(new IntegerValue(5))
                                        )
                                )
                            ),
                            new CompoundStatement(
                                new AssignmentStatement(
                                    "b",
                                    new BinaryOperatorExpression("+",
                                        new VariableExpression("a"),
                                        new ConstantExpression(new IntegerValue(1))
                                    )
                                ),
                                new PrintStatement(new VariableExpression("b"))
                            )
                        )
                    )
        );

        IStatement ex3 = new CompoundStatement(
                new VariableDeclarationStatement("a", SimpleType.BOOLEAN),
                new CompoundStatement(
                    new VariableDeclarationStatement("v", SimpleType.INTEGER),
                    new CompoundStatement(
                        new AssignmentStatement(
                            "a",
                            new ConstantExpression(new BooleanValue(true))
                        ),
                        new CompoundStatement(
                            new IfStatement(
                                new VariableExpression("a"),
                                new AssignmentStatement("v", new ConstantExpression(new IntegerValue(2))),
                                new AssignmentStatement("v", new ConstantExpression(new IntegerValue(3)))
                            ),
                            new PrintStatement(new VariableExpression("v"))
                        )
                    )
                )
        );

        ProgramState programState = null;

        Scanner input = new Scanner(System.in);
        System.out.println("Input a program:");
        System.out.println("1.");
        System.out.println("2.");
        System.out.println("3.");
        System.out.print("\n>");
        int option = input.nextInt();
        input.nextLine();

        switch (option) {
            case 1:
                programState = new ProgramState(stack, symbolTable, output, fileTable, heap, barrierTable, ex1, 1);
                break;
            case 2:
                programState = new ProgramState(stack, symbolTable, output, fileTable, heap, barrierTable, ex2, 2);
                break;
            case 3:
                programState = new ProgramState(stack, symbolTable, output, fileTable, heap, barrierTable, ex3, 3);
                break;
            default:
                System.out.println("Invalid option");
                input.close();
                return;
        }

        input.close();
//        Repository repository = new SingleProgramRepository();
//        repository.setProgramState(programState);
//        Controller controller = new Controller(repository);
//        controller.allSteps();
    }
}
