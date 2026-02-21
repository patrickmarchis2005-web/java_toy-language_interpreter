package model.statement;

import model.exception.MyException;
import model.state.ProgramState;
import model.state.TypeEnvironment;
import model.type.SimpleType;
import model.type.Type;

public record VariableDeclarationStatement(String variableName, Type simpleType) implements IStatement {

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        var symbolTable = state.symbolTable();
        if (symbolTable.isDefined(this.variableName)) {
            throw new MyException("Variable " + this.variableName + " is already defined\n");
        }
        symbolTable.declareVariable(this.variableName, this.simpleType);
        return null;
    }

    @Override
    public TypeEnvironment typecheck(TypeEnvironment typeEnv) throws MyException {
        typeEnv.add(variableName, simpleType);
        return typeEnv;
    }

    @Override
    public String toString() {
        return simpleType.toString() + " " + variableName + "=" + simpleType.getDefaultValue().toString();
    }

    @Override
    public String format() {
        return simpleType.toString() + " " + variableName;
    }

    @Override
    public IStatement deepCopy() {
        return new VariableDeclarationStatement(this.variableName, this.simpleType);
    }
}
