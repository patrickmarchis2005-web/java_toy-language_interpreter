package model.expression;

import model.exception.MyException;
import model.state.Heap;
import model.state.SymbolTable;
import model.state.TypeEnvironment;
import model.type.Type;
import model.value.Value;

public interface Expression {
    Value evaluate(SymbolTable symTable, Heap heap);
    Expression deepCopy();
    Type typecheck(TypeEnvironment typeEnv) throws MyException;
}
