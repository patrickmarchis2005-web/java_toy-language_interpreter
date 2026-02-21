package model.statement;

import model.exception.MyException;
import model.expression.Expression;
import model.state.ProgramState;
import model.state.TypeEnvironment;
import model.type.SimpleType;
import model.type.Type;
import model.value.BooleanValue;
import model.value.Value;

public record IfStatement(Expression condition, IStatement thenBranch, IStatement elseBranch) implements IStatement {

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        Value result = condition.evaluate(state.symbolTable(), state.heap());
        if (result instanceof BooleanValue booleanValue) {
            if (booleanValue.value()) {
                state.executionStack().push(thenBranch);
            } else {
                state.executionStack().push(elseBranch);
            }
        } else {
            throw new MyException("Condition expression does not evaluate to boolean");
        }
        return null;
    }

    @Override
    public TypeEnvironment typecheck(TypeEnvironment typeEnv) throws MyException {
        Type typeCondition = condition.typecheck(typeEnv);
        if (typeCondition == SimpleType.BOOLEAN) {
            thenBranch.typecheck(typeEnv.deepCopy());
            elseBranch.typecheck(typeEnv.deepCopy());
            return typeEnv;
        } else {
            throw new MyException("The condition of IF has not the type bool");
        }
    }

    @Override
    public IStatement deepCopy() {
        return new IfStatement(condition.deepCopy(), thenBranch.deepCopy(), elseBranch.deepCopy());
    }

    public String toString() {
        return "if (" + condition.toString() + ") then (" + thenBranch.toString() + ") else (" + elseBranch.toString() + ")";
    }
}
