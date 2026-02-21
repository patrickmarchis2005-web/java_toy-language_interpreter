package model.statement;

import model.exception.MyException;
import model.expression.Expression;
import model.state.ProgramState;
import model.state.TypeEnvironment;
import model.type.Type;
import model.value.Value;

public record AssignmentStatement(String variableName, Expression expression) implements IStatement {

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        var symbolTable = state.symbolTable();
        var heap = state.heap();
        if (!symbolTable.isDefined(this.variableName)) {
            throw new MyException("The variable " + variableName + " was not defined before");
        }
        Value value = expression.evaluate(symbolTable, heap);
        if (!value.getType().equals(symbolTable.getValue(variableName).getType())) {
            throw new MyException("Type mismatch between variable " + variableName + " and assigned expression");
        }
        symbolTable.update(variableName, value);
        return null;
    }

    @Override
    public TypeEnvironment typecheck(TypeEnvironment typeEnv) throws MyException {
        Type typeVariable = typeEnv.lookup(variableName);
        Type typeExpression = expression.typecheck(typeEnv);
        if (typeVariable == typeExpression) {
            return typeEnv;
        } else {
            throw new MyException("Assignment: right hand side and left hand side have different types");
        }
    }

    @Override
    public IStatement deepCopy() {
        return new AssignmentStatement(this.variableName, this.expression.deepCopy());
    }

    public String toString() {
        return variableName + " = " + expression.toString();
    }
}
