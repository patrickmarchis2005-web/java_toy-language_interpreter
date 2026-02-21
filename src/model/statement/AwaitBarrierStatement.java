package model.statement;

import model.exception.MyException;
import model.state.ProgramState;
import model.state.TypeEnvironment;
import model.type.SimpleType;
import model.value.IntegerValue;

public record AwaitBarrierStatement(String variableName) implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        if (!state.symbolTable().isDefined(variableName)) {
            throw new MyException("The variable " + variableName + " has not been declared");
        }
        if (!state.symbolTable().getValue(variableName).getType().equals(SimpleType.INTEGER)) {
            throw new MyException("The variable " + variableName + " is not of type integer");
        }
        var foundIndex = ((IntegerValue) state.symbolTable().getValue(variableName)).value();

        synchronized (state.barrierTable()) {
            if (!state.barrierTable().lookup(foundIndex)) {
                throw new MyException(foundIndex + " is not an index from the Barrier Table");
            }
            var pair = state.barrierTable().getPair(foundIndex);
            var listLength = pair.getValue().size();
            var maxLength = pair.getKey();

            if (maxLength > listLength) {
                if (pair.getValue().contains(state.id())) {
                    state.executionStack().push(this);
                } else {
                    pair.getValue().add(state.id());
                    state.executionStack().push(this);
                }
            }
        }
        return null;
    }

    @Override
    public TypeEnvironment typecheck(TypeEnvironment typeEnv) throws MyException {
        var variableType = typeEnv.lookup(variableName);
        if (!variableType.equals(SimpleType.INTEGER)) {
            throw new MyException("The variable " + variableName + " is not of type integer");
        }
        return typeEnv;
    }

    @Override
    public IStatement deepCopy() {
        return new AwaitBarrierStatement(variableName);
    }

    @Override
    public String toString() {
        return "await(%s)".formatted(variableName);
    }
}
